/*
 * 
 * This class simulates a food delivery service for a simple Uber app
 * 
 * A TMUberDelivery is-a TMUberService with some extra functionality
 */

 // Harsh Patel (501228508)

public class TMUberDelivery extends TMUberService
{
  public static final String TYPENAME = "DELIVERY";
 
  private String restaurant; 
  private String foodOrderId;
   
   // Constructor to initialize all inherited and new instance variables 
  public TMUberDelivery(String from, String to, User user, int distance, double cost,
                        String restaurant, String order)
  {
    // Fill in the code - make use of the super method
    super(from, to, user, distance, cost, TYPENAME);  // super the parameters that are not being initialized in this file
    this.restaurant = restaurant;   // setting this.restaurant to the restaurant parameter
    this.foodOrderId = order;       // setting this.foodOrderId to the order (order number) parameteres
  }
 
  
  public String getServiceType()
  {
    return TYPENAME;
  }
  public String getRestaurant()
  {
    return restaurant;
  }
  public void setRestaurant(String restaurant)
  {
    this.restaurant = restaurant;
  }
  public String getFoodOrderId()
  {
    return foodOrderId;
  }
  public void setFoodOrderId(String foodOrderId)
  {
    this.foodOrderId = foodOrderId;
  }
  /*
   * Two Delivery Requests are equal if they are equal in terms of TMUberServiceRequest
   * and the restaurant and food order id are the same  
   */
  public boolean equals(Object other)
  {
    // First check to see if other is a Delivery type
    // Cast other to a TMUService reference and check type
    // If not a delivery, return false
    if (!(other instanceof TMUberDelivery)) {return false;}     // checking if the object being provided is a TMUberDelivery object, if its not, it returns false
    TMUberDelivery otherD = (TMUberDelivery) other;             // if it is then initialize other parameter to the TMUberDelivery class
    // If this and other are deliveries, check to see if they are equal
    return ((super.equals(otherD)) && (this.restaurant.equals(otherD.restaurant)) && (this.foodOrderId.equals(otherD.foodOrderId)));          // checking if all the super parameters are equal to the otherD 
    // super.equals checks for all the supered components to this file and then the other two are for the instances that were created in this class
  }
  /*
   * Print Information about a Delivery Request
   */
  public void printInfo()
  {
    // Fill in the code
    // Use inheritance to first print info about a basic service request
    super.printInfo();    // calling the superclass method printInfo() from TMUberService
    // Then print specific subclass info
    System.out.printf("\nRestaurant: %-9s Food Order #: %-3s", restaurant, foodOrderId); // prints restaurant and foodOrderId in specific format
  }
}
