public class CartItem {
    private Product product;
    private String productOptions;

    public CartItem(Product product, String productOptions){
        this.product = product;
        this.productOptions = productOptions;
    }
    public String getOptions(){
        return productOptions;
    }
    public String toString(){
        return productOptions + " " +product.getName() ;
    }
    public String getProductName(){
        return product.getName();
    }
    public Product getProduct(){
        return product;
    }
}
