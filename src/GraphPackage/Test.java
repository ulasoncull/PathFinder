package GraphPackage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.lang.Iterable;

import ADTPackage.LinkedQueue;
import ADTPackage.LinkedStack;
import ADTPackage.ListInterface;
import ADTPackage.QueueInterface;


import java.io.File;
import java.io.FileNotFoundException;
public class Test {
	
	private static int rows = 0;
	private static int cols = 0;
	private static int xStart = 0;
	private static int yStart = 0;
	private static int xStop = 0;
	private static int yStop = 0;


	private static int characters = 0;
	static	char[][] mazeCheapest = null;
	static	char[][] maze = null;
	static	char[][] mazeBFS = null;
	static	char[][] mazeDFS = null;
	static	char[][] mazeShortest = null;

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner (System.in);
		System.out.println("Please enter the maze which u want to see."
				+ " Like:maze1.txt, maze3.txt");
		String fileName = scanner.next();
	
		main(fileName);
		xStart = 0;
		yStart=1;
		xStop=rows-2;
		yStop=cols-1;
		String start = Integer.toString(xStart)+"-"+Integer.toString(yStart);
		String stop = Integer.toString(xStop)+"-"+Integer.toString(yStop);

		UndirectedGraph<String> a = new UndirectedGraph<String>();
		// Cheapest algoritmasý için kullanýlýr. Wighteddýr.
		UndirectedGraph<String> b = new UndirectedGraph<String>();
		//Vertexleri ekler
	      for(int i = 0; i<maze.length;i++) {
	    	  for(int j = 0; j<maze[1].length;j++) {
	    		  if(maze[i][j]==' ') {
	    			  String col = Integer.toString(j);
	    			  String row = Integer.toString(i);	
	    			  a.addVertex(row+"-"+col);	  
	    		  }	  
	    	  }
	      }
	      for(int i = 0; i<maze.length;i++) {
	    	  for(int j = 0; j<maze[1].length;j++) {
	    		  if(maze[i][j]==' ') {
	    			  String col = Integer.toString(j);
	    			  String row = Integer.toString(i);	
	    			  b.addVertex(row+"-"+col);	  
	    		  }	  
	    	  }
	      }
	      //edgeleri oluþturur.
	      for(int i = 0; i<maze.length;i++) {
	    	  for(int j = 0; j<maze[1].length;j++) {
	    		  if(maze[i][j]==' ') {
	    			  String col = Integer.toString(j);
	    			  String row = Integer.toString(i);
	    			  if(i+1>=0 && i+1<=rows-1 && maze[i+1][j]==' ') {
	    				  String col2 = Integer.toString(j);
		    			  String row2 = Integer.toString(i+1);
		    			  a.addEdge(row+"-"+col,row2+"-"+col2);
	    			  }
	    			  
	    			  if(j+1>=0 && j+1<=cols-1 &&maze[i][j+1]==' ') {
	    				  String col2 = Integer.toString(j+1);
		    			  String row2 = Integer.toString(i);
		    			  a.addEdge(row+"-"+col,row2+"-"+col2);
	    			  }
	    			  if(i-1>=0 && i-1<=rows-1 &&maze[i-1][j]==' ') {
	    				  String col2 = Integer.toString(j);
		    			  String row2 = Integer.toString(i-1);
		    			  a.addEdge(row+"-"+col,row2+"-"+col2);
	    			  }
	    			  
	    			 if(j-1>=0 && j-1<=cols-1 &&maze[i][j-1]==' ') {
	    				  String col2 = Integer.toString(j-1);
		    			  String row2 = Integer.toString(i);
		    			  a.addEdge(row+"-"+col,row2+"-"+col2);
	    			  }
	    				 
	    		  }	  
	    	  }
	      }
	      for(int i = 0; i<maze.length;i++) {
	    	  for(int j = 0; j<maze[1].length;j++) {
	    		  if(maze[i][j]==' ') {
	    			  String col = Integer.toString(j);
	    			  String row = Integer.toString(i);
	    			  if(j+1>=0 && j+1<=cols-1 &&maze[i][j+1]==' ') {
	    				  Random rnd = new Random();
	    				  int weight = 1+rnd.nextInt(4);
	    				  String col2 = Integer.toString(j+1);
		    			  String row2 = Integer.toString(i);
		    			  b.addEdge(row+"-"+col,row2+"-"+col2,weight);
	    			  }
	    			  if(i+1>=0 && i+1<=rows-1 && maze[i+1][j]==' ') {
	    				  Random rnd = new Random();
	    				  int weight = 1+rnd.nextInt(4);
	    				  String col2 = Integer.toString(j);
		    			  String row2 = Integer.toString(i+1);
		    			  b.addEdge(row+"-"+col,row2+"-"+col2,weight);
	    			  }
	    			  if(i-1>=0 && i-1<=rows-1 &&maze[i-1][j]==' ') {
	    				  Random rnd = new Random();
	    				  int weight = 1+rnd.nextInt(4);
	    				  String col2 = Integer.toString(j);
		    			  String row2 = Integer.toString(i-1);
		    			  b.addEdge(row+"-"+col,row2+"-"+col2,weight);
	    			  }if(j-1>=0 && j-1<=cols-1 &&maze[i][j-1]==' ') {
	    				  Random rnd = new Random();
	    				  int weight = 1+rnd.nextInt(4);
	    				  String col2 = Integer.toString(j-1);
		    			  String row2 = Integer.toString(i);
		    			  b.addEdge(row+"-"+col,row2+"-"+col2,weight);
	    			  }
	    				 
	    		  }	  
	    	  }
	      }
	      //PRÝNTLER
	      System.out.println("Adjacency Lists of Each Vertex of the Graph After Maze to Graph Operation:");  	     
	      b.displayEdges();
	      System.out.println("Adjacency Matrix of the grapg after maze to graph:");
	      a.adjencyMatrix();
	      System.out.println("The Number Of Edges Found");   
	      System.out.println(a.getNumberOfEdges());
	      System.out.println("BFS output between the starting and the end points of the maze:");   
	      printMaze(mazeBFS, a.getBreadthFirstTraversal(start, stop));
	      int countOfVerticesBFS = 0;
	      QueueInterface<String> BFSQueue = a.getBreadthFirstTraversal(start, stop);
	      while(!BFSQueue.isEmpty()) {
	    	  BFSQueue.dequeue();
	    	  countOfVerticesBFS++;
	      }
	      System.out.println("The number of visited vertices for BFS");   
	      System.out.println(countOfVerticesBFS);
	      System.out.println("DFS output between the starting and the end points of the maze:");   
	      printMaze(mazeDFS, a.getDepthFirstTraversal(start, stop));
	      int countOfVerticesDFS = 0;
	      QueueInterface<String> DFSQueue = a.getDepthFirstTraversal(start, stop);
	      while(!DFSQueue.isEmpty()) {
	    	  DFSQueue.dequeue();
	    	  countOfVerticesDFS++;
	      }
	      System.out.println("The number of visited vertices for DFS");   
	      System.out.println(countOfVerticesDFS);
	      LinkedStack<String> path = new LinkedStack<String>();
	      a.getShortestPath(start, stop, path);
	      System.out.println("Shortest path between the starting and the end points of the maze");   
	      printMazee(mazeShortest, path);
	      int countOfVerticesShortest = 0;
	      a.getShortestPath(start, stop, path);
	      while(!path.isEmpty()) {
	    	  path.pop();
	    	  countOfVerticesShortest++;
	      }
	      System.out.println("The number of visited vertices for Shortest Path");   
	      System.out.println(countOfVerticesShortest);  
	      b.getCheapestPath(start, stop, path);
	      System.out.println("The cheapest path for the Weighted Graph");   
	      printMazee(mazeCheapest, path);
	      int countOfVerticesCheapest = 0;
	      b.getCheapestPath(start, stop, path);
	      while(!path.isEmpty()) {
	    	  path.pop();
	    	  countOfVerticesCheapest++;
	      }
	      System.out.println("The number of visited vertices for the Weighted Graph");   
	      System.out.println(countOfVerticesCheapest);
	      System.out.println("The cost of the cheapest path");
	      System.out.println(b.getCheapestPath(start, stop, path));
	      mazeCheapest = null;
	  		maze = null;
	  		mazeBFS = null;
	  		mazeDFS = null;
	  		mazeShortest = null;

	      
	      
	      
	      
	      
	      

	}
	//Mazedeki row ve column sayýlarýný saydýktan sonra ona göre bir maze oluþturur ve mazei arraye atayan placeMaze komudunu çaðýrýr.
	public static void main(String FileName) throws IOException {
		countRow(FileName);
		countColumn(FileName);
		mazeShortest = placeMaze(mazeShortest,FileName);
		mazeCheapest = placeMaze(mazeCheapest,FileName);
		mazeBFS = placeMaze(mazeBFS,FileName);
		maze = placeMaze(maze,FileName);
		mazeDFS = placeMaze(mazeDFS,FileName);
	}
	public static void printMaze(char[][] maze1,QueueInterface<String> queueInterface) {
		while(!queueInterface.isEmpty()) {
			String[] b = queueInterface.dequeue().split("-");			
			maze1[Integer.parseInt(b[0])][Integer.parseInt(b[1])]='.';
		}
		
		for(int i = 0; i<maze1.length;i++) {
	    	  for(int j = 0; j<maze1[1].length;j++) {
	    		  System.out.print(maze1[i][j]);  
	    		  }	 
	    	  System.out.println();
	    	  }
	      }
	public static void printMazee(char[][] maze,LinkedStack<String> path) {
		while(!path.isEmpty()) {
			String[] b = path.pop().split("-");			
			maze[Integer.parseInt(b[0])][Integer.parseInt(b[1])]='.';
		}
		
		for(int i = 0; i<maze.length;i++) {
	    	  for(int j = 0; j<maze[1].length;j++) {
	    		  System.out.print(maze[i][j]);  
	    		  }	 
	    	  System.out.println();
	    	  }
	      }		
	public static  void countRow(String fileName) throws IOException {
		rows = 0;
		File f=new File(fileName);     //Creation of File Descriptor for input file
	    FileReader fr=new FileReader(f);   //Creation of File Reader object
	    BufferedReader br=new BufferedReader(fr);  //Creation of BufferedReader object
	    while((br.readLine())!=null) {	    	      	  
	    	  rows++;
	      }
		
	}
	public static void countColumn(String fileName) throws IOException {
		  characters = 0;	
		  cols = 0;	
		  File f=new File(fileName);     //Creation of File Descriptor for input file
	      int c = 0;
	      FileReader frr = new FileReader(f);
	      BufferedReader brr=new BufferedReader(frr); 
	      while((c = brr.read()) != -1)         //Read char by Char
	      {
	            char character = (char) c; 
	            if(character=='#'||character==' ')//converting integer to char
		            characters++;
	      }
	      cols = characters/rows;
		
	}
	public static char[][] placeMaze(char [][]maze,String fileName)throws IOException {
		 maze = new char[rows][cols];
	     File file = new File(fileName);
	     Scanner scanner = new Scanner(file);
	     for (int row = 0; scanner.hasNextLine() && row < rows; row++) {
	   	    char[] chars = scanner.nextLine().toCharArray();
	   	    for (int i = 0; i < cols && i < chars.length; i++) {
	   	        maze[row][i] = chars[i];
	    	    }
	    	}
	     return maze;
	}
}	
 	
