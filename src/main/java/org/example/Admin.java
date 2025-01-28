package org.example;

import javax.swing.*;
import java.util.Scanner;

public class Admin {
    OrderManagement orderManagement;
    MenuManagement menuManagement;
    CustomerManagement customerManagement;
    public Admin(){
        initialize();
        start();
    }
    public void initialize(){
        orderManagement = new OrderManagement();
        menuManagement = new MenuManagement(orderManagement);
        menuManagement.addItem(new Item(1, "Burger1", 5.88, "Meals", true));
        menuManagement.addItem(new Item(6, "Burger2", 5.99, "Meals", true));
        menuManagement.addItem(new Item(7, "Burger3", 5.64, "Meals", true));
        menuManagement.addItem(new Item(2, "Fries", 2.99, "Snacks", true));
        menuManagement.addItem(new Item(3, "Coke", 1.49, "Beverages", true));
        menuManagement.addItem(new Item(4, "Salad", 4.99, "Meals", true));
        menuManagement.addItem(new Item(5, "Ice Cream", 3.49, "Dessert", true));
        customerManagement = new CustomerManagement(orderManagement,menuManagement);
        orderManagement.setCustomerManagement(customerManagement);

    }
    public void startGUI(){
        boolean exit = false;
        while (!exit) {
            String[] options = {"Display Menu", "Display Pending orders", "Exit"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Please choose an option:",
                    "START GUI",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            switch (choice) {
                case 0:
                    menuManagement.displayMenuGUI();
                    break;
                case 1:
                    orderManagement.displayPendingOrdersGUI();
                    break;
                case 2:
                    exit = true;
                    JOptionPane.showMessageDialog(null, "Exiting...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option, please try again.");
            }
        }
    }
    public void start(){
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nOrder Management System");
            System.out.println("1.order management");
            System.out.println("2. menu management");
            System.out.println("3. customer management\n4.GUI");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    orderManagement.startUI();
                    break;
                case 2:
                    menuManagement.startUI();
                    break;
                case 3:
                    customerManagement.logIn();
                    break;
                case 4:
                    startGUI();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }
}
