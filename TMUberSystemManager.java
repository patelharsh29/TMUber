import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.LinkedHashMap;

import javax.naming.InvalidNameException;

import java.util.Queue;

// Harsh Patel (501228508)

/*
 * 
 * This class contains the main logic of the system.
 * 
 *  It keeps track of all users, drivers and service requests (RIDE or DELIVERY)
 * 
 */
public class TMUberSystemManager
{
  private Map<String, User> users;
  private ArrayList<User>   usersList;
  private ArrayList<Driver> drivers;

  @SuppressWarnings("unchecked") // to aviod the unchecked operations error
  private Queue<TMUberService>[] serviceQueues = new Queue[4];   // creating a serviceQueues array for service requests

  public double totalRevenue; // Total revenues accumulated via rides and deliveries
  
  // Rates per city block
  private static final double DELIVERYRATE = 1.2;
  private static final double RIDERATE = 1.5;
  // Portion of a ride/delivery cost paid to the driver
  private static final double PAYRATE = 0.1;

  //These variables are used to generate user account and driver ids
  int userAccountId = 900;
  int driverId = 700;

  public TMUberSystemManager()
  {
    users = new LinkedHashMap<>();
    usersList = new ArrayList<User>();
    drivers = new ArrayList<Driver>();
    for (int i = 0; i < 4; i++) {     // looping through the queue 4 times as per its size and adding a linkedlist for each section in the queue
        serviceQueues[i] = new LinkedList<TMUberService>();
    }
    
    totalRevenue = 0;
  }

  public void addServiceRequest(TMUberService service, String address) {    // method to add service
    int zone = CityMap.getCityZone(address);    // getting zone value
    Queue<TMUberService> currenQueue = serviceQueues[zone];   // putting the current queue in the variable currentQueue
    currenQueue.add(service);           // adding service to the specific zone queue
  }

  // General string variable used to store an error message when something is invalid 
  // (e.g. user does not exist, invalid address etc.)  
  // The methods below will set this errMsg string and then return false
  
  // Given user account id, find user in list of users
  // Return null if not found
  public User getUser(String accountId) throws UserNotFoundException
  {
    // Fill in the code
    for (User user : users.values()) {  // Loops through the users arraylist and using the variable name user to represent each user in the users ArrayList.
      if (user.getAccountId().equals(accountId)) {return user;} // If the accountId from one of the User classes equals the accountId given by the input than we return that user from the ArrayList.
    }
    throw new UserNotFoundException("User not found");  // throwing exception if the user does not exsit
  }
  
  // Check for duplicate user
  private boolean userExists(User user)
  {
    // Fill in the code
    for (User dUser : users.values()) { // Checking if there are any duplicate users by looping through the ArrayList of users and if we find any users that match we return true. 
      if (dUser.equals(user)) {return true;}
    }
    return false; //Or else we return false
  }
  
  // adding the users to the usersList - helping the loadusers method
  public void addUsers (ArrayList<User> usersArray) {
    for (User user : usersArray) {
      users.put(user.getAccountId(), user);
    }
  }

  // adding the drivers to the driverList - helping the loaddrivers method
  public void addDrivers (ArrayList<Driver> driversArray) {
    for (Driver driver : driversArray) {
      drivers.add(driver);
    }
  }
  
 // Check for duplicate driver
 private boolean driverExists(Driver driver)
 {
   // Fill in the code
   for (Driver dDriver : drivers) { // Checking if there are any duplicate drivers by looping through the ArrayList of drivers and if we find any drivers that match we return true. 
    if (dDriver.equals(driver)) {return true;}
   }
   return false; // Or else we return false
 }
  
  // Given a user, check if user ride/delivery request already exists in service requests
  private boolean existingRequest(TMUberService req)
  {
    // Fill in the code
    for (Queue<TMUberService> zone : serviceQueues) {     // looping through the serviceQueues to check if the request made already exists
      if (zone.contains(req)) {return true;}
    }
    return false; // Or else we return false
  }

  // Calculate the cost of a ride or of a delivery based on distance 
  private double getDeliveryCost(int distance) 
  {
    return distance * DELIVERYRATE; 
  }

  private double getRideCost(int distance)
  {
    return distance * RIDERATE;
  }

  // Go through all drivers and see if one is available
  // Choose the first available driver
  // Return null if no available driver
  private Driver getAvailableDriver()
  {
    // Fill in the code
    for (Driver aDriver : drivers) { // So we are looping through the drivers ArrayList to see if any of the drivers have a status: AVAILABLE, and if we find a driver that does we return that driver as aDriver.
      if (aDriver.getStatus() == Driver.Status.AVAILABLE) {return aDriver;}
    }
    return null; //Or else we return nothing
  }

  // Print Information (printInfo()) about all sorted users in the system
  public void listAllUsersSorted()
  {
    System.out.println();
    
    int index = 0;
    for (User user : usersList)
    {
      index++;
      System.out.printf("%-2s. ", index);
      user.printInfo();
      System.out.println(); 
    }
  }

  // Print Information (printInfo()) about all registered users in the system
  public void listAllUsers()
  {
    System.out.println();
    
    int index = 0;
    for (User user : users.values())
    {
      index++;
      System.out.printf("%-2s. ", index);
      user.printInfo();
      System.out.println(); 
    }
  }

  // Print Information (printInfo()) about all registered drivers in the system
  public void listAllDrivers()
  {
    // Fill in the code 
    System.out.println(); // Looping through the drivers ArrayList of type Driver to display all drivers.
    for (int i = 0; i < drivers.size(); i++) { 
        int index = i + 1; // The reason there is a int index = i + 1 is because we know the indexing starts from 0 so we do not want to call the first driver 0, instead we want to call the first driver 1. and the next driver 2. and so on which is why we add 1 to i each time in the for loop.
        System.out.printf("%-2s. ", index);
        drivers.get(i).printInfo(); // We want to print each drivers info so the drivers.get(i) gets that drivers particular class and than the .printInfo() method prints that drivers information.
        System.out.println(); // Also added System.out.println() to break line each time.
    }
  }

  // Print Information (printInfo()) about all current service requests
  public void listAllServiceRequests() // 
  {
    // Fill in the code
    for (int i = 0; i < serviceQueues.length ; i++) { // Looping through the serviceQueues of type TMUberService to display all serviceRequests.
      System.out.println();
      System.out.println("Zone " + i);    // printing the zone and service requests strings to display for the user
      System.out.println("======");
      Queue<TMUberService> zone = serviceQueues[i];     // getting the zone queue 
      int counter = 1;    // number counter
      for (TMUberService service : zone) {          // looping through the zone services and printing the info of each service
        System.out.println();
        System.out.println(counter + ". " + "-".repeat(70));
        counter++;
        service.printInfo();
        System.out.println();
      }
      System.out.println();     // break line to make the display neat
    }
  }

  // Add a new user to the system
  public void registerNewUser(String name, String address, double wallet) throws InvalidAddressException, InvalidWalletException, UserAlreadyExistsException, InvalidNameException
  {
    // Fill in the code. Before creating a new user, check paramters for validity
    // See the assignment document for list of possible erros that might apply
    // Write the code like (for example):
    // if (address is *not* valid)
    // {
    //    set errMsg string variable to "Invalid Address "
    //    return false
    // }
    // If all parameter checks pass then create and add new user to array list users
    // Make sure you check if this user doesn't already exist!
    if (!CityMap.validAddress(address) || address == null || address.isEmpty()) { //If the new user does not enter a valid address, then we throw an invalid address exception
      throw new InvalidAddressException("Invalid Address");
    }
    if (name == null || name.isEmpty()) { //If the new user does not enter a valid address, then we throw an invalid address exception
      throw new InvalidNameException("Invalid Name");
    }
    if (wallet < 0) { // If the user has a wallet balance less than 0 then we throw an insufficient funds exception
      throw new InvalidWalletException("Insufficient Funds in Wallet");
    }

    String newUserId = "" + userAccountId + users.size();   // creating new Id for the new user
    User newUser = new User(newUserId, name, address, wallet);    // creating a user object for the new user
    if (userExists(newUser)) { // If the user already exists then we throw a user already exists exception
      throw new UserAlreadyExistsException("User already exists");
    }

    // If the cases above pass and none of them throw exception then we are safe to add another user in our users ArrayList of type User.
    ArrayList<User> temp = new ArrayList<>();
    temp.add(newUser);
    addUsers(temp);
  }

  // Add a new driver to the system
  public void registerNewDriver(String name, String carModel, String carLicencePlate, String address) throws DriverAlreadyExistsException, InvalidAddressException
  {
    // Fill in the code - see the assignment document for error conditions
    // that might apply. See comments above in registerNewUser
    if (!CityMap.validAddress(address)) { //If the new user does not enter a valid address, then we throw an invalid address exception
      throw new InvalidAddressException("Invalid Address");
    }
    Driver newDriver = new Driver(TMUberRegistered.generateDriverId(drivers), name, carModel, carLicencePlate, address, CityMap.getCityZone(address)); // Creating a new Driver object, with that drivers name, the car the driver has and the drivers licenseplate.
    if (driverExists(newDriver)) { // If the driver already exists then we throw a driver already exists exception
      throw new DriverAlreadyExistsException("Driver already exists");
    }

    drivers.add(newDriver); // If the case above passes and does not throw exception then we can add the newDriver to our ArrayList of Driver.
  }

  // Request a ride. User wallet will be reduced when drop off happens
  public void requestRide(String accountId, String from, String to) throws InvalidAddressException, UserNotFoundException, InvalidDistanceException, NoDriversAvailableException, InsufficientFundsException, ExistingRideRequestException
  {
    // Check for valid parameters
	// Use the account id to find the user object in the list of users
    // Get the distance for this ride
    // Note: distance must be > 1 city block!
    // Find an available driver
    // Create the TMUberRide object
    // Check if existing ride request for this user - only one ride request per user at a time!
    // Change driver status
    // Add the ride request to the list of requests
    // Increment the number of rides for this user
    if (!CityMap.validAddress(from) || !CityMap.validAddress(to)) { // checking valid address for from and to, throws invalid address if any are not valid
      throw new InvalidAddressException("Invalid Address");
    }

    User user = getUser(accountId); //getting the Users accountId
    if (user == null) { // If the users accountId does not exist or not found then user not found exception is thrown
      throw new UserNotFoundException("User Not Found");
    }

    int distance = CityMap.getDistance(from, to); // We get the distance from the address the user wants to get picked up from to the address the user wants to get dropped off to.
    if (distance <= 0) { // If the distance is 0 then that means the user does not need a ride because the user is not travelling any distance and if the distance is less than 0 then that means user entered invalid address. Thus, we throw insufficient distance exception.
      throw new InvalidDistanceException("Insufficient Travel Distance");

    }

    Driver driver = getAvailableDriver(); //Getting the next available driver to give the ride to the user.
    if (driver == null) { //If there is no driver available then no drivers available exception is thrown
      throw new NoDriversAvailableException("No Drivers Available");
    }

    double rideCost = distance * RIDERATE; //Getting the total cost of the ride for the user if the user decides to take the ride.
    if (rideCost > user.getWallet()) { // if the cost of the ride is more than the user can afford or the total balance the user has in the users account then we throw insufficient funds exception
      throw new InsufficientFundsException("Insufficient Funds");
    }

    TMUberRide newRide = new TMUberRide(from, to, user, distance, distance * RIDERATE); // if no exception is thrown that means we are safe to request a ride for the user
    if (existingRequest(newRide)) {   // if there is already an exisiting ride for this user then a ride already exists for this user exception is thrown
      throw new ExistingRideRequestException("Ride Already Exists For This User");
    }

    addServiceRequest(newRide, from); // adding the new ride request we created above into the servicerequests.
    user.addRide(); // we add the ride the user has taken or is going to take to the users class.
    System.out.println();
  }

  // Request a food delivery. User wallet will be reduced when drop off happens
  public void requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId) throws InvalidAddressException, UserNotFoundException, InvalidDistanceException, NoDriversAvailableException, InsufficientFundsException, ExistingDeliveryRequestException
  {
    // See the comments above and use them as a guide
    // For deliveries, an existing delivery has the same user, restaurant and food order id
    // Increment the number of deliveries the user has had
    if (!CityMap.validAddress(from) || !CityMap.validAddress(to)) { // checking valid address for from and to, throws invalid address if any are not valid
      throw new InvalidAddressException("Invalid Address");
    }

    User user = getUser(accountId); //getting the Users accountId
    if (user == null) { // If the users accountId does not exist or not found then user not found exception is thrown
      throw new UserNotFoundException("User Not Found");
    }

    int distance = CityMap.getDistance(from, to); //getting the distance for the from address to to address for the delivery
    if (distance <= 0) { // if the distance is 0 or less than 0 then insufficient travel distance exception is thrown
      throw new InvalidDistanceException("Insufficient Travel Distance");
    }

    Driver driver = getAvailableDriver(); //get next available driver
    if (driver == null) { //if no available driver is found then no drivers available exception is thrown
      throw new NoDriversAvailableException("No Drivers Available");
    }

    double deliveryCost = distance * DELIVERYRATE; //getting the cost of the delivery for user.
    if (deliveryCost > user.getWallet()) { // if the cost of this delivery is greater than the amount of money the user has in the users wallet then we cannot do the delivery as user cannot pay for it and insifficient funds exception is thrown
      throw new InsufficientFundsException("Insufficient Funds");
    }

    TMUberDelivery newDelivery = new TMUberDelivery(from, to, user, distance, distance * DELIVERYRATE, restaurant, foodOrderId); //if the cases above pass we can initiate this delivery.
    if (existingRequest(newDelivery)) {   // if there is already an exisiting delivery for this user then a delivery already exists for this user exception is thrown
      throw new ExistingDeliveryRequestException("Delivery Already Exists For This User");
    }

    addServiceRequest(newDelivery, from);   // adding delivery to the queue of requests
    user.addDelivery(); //add this delivery request to the user profile that is requesting it 
    System.out.println();
  }


  // Cancel an existing service request. 
  // parameter int request is the index in the serviceRequests array list
  public void cancelServiceRequest(int request, int zone) throws InvalidZoneException, NoServiceAvailableException, InvalidRequestException
  {
    // Check if valid request #
    // Remove request from list
    // Also decrement number of rides or number of deliveries for this user
    // since this ride/delivery wasn't completed
    if (zone < 0 || zone >= serviceQueues.length) { // if zone value is not between 0 and 3 then throws invalid zone number exception
      throw new InvalidZoneException("Invalid Zone #");
    }

    if (serviceQueues[zone].isEmpty()) {    // if the queue for a zone is empty then throws no services requests in zone
      throw new NoServiceAvailableException("No service requests in zone " + zone);
    }

    if (request < 1 || serviceQueues[zone].size() < request) {    // if the request number is less than 1 or greater than number of requests in the queue then throws invalid request number exception
      throw new InvalidRequestException("Invalid Request #");
    }

    //steps to remove request from zone
    Iterator<TMUberService> iterator = serviceQueues[zone].iterator();  // make iterator to go through the queues
    int count = 1;
    // while there is a request in a queue
    while (iterator.hasNext()) {
      TMUberService cancelService = iterator.next();    // holding the service in a variable incase it is the one getting removed
      // if the request number matches
      if (count == request) {
        iterator.remove();  // remove that request
        // if its a ride request then decrement the ride number for the user
        if (cancelService instanceof TMUberRide) {
          TMUberRide cancelRide = (TMUberRide) cancelService;
          User user = cancelRide.getUser();
          user.subtractRide();
        }
        // if its a delivery request then decrement the delivery number for the user
        else if (cancelService instanceof TMUberDelivery) {
          TMUberDelivery cancelDelivery = (TMUberDelivery) cancelService;
          User user = cancelDelivery.getUser();
          user.subtractDelivery();
        }
        return;   // end after we have found and removed the request
      }
      count++;
    }
  }
  
  // Drop off a ride or a delivery. This completes a service.
  // parameter request is the index in the serviceRequests array list
  public void dropOff(String driverId) throws DriverNotFoundException, InvalidDriverStatusException, NoServiceInProgressException
  {
    // See above method for guidance
    // Get the cost for the service and add to total revenues
    // Pay the driver
    // Deduct driver fee from total revenues
    // Change driver status
    // Deduct cost of service from user

    Driver driver = getDriverById(driverId);    // getting driver object with the ID
    if (driver == null) {   // if driver is not found then a driver not found exception is thrown
      throw new DriverNotFoundException("Driver Not Found");
    }

    if (driver.getStatus() != Driver.Status.DRIVING) {  // if the driver is avaiable and not driving then a driver is not currently driving exception is thrown because you cannot drop a passenger off if you have not pickup the passenger
      throw new InvalidDriverStatusException("Driver is not currently driving");
    }

    TMUberService completedService = driver.getService();   // getting the service that the driver is doing
    if (completedService == null) {   // if the driver is doing nothing then no service in progress for this driver exception is thrown
      throw new NoServiceInProgressException("No Service in Progress for this driver");
    }

    //getting service cost and adding it to revenue
    double cost = completedService.getCost();
    totalRevenue += cost;

    // getting driverFee, paying the driver and subtracting that from the total revenue
    double driverFee = cost * PAYRATE;
    driver.pay(driverFee);
    totalRevenue -= driverFee;

    // getting the user that the service was completed for and paying the cost from the uesr wallet
    User user = completedService.getUser();
    user.payForService(cost);

    // resetting the driver to avaiable and changing to its new location address and zone that it dropped off at 
    driver.setStatus(Driver.Status.AVAILABLE);
    driver.setService(null);
    driveTo(driverId, completedService.getTo());   // updating driver address and zone location
  }

  // pickup method for a service that takes in driverId as a parameter
  public void pickup(String driverId) throws DriverNotFoundException, InvalidDriverStatusException, NoServiceAvailableException {
    Driver driver = getDriverById(driverId);    // gets driver by its Id
    if (driver == null) {   // if driver does not exists then driver not found exception is thrown
      throw new DriverNotFoundException("Driver Not Found");
    }

    if (driver.getStatus() != Driver.Status.AVAILABLE) {  // if driver is not avaible for pickup then driver is not currently available exception is thrown
      throw new InvalidDriverStatusException("Driver Not Available");
    }

    Queue<TMUberService> currentQueue = serviceQueues[CityMap.getCityZone(driver.getAddress())];  // gets the Queue of the specific zone 
    if (currentQueue.isEmpty()) { // if the queue is empty then no service avaible in this zone is thrown
      throw new NoServiceAvailableException("No Service Request in Zone " + CityMap.getCityZone(driver.getAddress()));
    }

    TMUberService nextService = currentQueue.poll();  // retrives the next service in the queue for pickup
    driver.setService(nextService);   // gives driver that service
    driver.setStatus(Driver.Status.DRIVING);  //changes status to driving
    driveTo(driverId, nextService.getFrom());   // updating driver address and zone location
  } 

  // driveTo method to update driver address and zone
  public void driveTo(String driverId, String address) throws DriverNotFoundException, InvalidAddressException {
    Driver driver = getDriverById(driverId);    // getting the driver by its Id
    if (driver == null) { // if it does not exist then driver not found exception is thrown
      throw new DriverNotFoundException("Driver Not Found");
    }

    if (!CityMap.validAddress(address)) {   // if the addres is not valid then invalid address exception is thrown
      throw new InvalidAddressException("Invalid Address");
    }

    // updates the drivers address and zone
    driver.setAddress(address);
    driver.setZone(CityMap.getCityZone(address));
  }

  // method to get driver by its Id
  public Driver getDriverById(String driverId) {
    for (Driver driver : drivers) {   // goes through the drivers arraylist
      if (driver.getId().equals(driverId)) {return driver;} // if the any driver has the same id then returns the driver else null
    }
    return null;
  } 

  // Sort users by name
  // Then list all users
  public void sortByUserName()
  {
    usersList = new ArrayList<>(users.values());
    Collections.sort(usersList, new NameComparator());
    listAllUsersSorted();
  }

  // Helper class for method sortByUserName
  private class NameComparator implements Comparator<User>
  {
    public int compare(User user1, User user2) {
      return user1.getName().compareTo(user2.getName());
    }
  }

  // Sort users by number amount in wallet
  // Then ist all users
  public void sortByWallet()
  {
    usersList = new ArrayList<>(users.values());
    Collections.sort(usersList, new UserWalletComparator());
    listAllUsersSorted();
  }
  // Helper class for use by sortByWallet
  private class UserWalletComparator implements Comparator<User>
  {
    public int compare(User user1, User user2) {
      return Double.compare(user1.getWallet(), user2.getWallet());
    }
  }

}

// creating an extended RuntimeException InvalidAddressException
class InvalidAddressException extends RuntimeException {
  public InvalidAddressException(String msg) {
      super(msg);
  }
}

// creating an extended RuntimeException InvalidWalletException
class InvalidWalletException extends RuntimeException {
  public InvalidWalletException(String msg) {
      super(msg);
  }
}

// creating an extended RuntimeException UserAlreadyExistsException
class UserAlreadyExistsException extends RuntimeException {
  public UserAlreadyExistsException(String msg) {
      super(msg);
  }
}

// creating an extended RuntimeException DriverAlreadyExistsException
class DriverAlreadyExistsException extends RuntimeException {
  public DriverAlreadyExistsException(String msg) {
      super(msg);
  }
}

// creating an extended RuntimeException UserNotFoundException
class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String msg) {
      super(msg);
  }
}

// creating an extended RuntimeException InvalidDistanceException
class InvalidDistanceException extends RuntimeException {
  public InvalidDistanceException(String msg) {
      super(msg);
  }
}

// creating an extended RuntimeException NoDriversAvailableException
class NoDriversAvailableException extends RuntimeException {
  public NoDriversAvailableException(String msg) {
      super(msg);
  }
}

// creating an extended RuntimeException InsufficientFundsException
class InsufficientFundsException extends RuntimeException {
  public InsufficientFundsException(String msg) {
      super(msg);
  }
}

// creating an extended RuntimeException ExistingDeliveryRequestException
class ExistingDeliveryRequestException extends RuntimeException {
  public ExistingDeliveryRequestException(String msg) {
      super(msg);
  }
}

// creating an extended RuntimeException ExistingRideRequestException
class ExistingRideRequestException extends RuntimeException {
  public ExistingRideRequestException(String msg) {
      super(msg);
  }
}

// creating an extended RuntimeException InvalidRequestException
class InvalidRequestException extends RuntimeException {
  public InvalidRequestException(String msg) {
      super(msg);
  }
}

// creating an extended RuntimeException InvalidZoneException
class InvalidZoneException extends RuntimeException {
  public InvalidZoneException(String msg) {
      super(msg);
  }
}

// creating an extended RuntimeException DriverNotFoundException
class DriverNotFoundException extends RuntimeException {
  public DriverNotFoundException(String msg) {
      super(msg);
  }
}

// creating an extended RuntimeException InvalidDriverStatusException
class InvalidDriverStatusException extends RuntimeException {
  public InvalidDriverStatusException(String msg) {
      super(msg);
  }
}

// creating an extended RuntimeException NoServiceInProgressException
class NoServiceInProgressException extends RuntimeException {
  public NoServiceInProgressException(String msg) {
      super(msg);
  }
}

// creating an extended RuntimeException NoServiceAvailableException
class NoServiceAvailableException extends RuntimeException {
  public NoServiceAvailableException(String msg) {
      super(msg);
  }
}