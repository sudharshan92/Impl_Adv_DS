import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

// Program to implement the algorithm for finding minimum spanning trees in directed graphs
/**
 * @author sudharshan Narasimhan Vasudevan
 *
 */
public class MSTDirectedGraph {
	public static int V;
	public static int E;
	public static int s;
	public static GraphNode[] adjList;
	static int DFSNodeCount = 0;
	static int xcount = 1000001;
	public static GraphNode DfsStopNode;
	//public static int runcount = 0;
	public static int MSTValue=0;
	public MSTDirectedGraph() {
	}

	public boolean loadGraph() {

		Scanner scanner = new Scanner(System.in);
		this.V = scanner.nextInt(); //number of vertices
		this.E = scanner.nextInt(); // nuber of edges
		this.s = scanner.nextInt(); // source vertex
		adjList = new GraphNode[V + 1];
		// initializing adjList array
		for (int i = 1; i <= V; i++) {
			adjList[i] = new GraphNode(i);
		}
		while (scanner.hasNext()) {
			int source = scanner.nextInt();
			int destination = scanner.nextInt();
			int edgeweight = scanner.nextInt();
			if (destination != s) {
				addEdge(source, destination, edgeweight);
			}

		}
		return true;
	}

	/**
	 * create an edge between source and the destination vertex with weight w
	 * @param u - source vertex
	 * @param v - destination vertex
	 * @param w - weight of the edge
	 */
	void addEdge(int u, int v, int w) {
		// directed graph.
		Edge temp = new Edge(adjList[u], adjList[v], w);
		adjList[u].add(temp);
		// adjList[v].add(new Edge(u, w)); // for undirected
		adjList[v].addParent(adjList[u]);
		adjList[v].addEdgeToHeap(temp);

	}

	/**
	 * print the graph
	 */
	public static void printGraph() {
		// print the graph
		System.out.println("Vertices: " + V + " Edges " + E);
		for (int i = 1; i <= V; i++) {
			LinkedList<Edge> list = adjList[i].edges;
			System.out.println(i + " with degree " + adjList[i].outdegree
					+ " --> " + list.toString());

		}
	}

	public void printEdgesParents() {

		for (int i = 1; i <= V; i++) {
			// System.out.println(adjList[i].parentNodes);

			/*
			 * System.out.println("min incoming edge of " + i + " - " +
			 * adjList[i].q.element());
			 */

			 MSTValue = MSTValue + adjList[i].findMinElement(s,MSTValue);

		}
	}

	public static void main(String[] args) {
		MSTDirectedGraph graphAdjList = new MSTDirectedGraph();
		graphAdjList.loadGraph();
		graphAdjList.printEdgesParents();
		graphAdjList.DFS(adjList[s]);
		graphAdjList.printGraph();
		//graphAdjList.DFS(adjList[s]);
		//graphAdjList.printEdgesParents();
		//graphAdjList.DFS(adjList[s]);
		// graphAdjList.printGraph();
	}

	// DFS algorithm from cormen book.
	/**
	 * @param S - source vertex from where the DFS should start
	 */
	public static void DFS(GraphNode S) {
		// System.out.println("S is "+S);
		//printGraph();
		//System.out.println("I am here");
		DFSVisit(S);
		if (DFSNodeCount == V) {
			// pending
			System.out.println("Success! MST found");
		} else {
			// there is a zero weight cycle
			// System.out.println("I am here DFS node count = " + DFSNodeCount);

			findZeroWeightCycle();

		}

		/*
		 * for (int i = 1; i <= V; i++) { if (adjList[i].color == 0)
		 * DFSVisit(adjList[i]); }
		 */
	}

	/**
	 * @param graphNode - starting node for DFS
	 * Function performs the DFS of the graph on zero weighted edges
	 */
	public static void DFSVisit(GraphNode graphNode) {
		graphNode.color = 1;
		DFSNodeCount = DFSNodeCount + 1;
		// System.out.println("Node is"+ graphNode);
		//System.out.println("count is "+DFSNodeCount);

		DfsStopNode = graphNode;
		LinkedList<Edge> temp = graphNode.edges;
		// System.out.println(temp);
		for (Edge edge : temp) {
			if (edge.w == 0 && edge.liveedge == 1) {
				// System.out.println("I am here");
				if (edge.v.color == 0) {
					edge.v.parent = graphNode;
					DFSVisit(edge.v);
				}
			}

			// graphNode.color = 2;
		}

		/*
		 * while(node!=null){ Edge edge = (Edge)node.value; if(edge.v.color ==
		 * 0){ edge.v.parent = graphNode; DFSVisit(edge.v); } node = node.next;
		 * }
		 */
		graphNode.color = 2;
	}

	/**
	 * Function finds a cycle of zero weight edges
	 */
	public static void findZeroWeightCycle() {
		GraphNode z = new GraphNode();
		for (int i = 1; i <= V; i++) {
			if (adjList[i].color == 0 && adjList[i].livenode == 1) {
				z = adjList[i];
				// System.out.println("Z node is" +z);
				break;
			}
		}
		// System.out.println("Z node is " + z );
		ArrayList<Edge> BacktrackEdges = new ArrayList<Edge>();
		// all edges bactracked starting from random unreachable node

		ArrayList<GraphNode> cycleVertices = new ArrayList<GraphNode>();
		ArrayList<GraphNode> BackTrackVertices = new ArrayList<GraphNode>();
		// if required create a arraylist of vertices
		

		do {
			BackTrackVertices.add(z);
			Edge etemp = z.q.element();
			if (etemp.liveedge == 1) {
				BacktrackEdges.add(etemp);
				z = adjList[etemp.u.v];
			} else {
				Queue<Edge> queue = new PriorityQueue<Edge>();
				// = new Iterator<Edge>();
				Iterator<Edge> it = z.q.iterator();
				while (it.hasNext()) {
					queue.add(it.next());

				}
				Edge[] arr = queue.toArray(new Edge[queue.size()]);
				Arrays.sort(arr);
				for (Edge ei : arr) {
					if (ei.liveedge == 1 && (ei.u.v <= 1000000)) {
						etemp = ei;
						BacktrackEdges.add(etemp);
						z = adjList[etemp.u.v];
						break;
					}
				}
			}
			
		} while(!BackTrackVertices.contains(z));
		// vertex that is repeating is z
		// therefore z is the start of the cycle
		
		int position = 0;
		for(Edge pos: BacktrackEdges)
		{
			if(pos.v == z)
			{
				position = BacktrackEdges.indexOf(pos);
			}
		}
		//while (!BacktrackEdges.contains(z.q.element()));
		//System.out.println(pos);
		//System.out.println("Backtrack" + BacktrackEdges);
		//int position = BacktrackEdges.indexOf(pos);
	
		// System.out.println("Position is "+position+" Z is " + z);
		ArrayList<Edge> cycleEdges = new ArrayList<Edge>();// consists of all
															// edges in the zero
															// weight cycle

		for (int x = 0; x < (BacktrackEdges.size() - position); x++) {
			cycleEdges.add(BacktrackEdges.get(x + position));
		}
		//System.out.println("Cycle edges " + cycleEdges);
		for (int x = 0; x < cycleEdges.size(); x++) {
			cycleVertices.add(cycleEdges.get(x).u);
		}
		//System.out.println("vertices in cycle are " + cycleVertices);
		shrinkCycle(cycleEdges, cycleVertices); // creates node X and the
												// incoming
		// and outgoing edges corresponding
		// to the cycle

		// System.out.println("cycle is " + cycleEdges);

	}

	/**
	 * function shrinks the zero weight cycle into single node X
	 * @param cycleEdges - arraylist of edges in the zero weight cycle
	 * @param cycleVertices - arraylist of vertices in the zero weight cycle
	 */
	public static void shrinkCycle(ArrayList<Edge> cycleEdges,
			ArrayList<GraphNode> cycleVertices) {

		GraphNode X = new GraphNode(xcount);
		//System.out.println("X name"+ X.v +"X's queue" + X.q + "X's edges" + X.edges);
		xcount = xcount + 1;
		HashMap<GraphNode, Edge> IncomingcycleMin = new HashMap<GraphNode, Edge>();
		// contains source nodes of edges coming into the cycle as key and
		// the corresponding edge(which is the minimum) as value
		HashMap<GraphNode, Edge> OutgoingcycleMin = new HashMap<GraphNode, Edge>();
		// contains destination nodes of edges going out of the cycle as key and
		// the corresponding edge(which is the minimum) as value
		//System.out.println("cyc"+cycleEdges);
		for (int i = 0; i < cycleEdges.size(); i++) {
			// System.out.println("Cycle edges size is "+cycleEdges.size());
			cycleEdges.get(i).u.livenode = 0;

			// handle incoming edges to the cycle
			//System.out.println("Vertex is "+ cycleEdges.get(i).u + "incoming edges " + cycleEdges.get(i).u.q);
			for (Edge e : cycleEdges.get(i).u.q) {
				// System.out.println("current source is " +
				// cycleEdges.get(i).u);
				
				if (!cycleEdges.contains(e) && !cycleVertices.contains(e.u) && (e.u.livenode == 1) && (e.liveedge == 1)) {

					if (!IncomingcycleMin.containsKey(e.u)) {

						IncomingcycleMin.put(e.u, e);// adding entry<source
														// node(nodes not in
														// cycle from whom edges
														// originate) and
														// edge> to hashmap
						Edge temp = new Edge(e.u, X, e.w); // creating edge
															// between u and X
						// System.out.println("X incoming edges" + temp +
						// "dead status " + temp.liveedge);
						e.u.add(temp);
						// adjList[v].add(new Edge(u, w)); // for undirected
						X.addParent(e.u);
						X.addEdgeToHeap(temp);
						// remove edges that are not in the cycle permanently

					} else {
						Edge temp = IncomingcycleMin.get(e.u);
						if (temp.w <= e.w) {
							// do nothing
						} else {
							// temp.w = e.w;
							IncomingcycleMin.put(e.u, e);
							// replace with the minimum weight edge

						}
					}
				}
				e.liveedge = 0;// making the incoming edges dead
			}
		}
		//System.out.println("after adding incoming "+"X name"+ X.v +"X's queue" + X.q + "X's edges" + X.edges);
			// handle outgoing edges from the cycle
		for (int i = 0; i < cycleEdges.size(); i++) {
			
			for (Edge e : cycleEdges.get(i).u.edges) {
				
				if (!cycleEdges.contains(e) && !cycleVertices.contains(e.v) && (e.v.livenode == 1) && (e.liveedge == 1)) {

					if (!OutgoingcycleMin.containsKey(e.v)) {

						OutgoingcycleMin.put(e.v, e);// adding entry<destination
														// node(nodes not in
														// cycle to whom edges
														// terminate) and
														// edge> to hashmap

						Edge temp = new Edge(X, e.v, e.w); // creating edge
															// between X and v
						// System.out.println("edges outgoing X are "+ temp);
						X.add(temp);
						// adjList[v].add(new Edge(u, w)); // for undirected
						e.v.addParent(X);
						e.v.addEdgeToHeap(temp);
						// remove edges that are not in the cycle permanently

					} else {
						Edge temp = OutgoingcycleMin.get(e.v);
						if (temp.w <= e.w) {
							// do nothing
						} else {
							// temp.w = e.w;
							OutgoingcycleMin.put(e.v, e);

						}
					}
				}
				e.liveedge = 0;// making the outgoing edges dead
			}
			//System.out.println("after adding outgoing "+"X name"+ X.v +"X's queue" + X.q + "X's edges" + X.edges);
		}
		//printGraph();

		// finding the MST of new shrunken graph
		for (int itr = 1; itr <= V; itr++) {

			if(adjList[itr].livenode == 1)
			{
				MSTValue = MSTValue + adjList[itr].findMinElement(s,MSTValue);
			}
			

		}
		// printGraph();
		MSTValue = MSTValue + X.findMinElement(s,MSTValue);
		// System.out.println("X value is" + X.v);
		// System.out.println("incoming edges of x are" + X.q);
		// System.out.println("X problem is " + X.edges);
		//printGraph();
		//runcount = runcount +1;
		//if(runcount<=2)
		ExpandCycle(cycleEdges, X, IncomingcycleMin, OutgoingcycleMin);
		// System.out.println("Incoming Cycle min" + IncomingcycleMin);
		// System.out.println("outgoing Cycle min" + OutgoingcycleMin);

	}

	/**
	 * Expands the node X back into the cycle
	 * @param cycleEdges - arralist of edges in the cycle
	 * @param X - shrunken node X
	 * @param IncomingcycleMin - hashmap of edges coming into the cycle
	 * @param OutgoingcycleMin - hashmap of edges going out of the cycle
	 */
	public static void ExpandCycle(ArrayList<Edge> cycleEdges, GraphNode X,
			HashMap<GraphNode, Edge> IncomingcycleMin,
			HashMap<GraphNode, Edge> OutgoingcycleMin) {
		for (int itr = 0; itr < cycleEdges.size(); itr++) {
			cycleEdges.get(itr).u.livenode = 1; // making live the nodes in the
												// cycle
			cycleEdges.get(itr).liveedge = 1; // making live the edges in the
												// cycle

			
		}
		// handling incoming edges to the cycle while expanding
		for (Edge ed : IncomingcycleMin.values())
		// ed is edges present in hashmap (before shrinking)
		{
			// System.out.println("current edge is" + ed);
			ed.liveedge = 1;
			for (Edge enew : ed.u.edges)
			// edges that are connected to X after shrinking but before
			// expanding
			{
				if (enew.v.equals(X)) {
					// System.out.println("enew.v is " + enew.v);
					// System.out.println("enew weight "+ enew.w);
					int tempweight = enew.w;
					ed.w = tempweight;
					// replacing edge's weight with the weight obtained after
					// shrinking
					if (enew.w == 0) {
						// System.out.println("zero weight edge is " + ed);
						for (int itr = 0; itr < cycleEdges.size(); itr++) {
							if (cycleEdges.get(itr).v.equals(ed.v)) {
								// System.out.println("edge to be removed is "+
								// cycleEdges.get(itr).u);
								cycleEdges.get(itr).liveedge = 0;
							}
						}
					}
					enew.liveedge = 0;
				}
			}
		}
		// printGraph();
		// handling outgoing edges from the cycle while expanding
		for (Edge ed : OutgoingcycleMin.values()) {
			ed.liveedge = 1;
			for (Edge enew : X.edges) {
				if (enew.v.equals(ed.v)) {
					int tempweight = enew.w;
					ed.w = tempweight;
					enew.liveedge = 0;
				}
			}
		}
		// printGraph();
		X.livenode = 0;
		//System.out.println("X edges"+ X.edges);
		//System.out.println("X queue" + X.q);
		// System.out.println("x outgoing "+X.edges);
		// System.out.println("x incoming "+X.q);
		//printGraph();
		DFSNodeCount = DFSNodeCount - 1;
		//System.out.println(DfsStopNode);
		DFS(DfsStopNode);
	}
}

class Edge implements Comparable<Edge>{
	GraphNode u; // from node
	GraphNode v; // to node
	int w; // weight
	int liveedge; // denotes whether the edge is present or removed

	// present when 1 else 0

	public Edge(GraphNode u, GraphNode v, int w) {
		super();
		this.u = u;
		this.v = v;
		this.w = w;
		this.liveedge = 1;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (u == null) {
			if (other.u != null)
				return false;
		} else if (!u.equals(other.u))
			return false;
		if (v == null) {
			if (other.v != null)
				return false;
		} else if (!v.equals(other.v))
			return false;
		if (w != other.w)
			return false;
		return true;
	}

	public String toString() {
		return "source is " + u + "Destination is " + v + " with weight-" + w
				+ "status is " + liveedge;
	}

	@Override
	public int compareTo(Edge arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}

class GraphNode {
	//public static int MSTValue=0;

	int v; // node name
	LinkedList<Edge> edges;
	int outdegree;
	int indegree;
	PriorityQueue<Edge> q;
	public int color;
	public GraphNode parent;
	public ArrayList<GraphNode> parentNodes;
	public int livenode;// denotes whether the node is present or removed
	// present when 1 else 0

	Comparator<Edge> edgeComparator = new Comparator<Edge>() {

		@Override
		public int compare(Edge arg0, Edge arg1) {
			if (arg0.w < arg1.w) {
				return -1;
			} else if (arg0.w > arg1.w) {
				return 1;
			} else
				return 0;
		}
	};

	GraphNode() {

	}

	GraphNode(int v) {
		this.v = v;
		this.edges = new LinkedList<Edge>();
		this.parentNodes = new ArrayList<GraphNode>();
		this.outdegree = 0;
		this.indegree = 0;
		this.q = new PriorityQueue<Edge>(1, edgeComparator);
		this.livenode = 1;

	}

	public void add(Edge edge) {
		this.edges.add(edge);
		outdegree++;
	}

	public void addParent(GraphNode parent) {

		this.parentNodes.add(parent);
		indegree++;
	}

	public void addEdgeToHeap(Edge e) {
		this.q.add(e);
		/*
		 * System.out.println("Source " + e.u + "Dest " + e.v + "queue " +
		 * this.q.element());
		 */

	}

	public int findMinElement(int source,int MSTValue) {
		// to handle live nodes only
		if (this.v != source) {
			//if (this.livenode == 1) {
			//	System.out.println("i am in if loop" + this.v);
			if(!this.q.isEmpty())
			{
				int min = this.q.element().w;
				MSTValue = MSTValue+min;
				//	System.out.println("edge is " + min);
					// System.out.println("min is " + min);
					for (Edge e : this.q) {
						if (e.liveedge == 1) {
						//	System.out.println("Edges are " + e);
							e.w = e.w - min;
							//MSTValue+=e.w;
						}

					}
			}
				
			//System.out.println("MST value is "+MSTValue);

			//}

		}
		return MSTValue;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GraphNode other = (GraphNode) obj;
		if (v != other.v)
			return false;
		return true;
	}

	public String toString() {
		return "node " + v + "node status " + livenode;
	}
}
