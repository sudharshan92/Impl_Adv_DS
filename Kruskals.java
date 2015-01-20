import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Node {
	private int u;
	private int parent;
	private int ranking;

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public int getU() {
		return u;
	}

	public void setU(int u) {
		this.u = u;
	}

}

class Edge implements Comparable<Edge> {

	private int u;
	private int v;
	private int weight;

	public Edge(int u, int v, int weight) {
		super();
		this.u = u;
		this.v = v;
		this.weight = weight;
	}

	public int getU() {
		return u;
	}

	public void setU(int u) {
		this.u = u;
	}

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public int compareTo(Edge arg0) {
		if (this.weight < arg0.getWeight()) {
			return -1;
		} else if (this.weight > arg0.getWeight()) {
			return 1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "( " + this.u + " , " + this.v + " )";
	}

}
/**
 * @author sudharshan Narasimhan Vasudevan
 *
 */
public class Kruskals {
	static Map<Integer, Node> nodes = new HashMap<Integer, Node>();
	static List<Edge> edges = new ArrayList<Edge>();
	static List<Edge> resultantEdges = new ArrayList<Edge>();
	static Comparator<Edge> weightComparator = new Comparator<Edge>() {
		
		@Override
		public int compare(Edge e1, Edge e2) {
			if (e1.getWeight() < e2.getWeight()) {
				return -1;
			} else if (e1.getWeight() > e2.getWeight()) {
				return 1;
			} else {
				return 0;
			}
		}

	};

	/**
	 * creates a node with the given id u and add it to the map
	 * @param u  -  id of the node
	 */
	public static void makeSet(int u) {
		Node node = new Node();
		node.setParent(u);
		node.setRanking(0);
		node.setU(u);
		nodes.put(u, node);
	}
	/**
	 * method finds and returns the parent node of the given node
	 * @param node - node whose parent needs to be found
	 * @return the parent of the current node
	 */
	public static int find(Node node) {
		if (node.getU() != node.getParent()) {
			node.setParent(find(nodes.get(node.getParent())));
		}
		return node.getParent();

	}
	/**
	 * the methods unions two nodes if the edges between them does not form a cycle
	 * @param u - input node u
	 * @param v - input node v
	 */
	public static void union(Node u, Node v) {
		if (u.getRanking() > v.getRanking()) {
			v.setParent(u.getU());
		} else if (u.getParent() < v.getParent()) {
			u.setParent(v.getU());
		} else {
			v.setParent(u.getU());
			int rank = u.getRanking();
			u.setRanking(rank++);
		}
	}
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int path = 0;
		int no_of_vertex = in.nextInt();
		int no_of_edges = in.nextInt();
		Integer[] inputs = new Integer[3];
		int k = 0;
		long startTime = System.currentTimeMillis();
		while (k < no_of_edges) {
			if (in.hasNext()) {
			
				for (int i = 0; i < 3; i++) {
					inputs[i] = in.nextInt();
				}
				if (!nodes.containsKey(inputs[0])) {
					makeSet(inputs[0]);
				}
				if (!nodes.containsKey(inputs[1])) {
					makeSet(inputs[1]);
				}
				Edge edge = new Edge(inputs[0], inputs[1], inputs[2]);
				edges.add(edge);
				k++;
			}
		}
		Collections.sort(edges); // sort the edges in non decreasing order
		Iterator<Edge> iterator = edges.iterator(); // create an iterator for the edges
		while (iterator.hasNext()) {
			Edge edge = iterator.next();
			int u = find(nodes.get(edge.getU()));// find parent of u
			int v = find(nodes.get(edge.getV()));// find parent of v
			if (u != v) {
				resultantEdges.add(edge);
				path += edge.getWeight();
				union(nodes.get(u), nodes.get(v)); // union the two nodes
			}
		}
		iterator = resultantEdges.iterator();
		System.out.println(path);
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		long endTime = System.currentTimeMillis(); // end time of algorithm
		long totalRunningTime = endTime - startTime;  // Total running time in milliseconds
		System.out.println("Running time : "
				+ totalRunningTime+ " msec");
	}
}
