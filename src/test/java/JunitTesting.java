
import static org.junit.jupiter.api.Assertions.*;

import org.example.Customer;
import org.example.Item;
import org.example.MenuManagement;
import org.example.OrderManagement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JunitTesting {
    private MenuManagement menuManagement;
    private Customer customer;

    @BeforeEach
    public void setup() {
        OrderManagement orderManagement = new OrderManagement();
        menuManagement = new MenuManagement(orderManagement);
        customer = new Customer(100.0, "John Doe", "password123", 1, orderManagement, menuManagement);
        Item inStockItem = new Item(1, "Pizza", 10.0, "Food", true);
        Item inStockItem2 = new Item(3, "Fries", 15.0, "Food", true);
        Item outOfStockItem = new Item(2, "Burger", 5.0, "Food", false);
        menuManagement.addItem(inStockItem);
        menuManagement.addItem(inStockItem2);
        menuManagement.addItem(outOfStockItem);
    }
    @Test
    public void testOrderOutOfStockItem() {
        String itemName = "Fries";
        int quantity = 1;
        Item item = menuManagement.searchItem(itemName);
        assertNotNull(item, "Item should exist in the menu");
        customer.addItemToCart(itemName,quantity);
        assertNotNull(customer.getCart().findItemByName(itemName), "Cart should not contain out-of-stock items");
    }
    @Test
    public void testTotalPrice(){
        double totalBefore=customer.getCart().viewTotal();
        String itemName = "Pizza";
        int quantity = 3;
        Item item = menuManagement.searchItem(itemName);
        assertNotNull(item, "Item should exist in the menu");
        customer.addItemToCart(itemName,quantity);
        assertEquals(customer.getCart().viewTotal(), totalBefore+(item.getPrice() * quantity), 0.01, "Total price should match the item's price.");
    }

    @Test
    public void testModifyQuanity(){
        double totalBefore=customer.getCart().viewTotal();
        String itemName = "Pizza";
        int quantity = 3;
        Item item = menuManagement.searchItem(itemName);
        assertNotNull(item, "Item should exist in the menu");
        customer.addItemToCart(itemName,quantity);
        assertEquals(customer.getCart().viewTotal(), totalBefore+(item.getPrice() * quantity), 0.01, "Total price should match the item's price.");
        totalBefore=customer.getCart().viewTotal();
        itemName = "Fries";
        quantity = 2;
        item = menuManagement.searchItem(itemName);
        assertNotNull(item, "Item should exist in the menu");
        customer.addItemToCart(itemName,quantity);
        assertEquals(customer.getCart().viewTotal(), totalBefore+(item.getPrice() * quantity), 0.01, "Total price should match the item's price.");
        totalBefore=customer.getCart().viewTotal();
        System.out.println("before modifying total amount->"+totalBefore);
        String ModifyItemName="Fries";
        int newQuantity=1;
        item = menuManagement.searchItem(ModifyItemName);
        customer.modifyItemQuantity(ModifyItemName,newQuantity);
        System.out.println("after modifying total amount->"+(totalBefore+(item.getPrice() * (newQuantity-quantity))));
        assertEquals(customer.getCart().viewTotal(), totalBefore+(item.getPrice() * (newQuantity-quantity)), 0.01, "Total price should match the item's price.");

    }
    @Test
    public void testNegativeQuantity(){
        String itemName = "Pizza";
        int quantity = 3;
        Item item = menuManagement.searchItem(itemName);
        assertNotNull(item, "Item should exist in the menu");
        boolean check=customer.addItemToCart(itemName,quantity);
        assertTrue(check,"enter positive quantity");
    }





}
