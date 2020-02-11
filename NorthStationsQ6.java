/* Program name			NorthStationsQ6
	Version					01-00
	Author					Jennifer Lawson
	Date Written			11-Feb-14
	Date Last Modified	11-Feb-14
	System					Java Classes
	Source					WestStationsQ5.java
	Phase						Development
	
	This program takes two stations input as integer values, and uses a bubblesort algorithm,
	from StackExchange, found at:
	http://stackoverflow.com/questions/16266611/how-to-make-bubble-sort-in-java-to-output-the-sorted-numbers
	
	------------------------------------------------------------------------------
	
	Change Record:
	
	11-Feb-14	JL : Started initial Development
	
	------------------------------------------------------------------------------- 

*/

/*****************************************************************************************/

import java.io.*;
import java.util.Scanner;
import java.util.*;
import graphs.*;

/*****************************************************************************************/

public class NorthStationsQ6
{
   static int _NUMBER_OF_STATIONS = 308; //was 'N'
   
	static double [][] edges = new double[_NUMBER_OF_STATIONS][_NUMBER_OF_STATIONS];
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
		
/* Call this method to look up the station record number based on the station
   identifier passed as a parameter. 
*/

static int GetStationRecordNumber(int StationIDNumber, int NumberOfRecordsInArray)
{
int StationRecordNumber = -1;
int RecordNumber = 0;

	while (RecordNumber < NumberOfRecordsInArray)
		{
		if (StationIdentityNumber[RecordNumber] == StationIDNumber)
			{
			StationRecordNumber = RecordNumber;
			break;
			}
		RecordNumber++;
		}
	
	return StationRecordNumber;			
}

/*****************************************************************************************/

/* BubbleSort() performs a bubble sort on an array of numbers. 
*/

private static void BubbleSort(double[] StationLatitude) 
{
	for (int i = 0; i < StationLatitude.length; i++) 
		{
		for (int x = 1; x < StationLatitude.length - i; x++) 
			{
			if (StationLatitude[x - 1] > StationLatitude[x]) 
				{
            double temp             = StationLatitude[x - 1];
            StationLatitude[x - 1] = StationLatitude[x];
            StationLatitude[x]     = temp;
				
				/* Station ID next...*/
				int StationIDtemp            = StationIdentityNumber[x - 1];
				StationIdentityNumber[x - 1] = StationIdentityNumber[x];
				StationIdentityNumber[x]     = StationIDtemp;
				
				/* Now do the Station Name */
				String TempString  = StationName[x - 1];
				StationName[x - 1] = StationName[x];
				StationName[x]     = TempString;
				}
			}
		}
   System.out.println("Sorted!");
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
		int NumberOfRecordsRead = 0;
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
			
		NumberOfRecordsRead = RecordNumber;
		
		/* Call BubbleSort method to sort by Longitude */
		BubbleSort(StationLatitude);
	
		/* Now print out the results */
		for (RecordNumber = 0; RecordNumber < NumberOfRecordsRead; ++RecordNumber)
			System.out.println("Station " + StationName[RecordNumber] + "(" + StationIdentityNumber[RecordNumber] + "), Lat = " + StationLatitude[RecordNumber]);
   
		/* The spec says that out required station has n + 1 stations to the North than 
         there are to the South. So, considering the stations are ordered  want the RequiredStationNumber = (NumberOfRecordsRead / 2) + 1;
		*/	
	   int RequiredStationNumber = (NumberOfRecordsRead / 2) + 1;
		System.out.println("The required station is " + StationName[RequiredStationNumber] + " (" + StationIdentityNumber[RequiredStationNumber] + ") Latitude = " + StationLatitude[RequiredStationNumber]);
	}
}
  
  