package graphs;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;

public class arrayGraph extends abstractGraph
{
	
        double [][] adj; 

        public arrayGraph (double [][] ad) 
        {
           adj = ad;
        }
	
        

        public HashSet <Integer> neighbours(Integer v)
        {
			   HashSet kayHashSet = new HashSet <Integer>();
			   for (int j = 0; j < adj.length; j++) 
				   {
					if (adj[v][j] > 0)
					   kayHashSet.add(j);
			      }
				return kayHashSet;  //used to be k
        }	

}
