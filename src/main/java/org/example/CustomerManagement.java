package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class CustomerManagement {
    private ArrayList<Customer> customers;
    private OrderManagement orderManagement;
    private MenuManagement menuManagement;
    private Scanner scanner;

    public CustomerManagement(OrderManagement orderManagement, MenuManagement menuManagement) {
        this.customers = new ArrayList<>();
        this.orderManagement = orderManagement;
        this.menuManagement = menuManagement;
        this.customers.add(new Customer(1000,"k","k",1,orderManagement,menuManagement));
        this.customers.add(new Customer(1000,"s","s",0,orderManagement,menuManagement));
        scanner = new Scanner(System.in);
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public OrderManagement getOrderManagement() {
        return orderManagement;
    }

    public MenuManagement getMenuManagement() {
        return menuManagement;
    }


    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public void setOrderManagement(OrderManagement orderManagement) {
        this.orderManagement = orderManagement;
    }

    public void setMenuManagement(MenuManagement menuManagement) {
        this.menuManagement = menuManagement;
    }
    public void logIn(){
        System.out.println("enter name , pwd");
        String name=scanner.nextLine();
        String pwd=scanner.nextLine();
        for(Customer cust: customers){
            if(name.equals(cust.getName()) && pwd.equals(cust.getPassword())){
                cust.startUI();
                break;
            }
        }

    }

//    public void startUI(Customer customer){
//
//        boolean exit = false;
//
//        while (!exit) {
//            System.out.println("Please choose an option:");
//            System.out.println("1. Display Menu");
//            System.out.println("2. Exit");
//            System.out.print("Enter your choice: ");
//
//            int choice = scanner.nextInt();
//            scanner.nextLine(); // Consume the newline character
//
//            switch (choice) {
//                case 1:
//                    menuManagement.displayMenu(); // Print the menu
//                    break;
//                case 2:
//                    exit = true; // Exit the loop
//                    break;
//                default:
//                    System.out.println("Invalid option, please try again.");
//            }
//        }

//    }
}
