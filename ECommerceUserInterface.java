//Osereme Ibazebo
//501086523

import java.text.NumberFormat.Style;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple E-Commerce System (like Amazon)

public class ECommerceUserInterface
{
	public static void main(String[] args)
	{
		// Create the system
		ECommerceSystem amazon = new ECommerceSystem();

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
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;

			else if (action.equalsIgnoreCase("PRODS"))	// List all products for sale
			{
				amazon.printAllProducts(); 
			}
			else if (action.equalsIgnoreCase("STATS"))	// Prints order stats
			{
				amazon.printStats(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all books for sale
			{
				amazon.printAllBooks(); 
			}
			else if (action.equalsIgnoreCase("CUSTS")) 	// List all registered customers
			{
				amazon.printCustomers();	
			}
			else if (action.equalsIgnoreCase("ORDERS")) // List all current product orders
			{
				amazon.printAllOrders();	
			}
			else if (action.equalsIgnoreCase("SHIPPED"))	// List all orders that have been shipped
			{
				amazon.printAllShippedOrders();	
			}
			else if (action.equalsIgnoreCase("BOOKSBYAUTHOR")) //print out all the books by a certain author in order of year
			{
				String author = "";
				System.out.print("Author: ");
				author = scanner.nextLine();
				try{
				amazon.printAuthorBooks(author);
				}
				catch(UnkownAuthorException exception){
					System.out.print(exception.getMessage());
				}
				
				
			}
			else if (action.equalsIgnoreCase("NEWCUST"))	// Create a new registered customer
			{
				String name = "";
				String address = "";
				//get the customer information from scanner
				System.out.print("Name: ");
				if (scanner.hasNextLine())
					name = scanner.nextLine();
				System.out.print("\nAddress: ");
				if (scanner.hasNextLine())
					address = scanner.nextLine();
				try{
				amazon.createCustomer(name, address);
				}
				catch(InvalidAddressException exception){
					System.out.println(exception.getMessage());
				}
				catch(InvalidNameException exception){
					System.out.println(exception.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("SHIP"))	// ship an order to a customer
			{
					String orderNumber = "";
					System.out.print("Order Number: ");
					ProductOrder orderInfo = null;
					// Get order number from scanner
					orderNumber = scanner.nextLine();
					// Ship order to customer (see ECommerceSystem for the correct method to use
					try{
					orderInfo = amazon.shipOrder(orderNumber);
					}
					catch(InvalidOrderNumberException exception){
						System.out.println(exception.getMessage());
					}
					
					if (orderInfo != null)
					{
						orderInfo.print();
					}
			}
			else if (action.equalsIgnoreCase("CUSTORDERS")) // List all the current orders and shipped orders for this customer id
			{
				String customerId = "";
				System.out.print("Customer Id: ");
				// Get customer Id from scanner
				customerId = scanner.nextLine();
				// Print all current orders and all shipped orders for this customer
				try{
				amazon.printOrderHistory(customerId);
				}
				catch(UnknownCustomerException exception){
					System.out.println(exception.getMessage());
				}
				
			}
			else if (action.equalsIgnoreCase("ORDER")) // order a product for a certain customer
			{
				String productId = "";
				String customerId = "";
				String orderNumber = null;

				System.out.print("Product Id: ");
			  	// Get product Id from scanner
				productId = scanner.nextLine();
				System.out.print("\nCustomer Id: ");
			  	// Get customer Id from scanner
				customerId = scanner.nextLine();
				// Order the product. Check for valid orderNumber string return and for error message set in ECommerceSystem
				try{
				orderNumber  = amazon.orderProduct(productId, customerId, "");
				}
				catch(InvalidProductOptionsException exception){
					System.out.println(exception.getMessage());
				}
				catch(UnknownCustomerException exception){
					System.out.println(exception.getMessage());
				}
				catch(UnknownProductException exception){
					System.out.println(exception.getMessage());
				}
				catch(OutOfStockException exception){
					System.out.println(exception.getMessage());
				}

				// Print Order Number string returned from method in ECommerceSystem
				if (orderNumber!= null) System.out.print("Order Number:" + orderNumber);
			}
			else if (action.equalsIgnoreCase("ORDERBOOK")) // order a book for a customer, provide a format (Paperback, Hardcover or EBook)
			{
				String productId = "";
				String customerId = "";
				String options = "";
				String orderNumber = null;

				System.out.print("Product Id: ");
				// get product id
				productId = scanner.nextLine();
				System.out.print("\nCustomer Id: ");
				// get customer id
				customerId = scanner.nextLine();
				System.out.print("\nFormat [Paperback Hardcover EBook]: ");
				// get book forma and store in options string
				options =  scanner.nextLine();
				// Order product, catch exceptions
				try{
					orderNumber  = amazon.orderProduct(productId, customerId, options);
				}
				catch(InvalidProductOptionsException exception){
					System.out.println(exception.getMessage());
				}
				catch(UnknownCustomerException exception){
					System.out.println(exception.getMessage());
				}
				catch(UnknownProductException exception){
					System.out.println(exception.getMessage());
				}
				catch(OutOfStockException exception){
					System.out.println(exception.getMessage());
					}
				// Print order number string if order number is not null
				if (orderNumber!= null) System.out.println("Order Number:" + orderNumber);
			}
			else if (action.equalsIgnoreCase("ORDERSHOES")) // order shoes for a customer, provide size and color 
			{
				String productId = "";
				String customerId = "";
				String options = "";
				String orderNumber = null;

				System.out.print("Product Id: ");
				// get product id
				productId = scanner.nextLine();
				System.out.print("\nCustomer Id: ");
				// get customer id
				customerId = scanner.nextLine();
				System.out.print("\nSize: \"6\" \"7\" \"8\" \"9\" \"10\": ");
				// get shoe size and store in options	
				options = scanner.nextLine() + " ";
				System.out.print("\nColor: \"Black\" \"Brown\": ");
				// get shoe color and append to options
				options += scanner.nextLine();
				//order the shoes, catch exceptions
				try{
					orderNumber  = amazon.orderProduct(productId, customerId, options);
					}
					catch(InvalidProductOptionsException exception){
						System.out.println(exception.getMessage());
					}
					catch(UnknownCustomerException exception){
						System.out.println(exception.getMessage());
					}
					catch(UnknownProductException exception){
						System.out.println(exception.getMessage());
					}
					catch(OutOfStockException exception){
						System.out.println(exception.getMessage());
					}
				//print the order number if it is set
				if (orderNumber!= null) System.out.println("Order Number:" + orderNumber);
			}
			else if (action.equalsIgnoreCase("CANCEL")) // Cancel an existing order
			{
				String orderNumber = "";

				System.out.print("Order Number: ");
				// get order number from scanner
				orderNumber = scanner.nextLine();
				// cancel order. Check for error 
				try{
					amazon.cancelOrder(orderNumber);
				}
				catch(InvalidOrderNumberException exception){
					System.out.println(exception.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("PRINTBYPRICE")) // sort products by price
			{
				amazon.printByPrice();
			}
			else if (action.equalsIgnoreCase("PRINTBYNAME")) // sort products by name (alphabetic)
			{
				amazon.printByName();
			}
			else if (action.equalsIgnoreCase("SORTCUSTS")) // sort products by name (alphabetic)
			{
				amazon.sortCustomersByName();
			}
			else if (action.equalsIgnoreCase("ADDTOCART")) // sort products by name (alphabetic)
			{
				String productid = "";
				String customerID = "";
				String productOptions = "";

				//get the the product ID, product options and the user ID
				System.out.print("Product ID: ");
				productid = scanner.nextLine();
				//System.out.println("Shoes: ");
				System.out.println("Book Options: Hardcover , Paperback, EBook");
				System.out.print("Options:");
				productOptions = scanner.nextLine();
				System.out.print("Customer ID:");
				customerID = scanner.nextLine();
				try{
					amazon.addToCart(productid, customerID, productOptions);
				}
				catch (UnknownCustomerException exception){
					System.out.println(exception.getMessage());
				}
				catch(UnknownProductException exception){
					System.out.println(exception.getMessage());
				}
				catch (InvalidProductOptionsException exception){
					System.out.println(exception.getMessage());
				}
				
			}
			else if (action.equalsIgnoreCase("PRINTCART")) // sort products by name (alphabetic)
			{
				String customerID = "";
				
				//get the desired customer ID
				System.out.print("Customer ID: ");
				customerID = scanner.nextLine();
				
				//try to print out customer's cart catch any exceptions
				try{
					amazon.printCart(customerID);
				}
				catch(UnknownCustomerException exception){
					System.out.println(exception.getMessage());
				}
				
			}
			else if (action.equalsIgnoreCase("REMCARTITEM")) // sort products by name (alphabetic)
			{
				String customerID = "";
				String productID = "";

				//get the desired customer ID
				System.out.print("Customer ID: ");
				customerID = scanner.nextLine();
				
				//get the the product ID, product options and the user ID
				System.out.print("Product ID: ");
				productID = scanner.nextLine();

				try{
				amazon.removeCartItem(customerID, productID);
				}
				catch(UnknownCustomerException | UnknownProductException exception){
					System.out.println(exception.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("ORDERITEMS")) // sort products by name (alphabetic)
			{
				String customerID = "";
				
				//get the desired customer ID
				System.out.print("Customer ID: ");
				customerID = scanner.nextLine();
				
				//try to print out customer's cart catch any exceptions
				try{
					amazon.orderItems(customerID);
				}
				catch(UnknownCustomerException | UnknownProductException | OutOfStockException exception){
					System.out.println(exception.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("RateProd"))
			{
				String productID = "";
				int rating = 0;
				//get the product id
				System.out.print("Product ID: ");
				productID = scanner.nextLine();
				//get the rating from the user
				System.out.print("Rating: ");
				rating = Integer.valueOf(scanner.nextLine());
				try{
					amazon.rateProd(productID, rating);
				}
				catch(UnknownProductException | InvalidRatingException exception){
					System.out.println(exception.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("PRODREVIEWS"))
			{
				String productID = "";
				//get the product id
				System.out.print("Product ID: ");
				productID = scanner.nextLine();
				
				try{
					amazon.getReviews(productID);
				}
				catch(UnknownProductException exceotion){
					System.out.println(exceotion.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("PRINTBYRATING"))
			{
				String Rating = "";
				String Category = "";

				//get the caetgory
				System.out.print("Category(All Caps): ");
				Category = scanner.nextLine();
				//get the product id
				System.out.print("Rating: ");
				Rating = scanner.nextLine();
				
				try{
					amazon.PrintByRating(Rating, Category);
				}
				catch(UnknownProductException | UnknownCategoryException | InvalidRatingException exception){
					System.out.println(exception.getMessage());
				}
			}
			System.out.print("\n>");
		}
	}
}
