import java.util.Arrays;
import java.util.Scanner;

// Harsh Patel (501228508)

// The city consists of a grid of 9 X 9 City Blocks

// Streets are east-west (1st street to 9th street)
// Avenues are north-south (1st avenue to 9th avenue)

// Example 1 of Interpreting an address:  "34 4th Street"
// A valid address *always* has 3 parts.
// Part 1: Street/Avenue residence numbers are always 2 digits (e.g. 34).
// Part 2: Must be 'n'th or 1st or 2nd or 3rd (e.g. where n => 1...9)
// Part 3: Must be "Street" or "Avenue" (case insensitive)

// Use the first digit of the residence number (e.g. 3 of the number 34) to determine the avenue.
// For distance calculation you need to identify the the specific city block - in this example 
// it is city block (3, 4) (3rd avenue and 4th street)

// Example 2 of Interpreting an address:  "51 7th Avenue"
// Use the first digit of the residence number (i.e. 5 of the number 51) to determine street.
// For distance calculation you need to identify the the specific city block - 
// in this example it is city block (7, 5) (7th avenue and 5th street)
//
// Distance in city blocks between (3, 4) and (7, 5) is then == 5 city blocks
// i.e. (7 - 3) + (5 - 4) 

public class CityMap
{
  // Checks for string consisting of all digits
  // An easier solution would use String method matches()
  private static boolean allDigits(String s)
  {
    for (int i = 0; i < s.length(); i++)
      if (!Character.isDigit(s.charAt(i)))
        return false;
    return  true;
  }

  // Get all parts of address string
  // An easier solution would use String method split()
  // Other solutions are possible - you may replace this code if you wish
  private static String[] getParts(String address)
  {
    String parts[] = new String[3];
    
    if (address == null || address.length() == 0)
    {
      parts = new String[0];
      return parts;
    }
    int numParts = 0;
    Scanner sc = new Scanner(address);
    while (sc.hasNext())
    {
      if (numParts >= 3)
        parts = Arrays.copyOf(parts, parts.length+1);

      parts[numParts] = sc.next();
      numParts++;
    }
    if (numParts == 1)
      parts = Arrays.copyOf(parts, 1);
    else if (numParts == 2)
      parts = Arrays.copyOf(parts, 2);
    return parts;
  }

  // Checks for a valid address
  public static boolean validAddress(String address)
  {
    // Fill in the code
    // Make use of the helper methods above if you wish
    // There are quite a few error conditions to check for 
    // e.g. number of parts != 3
    String[] parts = address.split(" ");        // spliting the address into parts (will make 3 parts because of the spaces)
    if (parts.length == 3) {        // run rest of the code if there are 3 parts in the array
      try {                                       // try-catch to check for number format and index out of bound error
        int num = Integer.parseInt(parts[0]);       // make the first set of numbers into a integer
        int num1 = Integer.parseInt(parts[1].substring(0,1));     //getting the number from the middle of the address and making it into an integer
        if (num > 9 && num < 100) {       // making sure the first part of address is a 2 digit number
          if (num1 == 1 && !address.substring(4,6).equals("st")) {return false;}  // making sure the middle part is "1st"
          if (num1 == 2 && !address.substring(4,6).equals("nd")) {return false;}  //making sure the middle part is "2nd"
          if (num1 == 3 && !address.substring(4,6).equals("rd")) {return false;}  //making sure the middle part is "3rd"
          if (num1 < 1 || num1 > 9) {return false;}         // if the middle number is not a single digit than return false
          if ((num1 >= 4 && num1 <= 9) && !address.substring(4,6).equals("th")) {return false;}     // making sure the number between 4 and 9 inclusively have "th" behind them
          if (!address.substring(7).toLowerCase().equals("street") && !address.substring(7).toLowerCase().equals("avenue")) {   // making sure that the ending is either street or avenue
            return false;
          }
        }
        else {return false;}
      } catch (NumberFormatException | StringIndexOutOfBoundsException e) {   // if there is an error, return false instead of crashing
        return false;
      }
    } 
    else {return false;}    // if its not 3 part address then return false
    return true;          // return true if everything about the address is correct
  }

  // Computes the city block coordinates from an address string
  // returns an int array of size 2. e.g. [3, 4] 
  // where 3 is the avenue and 4 the street
  // See comments at the top for a more detailed explanation
  public static int[] getCityBlock(String address)
  {
    int[] block = {-1, -1};

    // Fill in the code
    // this is beacuse the order is different depending on whether it is a street or avenue
    if (address.substring(7).toLowerCase().equals("street")) {    // if street
      block[1] = Integer.parseInt(address.substring(3,4));        // middle number should be 2nd part of array
      block[0] = Integer.parseInt(address.substring(0,1));        // first number should be 1st part of the array
    }
    else {                                                                            // if avenue
      block[0] = Integer.valueOf(address.substring(3,4));         // middle number should be 1st part of the array
      block[1] = Integer.valueOf(address.substring(0,1));         // first number should be 2nd part of array
    }
    return block;   // return the array with the address coordinates
  }
  
  // Calculates the distance in city blocks between the 'from' address and 'to' address
  // Hint: be careful not to generate negative distances
  
  // This skeleton version generates a random distance
  // If you do not want to attempt this method, you may use this default code
  public static int getDistance(String from, String to)
  {
    // Fill in the code or use this default code below. If you use
    // the default code then you are not eligible for any marks for this part

    if (!validAddress(from) || !validAddress(to)) {       // returns -1 if the addresss provided are not valid
      return -1;
    }

    int[] fromAddress = getCityBlock(from);             // gets from address coordinate
    int[] toAddress = getCityBlock(to);                 // gets to address coordinate
    
    return Math.abs(fromAddress[0] - toAddress[0]) + Math.abs(fromAddress[1] - toAddress[1]);     // returns the total calculated distance from one address to another

    // Math.random() generates random number from 0.0 to 0.999
    // Hence, Math.random()*17 will be from 0.0 to 16.999
    //double doubleRandomNumber = Math.random() * 17;
    // cast the double to whole number
    //int randomNumber = (int)doubleRandomNumber;
    //return (randomNumber);
  }

  public static int getCityZone(String address) {     // creating a getCityZone method that tasks in a string address parameter
    if (!validAddress(address)) {return -1;}          // if the address is not valid then return -1

    // getting city block and giving the numbers to the correct street and avenue
    int[] cityBlock = getCityBlock(address);
    int avenueValue = cityBlock[0];
    int streetValue = cityBlock[1];
    // if the address indicates it apart of zone 0 it will return 0
    if ((streetValue > 5 && streetValue < 10) && (avenueValue > 0 && avenueValue < 6)) {
      return 0;
    }
    // if the address indicates it apart of zone 1 it will return 1
    if ((streetValue > 5 && streetValue < 10) && (avenueValue > 5 && avenueValue < 10)) {
      return 1;
    }
    // if the address indicates it apart of zone 2 it will return 2
    if ((streetValue > 0 && streetValue < 6) && (avenueValue > 5 && avenueValue < 10)) {
      return 2;
    }
    // if the address indicates it apart of zone 3 it will return 3
    if ((streetValue > 0 && streetValue < 6) && (avenueValue > 0 && avenueValue < 6)) {
      return 3;
    }
    return -1;    // return -1 if it is not apart of any zone
  }
}
