import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

// Harsh Patel (501228508)

public class TMUberRegistered
{
    // These variables are used to generate user account and driver ids
    private static int firstUserAccountID = 900;
    private static int firstDriverId = 700;

    // Generate a new user account id
    public static String generateUserAccountId(ArrayList<User> current)
    {
        return "" + firstUserAccountID + current.size();
    }

    // Generate a new driver id
    public static String generateDriverId(ArrayList<Driver> current)
    {
        return "" + firstDriverId + current.size();
    }

    // Database of Preregistered users
    // In Assignment 2 these will be loaded from a file
    // The test scripts and test outputs included with the skeleton code use these
    // users and drivers below. You may want to work with these to test your code (i.e. check your output with the
    // sample output provided). 

    // loadPreregisteredUsers method that loads them from the user file and throws IOException if not possible
    public static ArrayList<User> loadPreregisteredUsers(String filename) throws IOException, FileNotFoundException
    {
        ArrayList<User> users = new ArrayList<>();              // creating an empty arraylist for users
        File file = new File(filename);
        Scanner userInfo = new Scanner(file);     // creating a scanner to read each line from the file

        while (userInfo.hasNextLine()) {                            // while the file has information 
            String userName = userInfo.nextLine().trim();           // getting the name of the user
            String userAddress = userInfo.nextLine().trim();        // getting the address of the user
            double userWallet = Double.parseDouble(userInfo.nextLine().trim());         // getting the wallet balance of the user
            users.add(new User(generateUserAccountId(users), userName, userAddress, userWallet));   // creating a user object and adding it to the users arraylist
        }
        userInfo.close();       // closing the scanner
        return users;              // returning the arraylist of all the preregistered users
    }

    // Database of Preregistered users
    // In Assignment 2 these will be loaded from a file

    // loadPreregisteredDrivers method that loads them from the driver file and throws IOException if not possible
    public static ArrayList<Driver> loadPreregisteredDrivers(String filename) throws IOException, FileNotFoundException
    {
        ArrayList<Driver> drivers = new ArrayList<>();                  // creating an empty arraylist for drivers
        File file = new File(filename);
        Scanner driverInfo = new Scanner(file);           // creating a scanner to read each line from the file

        while (driverInfo.hasNextLine()) {                              // while the file has information 
            String driverrName = driverInfo.nextLine().trim();          // getting the name of the driver
            String carModel = driverInfo.nextLine().trim();             // getting the car model of the driver
            String carLisence = driverInfo.nextLine().trim();           // getting the car lisence of the driver
            String driverAddress = driverInfo.nextLine().trim();        // getting the address of the driver
            drivers.add(new Driver(generateDriverId(drivers), driverrName, carModel, carLisence, driverAddress, CityMap.getCityZone(driverAddress)));    // creating a driver object and adding it to the drivers arraylist
        }
        driverInfo.close();     // closing the scanner
        return drivers;         // returning the arraylist of all the preregistered drivers
    }
}

