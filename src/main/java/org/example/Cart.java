package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Item, Integer> cartItems;
    public Cart() {
        this.cartItems = new HashMap<>();
    }
    public Map<Item, Integer> getCartItems(){
        return cartItems;
    }
    public void addItem(Item item, int quantity) {
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }
        cartItems.put(item, cartItems.getOrDefault(item, 0) + quantity);
        System.out.println("Added " + quantity + " of " + item.getName() + " to the cart.");
    }
    public Item findItemByName(String itemName) {
        for (Item item : cartItems.keySet()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void modifyQuantity(Item item, int newQuantity) {
        if (!cartItems.containsKey(item)) {
            System.out.println("Item not found in cart.");
            return;
        }
        if (newQuantity <= 0) {
            System.out.println("Quantity must be greater than 0. Use removeItem() to remove an item.");
            return;
        }
        cartItems.put(item, newQuantity);
        System.out.println("Updated " + item.getName() + " quantity to " + newQuantity + ".");
    }

    public void removeItem(Item item) {
        if (cartItems.remove(item) != null) {
            System.out.println("Removed " + item.getName() + " from the cart.");
        } else {
            System.out.println("Item not found in cart.");
        }
    }

    public double viewTotal() {
        double total = 0;
        for (Map.Entry<Item, Integer> entry : cartItems.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public void displayCart() {
        if (cartItems.isEmpty()) {
            System.out.println("The cart is empty.");
            return;
        }
        System.out.println("Cart contents:");
        for (Map.Entry<Item, Integer> entry : cartItems.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(item.getName() + " | Quantity: " + quantity + " | Price: " + item.getPrice() +
                    " | Total: " + (item.getPrice() * quantity));
        }
        System.out.println("Total Cart Price: " + viewTotal());
    }
    public void clearCart(String filePath) {
        cartItems.clear();
        try (FileWriter writer = new FileWriter(filePath)) {
            // Overwrite the file with an empty content
            writer.write("");
            writer.flush();
            System.out.println("Checkout complete. The cart is now empty.");
        } catch (IOException e) {
            throw new RuntimeException("Error clearing the cart file: " + filePath, e);
        }
        System.out.println("Cart has been cleared.");
    }
}
