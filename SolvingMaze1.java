package solvemaze;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

import solvemaze.Stack_m.Node;

import java.io.File;
interface IMazeSolver{
	/***Readthemazefile,andsolveitusingBreadthFirstSearch*
	 * @parammazemazefile*@returnthecoordinatesofthefoundpathfrompoint’S’*topoint’E’inclusive,ornullifnopathisfound.*/
	public int[][]solveBFS(java.io.File maze);
	/***Readthemazefile,andsolveitusingDepthFirstSearch*
	 * @parammazemazefile*@returnthecoordinatesofthefoundpathfrompoint’S’*topoint’E’inclusive,ornullifnopathisfound.*/
	public int[][]solveDFS(java.io.File maze);
	}
 class coordinate { // class to store index of i & j of cell
	int x ; 
	int y ;

public coordinate(int x , int y ) {
 this.x = x;
 this.y = y ;
}
public coordinate() {
	this.x=0;
	this.y=0; 
}
}
 class Stack_m {
	 
		public class Node {  // create node 
			Object value ; 
			Node next ;
		}

		Node top;          // top of stack 
		int size ;          // size of stack 
		/*public MyStack() {     // constructor for stack 
	     top=null ;
	     size=0  ;
		}*/
		//static MyStack stack = new MyStack() ; 
		public int size() {     // return size of linledlist 
			return size;
		}
		public boolean isEmpty() {  // check if stack is empty or not  
			if(top==null)
				return true;
			return false ;
		}
		public void push(Object element) {   // push a new element to stack
			Node node = new Node();      
		    	node.value = element ;
		    	node.next = top ;  // next of node == prev top node 
		    	top = node ;       // make top point to new node
		    
			size ++ ;      
		}
		 public Object pop() {
			 if(top==null)  // if stack  is empty
			 {
				 System.out.print("Error");
				 System.exit(0);
			 }
				 
			 Node temp = top ;  // node to be removed
			 top=top.next;
			 size-- ;
			 return temp.value ; // return removed value of node
			 
		 }
		 public Object peek() {  // return value of top of stack 
			 if(top==null)  // if stack  is empty
			 {
				 System.out.print("Error");
				 System.exit(0);
			 }
				
			 return top.value ; 
		 }

}
 class Queue_m {
		public class Node {  // create node 
			Object value ; 
			Node next ;
		}
		Node tail , head ;
		int size ;
		public Queue_m() {
			tail = null ;
			head = null ;
			size = 0 ;
			
		}
		 public boolean isEmpty() {
			 return (size==0);
			 
		 }
		 public int size() {
			 return size ;
		 }
		 
		
		  public void enqueue(Object item) {
			Node node = new Node ();
	    	node.value = item;
			node.next = null ; 
			  if(size==0) { 
				 head = node ;
			     tail = node ;
			  }
			  else {
				 tail.next = node ;
				 tail = node ;
			  }
			  size++ ; 
		  }
		  public  Object dequeue() {
			  if (size == 0 ) {
				  System.out.print("Error");
				  System.exit(0);
			  }
			  Object removed = head.value ;
			  head = head.next;
			  size-- ;
			  if (size == 0 )
				  tail=null ;
			  return removed ;
			  
		  }
		  public Object front(Queue_m queue) {
			  if(queue == null)
				  return null ;
			  return queue.head.value ;
		  }

	}
public class SolvingMaze1 implements IMazeSolver {

	
	
	public static coordinate find_s(char[][]maze ) { // find index of The start S
		coordinate s = new coordinate() ;
		for(int i = 0 ; i < maze.length ; ++i   ) {
			for(int j = 0 ; j < maze[0].length ; ++j   ) {
				if (maze[i][j]=='S') {
					s.x=i;
					s.y=j;
					return s ;
				}
			}
		}
		return null ;
	}
	public static coordinate find_e(char[][]maze ) {  // find index of end E
		coordinate e = new coordinate() ;
		for(int i = 0 ; i < maze.length ; ++i   ) {
			for(int j = 0 ; j < maze[0].length ; ++j   ) {
				if (maze[i][j]=='E') {
					e.x=i;
					e.y=j;
					return e ;
				}
			}
		}
		return null ;
	}
	
	
	public static Stack_m dfs(int i ,int j,Stack_m index , char[][] maze , boolean[] visited,coordinate E) { 
		//recursive to visit each neighbour  of cell  
		
		if(visited[E.x*maze[0].length+E.y]) // if E found yet nolonger searching
			return index ; 
		if(visited[i*maze[0].length+j]) // cell visited before don't complete and return stack
			return index ; 
		if(maze[i][j]=='#')	// if a wall found block return to prev cell
			return index ;
		coordinate e = new coordinate(); 
		e.x = i ;
		e.y = j ;
		// make the cell visited
		visited[i*maze[0].length+j] = true ;
 		// push cell in stack 
		index.push(e);
		if(maze[i][j]=='E') 
            return index ;
		if(i<maze.length-1)
		  dfs(i+1,j,index,maze,visited,E); // search below
		if(i>0)
		  dfs(i-1,j,index,maze,visited,E); // search  above
		if(j<maze[0].length-1)
		  dfs(i,j+1,index,maze,visited,E); // search right
		if(j>0)
		  dfs(i,j-1,index,maze,visited,E); // search left
		if(!visited[E.x*maze[0].length+E.y]) // if we don't found E then pop unimportant cell as its neighbours not in our path
		  e=(coordinate)index.pop() ;
		
		return index ;
	}
	
	public int[][]solveDFS(java.io.File maze ){
		char [][] maze1 = parsingFile(maze) ;
		
		// create an array to indicate if we visit the cell or not
		
		boolean [] visited = new boolean [maze1.length*maze1[0].length] ;
		// creating stack to insert our path and to ease our backtrack 
		Stack_m index = new  Stack_m () ; 
		coordinate s = new coordinate();
		//find index of S
        s = find_s(maze1);
        //if there is no S in grid 
        if(s==null) {
        	System.out.println("No start of maze found!");
			System.exit(0);
        }
        coordinate e = find_e(maze1);
        if(e==null) {
        	System.out.println("No End of maze found!");
			System.exit(0);
        }
       
        // search in our neighbours by recursive fn 
		index=dfs(s.x,s.y,index,maze1,visited,e);
		//stack is empty >> no path found 
		if(index.isEmpty()) {
			System.out.println("No path found!");
			System.exit(0);
		}
		Stack_m temp = new Stack_m() ;
		int len_path = 0 ;
		//to reverse stack 
		while(!index.isEmpty()) {
		s=(coordinate)index.pop();
		temp.push(s);
		len_path ++ ;
		}
		int [][] res = new int [len_path][2];
		len_path = 0 ;
		// to make a array of indexs of path 
		while(!temp.isEmpty()) {
			s=(coordinate)temp.pop();
			res[len_path][0] = s.x ;
			res[len_path][1] = s.y ;
			len_path++ ;
		}
		return res ;
		
	}
	
	public int[][]solveBFS(java.io.File maze){
		char [][] maze1 = parsingFile(maze) ;
		boolean [] visited = new boolean [maze1.length*maze1[0].length] ; // initially false as no cell is visited
		Stack_m index = new  Stack_m () ;  // creating queue to get bfs traverse 
        Queue_m path = new Queue_m () ; // to store path and get died cell  
        coordinate s = new coordinate();
		//find index of S
        s = find_s(maze1);
        //if there is no S in grid 
        if(s==null) {
        	System.out.println("No start of maze found!");
			System.exit(0);
        }
        // find index of E
        coordinate e = find_e(maze1);
        if(e==null) {
        	System.out.println("No End of maze found!");
			System.exit(0);
        }
        path.enqueue(s);
        index=bfs(s.x,s.y,index,path,maze1,visited,e);
    
        Stack_m temp = new Stack_m();
        e = (coordinate)index.pop();
        temp.push(e);
        int sumOfCoord ; 
        int sumOfE = e.x + e.y;
        int path_len = 1 ;
        while(!index.isEmpty()) {
        e = (coordinate)index.pop();
        sumOfCoord = e.x + e.y ;
        if(Math.abs(sumOfCoord-sumOfE) == 1) {
        	temp.push(e);
        	sumOfE = sumOfCoord ;
        	++path_len ;
        	
        }
        }
        int[][] res = new int [path_len][2];
        path_len = 0 ;
        while(!temp.isEmpty()) {
        	e = (coordinate)temp.pop();
        	res[path_len][0] = e.x;
        	res[path_len][1] = e.y;
        	path_len++;
        	
        }
        
  
		return res ;
	}
	
	
	public static Stack_m bfs(int i , int j , Stack_m index, Queue_m path  , char[][] maze , boolean[] visited,coordinate E) {
	    while(!path.isEmpty() && !visited[E.x*maze[0].length+E.y]) {
	    	coordinate e = (coordinate)path.dequeue();
	    	if(maze[e.x][e.y]=='#')
	    		continue ; 
	    	visited[e.x*maze[0].length+e.y] = true ;
	    	index.push(e);
	    	

			if(e.x>0) {
				coordinate neighbour2 = new coordinate(e.x-1,e.y);// search above
				if(!visited[neighbour2.x*maze[0].length+neighbour2.y])
				  path.enqueue(neighbour2);
			}
			if(e.y<maze[0].length-1) {
				coordinate neighbour3 = new coordinate(e.x,e.y+1);// search right
				if(!visited[neighbour3.x*maze[0].length+neighbour3.y])
				  path.enqueue(neighbour3);

			}
			if(e.y>0) {
				coordinate neighbour4 = new coordinate(e.x,e.y-1);// search left
				if(!visited[neighbour4.x*maze[0].length+neighbour4.y])
				  path.enqueue(neighbour4);
			}
			if(e.x<maze.length-1) {
				coordinate neighbour1 = new coordinate(e.x+1,e.y);// search below
				if(!visited[neighbour1.x*maze[0].length+neighbour1.y])
				  path.enqueue(neighbour1);
			}
	
				
	    	
	    }
		if(!visited[E.x*maze[0].length+E.y]) {
		System.out.println("No path found!");
		System.exit(0);
	     }
		
		return index;
		
	}
	
	
	
    public static char[][] parsingFile(java.io.File maze){
		try {
    	Scanner input = new Scanner(maze);
		 
		String dim = input.nextLine();
		String[] dimension = dim.split(" ") ;
		int row = Integer.parseInt(dimension[0]);
		int col = Integer.parseInt(dimension[1]);
		String []maze_conc =new String[row] ;
		char [][] maze1 = new char[row][col];  
		for (int i = 0 ; i < row ;++i ) {
			maze_conc[i] = input.nextLine();
		    for(int j =0 ; j < col ;++j) {
		    	maze1[i][j] = maze_conc[i].charAt(j);
		    }
		    }

 	
  
   	    return maze1 ;
       
       }  catch(Exception e) {
	      System.out.println("Error!");
           }
	return null ;

    }
    
	public static void printing(int[][]res) {
		
		for(int i = 0 ; i < res.length ; ++i  ) {
		
				if(i<res.length-1)
					System.out.print("{"+res[i][0]+","+res[i][1]+"},");
				else
					System.out.print("{"+res[i][0]+","+res[i][1]+"}");

				
			}
	}
	
	
	public static void main(String[] args) {
		try {
      File maze = new File("C:\\Users\\Lenovo\\eclipse-workspace\\solvemaze\\test.txt.txt");
      SolvingMaze1 solve = new SolvingMaze1();
      Scanner input = new Scanner(System.in);
      System.out.println("solve maze using BFS or DFS ?");
      System.out.println("for BFS Enter 1");
      System.out.println("for DFS Enter 2");
      int choose = input.nextInt();
      switch(choose) {
      case(1):
    	    int [][] res1=solve.solveBFS(maze)   ;
            System.out.println();
            System.out.print("BFS:") ;
            printing(res1) ;
            break ;
      case(2) :
  	        int [][] res2=solve.solveDFS(maze)   ;
            System.out.println();
            System.out.print("DFS:") ;
            printing(res2) ;
            break ;
      default :
          System.out.println("Error in choice");      	
      }
		}catch (Exception e) {
			System.out.println("Error");
		}

	}

}