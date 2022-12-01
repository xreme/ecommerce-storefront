//Osereme Ibazebo
//501086523

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Target;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Map;
import java.util.TreeMap;

/*
 * Models a simple ECommerce system. Keeps track of products for sale, registered customers, product orders and
 * orders that have been shipped to a customer
 */
public class ECommerceSystem
{
    //private ArrayList<Product>  products = new ArrayList<Product>();
    private Map<String, Product> products = new HashMap<String, Product>();
    private Map<Product, Integer> productOrderCount = new HashMap<Product, Integer>();
    private ArrayList<Customer> customers = new ArrayList<Customer>();	
    
    private ArrayList<ProductOrder> orders   = new ArrayList<ProductOrder>();
    private ArrayList<ProductOrder> shippedOrders   = new ArrayList<ProductOrder>();
    
    // These variables are used to generate order numbers, customer id's, product id's 
    private int orderNumber = 500;
    private int customerId = 900;
    private int productId = 700;
    
    // General variable used to store an error message when something is invalid (e.g. customer id does not exist)  
    String errMsg = null;
    
    // Random number generator
    Random random = new Random();
    
    public ECommerceSystem()
    {
    	// NOTE: do not modify or add to these objects!! - the TAs will use for testing
    	// If you do the class Shoes bonus, you may add shoe products
    	
      //create products
      try{
        products = readProducts("products.txt");
      }
      catch (IOException exception){ 
        System.out.println(exception.getMessage());
        System.exit(1);
      }

      //add products to stat tracker map and set their orders to 0
      for(Product prods : products.values() ){
        productOrderCount.put(prods, 0);
      }

    	// Create some customers. Notice how generateCustomerId() method is used
    	customers.add(new Customer(generateCustomerId(),"Inigo Montoya", "1 SwordMaker Lane, Florin"));
    	customers.add(new Customer(generateCustomerId(),"Prince Humperdinck", "The Castle, Florin"));
    	customers.add(new Customer(generateCustomerId(),"Andy Dufresne", "Shawshank Prison, Maine"));
    	customers.add(new Customer(generateCustomerId(),"Ferris Bueller", "4160 Country Club Drive, Long Beach"));
    }
    
    private String generateOrderNumber()
    {
    	return "" + orderNumber++;
    }

    private String generateCustomerId()
    {
    	return "" + customerId++;
    }
    
    private String generateProductId()
    {
    	return "" + productId++;
    }
    
    //reads file, creates product objects, and stores them in a hash map
    private HashMap<String, Product> readProducts(String fileName) throws IOException{
      HashMap<String, Product> readProducts = new HashMap<String, Product>();
      File fProds = new File(fileName);
      Scanner input = new Scanner(fProds);
      
      //store necessary product variables from file 
      while(input.hasNextLine()){
          Product.Category category = Product.Category.valueOf(input.nextLine());
          String name = input.nextLine();
          String id = generateProductId();
          double price = Double.parseDouble(input.nextLine());
          
          //if the category is book, store it's book-specific variables
          if(category == Product.Category.BOOKS){
            String[] bookStock = input.nextLine().split(" ");
            int paperbackStock = Integer.parseInt(bookStock[0]);
            int hardcoverStock = Integer.parseInt(bookStock[1]);
            String[] bookInfo = input.nextLine().split(":");
            String title = bookInfo[0];
            String author = bookInfo[1];
            int year = Integer.parseInt(bookInfo[2]);
            //add the new book object to the hash map
            readProducts.put(id, new Book(name, id, price, paperbackStock, hardcoverStock, title, author, year));
          }
          //create and add new non-books items into the hash map
          else{
            int stock = Integer.parseInt(input.nextLine());
            readProducts.put(id, new Product(name, id, price, stock, category));
            input.nextLine();
          }
      }
      input.close();
      System.out.println("--Products Read!--");
      return readProducts;
    }


    public String getErrorMessage()
    {
    	return errMsg;
    }
    
    public void printAllProducts()
    {
    	for (Product prod : products.values()){
    		prod.print();}
    }
    
    // Print all products that are books. See getCategory() method in class Product *done*
    public void printAllBooks()
    {
    	for (Product item : products.values()){
        if (item.getCategory() == Product.Category.BOOKS){
          item.print();
        }
      }
    }
    // Print all current orders 
    public void printAllOrders()
    {
      for (int i = 0; i < orders.size(); i++){
        orders.get(i).print();
      }
    }
    // Print all shipped orders
    public void printAllShippedOrders()
    {
    	for (ProductOrder ProdOrder : shippedOrders){
        ProdOrder.print();
      }
    }
    // Print all customers
    public void printCustomers()
    {
    	for (int i = 0; i < customers.size(); i++){
       customers.get(i).print();
      }
    }
    public void printAuthorBooks(String author) throws UnkownAuthorException //print all books by a specific author
    {
      //loop through the products and add the books of the specified author into an array list
      ArrayList<Book>  books = new ArrayList<Book>();
      for(Product p: products.values()){
        if (p.getCategory() == Product.Category.BOOKS){ 
          Book book = (Book) p;
          if (book.getAuthor().equals(author)){
            books.add(book);
          }
        }
      }
      //throw exception if there are no results
      if(books.size() == 0){
        throw new UnkownAuthorException(author);
      }
      //sort the books by year
      int len = books.size();
      for(int i = 0 ; i < len-1; i++){
        for (int j = 0; j < len-1-i; j++){
          if (books.get(j).getYear() > books.get(j+1).getYear()){
            Collections.swap(books, j, j+1);
          }
        }
      }
      //print out the books by given author
      for(Book b:books){
        b.print();
      }
    }
    //print the number of times of particular product has been ordered
    public void printStats(){
      //create temporary array list for the stats to be sorted with
      ArrayList<Product> prodStats = new ArrayList<Product>();
      for(Product p : productOrderCount.keySet()){
        prodStats.add(p);
      }
      //use bubble sort to sort the stats
      int len = prodStats.size();
      for(int i = 0 ; i < len-1; i++){
        for (int j = 0; j < len-1-i; j++){
          if (productOrderCount.get(prodStats.get(j)) < (productOrderCount.get(prodStats.get(j+1)))){
            Collections.swap(prodStats, j, j+1);
          }
        }
      }
      //print out the formatted stats from array list
      for(Product p : prodStats){
        System.out.printf("\nProduct: %-38s Orders:  %-9s", p.getName(), productOrderCount.get(p));
      }

    }
    /*
     * Given a customer id, print all the current orders and shipped orders for them (if any) 
     */
    public void printOrderHistory(String customerId) throws UnknownCustomerException
    {
      // Make sure customer exists - check using customerId
    	// If customer does not exist, set exception
    	// see video for an appropriate error message string
    	// ... code here
    	//loop through the list of customers and search for desired customer
      Customer targetCustomer = null;
      for(Customer cust:customers){
        if (cust.getId().equals(customerId)){
          targetCustomer = cust;
          break;
        }
      }
      //throw exception if customer is not found
      if(targetCustomer == null){
        throw new UnknownCustomerException(customerId);
      }
    	// Print current orders of this customer 
    	System.out.println("Current Orders of Customer " + customerId);
    	// enter code here
    	for(ProductOrder custOrder: orders){
        if(custOrder.getCustomer() == targetCustomer){
          custOrder.print();
        }
      }
    	// Print shipped orders of this customer 
    	System.out.println("\nShipped Orders of Customer " + customerId);
    	//enter code here
    	for(ProductOrder custOrder: shippedOrders){
        if(custOrder.getCustomer() == targetCustomer){
          custOrder.print();
          break;
        }
      }
    }
    
    public String orderProduct(String productId, String customerId, String productOptions) throws UnknownProductException, UnknownCustomerException, InvalidProductOptionsException, OutOfStockException
    {
    	// First check to see if customer object with customerId exists in array list customers
    	// if it does not, throw exception
    	// else get the Customer object
      //seach for the customer in the customer array list
      Customer targetCust = null;
      for (Customer cust : customers){
        if (cust.getId().equals(customerId)){
          targetCust = cust;
          break;
        }
      }
      //throw exception if customer is not found
      if (targetCust == null){
        throw new UnknownCustomerException(customerId);
      }
    	// Check to see if product object with productId exists in products
    	// if it does not, throw exception
    	// else get the Product object 
      Product wantProd = products.get(productId);

      //throw exception if product does not exist
      if ((wantProd == null)){
        throw new UnknownProductException(productId);
      }
    	
    	// Check if the options are valid for this product (e.g. Paperback or Hardcover or EBook for Book product)
    	// See class Product and class Book for the method vaidOptions()
      //advise the user to use correct order functions to order shoes or books
      if (productOptions.equals("")){
        if(wantProd.getCategory() == Product.Category.SHOES ||wantProd.getCategory() == Product.Category.BOOKS){
          throw new InvalidProductOptionsException(wantProd);
        }
      }
      
      //if the product options are invalid throw exception
      if (!wantProd.validOptions(productOptions)){
       throw new InvalidProductOptionsException(wantProd, productOptions);
      }

    	// Check if the product has stock available (i.e. not 0)
    	// See class Product and class Book for the method getStockCount()
    	// If no stock available, throw exception
    	if (wantProd.getStockCount(productOptions) < 1){
        throw new OutOfStockException(wantProd, productOptions);
      }

      // Create a ProductOrder, (make use of generateOrderNumber() method above)
    	// reduce stock count of product by 1 (see class Product and class Book)
    	// Add to orders list and return order number string
      ProductOrder CompleteOrder = new ProductOrder(generateOrderNumber(), wantProd, targetCust, productOptions);
      orders.add( CompleteOrder);
      wantProd.reduceStockCount(productOptions);
      System.out.println("Remaining stock: " + wantProd.getStockCount(productOptions));
      
      //increment the order count for the product
      productOrderCount.put(wantProd, productOrderCount.get(wantProd)+1);

      return CompleteOrder.getOrderNumber();
    }
    
    /*
     * Create a new Customer object and add it to the list of customers
     */
    public void createCustomer(String name, String address)throws InvalidAddressException, InvalidNameException
    {
    	// Check name parameter to make sure it is not null or ""
    	// If it is not a valid name, throw exception
    	// Repeat this check for address parameter
    	if (name == null || name.equals("")){
        throw new InvalidNameException ("Invalid Customer Name");
      }
      if (address == null || address.equals("")){
        throw new InvalidAddressException("Invalid Customer Address");
      }
    	// Create a Customer object and add to array list
      customers.add(new Customer(generateCustomerId(), name, address));
    }
    
    public ProductOrder shipOrder(String orderNumber) throws InvalidOrderNumberException
    {
      // Check if order number exists first. If it doesn't throw exception
    	// and return false
    	// Retrieve the order from the orders array list, remove it, then add it to the shippedOrders array list
    	// return a reference to the order
    	
      //loop through array list of orders and store both the index of the order and a reference to the object
      ProductOrder order =  null;
      int orderInd = 0;
      for(int i = 0; i < orders.size(); i++){
        if (orders.get(i).getOrderNumber().equals(orderNumber)){
          orderInd = i;
          order = orders.get(i);
          break;
        }
      }
      //throw exeption
      if(order == null){
        throw new InvalidOrderNumberException(orderNumber);
      }
      //remove the order from the orders array list and add the order to the shipped orders array list
      orders.remove(orderInd);
      shippedOrders.add(order);
      return order;
    }
    
    /*
     * Cancel a specific order based on order number
     */
    public void cancelOrder(String orderNumber) throws InvalidOrderNumberException
    {
      // Check if order number exists first. If it doesn't, set throw exception
    	// and return false
    	int target = 0;
      Product wantProd = null;
      boolean found = false;
      for(int i = 0; i < orders.size(); i++){
        if (orders.get(i).getOrderNumber().equals(orderNumber)){
          target = i;
          found = true;
          wantProd = orders.get(i).getProduct();
          break;
        }
      }
      //if the order is not found, set error message and return false
      if(!found){
        throw new InvalidOrderNumberException(orderNumber);
      }
      //remove the order from the orders array list
      orders.remove(target);
      //remove the order from the count
      productOrderCount.put(wantProd, productOrderCount.get(wantProd)-1);
      //return true;
    }

    // Sort products by increasing price
    public void printByPrice()
    {
  	  ArrayList <Product> productList = new ArrayList<Product>();
      //add values from map into an array list
      for(Product item : products.values()){
        productList.add(item);
      }
      
      //use bubble sort algorithm to sort the products in asceding price
      int len = productList.size();
      for(int i = 0 ; i < len-1; i++){
        for (int j = 0; j < len-1-i; j++){
          if (productList.get(j).getPrice() < productList.get(j+1).getPrice()){
            Collections.swap(productList, j, j+1);
          }
        }
      }

      for(Product item: productList){
        item.print();
      }
    }
    // Print products alphabetically by product name
    public void printByName()
    {
  	  ArrayList <Product> productList = new ArrayList<Product>();
      //add values from map into an array list
      for(Product item : products.values()){
        productList.add(item);
      }
      //sort array list and print items in sorted order
      Collections.sort(productList);
      for(Product item: productList){
        item.print();
      }
    }    
    // Sort Customers alphabetically by name
    public void sortCustomersByName()
    {
  	  
      Collections.sort(customers);
    }
    //add items to a customer's cart
    public void addToCart(String productid, String customerID, String productOptions) throws UnknownCustomerException, UnknownProductException, InvalidProductOptionsException
    {
    Customer targetCust = null;
    for (Customer cust : customers){
      if (cust.getId().equals(customerID)){
        targetCust = cust;
        break;
      }
    }
      //throw exception if customer is not found
      if (targetCust == null){
        throw new UnknownCustomerException(customerID);
      }
      // Check to see if product object with productId exists in products
    	// if it does not, throw exception
    	// else get the Product object 
      Product wantProd = products.get(productid);

      //throw exception if product does not exist
      if ((wantProd == null)){
        throw new UnknownProductException(productid);
      }
      
      //throw exception if product options are invalid
      if (productOptions.equals("")){
        if(wantProd.getCategory() == Product.Category.SHOES ||wantProd.getCategory() == Product.Category.BOOKS){
          throw new InvalidProductOptionsException();
        }
      }
      //if the product options are invalid throw exception
      if (!wantProd.validOptions(productOptions)){
        throw new InvalidProductOptionsException(wantProd, productOptions);
      }

      // Check if the product has stock available (i.e. not 0)
    	// See class Product and class Book for the method getStockCount()
    	// If no stock available, throw exception
    	if (wantProd.getStockCount(productOptions) < 1){
        throw new OutOfStockException(wantProd, productOptions);
      }
      
      //add the wanted product to the customer's cart 
      CartItem item = new CartItem(wantProd, productOptions);
      targetCust.getCart().addToCart(item);
    }
    public void printCart(String customerID) throws UnknownCustomerException{
      //find the customer
      Customer targetCust = null;
      for (Customer cust : customers){
        if (cust.getId().equals(customerID)){
          targetCust = cust;
          break;
        }
      }
      if (targetCust == null){
        throw new UnknownCustomerException(customerID);
      }
      //print out the customer's cart
      targetCust.getCart().printCart();
    }
    public void removeCartItem(String customerID, String productID) throws UnknownCustomerException, UnknownProductException{
      //serach for the customer
      Customer targetCust = null;
      for (Customer cust : customers){
        if (cust.getId().equals(customerID)){
          targetCust = cust;
          break;
        }
      }
      if (targetCust == null){
        throw new UnknownCustomerException(customerID);
      }
      Product wantProd = products.get(productID);
      //throw exception if product does not exist
      if ((wantProd == null)){
        throw new UnknownProductException(productID);
      }
      //remove product from the customer's cart
      targetCust.getCart().removeItem(wantProd);
    }
    public void orderItems(String customerID) throws UnknownCustomerException, UnknownProductException,OutOfStockException{
      //find the customer
      Customer targetCust = null;
      for (Customer cust : customers){
        if (cust.getId().equals(customerID)){
          targetCust = cust;
          break;
        }
      }
      if (targetCust == null){
        throw new UnknownCustomerException(customerID);
      }
      //get the array list of cart items
      ArrayList<CartItem> itemsToOrder =  targetCust.getCart().getCartItems();

      //make individual orders for the items
      for(CartItem item: itemsToOrder){
        //get order information
        String productId = item.getProduct().getId();
        String productOptions = item.getOptions();
          orderProduct(productId, customerID, productOptions);
          System.out.println("Ordered "+ productId + "\n");
      }
      targetCust.getCart().clearCart();
    }
    public void rateProd(String productID, int stars) throws UnknownProductException, InvalidRatingException{
      
      Product wantProd = products.get(productID);
      //throw exception if product does not exist
      if ((wantProd == null)){
        throw new UnknownProductException(productID);      
      }
      //throw an excption if the rating is invalid
      if(stars < 1 || stars > 5){
        throw new InvalidRatingException();
      }

      wantProd.addRating(stars);
      System.out.println("Avg: "+ wantProd.getAvgRating());
      System.out.println("All: " + wantProd.getRatings());
    }
    public void getReviews(String productID) throws UnknownProductException{
      
      Product wantProd = products.get(productID);
      //throw exception if product does not exist
      if ((wantProd == null)){
        throw new UnknownProductException(productID);      
      }
      //display the name of the product
      System.out.println(wantProd.getName());
      //display the average rating
      System.out.println("Overall Rating: "+wantProd.getAvgRating());
      //create variables to store the total amount of reviews with a certain rating
      int t1 = 0;
      int t2 = 0;
      int t3 = 0;
      int t4 = 0;
      int t5 = 0;
      //total the ratings
      ArrayList<Integer> ratings = wantProd.getRatings();
      for(int r : ratings ){
        if(r == 1) t1++;
        if(r == 2) t2++;
        if(r == 3) t3++;
        if(r == 4) t4++;
        if(r == 5) t5++;
      }
      //print out the ratings totals
      System.out.println("[" + t5 + "]"+" ★★★★★");
      System.out.println("[" + t4 + "]"+" ★★★★");
      System.out.println("[" + t3 + "]"+" ★★★");
      System.out.println("[" + t2 + "]"+" ★★");
      System.out.println("[" + t1 + "]"+" ★");
    }
    public void PrintByRating(String minRating, String Category) throws UnknownProductException, UnknownCategoryException,  InvalidRatingException{
      //throw an exception if the invalid rating
      if(Integer.valueOf(minRating) < 1){
        throw new InvalidRatingException();
      }

      if (Category.equals("BOOKS")||Category.equals("GENERAL")||Category.equals("CLOTHING")||Category.equals("COMPUTERS")||Category.equals("FURNITURE")||Category.equals("SHOES")){
        for (Product item : products.values()){
          
          if (item.getCategory() == Product.Category.valueOf(Category)){
            if(item.getAvgRating().equals("No Ratings")){
            }
            else if (Double.valueOf(minRating) <= Double.valueOf(item.getAvgRating())){
              System.out.println(item.getName() + " AVG RATING: "+item.getAvgRating() );
            }
          }
        }
        
      }
      else{
        //throw new exception if category does not exist
        throw new UnknownCategoryException();
      }
    }
    
}


//declaring exception classes 
class UnknownCustomerException extends RuntimeException{
  public UnknownCustomerException () {}
  public UnknownCustomerException (String custID){
    super("Customer "+ custID + " Not Found");
  }
}
class InvalidOrderNumberException extends RuntimeException{
  public InvalidOrderNumberException () {}
  public InvalidOrderNumberException (String orderNumber){
    super("Order " + orderNumber + " Not Found");
  }
}
class UnknownProductException extends RuntimeException{
  public UnknownProductException () {}
  public UnknownProductException (String productId){
    super("Product " + productId + " Not Found ");
  }
}  
class InvalidProductOptionsException extends RuntimeException{
  public InvalidProductOptionsException () {
    super("Bad product options");
  }
  public InvalidProductOptionsException (Product prod, String options){
    super("Product " + prod.getName() + ", Product ID: " + prod.getId() + ", Invalid option: " + options);
  }
  public InvalidProductOptionsException(Product prod){
    super(prod.getName() + " is missing options please use 'ORDERBOOK' for books or 'ORDERSHOES' for shoes");
  }
}
class OutOfStockException extends RuntimeException{
  public OutOfStockException () {}
  public OutOfStockException (Product prod, String options){
    super("Product " + prod.getName() + ", Product ID: " + prod.getId() + ", Option: " + options + " is sold out.");
  }
}
class InvalidNameException extends RuntimeException{
  public InvalidNameException () {}
  public InvalidNameException (String message){
    super(message);
  }
}
class InvalidAddressException extends RuntimeException{
  public InvalidAddressException () {}
  public InvalidAddressException (String message){
    super(message);
  }
}
class UnkownAuthorException extends RuntimeException{
  public UnkownAuthorException () {}
  public UnkownAuthorException (String author){
    super("There are not results for books by "+ author);
  }
}
class InvalidRatingException extends RuntimeException{
  public InvalidRatingException (){
    super("Please leave a rating from 1-5");
  }
}
class UnknownCategoryException extends RuntimeException{
  public UnknownCategoryException () {
    super("Uknown Category");
  }
}
class Exception extends RuntimeException{
  public Exception () {}
  public Exception (String message){
    super(message);
  }
}