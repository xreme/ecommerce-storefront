import java.util.ArrayList;

public class Cart {
    private ArrayList<CartItem> CartItems = new ArrayList<CartItem>();

    public Cart(){

    }
    //add cart item to cart
    public void addToCart(CartItem item){
        CartItems.add(item);
        System.out.println(item + " added to cart!");
    }
    //print out the customer's cart
    public void printCart(){
        for(CartItem i : CartItems){
            System.out.println(i.getProductName() + ", Options: " + i.getOptions() );
        }
    }
    //remove item from the user's cart
    public void removeItem(Product prod){
        for(int i =0; i < CartItems.size(); i++)
        {
            if(prod.equals(CartItems.get(i).getProduct())){
                CartItems.remove(i);
            }
        }
    }
    //return list of the items in user's cart
    public ArrayList<CartItem> getCartItems(){
       return CartItems;
    }
    //remove all items from user's cart
    public void clearCart(){
        while(CartItems.size() > 0){
            CartItems.remove(0);
        }
    }
}
