/* Program name			weightAdjacentStationsQ3
	Version					01-00
	Author					Jennifer Lawson
	Date Written			09-Feb-14
	Date Last Modified	10-Feb-14
	System					Java Classes
	Source					closeTunnel.java
	Phase						Development
	
	This program takes two stations input as integer values, and finds the distance between them as the crow flies.
	------------------------------------------------------------------------------
	
	Change Record:
	
	10-Feb-14	JL : Started initial Development
	
	------------------------------------------------------------------------------- 

*/

/*****************************************************************************************/

import java.io.*;
import java.util.Scanner;
import java.util.*;
import graphs.*;

/*****************************************************************************************/

public class weightAdjacentStationsQ3
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
		
		
/*         int MyRecordNumber = 0;
			while (MyRecordNumber < RecordNumber)
			{
			   System.out.println(StationIdentityNumber[MyRecordNumber] + "\t" + StationLatitude[MyRecordNumber] + "\t" + StationLongitude[MyRecordNumber] + "\t" + StationName[MyRecordNumber]);
				MyRecordNumber++;
		   }
			//Form is (double lat1, double lon1, double lat2, double lon2)
         double InterstationDistance = HaversineFormula(StationLatitude[StationOneRecordNumber], StationLongitude[StationOneRecordNumber], StationLatitude[StationTwoRecordNumber], StationLongitude[StationTwoRecordNumber]);
			
			*/
	
					
		// Open the Input stream on the file 'edges'. Note we replaced the 's' variable 
		// with MyInputStream, which is perhaps more meaningful
      MyInputStream = new Scanner(new FileReader("edges")); 
    	// Skip past the first line, which contains formatting information to the first line of the file...
		MyInputString = MyInputStream.nextLine();
			
		/* Read the line in, which should be comma-seperated values, and split into 
		   individual tokens.
		*/
		int NumberOfEdgesRead = 0;
		double MyFurthestConnectedStationDistance = 0;
		int StationOneFurthest = 0;
		int StationTwoFurthest = 0;
		String StationOneTempName;
		String StationTwoTempName;
	   while (MyInputStream.hasNext())
	      {
			/* Build the matrix */
		   MyInputString = MyInputStream.nextLine();
		   String[] results = MyInputString.split(",");
         int StationOne = Integer.parseInt(results[0]);
         int StationTwo = Integer.parseInt(results[1]);
         int StationOneRecordNumber  = GetStationRecordNumber(StationOne, NumberOfRecordsRead);			
         int StationTwoRecordNumber  = GetStationRecordNumber(StationTwo, NumberOfRecordsRead);			
         double StationOneLatitude   = StationLatitude[StationOneRecordNumber];
         double StationOneLongitude  = StationLongitude[StationOneRecordNumber];
         double StationTwoLatitude   = StationLatitude[StationTwoRecordNumber];
         double StationTwoLongitude  = StationLongitude[StationTwoRecordNumber];
         double InterstationDistance = HaversineFormula(StationOneLatitude, StationOneLongitude, StationTwoLatitude, StationTwoLongitude);

	      edges[StationOne][StationTwo] = InterstationDistance;
		   edges[StationTwo][StationOne] = InterstationDistance;

			System.out.println("Station 1 " + StationOne + "(" + StationOneLatitude + ", " + StationOneLongitude + "), Station 2 " + StationTwo + " (" + StationTwoLatitude + ", " + StationTwoLongitude + ") : " + InterstationDistance);

			if (InterstationDistance > MyFurthestConnectedStationDistance)
				{
				MyFurthestConnectedStationDistance = InterstationDistance;
				StationOneFurthest = StationOneRecordNumber;
				StationTwoFurthest = StationTwoRecordNumber;
				}
			NumberOfEdgesRead++;
	      }
	
   int StationOneID = StationIdentityNumber[StationOneFurthest];
   int StationTwoID = StationIdentityNumber[StationTwoFurthest];	
	double InterstationDistance = edges[StationOneID][StationTwoID];
	System.out.println("The stations which are furthest apart are: Station 1 " + StationName[StationOneFurthest] + "(" + StationIdentityNumber[StationOneFurthest] + ") and " +  StationName[StationTwoFurthest] + "(" + StationIdentityNumber[StationTwoFurthest] + ") : Distance " + Math.round(InterstationDistance * 1000) + " m");		
   }
}
  
  