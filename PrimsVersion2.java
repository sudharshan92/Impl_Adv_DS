import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

final class UndirectedGraph<T> implements Iterable<T> {
	private final Map<T, Map<T, Double>> mGraph = new HashMap<T, Map<T, Double>>();

	public boolean addNode(T node) {
		if (mGraph.containsKey(node))
			return false;

		mGraph.put(node, new HashMap<T, Double>());
		return true;
	}

	/**
	 * Given two nodes and a length, adds an arc of that length between those
	 * nodes. If the arc already existed, the length is updated to the specified
	 * value. If either endpoint does not exist in the graph, throws a
	 * NoSuchElementException.
	 *
	 * @param one
	 *            The first node.
	 * @param two
	 *            The second node.
	 * @param length
	 *            The length of the edge.
	 * @throws NoSuchElementException
	 *             If either the start or destination nodes do not exist.
	 */
	public void addEdge(T one, T two, double length) {
		if (!mGraph.containsKey(one) || !mGraph.containsKey(two))
			throw new NoSuchElementException("Both nodes must be in the graph.");

		mGraph.get(one).put(two, length);
		mGraph.get(two).put(one, length);
	}

	/**
	 * Removes the edge between the indicated endpoints from the graph. If the
	 * edge does not exist, this operation is a no-op. If either endpoint does
	 * not exist, this throws a NoSuchElementException.
	 *
	 * @param one
	 *            The start node.
	 * @param two
	 *            The destination node.
	 * @throws NoSuchElementException
	 *             If either node is not in the graph.
	 */
	public void removeEdge(T one, T two) {
		if (!mGraph.containsKey(one) || !mGraph.containsKey(two))
			throw new NoSuchElementException("Both nodes must be in the graph.");

		mGraph.get(one).remove(two);
		mGraph.get(two).remove(one);
	}

	/**
	 * Given two endpoints, returns the cost of the edge between them. If either
	 * endpoint does not exist in the graph, or if the edge is not contained in
	 * the graph, this throws a NoSuchElementException.
	 *
	 * @param one
	 *            The first endpoint.
	 * @param two
	 *            The second endpoint.
	 * @return The cost of the edge between the endpoints.
	 * @throws NoSuchElementException
	 *             If the edge is not found or the endpoints are not nodes in
	 *             the graph.
	 */
	public double edgeCost(T one, T two) {
		if (!mGraph.containsKey(one) || !mGraph.containsKey(two))
			throw new NoSuchElementException("Both nodes must be in the graph.");

		Double result = mGraph.get(one).get(two);

		if (result == null)
			throw new NoSuchElementException(
					"Edge does not exist in the graph.");

		return result;
	}

	/**
	 * Given a node in the graph, returns an immutable view of the edges leaving
	 * that node, as a map from endpoints to costs.
	 *
	 * @param node
	 *            The node whose edges should be queried.
	 * @return An immutable view of the edges leaving that node.
	 * @throws NoSuchElementException
	 *             If the node does not exist.
	 */
	public Map<T, Double> edgesFrom(T node) {

		Map<T, Double> arcs = mGraph.get(node);
		if (arcs == null)
			throw new NoSuchElementException("Source node does not exist.");

		return Collections.unmodifiableMap(arcs);
	}

	/**
	 * Returns whether a given node is contained in the graph.
	 *
	 * @param The
	 *            node to test for inclusion.
	 * @return Whether that node is contained in the graph.
	 */
	public boolean containsNode(T node) {
		return mGraph.containsKey(node);
	}

	/**
	 * Returns an iterator that can traverse the nodes in the graph.
	 *
	 * @return An iterator that traverses the nodes in the graph.
	 */
	public Iterator<T> iterator() {
		return mGraph.keySet().iterator();
	}

	/**
	 * Returns the number of nodes in the graph.
	 *
	 * @return The number of nodes in the graph.
	 */
	public int size() {
		return mGraph.size();
	}

	/**
	 * Returns whether the graph is empty.
	 *
	 * @return Whether the graph is empty.
	 */
	public boolean isEmpty() {
		return mGraph.isEmpty();
	}
}

final class PrimEva {

	public static int array[][];
	public static double minimumCost = 0;

	public static <T> UndirectedGraph<T> mst(UndirectedGraph<T> graph) {
		FibonacciHeap<T> pq = new FibonacciHeap<T>();

		Map<T, FibonacciHeap.Entry<T>> entries = new HashMap<T, FibonacciHeap.Entry<T>>();

		UndirectedGraph<T> result = new UndirectedGraph<T>();

		if (graph.isEmpty())
			return result;

		T startNode = graph.iterator().next();

		result.addNode(startNode);

		addOutgoingEdges(startNode, graph, pq, result, entries);

		for (int i = 0; i < graph.size() - 1; ++i) {
			T toAdd = pq.dequeueMin().getValue();
			T endpoint = minCostEndpoint(toAdd, graph, result);

			minimumCost = minimumCost + graph.edgeCost(endpoint, toAdd);
			result.addNode(toAdd);
			result.addEdge(toAdd, endpoint, graph.edgeCost(toAdd, endpoint));
			addOutgoingEdges(toAdd, graph, pq, result, entries);
		}
		System.out.println(minimumCost);
		return result;
	}

	/**
	 * Given a node in the source graph and a set of nodes that we've visited so
	 * far, returns the minimum-cost edge from that node to some node that has
	 * been visited before.
	 *
	 * @param node
	 *            The node that has not been considered yet.
	 * @param graph
	 *            The original graph whose MST is being computed.
	 * @param result
	 *            The resulting graph, used to check what has been visited so
	 *            far.
	 */
	public static <T> T minCostEndpoint(T node, UndirectedGraph<T> graph,
			UndirectedGraph<T> result) {
		T endpoint = null;
		double leastCost = Double.POSITIVE_INFINITY;

		for (Map.Entry<T, Double> entry : graph.edgesFrom(node).entrySet()) {
			if (!result.containsNode(entry.getKey()))
				continue;

			if (entry.getValue() >= leastCost)
				continue;

			endpoint = entry.getKey();
			leastCost = entry.getValue();

		}

		return endpoint;
	}

	public static <T> int minCostPath(T node, UndirectedGraph<T> graph) {
		T endpoint = null;
		double leastCost = Double.POSITIVE_INFINITY;
		for (Map.Entry<T, Double> entry : graph.edgesFrom(node).entrySet()) {
			T vertex = entry.getKey();
			Double vertexValue = entry.getValue();
			System.out.println(entry.getClass());

		}

		return 0;
	}

	/**
	 * Given a node in the graph, updates the priorities of adjacent nodes to
	 * take these edges into account. Due to some optimizations we make, this
	 * step takes in several parameters beyond what might seem initially
	 * required.
	 *
	 * @param node
	 *            The node to explore outward from.
	 * @param graph
	 *            The graph whose MST is being computed
	 * @param pq
	 *            The Fibonacci heap holding each endpoint.
	 * @param result
	 *            The result graph.
	 * @param entries
	 *            A map from nodes to their corresponding heap entries.
	 */
	private static <T> void addOutgoingEdges(T node, UndirectedGraph<T> graph,
			FibonacciHeap<T> pq, UndirectedGraph<T> result,
			Map<T, FibonacciHeap.Entry<T>> entries) {
		for (Map.Entry<T, Double> arc : graph.edgesFrom(node).entrySet()) {
			if (result.containsNode(arc.getKey()))
				continue;

			if (!entries.containsKey(arc.getKey())) {
				entries.put(arc.getKey(),
						pq.enqueue(arc.getKey(), arc.getValue()));
			} else if (entries.get(arc.getKey()).getPriority() > arc.getValue()) {
				pq.decreaseKey(entries.get(arc.getKey()), arc.getValue());
			}
		}
	}

	public static void callprim(int v) {
		int d[], p[], visited[];
		visited = new int[v + 1];
		d = new int[v + 1];
		p = new int[v + 1];
		for (int i = 1; i <= v; i++)
			p[i] = visited[i] = 0;
		for (int i = 1; i <= v; i++)
			d[i] = 999999999;

		int c, current, mincost = 0;
		current = 1;
		visited[current] = 1;
		d[current] = 0;
		c = 1;
		for (int i = 1; i <= v; i++)
			mincost += d[i];
		System.out.println("minimum cost= " + mincost);
	}

};

final class FibonacciHeap<T> {
	public static final class Entry<T> {
		private int mDegree = 0;
		private boolean mIsMarked = false;

		private Entry<T> mNext;
		private Entry<T> mPrev;

		private Entry<T> mParent;
		private Entry<T> mChild;
		private T mElem;
		private double mPriority;

		public T getValue() {
			return mElem;
		}

		public void setValue(T value) {
			mElem = value;
		}

		public double getPriority() {
			return mPriority;
		}

		/**
		 * Constructs a new Entry that holds the given element with the
		 * indicated priority.
		 *
		 * @param elem
		 *            The element stored in this node.
		 * @param priority
		 *            The priority of this element.
		 */
		private Entry(T elem, double priority) {
			mNext = mPrev = this;
			mElem = elem;
			mPriority = priority;
		}
	}

	private Entry<T> mMin = null;

	private int mSize = 0;

	/**
	 * Inserts the specified element into the Fibonacci heap with the specified
	 * priority. Its priority must be a valid double, so you cannot set the
	 * priority to NaN.
	 *
	 * @param value
	 *            The value to insert.
	 * @param priority
	 *            Its priority, which must be valid.
	 * @return An Entry representing that element in the tree.
	 */
	public Entry<T> enqueue(T value, double priority) {
		checkPriority(priority);

		Entry<T> result = new Entry<T>(value, priority);

		mMin = mergeLists(mMin, result);

		++mSize;

		return result;
	}

	/**
	 * Returns an Entry object corresponding to the minimum element of the
	 * Fibonacci heap, throwing a NoSuchElementException if the heap is empty.
	 *
	 * @return The smallest element of the heap.
	 * @throws NoSuchElementException
	 *             If the heap is empty.
	 */
	public Entry<T> min() {
		if (isEmpty())
			throw new NoSuchElementException("Heap is empty.");
		return mMin;
	}

	public boolean isEmpty() {
		return mMin == null;
	}

	public int size() {
		return mSize;
	}

	/**
	 * Given two Fibonacci heaps, returns a new Fibonacci heap that contains all
	 * of the elements of the two heaps.
	 *
	 * @param one
	 *            The first Fibonacci heap to merge.
	 * @param two
	 *            The second Fibonacci heap to merge.
	 * @return A new FibonacciHeap containing all of the elements of both heaps.
	 */
	public static <T> FibonacciHeap<T> merge(FibonacciHeap<T> one,
			FibonacciHeap<T> two) {
		FibonacciHeap<T> result = new FibonacciHeap<T>();

		result.mMin = mergeLists(one.mMin, two.mMin);

		result.mSize = one.mSize + two.mSize;

		one.mSize = two.mSize = 0;
		one.mMin = null;
		two.mMin = null;

		return result;
	}

	/**
	 * Dequeues and returns the minimum element of the Fibonacci heap. If the
	 * heap is empty, this throws a NoSuchElementException.
	 *
	 * @return The smallest element of the Fibonacci heap.
	 * @throws NoSuchElementException
	 *             If the heap is empty.
	 */
	public Entry<T> dequeueMin() {
		if (isEmpty())
			throw new NoSuchElementException("Heap is empty.");

		--mSize;

		Entry<T> minElem = mMin;

		if (mMin.mNext == mMin) {
			mMin = null;
		} else {
			mMin.mPrev.mNext = mMin.mNext;
			mMin.mNext.mPrev = mMin.mPrev;
			mMin = mMin.mNext;
		}

		if (minElem.mChild != null) {
			Entry<?> curr = minElem.mChild;
			do {
				curr.mParent = null;
				curr = curr.mNext;
			} while (curr != minElem.mChild);
		}

		mMin = mergeLists(mMin, minElem.mChild);

		if (mMin == null)
			return minElem;

		List<Entry<T>> treeTable = new ArrayList<Entry<T>>();

		List<Entry<T>> toVisit = new ArrayList<Entry<T>>();

		for (Entry<T> curr = mMin; toVisit.isEmpty() || toVisit.get(0) != curr; curr = curr.mNext)
			toVisit.add(curr);

		for (Entry<T> curr : toVisit) {
			while (true) {
				while (curr.mDegree >= treeTable.size())
					treeTable.add(null);

				if (treeTable.get(curr.mDegree) == null) {
					treeTable.set(curr.mDegree, curr);
					break;
				}

				Entry<T> other = treeTable.get(curr.mDegree);
				treeTable.set(curr.mDegree, null); // Clear the slot

				Entry<T> min = (other.mPriority < curr.mPriority) ? other
						: curr;
				Entry<T> max = (other.mPriority < curr.mPriority) ? curr
						: other;

				max.mNext.mPrev = max.mPrev;
				max.mPrev.mNext = max.mNext;

				max.mNext = max.mPrev = max;
				min.mChild = mergeLists(min.mChild, max);

				max.mParent = min;

				max.mIsMarked = false;

				++min.mDegree;

				curr = min;
			}

			if (curr.mPriority <= mMin.mPriority)
				mMin = curr;
		}
		return minElem;
	}

	/**
	 * Decreases the key of the specified element to the new priority. If the
	 * new priority is greater than the old priority, this function throws an
	 * IllegalArgumentException.
	 *
	 * @param entry
	 *            The element whose priority should be decreased.
	 * @param newPriority
	 *            The new priority to associate with this entry.
	 * @throws IllegalArgumentException
	 *             If the new priority exceeds the old priority, or if the
	 *             argument is not a finite double.
	 */
	public void decreaseKey(Entry<T> entry, double newPriority) {
		checkPriority(newPriority);
		if (newPriority > entry.mPriority)
			throw new IllegalArgumentException("New priority exceeds old.");

		/* Forward this to a helper function. */
		decreaseKeyUnchecked(entry, newPriority);
	}

	public void delete(Entry<T> entry) {
		decreaseKeyUnchecked(entry, Double.NEGATIVE_INFINITY);

		dequeueMin();
	}

	/**
	 * Utility function which, given a user-specified priority, checks whether
	 * it's a valid double and throws an IllegalArgumentException otherwise.
	 *
	 * @param priority
	 *            The user's specified priority.
	 * @throws IllegalArgumentException
	 *             If it is not valid.
	 */
	private void checkPriority(double priority) {
		if (Double.isNaN(priority))
			throw new IllegalArgumentException(priority + " is invalid.");
	}

	/**
	 * Utility function which, given two pointers into disjoint circularly-
	 * linked lists, merges the two lists together into one circularly-linked
	 * list.
	 * 
	 * @param one
	 *            A pointer into one of the two linked lists.
	 * @param two
	 *            A pointer into the other of the two linked lists.
	 * @return A pointer to the smallest element of the resulting list.
	 */
	private static <T> Entry<T> mergeLists(Entry<T> one, Entry<T> two) {
		if (one == null && two == null) {
			return null;
		} else if (one != null && two == null) {
			return one;
		} else if (one == null && two != null) {
			return two;
		} else {
			Entry<T> oneNext = one.mNext;
			one.mNext = two.mNext;
			one.mNext.mPrev = one;
			two.mNext = oneNext;
			two.mNext.mPrev = two;

			return one.mPriority < two.mPriority ? one : two;
		}
	}

	/**
	 * Decreases the key of a node in the tree without doing any checking to
	 * ensure that the new priority is valid.
	 *
	 * @param entry
	 *            The node whose key should be decreased.
	 * @param priority
	 *            The node's new priority.
	 */
	private void decreaseKeyUnchecked(Entry<T> entry, double priority) {
		entry.mPriority = priority;

		if (entry.mParent != null && entry.mPriority <= entry.mParent.mPriority)
			cutNode(entry);

		if (entry.mPriority <= mMin.mPriority)
			mMin = entry;
	}

	private void cutNode(Entry<T> entry) {
		entry.mIsMarked = false;

		if (entry.mParent == null)
			return;

		if (entry.mNext != entry) {
			entry.mNext.mPrev = entry.mPrev;
			entry.mPrev.mNext = entry.mNext;
		}

		if (entry.mParent.mChild == entry) {
			if (entry.mNext != entry) {
				entry.mParent.mChild = entry.mNext;
			} else {
				entry.mParent.mChild = null;
			}
		}

		--entry.mParent.mDegree;

		entry.mPrev = entry.mNext = entry;
		mMin = mergeLists(mMin, entry);

		if (entry.mParent.mIsMarked)
			cutNode(entry.mParent);
		else
			entry.mParent.mIsMarked = true;

		entry.mParent = null;
	}
}

/**
 * @author Tejashri Naikar
 *
 */
public class PrimsVersion2 {
	@SuppressWarnings("unchecked")
	public static void main(String Str[]) throws FileNotFoundException {
		PrimEva p = new PrimEva();
		UndirectedGraph<Integer> ug = new UndirectedGraph<Integer>();
		UndirectedGraph<Integer> ug2 = new UndirectedGraph<Integer>();
		int v = 0, e = 0;
		int v1, v2, weight;

		Scanner s = new Scanner(System.in);
		int count = 1;
		while (s.hasNextLine()) {
			
			String partSplit = s.nextLine();
			if(partSplit.contains("break"))
			{
				break;
			}
			String[] parts = partSplit.split(" ");
			if (count == 1) {
				String part1 = parts[0].trim();
				v = Integer.parseInt(part1);
				for (int i = 1; i <= v; i++)
					ug.addNode(i);
				String part2 = parts[1].trim();
				e = Integer.parseInt(part2);
				count++;
				continue;
			} else {
				parts = partSplit.split("\t");
				String part1 = parts[0].trim();
				v1 = Integer.parseInt(part1);
				String part2 = parts[1].trim();
				v2 = Integer.parseInt(part2);
				String part3 = parts[2].trim();
				weight = Integer.parseInt(part3);
				ug.addEdge(v1, v2, weight);
			}
		}

		long startTime = System.currentTimeMillis();
		ug2 = p.mst(ug);
		long endTime = System.currentTimeMillis();
		long totalRunningTime = endTime - startTime;
		System.out.println("\nTotal running time in MilliSecs : "
				+ totalRunningTime);

	}
}
