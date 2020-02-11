package graphs;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.PriorityQueue;


public abstract class abstractGraph
{
  public abstract HashSet <Integer> neighbours(Integer v); 
 
 // public abstract double weight (Integer v, Integer w); 
 // public abstract Set <Integer> vertices(); 

  private     boolean done; 
  //set to true if we find what we looking for or nothing left to find

  private     HashSet <Integer> alreadyVisited; 
  // the vertices visited altogether

  private     HashSet <Integer> newAlreadyVisited; 
  // the vertices visited at this level

  private     HashSet <ArrayList <Integer>> shortestPaths;   
  private     double infinity=1000000.0;    
 
  ArrayList <Integer> addToEnd (Integer i, ArrayList <Integer> path) 
  // returns a new path with i at the end of path
    {
      ArrayList <Integer> kayHashSet;
      kayHashSet = (ArrayList<Integer>)path.clone();
      kayHashSet.add(i);
      return kayHashSet;
    } 
   
/*****************************************************************************************/   
   
   HashSet <ArrayList <Integer>> nexts(ArrayList <Integer> path, Integer end)                 
  /* given a path, finds all the paths that are one longer 
   can be made by adding a vertex 
  that hasn't already been visited at earlier levels */ 
   { 											 
      Integer last=path.get(path.size()-1);
      HashSet <ArrayList <Integer>> kayHashSet= new HashSet <ArrayList <Integer>>();
      Set <Integer> neighs=neighbours(last);
      if (!neighs.contains(end))
      { 
         for (Integer i:neighs) if (!alreadyVisited.contains(i))
            kayHashSet.add(addToEnd(i,path));
            newAlreadyVisited.addAll(neighs);
      } 
      else
	   {  
		   done=true; 
		   shortestPaths.add(addToEnd(end,path));
	   }
	   
   return kayHashSet;	
   
	}
/*****************************************************************************************/
  
 HashSet <ArrayList <Integer>> nexts(HashSet<ArrayList <Integer>> paths, Integer end) 
   /* applies nexts above to whole set of paths*/ 
   {
	
   HashSet <ArrayList <Integer>>kayHashSet= new HashSet <ArrayList <Integer>>();                              
      for (ArrayList <Integer> path:paths)  
		   kayHashSet.addAll(nexts(path,end));                    
      
		if (kayHashSet.isEmpty())
		   done=true;
			  
      return kayHashSet;	
   }
   
/*****************************************************************************************/

public  HashSet <ArrayList <Integer>> shortestPaths( Integer start, Integer end)
   {		
   shortestPaths=new HashSet <ArrayList <Integer>> ();	
	 
	alreadyVisited=new HashSet <Integer>();

	newAlreadyVisited=new HashSet <Integer>();
	 
	done=false;
	 
	ArrayList <Integer> begin = new ArrayList <Integer>(); 

	begin.add(start);

	/* Create a new HashSet (like a phone directory) from the contents of ArrayList */
	HashSet <ArrayList <Integer>> pathsSoFar= new HashSet <ArrayList <Integer>>();
	

	pathsSoFar.add(begin);
	 
	if (end==start) return pathsSoFar;
	  
	   newAlreadyVisited.add(start);

	while(!done)
	{
      alreadyVisited.addAll(newAlreadyVisited);
		
		pathsSoFar=nexts(pathsSoFar,end); 
	}		
	 
	return shortestPaths;
   }
/*****************************************************************************************/

}
