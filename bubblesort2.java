import java.util.Scanner;

public class BubbleSort2 {

/**
 * @param args the command line arguments
 */
public static void main(String[] args)
{
    Scanner read = new Scanner (System.in);
    int[] num = new int[15];
    int size = 15;

    System.out.println("Enter 15 numbers: ");
    for (int i=0; i <= size-1; i++)
    {
        num[i] = read.nextInt();

    }

    for (int i=0; i <= size-1; i++)
    {
        if (num[i] >=1 && num[i] <= 1000)
       {
        System.out.println("The numbers you entered are: ");
        System.out.println(+num[0]);
        System.out.println(+num[1]);
        System.out.println(+num[2]);
        System.out.println(+num[3]);
        System.out.println(+num[4]);
        System.out.println(+num[5]);
        System.out.println(+num[6]);
        System.out.println(+num[7]);
        System.out.println(+num[8]);
        System.out.println(+num[9]);
        System.out.println(+num[10]);
        System.out.println(+num[11]);
        System.out.println(+num[12]);
        System.out.println(+num[13]);
        System.out.println(+num[14]);
     }
    else
    {
        System.out.println("Data input is invalid. Enter a number between "
                +
                "1 and 1000.");
        break;
    }
    }

    BubbleSort (num);
    for (int i=0; i < num.length; i++)
    {
        System.out.println("The sorted numbers are: ");
        System.out.print(num[i]+ " ");
    }

}

/*****************************************************************************/

/* BubbleSort() performs a bubble sort on an array of numbers. 
*/

private static void BubbleSort(int[] num) 
{
	for (int i = 0; i < num.length; i++) 
		{
		for (int x = 1; x < num.length - i; x++) 
			{
			if (num[x - 1] > num[x]) 
				{
            int temp = num[x - 1];
            num[x - 1] = num[x];
            num[x] = temp;
				}
			}
		}
}

/*****************************************************************************/

}