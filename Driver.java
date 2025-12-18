/*
 * 
 * This class simulates a car driver in a simple uber app 
 * 
 * Everything has been done for you except the equals() method
 */

 // Harsh Patel (501228508)
 
public class Driver
{
  private String id;
  private String name;
  private String carModel;
  private String licensePlate;
  private double wallet;
  private String type;
  private TMUberService service;  // service instance variable
  private String address;         // address instance variable
  private int zone;               // zone instance variable
  
  public static enum Status {AVAILABLE, DRIVING};
  private Status status;
    
  
  public Driver(String id, String name, String carModel, String licensePlate, String address, int zone)
  {
    this.id = id;
    this.name = name;
    this.carModel = carModel;
    this.licensePlate = licensePlate;
    this.status = Status.AVAILABLE;
    this.wallet = 0;
    this.type = "";
    this.service = null;
    this.address = address;
    this.zone = zone;
  }
  // Print Information about a driver
  public void printInfo()
  {
    System.out.printf("Id: %-3s Name: %-15s Car Model: %-15s License Plate: %-10s Wallet: %2.2f \n    Status: %-15s Address: %-15s Zone: %-15s", 
                      id, name, carModel, licensePlate, wallet, status, address, zone);
  }
  // Getters and Setters
  public String getType()
  {
    return type;
  }
  public void setType(String type)
  {
    this.type = type;
  }
  public String getId()
  {
    return id;
  }
  public void setId(String id)
  {
    this.id = id;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public String getCarModel()
  {
    return carModel;
  }
  public void setCarModel(String carModel)
  {
    this.carModel = carModel;
  }
  public String getLicensePlate()
  {
    return licensePlate;
  }
  public void setLicensePlate(String licensePlate)
  {
    this.licensePlate = licensePlate;
  }
  public Status getStatus()
  {
    return status;
  }
  public void setStatus(Status status)
  {
    this.status = status;
  }
  public double getWallet()
  {
    return wallet;
  }
  public void setWallet(double wallet)
  {
    this.wallet = wallet;
  }
  public TMUberService getService()        // getService method 
  {
    return service;
  }
  public void setService(TMUberService service)   // setting the service method
  {
    this.service = service;
  }
  public String getAddress()      // getAddress method
  {
    return address;
  }
  public void setAddress(String address)    // setting the address method
  {
    this.address = address;
  }
  public double getZone()       // getZone method
  {
    return zone;
  }
  public void setZone(int zone)       // setting the zone method
  {
    this.zone = zone;
  } 
  /*
   * Two drivers are equal if they have the same name and license plates.
   * This method is overriding the inherited method in superclass Object
   * 
   * Fill in the code 
   */
  public boolean equals(Object other)
  {
    Driver person = (Driver) other;       // initializing the object provided to the driver class
    if (this.name.equals(person.getName()) && this.licensePlate.equals(person.getLicensePlate())) {return true;}    // checking if the attributes of the driver are equals to the driver attributes from the object provided
    return false;   // if the attributes are not the same 
  }
  
  // A driver earns a fee for every ride or delivery
  public void pay(double fee)
  {
    wallet += fee;
  }
}
