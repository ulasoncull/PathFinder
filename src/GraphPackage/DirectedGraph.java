package GraphPackage;
import java.util.Iterator;
import ADTPackage.*; // Classes that implement various ADTs
/**
 A class that implements the ADT directed graph.
 @author Frank M. Carrano
 @author Timothy M. Henry
 @version 5.1
 */
public class DirectedGraph<T> implements GraphInterface<T>
{
   private DictionaryInterface<T, VertexInterface<T>> vertices;
   private int edgeCount;
   
   public DirectedGraph()
   {
      vertices = new UnsortedLinkedDictionary<>();
      edgeCount = 0;
   } // end default constructor

   public boolean addVertex(T vertexLabel)
   {
      VertexInterface<T> addOutcome = vertices.add(vertexLabel, new Vertex<>(vertexLabel));
      return addOutcome == null; // Was addition to dictionary successful?
   } // end addVertex
   
   public boolean addEdge(T begin, T end, double edgeWeight)
   {
      boolean result = false;
      VertexInterface<T> beginVertex = vertices.getValue(begin);
      VertexInterface<T> endVertex = vertices.getValue(end);
      if ( (beginVertex != null) && (endVertex != null) )
         result = beginVertex.connect(endVertex, edgeWeight);
      if (result)
         edgeCount++;
      return result;
   } // end addEdge
   
   public boolean addEdge(T begin, T end)
   {
      return addEdge(begin, end, 0);
   } // end addEdge

   public boolean hasEdge(T begin, T end)
   {
      boolean found = false;
      VertexInterface<T> beginVertex = vertices.getValue(begin);
      VertexInterface<T> endVertex = vertices.getValue(end);
      if ( (beginVertex != null) && (endVertex != null) )
      {
         Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
         while (!found && neighbors.hasNext())
         {
            VertexInterface<T> nextNeighbor = neighbors.next();
            if (endVertex.equals(nextNeighbor))
               found = true;
         } // end while
      } // end if
      
      return found;
   } // end hasEdge

	public boolean isEmpty()
	{
	  return vertices.isEmpty();
	} // end isEmpty

	public void clear()
	{
	  vertices.clear();
	  edgeCount = 0;
	} // end clear

	public int getNumberOfVertices()
	{
	  return vertices.getSize();
	} // end getNumberOfVertices

	public int getNumberOfEdges()
	{
	  return edgeCount;
	} // end getNumberOfEdges

	protected void resetVertices()
	{
	   Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
	   while (vertexIterator.hasNext())
	   {
	      VertexInterface<T> nextVertex = vertexIterator.next();
	      nextVertex.unvisit();
	      nextVertex.setCost(0);
	      nextVertex.setPredecessor(null);
	   } // end while
	} // end resetVertices
	
	public StackInterface<T> getTopologicalOrder() 
	{
		resetVertices();
		StackInterface<T> vertexStack = new LinkedStack<>();
		int numberOfVertices = getNumberOfVertices();
		for (int counter = 1; counter <= numberOfVertices; counter++)
		{
			VertexInterface<T> nextVertex = findTerminal();
			nextVertex.visit();
			vertexStack.push(nextVertex.getLabel());
		} // end for
		
		return vertexStack;	
	} // end getTopologicalOrder
	
	
  
      public QueueInterface<T> getBreadthFirstTraversal(T origin, T end) {
    	  resetVertices();
    	  LinkedQueue<T> traversalOrder = new LinkedQueue<T>();
    	  LinkedQueue<T> vertexQueue = new LinkedQueue<T>();
    	  vertices.getValue(origin).visit();
    	  traversalOrder.enqueue(origin);
    	  vertexQueue.enqueue(origin);
    	  while (!vertexQueue.isEmpty()) {
    		  VertexInterface<T> frontVertex = vertices.getValue(vertexQueue.dequeue());
    		  while(frontVertex.hasNeighbor()&&frontVertex.getUnvisitedNeighbor()!=null) {
    			  VertexInterface<T> nextNeighbor = frontVertex.getUnvisitedNeighbor();
    			  if(!nextNeighbor.isVisited()) {
    				  
    				  nextNeighbor.visit();
    				  traversalOrder.enqueue(nextNeighbor.getLabel());
    				  vertexQueue.enqueue(nextNeighbor.getLabel());
    			  }
    			  if(end.equals(nextNeighbor.getLabel())) {
    				  
    				  return traversalOrder;

    			  }
    		  }	  
		}
    	  
    	  return traversalOrder;
    	  
      }
    		
      public QueueInterface<T> getDepthFirstTraversal(T origin, T end){
    	  resetVertices();
    	  LinkedQueue<T> traversalOrder = new LinkedQueue<T>();
    	  LinkedStack<T> vertexStack = new LinkedStack<>();
    	  vertices.getValue(origin).visit();
    	  traversalOrder.enqueue(origin);
    	  vertexStack.push(origin);
    	  while(!vertexStack.isEmpty()) {
    		  VertexInterface<T> topVertex = vertices.getValue(vertexStack.peek());
    		  if(topVertex.hasNeighbor()&&topVertex.getUnvisitedNeighbor()!=null) {
    			  VertexInterface<T> nextNeighbor = topVertex.getUnvisitedNeighbor();
    			  nextNeighbor.visit();
    			  traversalOrder.enqueue(nextNeighbor.getLabel());
    			  vertexStack.push(nextNeighbor.getLabel());
    			  if(end.equals(nextNeighbor.getLabel())) {
    				 
    				  return traversalOrder;
    			  }
    			  
    		  }
    		  else {
    			  vertexStack.pop();
    		  }
    	  }
    	  
    	  return traversalOrder;
      } 
 
	
	
	
	
	   public int getShortestPath(T begin, T end, StackInterface<T> path) {
		   resetVertices();
		   boolean done = false;
		   LinkedQueue<T> vertexQueue = new LinkedQueue<T>();
		   vertices.getValue(begin).visit();
		   vertexQueue.enqueue(begin);
		   while(!done &&!vertexQueue.isEmpty()) {
			   VertexInterface<T> frontVertex = vertices.getValue(vertexQueue.dequeue());
			   while(!done &&frontVertex.hasNeighbor()&&frontVertex.getUnvisitedNeighbor()!=null) {
	    			  VertexInterface<T> nextNeighbor = frontVertex.getUnvisitedNeighbor();
	    			  if(!nextNeighbor.isVisited()) {
	    				  nextNeighbor.visit();
	    				  nextNeighbor.setCost(1+frontVertex.getCost());
	    				  nextNeighbor.setPredecessor(frontVertex);
	    				  vertexQueue.enqueue(nextNeighbor.getLabel());	    			
	    			  }	    			  
	    			  if(end.equals(nextNeighbor.getLabel()))
	    				  done = true;
				   
			   }
		   }
		   double pathLength = vertices.getValue(end).getCost();
		   path.push(vertices.getValue(end).getLabel());
		   VertexInterface<T> vertex = vertices.getValue(end);
		   while(vertex.hasPredecessor()) {
			   vertex = vertex.getPredecessor();
			   path.push(vertex.getLabel());			   			   
		   }		   
		   return (int)pathLength;
	   } 
	   
	   public double getCheapestPath(T begin, T end, StackInterface<T> path) {
		   resetVertices();
		   boolean done = false;
		   HeapPriorityQueue<EntryPQ> priorityQueue = new HeapPriorityQueue<>();		   
		   priorityQueue.add(new EntryPQ(vertices.getValue(begin), 0, null));
		   while(!done&& !priorityQueue.isEmpty()) {
			   EntryPQ frontEntry = priorityQueue.remove();
			   VertexInterface<T> frontVertex = frontEntry.getVertex();
			   if(!frontVertex.isVisited()) {				   
				   frontVertex.visit();
				   frontVertex.setCost(frontEntry.getCost());
				   frontVertex.setPredecessor(frontEntry.getPredecessor());
				   if(frontVertex.getLabel().equals(end))
					   done = true;
				   else {
					   Iterator<VertexInterface<T>> iterator = frontVertex.getNeighborIterator();
					   Iterator<Double> iteratorWeight = frontVertex.getWeightIterator();
					   while(iterator.hasNext()) {
						   double weightOfEdgeToNeighbor = iteratorWeight.next();
						   VertexInterface<T> nextNeighbor = iterator.next();
						   if(!nextNeighbor.isVisited()&&nextNeighbor!=null) {
							   double nextCost = weightOfEdgeToNeighbor+frontVertex.getCost();
							   priorityQueue.add(new EntryPQ(nextNeighbor, nextCost, frontVertex));
						   }
					   }					   					   					   
				   }				   				   
			   }			  
		   }
		   double pathCost = vertices.getValue(end).getCost();
		   path.push(end);
		   VertexInterface<T> vertex = vertices.getValue(end);
		   while(vertex.hasPredecessor()) {
			   vertex = vertex.getPredecessor();
			   path.push(vertex.getLabel());
		   }
		   return pathCost;
		   
		   
		   
	   }
	   //Adjacency Matrixi oluþturur.
	   public void adjencyMatrix(){
		   int[][] maze = new int[getNumberOfVertices()][getNumberOfVertices()];
		   Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
		   
		   for(int i = 0;i<getNumberOfVertices();i++) {			   
			   for(int j=0;j<getNumberOfVertices();j++) {
				   maze[i][j] = 0;  
			   }
		   }
		   for(int i =maze.length-1;i>=0;i--) {
			   VertexInterface<T> vertex1 = vertexIterator.next();
			   Iterator<VertexInterface<T>> vertexIterator2 = vertices.getValueIterator();
			   for(int j =maze[1].length-1;j>=0;j--) {
				   VertexInterface<T> vertex2 = vertexIterator2.next();
				   if(hasEdge(vertex1.getLabel(), vertex2.getLabel()))
					   maze[i][j] = 1;
			   }
		   }
		   for(int i =0;i<maze.length;i++) {
			   for(int j = 0;j<maze[1].length;j++) {
				  System.out.print(maze[i][j]);
			   }
			   System.out.println();
		   }
			   
			 
	   }
	   
	protected VertexInterface<T> findTerminal()
	{
		boolean found = false;
		VertexInterface<T> result = null;

		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();

		while (!found && vertexIterator.hasNext())
		{
			VertexInterface<T> nextVertex = vertexIterator.next();
			
			// If nextVertex is unvisited AND has only visited neighbors)
			if (!nextVertex.isVisited())
			{ 
				if (nextVertex.getUnvisitedNeighbor() == null )
				{ 
					found = true;
					result = nextVertex;
				} // end if
			} // end if
		} // end while

		return result;
	} // end findTerminal

	// Used for testing
	public void displayEdges()
	{
		System.out.println("\nEdges exist from the first vertex in each line to the other vertices in the line.");
		System.out.println("(Edge weights are given; weights are zero for unweighted graphs):\n");
		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
		while (vertexIterator.hasNext())
		{
			((Vertex<T>)(vertexIterator.next())).display();
		} // end while
	} // end displayEdges 
	
	private class EntryPQ implements Comparable<EntryPQ>
	{
		private VertexInterface<T> vertex; 	
		private VertexInterface<T> previousVertex; 
		private double cost; // cost to nextVertex
		
		private EntryPQ(VertexInterface<T> vertex, double cost, VertexInterface<T> previousVertex)
		{
			this.vertex = vertex;
			this.previousVertex = previousVertex;
			this.cost = cost;
		} // end constructor
		
		public VertexInterface<T> getVertex()
		{
			return vertex;
		} // end getVertex
		
		public VertexInterface<T> getPredecessor()
		{
			return previousVertex;
		} // end getPredecessor

		public double getCost()
		{
			return cost;
		} // end getCost
		
		public int compareTo(EntryPQ otherEntry)
		{
			// Using opposite of reality since our priority queue uses a maxHeap;
			// could revise using a minheap
			return (int)Math.signum(otherEntry.cost - cost);
		} // end compareTo
		
		public String toString()
		{
			return vertex.toString() + " " + cost;
		} // end toString 
	} // end EntryPQ
} // end DirectedGraph
