import java.util.*;

public class Evacuation {
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		int N = sc.nextInt();
		int T = sc.nextInt();
		
		int[][] scientists = new int[N][N];
		int[][] capsules = new int[N][N];

		/* Create the graph:
		 * 		Bipartite graph with scientists U and capsules V
		 * 		Sink connecting to each scientist, Source connecting each capsule
		 * 		Edge between U and V if a scientist can reach a given capsule block
		 * 		at any time t.
		 *  */
		Node src = new Node(-1, -1); /* Defective reactor */
		Node sink = new Node(-1, -1); 
		
		/* First matrix */
		Set<Node> U = new HashSet<Node>();

		for(int i = 0 ; i < N; i++){
			String line = sc.next();
			for(int j = 0 ; j < N; j++){
				if(line.charAt(j) == 'Y'){
					scientists[i][j] = -2;
				}
				else if(line.charAt(j) == 'Z'){
					scientists[i][j] = -1;
					src.x = i;
					src.y = j;
				}
				else if(line.charAt(j) != '0'){/* Create a node for the block. Add an edge from src to node with capacity = # of scientists */
					scientists[i][j] = (int)(line.charAt(j) -'0');
					Node temp = new Node(i, j);
					src.addEdge(new Edge(src, temp, scientists[i][j], 0));
					U.add(temp);
				}
			}	
		}
		/* Second matrix */
		Set<Node> V = new HashSet<Node>();
		for(int i = 0 ; i < N; i++){
			String line = sc.next();
			for(int j = 0 ; j < N; j++){
				if(line.charAt(j) == 'Y'){
					capsules[i][j] = -2;
				}
				else if(line.charAt(j) == 'Z'){
					capsules[i][j] = -1;
				}
				else if(line.charAt(j) != '0'){/* Create a node for the block. Add an edge from node to sink with capacity = # of capsules */
					capsules[i][j] = (int)(line.charAt(j) - '0');
					Node temp = new Node(i, j);
					temp.addEdge(new Edge(temp, sink, capsules[i][j], 0));
					V.add(temp);
				}
			}	
		}
		sc.close();
		
		/* Create a mapping for all the nodes in the graph for easy access as needed */
		Map<String, Node> U_map = new HashMap<String, Node>();
		for(Node s : U){ 
			U_map.put(s.x+"|"+s.y, s);
		}
		Map<String, Node> V_map = new HashMap<String, Node>();
		for(Node c : V){ 
			V_map.put(c.x+"|"+c.y, c);
		}
		
		/* BFS */
		// 1. Create a graph. The only way to access this graph is through set S
		Node z = null;
		Map<String, Node> mapping = new HashMap<String, Node>();
		Set<Node> S = new HashSet<Node>();
		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++){
				Node n;
				if(mapping.containsKey(i+"|"+j)){
					n = mapping.get(i+"|"+j);
				}
				else{
					n = new Node(i, j);
					mapping.put(i+"|"+j, n);
				}
				Node right = null;
				Node down = null;
				//right node
				if(j+1 < N){
					right = mapping.get(i+"|"+(j+1));
					if(right == null){
						right = new Node(i, j+1);
					}
					n.edges.add(new Edge(n, right));
					right.edges.add(new Edge(right, n));
					mapping.put(i+"|"+(j+1), right);
				}
				//bottom node
				if(i+1 < N){
					down = mapping.get((i+1)+"|"+j);
					if(down == null){
						down = new Node(i+1, j);
					}
					n.edges.add(new Edge(n, down));
					down.edges.add(new Edge(down, n));
					mapping.put((i+1)+"|"+j, down);
				}
				if(scientists[i][j] == -1){
					n.scratch = -1;
					z = n;
				}
				else if(scientists[i][j] == -2){
					n.scratch = -2;
				}
				else if(scientists[i][j] == 0){
					n.scratch = 0;
				}
				else{
					S.add(n); //need to go through scientists to bfs
					n.scratch = 0;
				}
			}
		}
		
		Graph g1 = new Graph(null, null, new HashSet<Node>(mapping.values()));
		/* Find all the reachable nodes */
		
		/* Mark at which time the node was poisoned and mark at which time the bfs reached that block. 
		 * If bfs reached it <= the time there then we know it's possible!!! */
		/* If scientists are already positioned at capsules, mark them as reachable */
		Set<Node> frontier_of_poison = new HashSet<Node>();
		frontier_of_poison.add(z);
		z.poisoned = 0;
		Set<String> visited_ = new HashSet<String>(); //optimization
		for(int t = 1; t<= T; t++){
			Set<Node> next = new HashSet<Node>();
			for(Node p : frontier_of_poison){
				for(Edge e : p.edges)
				{
					Node adj = e.v; 
					if(adj.scratch >= 0 && !visited_.contains(adj.x+"|"+adj.y)){ /* mark all the reachable capsules with the time they are poisoned, all non-poisonable capsules are -1 */
						Node to_poison = V_map.get(adj.x+"|"+adj.y);
						adj.poisoned = t; //poison so that when we bfs, we check if the node visiting has been poisoned at an earlier time
						next.add(adj);
						if(to_poison != null){
							to_poison.poisoned = t; 
						}
					}
					visited_.add(adj.x+"|"+adj.y);
				}
			}
			frontier_of_poison = next;
		}
		
		for(Node c : V){
			Node s = U_map.get(c.x+"|"+c.y);
			if(s != null){
				s.reachable_capsules.put(c.x+"|"+c.y, 0);
			}
		}
		for(Node s : S){
			Map<String, Integer> visited = new HashMap<String, Integer>(); //marks t (distance)
			Queue<Node> q = new LinkedList<Node>();
			
			Node original = U_map.get(s.x+"|"+s.y);
			
			q.add(s);
			visited.put(s.x+"|"+s.y, 0);
			while(!q.isEmpty()){
				Node curr = q.remove();
				int t_at_curr = visited.get(curr.x+"|"+curr.y);
				if(t_at_curr > T) break;
				for(Edge e : curr.edges){
					Node adj = e.v; 
					if(!visited.containsKey(adj.x+"|"+adj.y) && adj.scratch >=0 ){
						visited.put(adj.x+"|"+adj.y, (t_at_curr + 1)); /* adj is reachable at curr's t + 1 */
						if(adj.poisoned >= (t_at_curr + 1) || adj.poisoned == -1){
							if(adj.poisoned != t_at_curr + 1) q.add(adj);
							if(capsules[adj.x][adj.y] > 0 && t_at_curr < T){ /* Only mark as reachable if there is even a capsule at that block */
								original.reachable_capsules.put(adj.x+"|"+adj.y, (t_at_curr + 1) ); /* All of this for this one line ^^; */
							}
						}
					}
				}
			}
		}
				
		
		/*Now supposedly we have marked all the nodes where they can visit. */
		
		//Add edges from s to c
		for(Node s: U){
			for(Node c : V){
				Integer time_reached = s.reachable_capsules.get(c.x+"|"+c.y);
				if(time_reached != null ){
					Edge e = new Edge(s, c, Integer.MAX_VALUE, 0);
					s.edges.add(e);
				}
			}
		}
		
		//now create the maxflow graph
		U.addAll(V);
		//create a backward edge --> current_flow value useless
		for(Node u: U){
			for(Edge e: u.edges){
				Edge x = new Edge(e.v, e.u, 0, 0, true);
				e.v.edges.add(x);
				e.ref = x;
			}
		}
		Graph g = new Graph(src, sink, U);
		int max = g.ford_fulkerson();
		System.out.println(max);
	}

	/* create a graph with matrix. Do bfs from */
	public static class Node{
		int x; /* Indicate the position of the block */
		int y;
		Map<String, Integer> reachable_capsules; /* all the capsules the scientist can reach at time t */
		int scratch;
		int poisoned;
		boolean visited; /* used to compute the max flow */
		ArrayList<Edge> edges;
		
		public Node(int x, int y){
			this.x = x;
			this.y = y;
			poisoned = -1; /* -1 --> never poisoned, 0 <= t <= 60 time poisoned */
			visited = false;
			edges = new ArrayList<Edge>();
			reachable_capsules = new HashMap<String, Integer>();
		}
		
		public void addEdge(Edge e){
			this.edges.add(e);
		}
	}
	
	public static class Edge{
		Node u, v;
		int remaining_capacity;
		boolean back;
		Edge ref;
		
		public Edge(Node u, Node v, int c, int f){
			this.u = u;
			this.v = v;
			this.remaining_capacity = c;
			back = false;
			ref = null;
		}
		public Edge(Node u, Node v, int c, int f, boolean b){
			this.u = u;
			this.v = v;
			this.remaining_capacity = c;
			back = b;
			ref = null;
		}
		/*For the simple graph to bfs */
		public Edge(Node u, Node v){
			this.u = u;
			this.v = v;
		}
	}
	
	public static class Graph{
		private Node s;
		private Node t;
		private Set<Node> nodes;
		public Graph(Node s, Node t, Set<Node> nodes){
			this.s = s;
			this.t = t;
			this.nodes = nodes;
		}
		
		public void clearVisited(){
			for(Node n : nodes)
				n.visited = false;
			s.visited = false;
			t.visited = false;
		}
					
		public int ford_fulkerson(){
			int max_flow = 0;
		    int f;
		    int x =1;
			while ((f = get_augmenting_path(s, Integer.MAX_VALUE)) != 0){
				clearVisited();
				max_flow += f;	
			}
		    return max_flow;
		}

		/* Finds an augmenting s-t path using dfs and adjusts the flow using the bottleneck found along that path*/
		public int get_augmenting_path(Node n, int bottleneck){
			if(n == t) //base case
				return bottleneck;
			if(n.visited){
				return 0;
			}
			n.visited = true;
			
			int b;
			for(Edge adj: n.edges){
		    	  if((adj.remaining_capacity > 0) && !adj.v.visited){
		    		    if(bottleneck > adj.remaining_capacity)
		    		    	bottleneck = adj.remaining_capacity; 
			            b = get_augmenting_path(adj.v, bottleneck);
			            if(b > 0){
			    			augment(adj, b);
				            return b;
			            }
		    	  }
		      }
		      return 0;
		}
		
		public void augment(Edge e, int bottleneck){
			e.remaining_capacity -= bottleneck;
			if(e.ref != null) e.ref.remaining_capacity += bottleneck;
		}
	
	}
	
}
