
import java.io.*;
import java.util.*;

public class BipartiteGraph {
	/**
	 * 
	 * Implementation for finding maximum matches in a bipartite graph.
	 * 		   The element classes are from RbkBFS.java
	 */

	static VertexQueue Q;

	/**
	 * Class to supports Queue operations on Vertex
	 * 
	 */
	class VertexQueue {
		public int size, head, tail, maxN; // queue parameters
		Vertex[] item; // array of Vertex objects

		/**
		 * Constructor for VertexQueue
		 * 
		 * @param n
		 *            : maximum size of the queue
		 */
		VertexQueue(int n) {
			item = new Vertex[n];
			maxN = n;
		}

		/**
		 * Method to clear the queue
		 */
		void clear() {
			size = 0;
			head = 0;
			tail = -1;
		}

		/**
		 * Method to add a vertex to the tail of the queue
		 * 
		 * @param x
		 *            : Vertex
		 */
		void enqueue(Vertex x) {
			item[++tail] = x;
			size++;
		}

		/**
		 * Method to remove a vertex from the head of the queue
		 * 
		 * @return the Vertex reference that has been removed from the queue
		 */
		Vertex dequeue() {
			size--;
			return item[head++];
		}

		/**
		 * Method to check if the queue is empty
		 * 
		 * @return true if the queue is empty
		 */
		boolean isEmpty() {
			return size == 0;
		}

	}

	/**
	 * Class that represents an arc in a Graph
	 *
	 */
	class Edge {
		public Vertex From; // head vertex
		public Vertex To; // tail vertex
		public int Weight;// weight of the arc

		/**
		 * Constructor for Edge
		 * 
		 * @param u
		 *            : Vertex - The head of the arc
		 * @param v
		 *            : Vertex - The tail of the arc
		 * @param w
		 *            : int - The weight associated with the arc
		 */
		Edge(Vertex u, Vertex v, int w) {
			From = u;
			To = v;
			Weight = w;
		}

		/**
		 * Method to find the other end end of the arc given a vertex reference
		 * 
		 * @param u
		 *            : Vertex
		 * @return
		 */
		public Vertex otherEnd(Vertex u) {
			// if the vertex u is the head of the arc, then return the tail
			// else return the tail
			if (From == u)
				return To;
			else
				return From;
		}

		/**
		 * Method to represent the edge in the form (x,y) where x is the head of
		 * the arc and y is the tail of the arc
		 */
		public String toString() {
			return "(" + From + "," + To + ")";
		}
	}

	/**
	 * Class to represent a vertex of a graph
	 * 
	 *
	 */
	class Vertex {
		public int name; // name of the vertex
		public boolean seen; // flag to check if the vertex has already been
		// visited
		public Vertex parent; // parent of the vertex
		public int distance; // distance to the vertex from the source vertex
		public LinkedList<Edge> Adj; // adjacency list
		public Vertex mate;

		/**
		 * Constructor for the vertex
		 * 
		 * @param n
		 *            : int - name of the vertex
		 */
		Vertex(int n) {
			name = n;
			seen = false;
			parent = null;
			Adj = new LinkedList<Edge>();
			mate = null;
		}

		/**
		 * Method to represent a vertex by its name
		 */
		public String toString() {
			return Integer.toString(name);
		}
	}

	/**
	 * Class to represent a graph
	 * 
	 *
	 */
	class Graph implements Iterable<Vertex> {
		public Vertex[] V; // array of vertices
		public int N; // number of verices in the graph
		public Edge[] edges;
		public int count;

		/**
		 * Constructor for Graph
		 * 
		 * @param size
		 *            : int - number of vertices
		 */
		Graph(int size, int edgesSize) {
			N = size;
			V = new Vertex[size + 1];
			edges = new Edge[edgesSize];
			count = 0;
			// create an array of Vertex objects
			for (int i = 1; i <= size; i++)
				V[i] = new Vertex(i);
		}

		/**
		 * Method to add an arc to the graph
		 * 
		 * @param a
		 *            : int - the head of the arc
		 * @param b
		 *            : int - the tail of the arc
		 * @param weight
		 *            : int - the weight of the arc
		 */
		void addEdge(int a, int b, int weight, int i) {
			Edge e = new Edge(V[a], V[b], weight);
			V[a].Adj.add(e);
			V[b].Adj.add(e);
			edges[i] = e;
		}

		/**
		 * Method to create an instance of VertexIterator
		 */
		public Iterator<Vertex> iterator() {
			return new VertexIterator<Vertex>(V, N);
		}

		/**
		 * A Custom Iterator Class for iterating through the vertices in a graph
		 * 
		 *
		 * @param <Vertex>
		 */
		private class VertexIterator<Vertex> implements Iterator<Vertex> {
			private int nodeIndex = 0;
			private Vertex[] iterV;// array of vertices to iterate through
			private int iterN; // size of the array

			/**
			 * Constructor for VertexIterator
			 * 
			 * @param v
			 *            : Array of vertices
			 * @param n
			 *            : int - Size of the graph
			 */
			private VertexIterator(Vertex[] v, int n) {
				nodeIndex = 0;
				iterV = v;
				iterN = n;
			}

			/**
			 * Method to check if there is any vertex left in the iteration
			 * Overrides the default hasNext() method of Iterator Class
			 */
			public boolean hasNext() {
				return nodeIndex != iterN;
			}

			/**
			 * Method to return the next Vertex object in the iteration
			 * Overrides the default next() method of Iterator Class
			 */
			public Vertex next() {
				nodeIndex++;
				return iterV[nodeIndex];
			}

			/**
			 * Throws an error if a vertex is attempted to be removed
			 */
			public void remove() {
				throw new UnsupportedOperationException();
			}
		}

	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
		BipartiteGraph x = new BipartiteGraph();

		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}

		// read the graph related parameters
		int n = in.nextInt(); // number of vertices in the graph
		int m = in.nextInt(); // number of edges in the graph

		// initialize the static queue instance
		Q = x.new VertexQueue(n);

		// create a graph instance
		Graph g = x.new Graph(n, m);
		for (int i = 0; i < m; i++) {
			int u = in.nextInt();
			int v = in.nextInt();
			int w = in.nextInt();
			g.addEdge(u, v, w , i);
		}
		in.close();
		Long beg = System.currentTimeMillis();
		x.driver(g);
		x.maximumMatching(g);
		boolean found = false;
		while(!found){
			found = x.augmentedPath(g);
		}
		Long end = System.currentTimeMillis();
		System.out.println(g.count+" "+ (end-beg));
		if(g.V.length <= 100){
			for(Vertex u : g){
				if(u.mate != null){
					System.out.println(u.name +" " + u.mate);
				}else {
					System.out.println(u.name + " -");
				}
			}
		}
	}

	/**
	 * Driver method to check whether or not the graph is a bipartite and print
	 * the graph
	 * 
	 * @param in
	 */
	void driver(Graph g) {

		boolean isBipartite = true;
		initialize(g);
		// Run BFS on every component
		for (Vertex src : g) {
			if (!src.seen) {
				src.distance = 0;
				isBipartite &= isBipartite(g, src);
			}
		}

		// prints the detailed output if the graph is bipartite
		if (!isBipartite) {
			System.out.println("Graph is not bipartite");
		} else {
//			System.out.println("Graph is bipartite");
			printResult(g);
		}
	}

	/**
	 * Method to initialize a graph 1) Sets the parent of every vertex as null
	 * 2) Sets the seen attribute of every vertex as false 3) Sets the distance
	 * of every vertex as infinite
	 * 
	 * @param g
	 *            : Graph - The reference to the graph
	 */
	void initialize(Graph g) {
		g.count = 0;
		for (Vertex u : g) {
			u.seen = false;
			u.parent = null;
			u.distance = Integer.MAX_VALUE;
			u.mate = null;
		}
	}

	/**
	 * Method to print the graph
	 * 
	 * @param g
	 *            : Graph - The reference to the graph
	 */
	void printResult(Graph g) {
		for (Vertex u : g) {
			if (u.distance % 2 == 0) {
//				System.out.println(u + " Outer");
			} else {
//				System.out.println(u + " Inner");
			}
		}
	}

	/**
	 * Method to perform BFS on the graph to check if the graph is bipartite or
	 * not
	 * 
	 * @param g
	 *            : Graph - The reference to the graph
	 * @param src
	 *            : Vertex - The reference to the source vertex of the component
	 * @return true if the graph is bipartite
	 */
	boolean isBipartite(Graph g, Vertex src) {
		boolean isBipartite = true;

		Q.clear();
		// add the source vertex to the queue
		Q.enqueue(src);
		// mark the source as visited
		src.seen = true;

		// Perform BFS
		while (!Q.isEmpty()) {
			// remove a vertex from the head of the queue
			Vertex u = Q.dequeue();
			// iterate through the u's adjacency list
			for (Edge e : u.Adj) {
				Vertex v = e.otherEnd(u);
				/*
				 * if the vertex v is not visited then mark v as visited and
				 * update v's distance and parent and then add v to the queue
				 */
				if (!v.seen) {
					v.seen = true;
					v.parent = u;
					v.distance = u.distance + 1;
					Q.enqueue(v);
				} else {
					/*
					 * if the ends of edge (u,v), vertices u and v, are at the
					 * same distance from the source, the graph is not bipartite
					 */
					if (u.distance == v.distance)
						isBipartite = false;
				}
			}
		}
		return isBipartite;
	}
	
	
	/**
	 * This method initially finds all the possible matches and then assign 
	 * the mates to the vertices.
	 * @param g 
	 *            : Graph - The reference to the graph
	 */
	public void maximumMatching(Graph g){
		for(Edge e : g.edges){
			Vertex u = e.From;
			Vertex v = e.To;
			
			if(u.mate == null && v.mate == null){
				u.mate = v;
				v.mate = u;
				g.count++;
			}
		}
	}
	
	
	/**
	 * In this method we find if there are any augmented paths, if we have augmented 
	 * paths means if modify the matching and increase the matching by one. This 
	 * method initially checks for all the vertices without mate and which are outer 
	 * nodes and puts them into a queue. Then we go through this queue elements to 
	 * find an augmented path. For every element in the queue we go through the adjacency 
	 * list, for each edge if there exists other end vertex without mate then we have 
	 * found augmented path and we call processAughPath(). Else we find the mate of this 
	 * vertex and put it in the queue.
	 * 
	 * @param g  
	 *             : Graph - The reference to the graph
	 * @return 
	 * 			   : a boolean which is true if all matches are found
	 */
	public boolean augmentedPath(Graph g){
		boolean matchesFound = false;
		Q.clear();
		for(Vertex u: g){
			u.seen =  false;
			u.parent = null;
			//check for all the outer vertices in the graph which do not have a mate
			//put then in the queue to check for augmente path
			if(u.mate==null && u.distance % 2 == 0){
				u.seen = true;
				Q.enqueue(u);
			}
		}
		
		//Go through the queue elements if any of them will end up in 
		//an augmented path
		while(!Q.isEmpty()){
			Vertex u = Q.dequeue();
			for(Edge e : u.Adj){
				Vertex v = e.otherEnd(u);
				if(!v.seen){
					v.parent = u;
					v.seen = true;
					//if mate is null it means augmented path exists hence process it
					if(v.mate == null){
						processAugPath(v);
						g.count++;
						return matchesFound;
					} else {
						
						//if match exists then queue its mate 
						Vertex x = v.mate;
						x.seen = true;
						x.parent = v;
						Q.enqueue(x);
					}
				}
			}
		}
		matchesFound = true;
		return matchesFound;
	}
	
	/**
	 * In this method we keep flipping the mates till the last node in the path, 
	 * this will increase the matching by one. When all the augmented paths are 
	 * processed in this way, we find all the possible matching in the graph.
	 * 
	 * @param u
	 * 			: Vertex u start point of augmented path
	 */
	public void processAugPath(Vertex u){
		Vertex p = u.parent;
		Vertex x = p.parent;
		u.mate = p;
		p.mate = u;
		//keep flipping the mates till the last one 
		while (x != null ){
			Vertex nmx = x.parent;
			Vertex y = nmx.parent;
			x.mate = nmx;
			nmx.mate =x;
			x = y;
		}
	}
}
