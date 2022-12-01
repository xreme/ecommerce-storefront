//Osereme Ibazebo
//501086523

/* Class shoe is a sublcass of the superclass product
  with more unfomration such as shoe color and size.
  There is also unique stock counts for the unique options
*/

import java.lang.reflect.Array;

public class Shoe extends Product
{
//declare a 2d array to keep stock of the shoes and their different options
int[][] shoeStock;

public Shoe(String name, String id, double price, int[][] array)
  {
  	 // Make use of the constructor in the super class Product. Initialize additional Book instance variables. 
  	 //initialize shoes to the passed arrays
    super(name, id, price, 1000000, Product.Category.SHOES);
    shoeStock = array;
  }
  
  // Check if a valid format  
  public boolean validOptions(String productOptions)
  {
    //split the productOptions string into the size and color
    String options[] = productOptions.split(" ");
    String size = options[0];
    String color = options[1];

    //check if the size and color are valid, return true if so
    if(color.equals("Brown")||color.equals("Black"))
    {
      if (Integer.valueOf(size) <= 10 && Integer.valueOf(size) >= 6)
      {
        return true;
      }
    }
    return false;
  }
  // Override getStockCount() in super class.
  public int getStockCount(String productOptions)
	{
  	//split the productOptions string into size and color
    String options[] = productOptions.split(" ");
    String size = options[0];
    String color = options[1];
    int row;

    //determine the correct stock based on the product options
    if(color.equals("Black"))
    {
        row = 0;
    }
    else {row = 1;}
    int column = Integer.valueOf(size)-6;
    
    return Integer.valueOf(shoeStock[row][column]);
	}
  
  public void setStockCount(int stockCount, String productOptions)
	{
    String options[] = productOptions.split(" ");
    String size = options[0];
    String color = options[1];
    int row;
    
    if (color.equals("Black")){
      row = 0;
    }
    else {row = 1;}
    int column = Integer.valueOf(size)-6;

    //set stock count based on the product options
    shoeStock[row][column] = stockCount;
	}
  
  public void reduceStockCount(String productOptions)
	{
  	String options[] = productOptions.split(" ");
    String size = options[0];
    String color = options[1];
    int row;
    if(color.equals("Black")){
        row = 0;
    }
    else {row = 1;}
    int column = Integer.valueOf(size)-6;
    //reduce stock count based on product options
    shoeStock[row][column] --;
	}
}