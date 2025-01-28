package org.example;//package org.example;

import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
public class MenuManagement {
    private Map<String, Item> menuMap;
    private TreeSet<Item>  priceAscQueue;
    private TreeSet<Item>  priceDescQueue;
    private OrderManagement orderManagement;
    private Scanner scanner;
    // Constructor
    public MenuManagement(OrderManagement orderManagement) {
        this.orderManagement=orderManagement;
        this.menuMap = new HashMap<>();
        this.priceAscQueue = new TreeSet<>(Comparator.comparingDouble(Item::getPrice));
        this.priceDescQueue = new TreeSet<>((i1, i2) -> Double.compare(i2.getPrice(), i1.getPrice()));
        this.scanner = new Scanner(System.in);
    }

    // Getter for menuMap
    public Map<String, Item> getMenuMap() {
        return menuMap;
    }

    // Method to add an Item to the menu
    public void addItem(Item item) {
        if (menuMap.containsKey(item.getName())) {
            System.out.println("Error: Item with the name '" + item.getName() + "' already exists in the menu.");
            return;
        }

        for (Item existingItem : menuMap.values()) {
            if (existingItem.getId() == item.getId()) {
                System.out.println("Error: Item with the ID '" + item.getId() + "' already exists in the menu.");
                return;
            }
        }
        menuMap.put(item.getName(), item);
        priceAscQueue.add(item);
        priceDescQueue.add(item);
        System.out.println("Item added: " + item);
    }

    public void updateItem(String itemName) {
        Item item = menuMap.get(itemName);
        if (item == null) {
            System.out.println("Error: Item with the name '" + itemName + "' does not exist.");
            return;
        }
        priceAscQueue.remove(item);
        priceDescQueue.remove(item);

        System.out.println("Updating item: " + item);
        System.out.print("Enter new price (current: " + item.getPrice() + "): ");
        double newPrice = scanner.nextDouble();
        System.out.print("Enter new category (current: " + item.getCategory() + "): ");
        scanner.nextLine(); // Consume the newline character
        String newCategory = scanner.nextLine();
        System.out.print("Is the item available? (true/false, current: " + item.isAvailability() + "): ");
        boolean newAvailability = scanner.nextBoolean();

        item.setPrice(newPrice);
        item.setCategory(newCategory);
        item.setAvailability(newAvailability);
        priceAscQueue.add(item);
        priceDescQueue.add(item);
        System.out.println("Item updated: " + item);
    }
    public void filterByCategory() {
        Set<String> categories = new HashSet<>();
        for (Item item : menuMap.values()) {
            categories.add(item.getCategory());
        }
        for (String category : categories) {
            System.out.println("available cate " + category);
        }
        System.out.print("Enter the category to filter ");
        String selectedCategory = scanner.nextLine();
        System.out.println("Items : \"" + selectedCategory + "\":");
        for (Item item : menuMap.values()) {
            if (item.getCategory().equalsIgnoreCase(selectedCategory)) {
                System.out.println(item);
            }
        }

    }

    public void removeItem(String itemName) {
        Item item = menuMap.remove(itemName);
        if (item != null) {
            priceAscQueue.remove(item);
            priceDescQueue.remove(item);
            System.out.println("Item '" + itemName + "' removed successfully.");
            orderManagement.updateDeletedOrders(itemName);
        } else {
            System.out.println("Error: Item with the name '" + itemName + "' does not exist.");
        }
    }

    public Item searchItem(String itemName) {
        return menuMap.getOrDefault(itemName, null);
    }

    public void displayMenu() {
        if (menuMap.isEmpty()) {
            System.out.println("The menu is empty.");
            return;
        }
        for (Item item : menuMap.values()) {
            System.out.println(item);
        }
    }

    public void displayItemsByPriceAscending() {
        if (priceAscQueue.isEmpty()) {
            System.out.println("The menu is empty.");
            return;
        }
        System.out.println("Menu items sorted by price (ascending):");
        for (Item item : priceAscQueue) {
            System.out.println(item);
        }
    }

    public void displayItemsByPriceDescending() {
        if (priceDescQueue.isEmpty()) {
            System.out.println("The menu is empty.");
            return;
        }
        System.out.println("Menu items sorted by price (descending):");
        for (Item item : priceDescQueue) {
            System.out.println(item);
        }
    }
    public void displayMenuGUI() {
        if (menuMap.isEmpty()) {
            JOptionPane.showMessageDialog(null, "The menu is empty.");
            return;
        }

        String[] columnNames = {"ID", "Name", "Price", "Category", "Availability"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Item item : menuMap.values()) {
            Object[] rowData = {
                    item.getId(),
                    item.getName(),
                    item.getPrice(),
                    item.getCategory(),
                    item.isAvailable() ? "Available" : "Unavailable"
            };
            tableModel.addRow(rowData);
        }

        JTable table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);

        JOptionPane.showMessageDialog(null, scrollPane, "Menu", JOptionPane.INFORMATION_MESSAGE);
    }
    public void setStringReview(String itemName,String rev){
        Item item = menuMap.get(itemName);
        if (item == null) {
            System.out.println("Error: Item with the name '" + itemName + "' does not exist.");
            return;
        }
        item.addReview(new Review<>(rev));
    }
    public void setIntReview(String itemName,Integer rev){
        Item item = menuMap.get(itemName);
        if (item == null) {
            System.out.println("Error: Item with the name '" + itemName + "' does not exist.");
            return;
        }
        item.addReview(new Review<>(rev));
    }
    public void getReview(){
        String itemName=scanner.nextLine();
        Item item = menuMap.get(itemName);
        if (item == null) {
            System.out.println("Error: Item with the name '" + itemName + "' does not exist.");
            return;
        }
        for (Review<?> feedback : item.getReviews()) {
            System.out.println(feedback.getReview());
        }
    }
    public void startUI() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Display Menu");
            System.out.println("2. Add Item");
            System.out.println("3. Update Item");
            System.out.println("4. Remove Item");
            System.out.println("5. Display Items by Price (Ascending)");
            System.out.println("6. Display Items by Price (Descending)");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayMenu();
                    break;
                case 2:
                    System.out.print("Enter item ID (integer): ");
                    int id = scanner.nextInt();
                    System.out.print("Enter item name: ");
                    scanner.nextLine();
                    String name = scanner.nextLine();
                    System.out.print("Enter item price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter item category: ");
                    scanner.nextLine();
                    String category = scanner.nextLine();
                    System.out.print("Is the item available? (true/false): ");
                    boolean available = scanner.nextBoolean();
                    addItem(new Item(id, name, price, category, available));
                    break;
                case 3:
                    System.out.print("Enter item name to update: ");
                    String itemNameToUpdate = scanner.nextLine();
                    updateItem(itemNameToUpdate);
                    break;
                case 4:
                    System.out.print("Enter item name to remove: ");
                    String itemNameToRemove = scanner.nextLine();
                    removeItem(itemNameToRemove);
                    break;
                case 5:
                    displayItemsByPriceAscending();
                    break;
                case 6:
                    displayItemsByPriceDescending();
                    break;

                case 7:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
}