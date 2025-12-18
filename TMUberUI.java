import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.naming.InvalidNameException;

// Harsh Patel (501228508)

// Simulation of a Simple Command-line based Uber App 

// This system supports "ride sharing" service and a delivery service

public class TMUberUI
{
  public static void main(String[] args)
  {
    // Create the System Manager - the main system code is in here 

    TMUberSystemManager tmuber = new TMUberSystemManager();
    
    Scanner scanner = new Scanner(System.in);
    System.out.print(">");

    // Process keyboard actions
    while (scanner.hasNextLine())
    {
      String action = scanner.nextLine();

      if (action == null || action.equals("")) 
      {
        System.out.print("\n>");
        continue;
      }
      // Quit the App
      else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
        return;
      // Print all the registered drivers
      else if (action.equalsIgnoreCase("DRIVERS"))  // List all drivers
      {
        tmuber.listAllDrivers(); 
      }
      // Print all the registered users
      else if (action.equalsIgnoreCase("USERS"))  // List all users
      {
        tmuber.listAllUsers(); 
      }
      // Print all current ride requests or delivery requests
      else if (action.equalsIgnoreCase("REQUESTS"))  // List all requests
      {
        tmuber.listAllServiceRequests(); 
      }
      // Register a new driver
      else if (action.equalsIgnoreCase("REGDRIVER")) 
      {
        String name = "";
        System.out.print("Name: ");
        if (scanner.hasNextLine())
        {
          name = scanner.nextLine();
        }
        String carModel = "";
        System.out.print("Car Model: ");
        if (scanner.hasNextLine())
        {
          carModel = scanner.nextLine();
        }
        String license = "";
        System.out.print("Car License: ");
        if (scanner.hasNextLine())
        {
          license = scanner.nextLine();
        }
        String address = "";                // creating empty string for address
        System.out.print("Address: ");    // indicating to the user to enter an address
        if (scanner.hasNextLine())          // if something was entered in the next line
        {
          address = scanner.nextLine();     // give it to the address variable
        }
        
        // try-catch block to register new driver and catch the driver already exists exception and invalid address exception
        try {
          tmuber.registerNewDriver(name, carModel, license, address);
          System.out.printf("Driver: %-15s Car Model: %-15s License Plate: %-10s Address: %-15s", name, carModel, license, address); // printing the address of the driver with the other characteristics
        }
        catch (DriverAlreadyExistsException e) {
          System.out.println(e.getMessage());
        }
        catch (InvalidAddressException e) {
          System.out.println(e.getMessage());
        }
      }
      // Register a new user
      else if (action.equalsIgnoreCase("REGUSER")) 
      {
        String name = "";
        System.out.print("Name: ");
        if (scanner.hasNextLine())
        {
          name = scanner.nextLine();
        }
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        double wallet = 0.0;
        System.out.print("Wallet: ");
        if (scanner.hasNextDouble())
        {
          wallet = scanner.nextDouble();
          scanner.nextLine(); // consume nl!! Only needed when mixing strings and int/double
        }
        
        // try-catch block to register user and catchs: invalid address, insufficient wallet funds, user already exists, invalid name
        try {
          tmuber.registerNewUser(name, address, wallet);
          System.out.println();
          System.out.printf("User: %-15s Address: %-15s Wallet: %2.2f", name, address, wallet);
          System.out.println();
        }
        catch (InvalidAddressException e) {
          System.out.println(e.getMessage());
        }
        catch (InvalidWalletException e) {
          System.out.println(e.getMessage());
        }
        catch (UserAlreadyExistsException e) {
          System.out.println(e.getMessage());
        }
        catch (InvalidNameException e) {
          System.out.println(e.getMessage());
        }
      }
      // Request a ride
      else if (action.equalsIgnoreCase("REQRIDE")) 
      {
        // Get the following information from the user (on separate lines)
        // Then use the TMUberSystemManager requestRide() method properly to make a ride request
        // "User Account Id: "      (string)
        // "From Address: "         (string)
        // "To Address: "           (string)
        String accountId = "";    // initializing "accountId" to a blank string
        String from = "";         // initializing "from" to a blank string
        String to = "";           // initializing "to" to a blank string

        System.out.print("User Account Id: ");    //prinitng  to get account id 
        if (scanner.hasNextLine()) {                // checking if there was input in scanner
            accountId = scanner.nextLine();         // assign that input to accountId
        }
        System.out.print("From Address: ");       //priniting to get "from address"
        if (scanner.hasNextLine()) {                //checking if there was input in scanner
            from = scanner.nextLine();              // assign that input to "from"
        }
        System.out.print("To Address: ");         // printing to get "to address"
        if (scanner.hasNextLine()) {                //checking if there was input in scanner
            to = scanner.nextLine();                // assign that input to "from"
        }
        
        // try-catch block to request ride and catch: invalid address, user not found, invalid distance, no drivers available, insufficient funds, existing ride request
        try {
          tmuber.requestRide(accountId, from, to);
          System.out.println("RIDE for: " + tmuber.getUser(accountId).getName() + "       From: " + from + "    To: " + to);
        }
        catch (InvalidAddressException e) {
          System.out.println(e.getMessage());
        }
        catch (UserNotFoundException e) {
          System.out.println(e.getMessage());
        }
        catch (InvalidDistanceException e) {
          System.out.println(e.getMessage());
        }
        catch (NoDriversAvailableException e) {
          System.out.println(e.getMessage());
        }
        catch (InsufficientFundsException e) {
          System.out.println(e.getMessage());
        }
        catch (ExistingRideRequestException e) {
          System.out.println(e.getMessage());
        }

      }
      // Request a food delivery
      else if (action.equalsIgnoreCase("REQDLVY")) 
      {
        // Get the following information from the user (on separate lines)
        // Then use the TMUberSystemManager requestDelivery() method properly to make a ride request
        // "User Account Id: "      (string)
        // "From Address: "         (string)
        // "To Address: "           (string)
        // "Restaurant: "           (string)
        // "Food Order #: "         (string)
        String accountId = "";    // initializing "accountId" to a blank string
        String from = "";         /// initializing "from" to a blank string
        String to = "";           // initializing "to" to a blank string
        String restaurant = "";   // initializing "restaurant" to a blank string
        String foodOrderId = "";  // initializing "foodOrderId" to a blank string

        System.out.print("User Account Id: ");    //prinitng  to get account id 
        if (scanner.hasNextLine()) {              // checking if there was input in scanner
            accountId = scanner.nextLine();       // assign that input to accountId
        }
        System.out.print("From Address: ");   //prinitng  to get from address 
        if (scanner.hasNextLine()) {              // checking if there was input in scanner
            from = scanner.nextLine();            // assign that input to "from"
        }
        System.out.print("To Address: ");   //prinitng  to get to address
        if (scanner.hasNextLine()) {          // checking if there was input in scanner
            to = scanner.nextLine();            // assign that input to "to"
        }
        System.out.print("Restaurant: ");   //prinitng  to get restaurant 
        if (scanner.hasNextLine()) {          // checking if there was input in scanner
            restaurant = scanner.nextLine();      // assign that input to restaurant
        }
        System.out.print("Food Order #: ");   //prinitng  to get food order #
        if (scanner.hasNextLine()) {          // checking if there was input in scanner
            foodOrderId = scanner.nextLine();     // assign that input to foodOrderId
        }

        // try-catch block to request delivery and catch: invalid address, user not found, invalid distance, no drivers available, insufficient funds, exisitng delivery request
        try {
          tmuber.requestDelivery(accountId, from, to, restaurant, foodOrderId);
          System.out.println("DELIVERY for: " + tmuber.getUser(accountId).getName() + "       From: " + from + "    To: " + to);
        }
        catch (InvalidAddressException e) {
          System.out.println(e.getMessage());
        }
        catch (UserNotFoundException e) {
          System.out.println(e.getMessage());
        }
        catch (InvalidDistanceException e) {
          System.out.println(e.getMessage());
        }
        catch (NoDriversAvailableException e) {
          System.out.println(e.getMessage());
        }
        catch (InsufficientFundsException e) {
          System.out.println(e.getMessage());
        }
        catch (ExistingDeliveryRequestException e) {
          System.out.println(e.getMessage());
        }
      }
      // Sort users by name
      else if (action.equalsIgnoreCase("SORTBYNAME")) 
      {
        tmuber.sortByUserName();
      }
      // Sort users by number of ride they have had
      else if (action.equalsIgnoreCase("SORTBYWALLET")) 
      {
        tmuber.sortByWallet();
      }
      // Cancel a current service (ride or delivery) request
      else if (action.equalsIgnoreCase("CANCELREQ")) 
      {
        int request = -1;
        int zone = -1;                      // setting zone number to -1 incase no zone value is entered
        System.out.print("Zone #: ");     // priniting Zone # indicating to the user to enter their zone number
        if (scanner.hasNextInt())           // if the user enters an integer
        {
          zone = scanner.nextInt();         // give that entered integer to the variable zone that hold the integer zone number
          scanner.nextLine(); // consume nl character
        }
        System.out.print("Request #: ");
        if (scanner.hasNextInt())
        {
          request = scanner.nextInt();
          scanner.nextLine(); // consume nl character
        }

        // try-catch block to cancel service and catch: invalid zone number, no service available (invalid request number)
        try {
          tmuber.cancelServiceRequest(request, zone);
          System.out.println("Service Request # " + request + " in Zone " + zone + " Cancelled");      // printing which service number in which zone was cancelled
        }
        catch (InvalidZoneException e) {
          System.out.println(e.getMessage());
        }
        catch (NoServiceAvailableException e) {
          System.out.println(e.getMessage());
        }
        catch (InvalidRequestException e) {
          System.out.println(e.getMessage());
        }
      }
      // Drop-off the user or the food delivery to the destination address
      else if (action.equalsIgnoreCase("DROPOFF")) 
      {
        String driverId = "";                       // empty string for driverId
        System.out.print("Driver Id: ");          // indicating to user to enter the driverId
        if (scanner.hasNextLine())                   // checking if there was an entry
        {
          driverId = scanner.nextLine();        // giving the entry value to driverId variable
        }

        // try-catch block to drop off and catch: driver not found, invalid driver status, no service in progress to drop off
        try {
          tmuber.dropOff(driverId);
          System.out.println("Driver " + driverId + " Dropping off");   // dropping off the passenger
        }
        catch (DriverNotFoundException e) {
          System.out.println(e.getMessage());
        }
        catch (InvalidDriverStatusException e) {
          System.out.println(e.getMessage());
        }
        catch (NoServiceInProgressException e) {
          System.out.println(e.getMessage());
        }
      }
      // Get the Current Total Revenues
      else if (action.equalsIgnoreCase("REVENUES")) 
      {
        System.out.println("Total Revenue: " + tmuber.totalRevenue);
      }
      // Unit Test of Valid City Address 
      else if (action.equalsIgnoreCase("ADDR")) 
      {
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        System.out.print(address);
        if (CityMap.validAddress(address))
          System.out.println("\nValid Address"); 
        else
          System.out.println("\nBad Address"); 
      }
      // Unit Test of CityMap Distance Method
      else if (action.equalsIgnoreCase("DIST")) 
      {
        String from = "";
        System.out.print("From: ");
        if (scanner.hasNextLine())
        {
          from = scanner.nextLine();
        }
        String to = "";
        System.out.print("To: ");
        if (scanner.hasNextLine())
        {
          to = scanner.nextLine();
        }
        System.out.print("\nFrom: " + from + " To: " + to);
        System.out.println("\nDistance: " + CityMap.getDistance(from, to) + " City Blocks");
      }
      // Pickup Driver Method
      else if (action.equalsIgnoreCase("PICKUP")) {   //creating PICKUP action method
        String driverId = "";                       // empty string for driverId
        System.out.print("Driver Id: ");          // indicating to user to enter the driverId
        if (scanner.hasNextLine())                   // checking if there was an entry
        {
          driverId = scanner.nextLine();        // giving the entry value to driverId variable
        }

        // try-catch block to pick up and catch: driver not found, invalid driver status, no service available 
        try {
          tmuber.pickup(driverId);
          System.out.println("Driver " + driverId + " Picking Up in Zone " + CityMap.getCityZone(tmuber.getDriverById(driverId).getAddress()));   // picking up the passenger
        }
        catch (DriverNotFoundException e) {
          System.out.println(e.getMessage());
        }
        catch (InvalidDriverStatusException e) {
          System.out.println(e.getMessage());
        }
        catch (NoServiceAvailableException e) {
          System.out.println(e.getMessage());
        }
      }
      // Loading Users Method
      else if (action.equalsIgnoreCase("LOADUSERS")) {    //creating LOADUSERS action method
        System.out.print("User File: ");     // printing "Filename: " to the screen
        String filename = "";
        try {                                                                // using try-catch block 
          if (scanner.hasNextLine()) {            // if there is an entry then store it in the variable filename
            filename = scanner.nextLine();
            ArrayList<User> array = TMUberRegistered.loadPreregisteredUsers(filename); // getting the filename using scanner, using it as a parameter for the loadPreregisteredUsers method in TMUberRegistered
            tmuber.addUsers(array);
            System.out.println("Users Loaded");
          }
        }
        catch (FileNotFoundException e) {         // catching file not found exception
          System.out.println(filename + " Not Found");
        }
        catch (IOException e) {     // catching any other IO exceptions 
          System.exit(1);     // exiting from the program
        }
      }
      // Loading Drivers Method
      else if (action.equalsIgnoreCase("LOADDRIVERS")) {    // creating LOADDRIVERS action method
        System.out.print("Driver File: ");                                 // printing "Filename: " to the screen
        String filename = "";
        try {                                                                // using try-catch block 
          if (scanner.hasNextLine()) {            // if there is an entry then store it in the variable filename
            filename = scanner.nextLine();                                                                 
            ArrayList<Driver> array = TMUberRegistered.loadPreregisteredDrivers(filename); // getting the filename using scanner, using it as a parameter for the loadPreregisteredDrivers method in TMUberRegistered
            tmuber.addDrivers(array);
            System.out.println("Drivers Loaded");
          }
        }
        catch (FileNotFoundException e) {         // catching file not found exception
          System.out.println(filename + " Not Found");
        }
        catch (IOException e) {     // catching any other IO exceptions 
          System.exit(1);    // exiting from the program
        }
      }
      // Driving to Location Method
      else if (action.equalsIgnoreCase("DRIVETO")) {      // creating DRIVETO action method
        System.out.print("Driver Id: ");              //printing "Driver ID: " to main screen
        String driverID = scanner.nextLine();           // storing the terminal input to string variable driverID
        System.out.print("Address: ");               //printing "Address: " to main screen
        String address = scanner.nextLine();            // storing the terminal input to string variable address
        
        // try-catch block to update driver destination and catches: driver not found, invalid address
        try {
          tmuber.driveTo(driverID, address);            // using driverID and address as parameters for driveTo method in TMUberSystemManager
          System.out.println("Driver " + driverID + " Now in Zone " + CityMap.getCityZone(address));
        }
        catch (DriverNotFoundException e) {
          System.out.println(e.getMessage());
        }
        catch (InvalidAddressException e) {
          System.out.println(e.getMessage());
        }
      }

      System.out.print("\n>");
    }
  }
}

