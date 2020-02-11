import java.io.*;
import java.util.Scanner;
import java.util.*;
import graphs.*;

class ShortestDistancesQ7
{
   static int _NUMBER_OF_STATIONS = 308; //was 'N'
   
	static   double [][] edges = new double[_NUMBER_OF_STATIONS][_NUMBER_OF_STATIONS];
   static String []  stationNames = new String[_NUMBER_OF_STATIONS]; 	


   static ArrayList<String> convert (ArrayList<Integer> m)
   {
      ArrayList<String> z= new ArrayList<String>();
	   for (Integer i:m) z.add(stationNames[i]);
	      return z;
   }
    
   
   static HashSet<ArrayList<String>> convert (HashSet<ArrayList<Integer>> paths)
   {
      HashSet <ArrayList <String>> kayHashSet= new HashSet <ArrayList <String>>();
	   for (ArrayList <Integer> p:paths) kayHashSet.add(convert(p));
	      return kayHashSet;
   }
    
    
   public static void main(String[] args) throws Exception
   {
      /* See if args are valid before we open any files */
			
		for (int i = 0; i < _NUMBER_OF_STATIONS; i++)
		   {
         /* Initialise the 'edges' array with known values */
			for (int j = 0; j < _NUMBER_OF_STATIONS; j++)
			   edges[i][j] = 0.0;
			}
			
		// Open the Input stream on the file 'edges'. Note we replaced the 's' variable 
		// with MyInputStream, which is perhaps more meaningful
      Scanner MyInputStream = new Scanner(new FileReader("edges")); 
    	// Go to the first line of the file...
		String  MyInputString = MyInputStream.nextLine();
			
		/* Read the line in, which should be comma-seperated values, and split into 
		   individual tokens.
		*/
	   while (MyInputStream.hasNext())
	      {
			/* Build the matrix */
		   MyInputString = MyInputStream.nextLine();
		   String[] results = MyInputString.split(",");
         int StationOne = Integer.parseInt(results[0]);
         int StationTwo = Integer.parseInt(results[1]);				
	      edges[StationOne][StationTwo] = 1.0;
		   edges[StationTwo][StationOne] = 1.0;
	      }
      
		/* That's the edges' database set up. Now go to work on the station names */		
		MyInputStream = new Scanner(new FileReader("stations"));
    	MyInputString = MyInputStream.nextLine();
		
		while (MyInputStream.hasNext())
		   {
			MyInputString = MyInputStream.nextLine();
			String[] results = MyInputString.split(",");
         /* Build the station name array up */
         int CurrentStationID = Integer.parseInt(results[0]);
         String CurrentStationName = results[3];			
			stationNames[CurrentStationID]= CurrentStationName;	
		   }
			 
		arrayGraph MyArrayGraph = new arrayGraph(edges);
		
		int StationOne = Integer.parseInt(args[0]);
		int StationTwo = Integer.parseInt(args[1]);
		
//		int ClosedStationOne = Integer.parseInt(args[2]);
//		int ClosedStationTwo = Integer.parseInt(args[3]);

//		edges[ClosedStationOne][ClosedStationTwo] = 0.0;
//		edges[ClosedStationTwo][ClosedStationOne] = 0.0;
   
   	HashSet<ArrayList<Integer>> shortestPathOutput = MyArrayGraph.shortestPaths(StationOne, StationTwo);
		int sizeofmynewarray = shortestPathOutput.size();
  //    String[] shortestPathOutputStringArray = shortestPathOutput.toArray( new String[shortestPathOutput.size()] );

 
	//	System.out.println(convert(ClosedStationOne, ClosedStationTwo);
//		System.out.println(convert(MyArrayGraph.shortestPaths(StationOne, StationTwo)));
		System.out.println("My new array is " + sizeofmynewarray);
		System.out.println("Shortest path output = " + shortestPathOutput[0]);
		/* Close the Input Stream after use */
   } 	
}
  
  