
/* Program name			weightAdjacentStations
	Version					01-00
	Author					Jennifer Lawson
	Date Written			09-Mar-14
	Date Last Modified	10-Mar-14
	System					Java Classes
	Source					closeTunnel.java
	Phase						Development
	
	This program takes two stations input as integer values, and finds the distance between them as the crow flies.
	------------------------------------------------------------------------------
	
	Change Record:
	
	10-Mar-13 JL : Started initial Development
	
	------------------------------------------------------------------------------- 


/******************************************************************************/

/*****************************************************************************************/

import java.io.*;
import java.util.Scanner;
import java.util.*;
import graphs.*;

/*****************************************************************************************/

public class weightAdjacentStations
{
   static int _NUMBER_OF_STATIONS = 308; //was 'N'
   
	static double [][] stations = new double[_NUMBER_OF_STATIONS][_NUMBER_OF_STATIONS];
   static String []  stationNames = new String[_NUMBER_OF_STATIONS]; 	

	static int    [] StationIdentityNumber = new int[_NUMBER_OF_STATIONS]; 	
	static double [] StationLatitude       = new double[_NUMBER_OF_STATIONS];
	static double [] StationLongitude      = new double[_NUMBER_OF_STATIONS];
	static String [] StationName           = new String[_NUMBER_OF_STATIONS];

/*****************************************************************************************/

   static ArrayList<String> convert (ArrayList<Integer> m)
   {
      ArrayList<String> z= new ArrayList<String>();
	   for (Integer i:m) z.add(stationNames[i]);
	      return z;
   }
    
/*****************************************************************************************/   
   static HashSet<ArrayList<String>> convert (HashSet<ArrayList<Integer>> paths)
   {
      HashSet <ArrayList <String>> kayHashSet= new HashSet <ArrayList <String>>();
	   for (ArrayList <Integer> p:paths) kayHashSet.add(convert(p));
	      return kayHashSet;
   }
   
	
/*****************************************************************************************/
	
	public static double HaversineFormula(double lat1, double lon1, double lat2, double lon2)
	{
	/* This code uses the Haversine formula to calculate the distances between
	three points on the surface of a sphere.  It assumes the Earth is a perfect
	sphere, not an oblate spheroid.  The code has been simplified to reduce the number of repeat calculations, by adding variables instead of leaving long lines of calculations. */
	
	int EarthRadius = 6371; // km (change this constant to get miles)
	double Curvature = Math.PI / 180;
	double DifferenceInLat = (lat2-lat1) * Curvature;
	double DifferenceInLon = (lon2-lon1) * Curvature;
	
	double SineDiffInLat = Math.sin(DifferenceInLat/2);
	double CosDiffInLat1 = Math.cos(lat1 * Curvature);
	double CosDiffInLat2 = Math.cos(lat2 * Curvature);
	double SineDiffInLon = Math.sin(DifferenceInLon/2);
	
	double SineSqrDifferenceInLat = (SineDiffInLat * SineDiffInLat);
	double CosSqrDifferenceInLat  = (CosDiffInLat1 * CosDiffInLat2);
   double SineSqrDifferenceInLon = (SineDiffInLon * SineDiffInLon);
	
	double a = (SineSqrDifferenceInLat + CosSqrDifferenceInLat * SineSqrDifferenceInLon);
	
	double c = 2 * Math.asin(Math.sqrt(a));
	/*       "2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))"
	replaced with:
	         "2 * Math.asin(Math.sqrt(a))"
				
	as per comments from http://snipplr.com/view/25479/*/

	double DistanceBetweenPoints = EarthRadius * c;
	return DistanceBetweenPoints;
	}

/*****************************************************************************************/	
	
   public static void main(String[] args) throws Exception
   {
      /* See if args are valid before we open any files */
			
		for (int i = 0; i < _NUMBER_OF_STATIONS; i++)
		   {
         /* Initialise the 'stations' array with known values */
			for (int j = 0; j < _NUMBER_OF_STATIONS; j++)
			   stations[i][j] = 0.0;
			}
			
		// Open the Input stream on the file 'edges'. Note we replaced the 's' variable 
		// with MyInputStream, which is perhaps more meaningful
		//change "edges" to "stations" to accomodate Lat/long measurements
      Scanner MyInputStream = new Scanner(new FileReader("stations")); 
    	/* Go past the first line of the file because the first line contains information about the type of data contained in the file*/
		String  MyInputString = MyInputStream.nextLine();
		
			
		/* Read the line in, which should be comma-seperated values, and split into 
		   individual tokens.
		*/
      
//		System.out.println("Station \t Latitude \t Longitude \t Name");
		
		int RecordNumber = 0;
	   while (MyInputStream.hasNext())
	      {
			/* Build the matrix */
		   MyInputString = MyInputStream.nextLine();
		   String[] results = MyInputString.split(",");
			
			/* Look up details from new record set*/
         int StationID = Integer.parseInt(results[0]);
         Double StationLat = Double.parseDouble(results[1]);	
			Double StationLon = Double.parseDouble(results[2]);
         String ThisStationName = results[3];	
			
			StationIdentityNumber[RecordNumber] = StationID;
			StationLatitude[RecordNumber]       = StationLat;
	      StationLongitude[RecordNumber]      = StationLon;
	      StationName[RecordNumber]           = ThisStationName;

			++RecordNumber;
			
	      }
/*         int MyRecordNumber = 0;
			while (MyRecordNumber < RecordNumber)
			{
			   System.out.println(StationIdentityNumber[MyRecordNumber] + "\t" + StationLatitude[MyRecordNumber] + "\t" + StationLongitude[MyRecordNumber] + "\t" + StationName[MyRecordNumber]);
				MyRecordNumber++;
		   }*/
		

		
		int StationOne = Integer.parseInt(args[0]);
		int StationTwo = Integer.parseInt(args[1]);
		int StationOneRecordNumber = 0;
		int StationTwoRecordNumber = 0;

		
		int MyRecordNumber = 0;
		while (MyRecordNumber < RecordNumber)
		   {
		   if (StationIdentityNumber[MyRecordNumber] == StationOne)
				{
				StationOneRecordNumber = MyRecordNumber;
			   break;
			   }
			MyRecordNumber++;
		   }
			
	   MyRecordNumber = 0;
		while (MyRecordNumber < RecordNumber)
			{
			if (StationIdentityNumber[MyRecordNumber] == StationTwo)
			   {
				StationTwoRecordNumber = MyRecordNumber;
				break;
				}
			MyRecordNumber++;
			}
		
		//Print stations entered as integers, tells user the station names.
		System.out.println("Station one is " + StationIdentityNumber[StationOneRecordNumber] + " " + StationName[StationOneRecordNumber] + " Station two is " + StationIdentityNumber[StationTwoRecordNumber] + " " + StationName[StationTwoRecordNumber] );

		//Form is (double lat1, double lon1, double lat2, double lon2)
      double InterstationDistance = HaversineFormula(StationLatitude[StationOneRecordNumber], StationLongitude[StationOneRecordNumber], StationLatitude[StationTwoRecordNumber], StationLongitude[StationTwoRecordNumber]);
		System.out.println("Distance between stations is "  + (Math.round(InterstationDistance*1000)) + " m");
		
   }
}
  
  