import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;
// program to implement minimum spanning tree of undirected graph using prims version 1 algorithm
class Edge implements Comparable<Edge>{
	
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
		if(this.weight < arg0.getWeight()){
			return -1;
		}else if(this.weight > arg0.getWeight()){
			return 1;
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return "( "+this.u+" , "+this.v+" , "+this.weight+" )";
	}

}
class PrimsNode {
	boolean seen;
	int node;
	int parent ;
	
	public PrimsNode(boolean seen, int node, int parent) {
		super();
		this.seen = seen;
		this.node = node;
		this.parent = parent;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	public int getNode() {
		return node;
	}

	public void setNode(int node) {
		this.node = node;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}
	
	@Override
	public String toString() {
		
		return "( "+parent+" , "+node+" )";
	}

}


/**
 * @author Sudharshan Narasimhan Vasudevan
 *
 */
public class PrimsVersion1 {
	static TreeMap<Integer, PriorityQueue<Edge>> adjList = new TreeMap<Integer, PriorityQueue<Edge>>();
	static HashMap<Integer, PrimsNode> nodes = new HashMap<Integer, PrimsNode>();
	static int size = 0;

	static Comparator<Edge> weightComparator = new Comparator<Edge>() {

		@Override
		public int compare(Edge o1, Edge o2) {
			if (o1.getWeight() < o2.getWeight()) {
				return -1;
			} else if (o1.getWeight() > o2.getWeight()) {
				return 1;
			} else {
				return 0;
			}
		}

	};

	static PriorityQueue<Edge> queue;

	public static void process(int u) {
		
		size++;
		PrimsNode n = nodes.get(u);
		n.setSeen(true);
		PriorityQueue<Edge> edges = adjList.get(u);
		while (edges != null && edges.size() != 0) {
			Edge e = edges.poll();
			PrimsNode v = nodes.get(e.getV());
			PrimsNode s = nodes.get(e.getU());

			if (!v.isSeen() || !s.isSeen()) {
				queue.add(e);
			}
		}

	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		//System.out.println("Enter the data");

		int  k = 0;
		int totalWeight = 0;
		int noOfVertex = in.nextInt();
		int edges = in.nextInt();		
		int input1 = 0, input2 = 0, input3 = 0;
		queue = new PriorityQueue<Edge>(edges, weightComparator);
		long startTime = System.currentTimeMillis();
		while (k < edges) {
			if (in.hasNext()) {				
				input1 = in.nextInt();
				input2 = in.nextInt();
				input3 = in.nextInt();
				
				Edge edge = new Edge(input1, input2, input3);
				if (adjList.containsKey(input1)) {
					adjList.get(input1).add(edge);
				} else {
					PriorityQueue<Edge> nodes = new PriorityQueue<Edge>(edges, weightComparator);
					nodes.add(edge);
					adjList.put(input1, nodes);
				}
				if (adjList.containsKey(input2)) {
					adjList.get(input2).add(edge);
				} else {
					PriorityQueue<Edge> nodes = new PriorityQueue<Edge>(edges, weightComparator);
					nodes.add(edge);
					adjList.put(input2, nodes);
				}
				if (!nodes.containsKey(input1)) {
					PrimsNode n = new PrimsNode(false, input1, 0);
					nodes.put(input1, n);
				}
				if (!nodes.containsKey(input2)) {
					PrimsNode n = new PrimsNode(false, input2, 0);
					nodes.put(input2, n);
				}

				k++;
			}
		}
		process(1);
		while (size < noOfVertex) {
			Edge e = queue.poll();
			PrimsNode a = nodes.get(e.getU());
			PrimsNode b = nodes.get(e.getV());
			while (a.isSeen() && b.isSeen()) {
				e = queue.poll();
				a = nodes.get(e.getU());
				b = nodes.get(e.getV());
			}
			if (a.isSeen()) {
				totalWeight += e.getWeight();
				process(e.getV());
				b.setParent(a.getNode());
			} else {
				totalWeight += e.getWeight();
				process(e.getU());
				a.setParent(b.getNode());
			}
		}
		System.out.println(totalWeight);
		for(PrimsNode n: nodes.values()){
			if (n.getParent() != 0) {
				System.out.println(n);
			}
		}
		long endTime = System.currentTimeMillis(); // end time of algorithm
	     long totalRunningTime = endTime - startTime; //Total running time in milliseconds
	     System.out.println("\nTotal running time in MilliSecs : " + totalRunningTime);
	}

}
