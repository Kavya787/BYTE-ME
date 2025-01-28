package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class OrderManagement {
    private PriorityQueue<QueueElement> orderQueue;
    private ArrayList<Order> completedOrders;
    static public Integer orderId,date;
    Scanner scanner ;
    CustomerManagement customerManagement;
    private Map<Integer, ArrayList<Order>> orderHistory;
    public OrderManagement() {
        Comparator<QueueElement> comparator = (e1, e2) -> {
            if (e1.getCustomerType() != e2.getCustomerType()) {
                return Integer.compare(e2.getCustomerType(), e1.getCustomerType());
            }
            return Integer.compare(e2.getTime(), e1.getTime());
        };
        scanner= new Scanner(System.in);
        orderId=1;
        date=1;
        this.orderHistory = new HashMap<>();
        this.orderQueue = new PriorityQueue<>(comparator);
        this.completedOrders = new ArrayList<>();
    }
    public void increaseDate(){
        if(orderQueue.isEmpty()){
            date+=1;
            System.out.println("new date : "+date);
        }
        else{
            System.out.println("order queue is not empty");
        }

    }
    public void removeOrderByName(String orderName) {

        for (QueueElement element : orderQueue) {
            if (element.getOrder().getName().equals(orderName)) {
                orderQueue.remove(element);
                System.out.println("Order with name \"" + orderName + "\" has been removed from the queue.");
                return;
            }
        }
        System.out.println("Order with name \"" + orderName + "\" not found in the queue.");
    }
    public void removeOrderById(Integer id) {
        for (QueueElement element : orderQueue) {
            if (element.getOrder().getOrderId().equals(id)) {
                orderQueue.remove(element);
                System.out.println("Order with id \"" + id + "\" has been removed from the queue.");
                return;
            }
        }
        System.out.println("Order with name \"" + id + "\" not found in the queue.");
    }
    public void increaseOrderId(){
        orderId+=1;
    }
    public void addOrder(int customerType, int time, Order order) {
        QueueElement newOrder = new QueueElement(customerType, time, order);
        orderQueue.add(newOrder);
    }

    public QueueElement popOrder() {
        return orderQueue.isEmpty() ? null : orderQueue.poll();
    }
    public void setCustomerManagement(CustomerManagement cust){
        this.customerManagement=cust;
    }
    public void completeOrder() {
        QueueElement topOrder = popOrder();
        if (topOrder != null) {
            Order completedOrder = topOrder.getOrder();
            try{
                completedOrder.setStatus("cooking");
            }
            catch (Exception e){
                System.out.println(e);
            }

            completedOrders.add(completedOrder);
            System.out.println("Order cooking: " + completedOrder);

        } else {
            System.out.println("No pending orders to complete.");
        }
    }
    public void updateStatus(){
        System.out.println("enter pid");
        Integer id=scanner.nextInt();
        scanner.nextLine();
        ArrayList<Order>cOrders=orderHistory.get(id);
        if(cOrders!=null){
            System.out.println("orders available ");
            for(Order ord:cOrders){
                System.out.println(ord);
            }
            System.out.println("name : new status");
            String name=scanner.nextLine();
            String newStatus=scanner.nextLine();
            for(Order ord:completedOrders){
                if(ord.getName().equals(name) && ord.getOrderId().equals(id)){
                    try{
                        ord.setStatus(newStatus);
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }
                }
            }
        }
    }
    public void displayPendingOrders() {
        if (orderQueue.isEmpty()) {
            System.out.println("No pending orders.");
        } else {
            System.out.println("Pending Orders:");
            for (QueueElement element : orderQueue) {
                System.out.println(element);
                System.out.println("\n");
            }
        }
    }

    public void displayPendingOrdersGUI() {
        if (orderQueue.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No pending orders.");
            return;
        }

        String[] columnNames = {
                "Order ID", "Date","Time", "Customer Name",
                "Delivery Address", "Payment Mode",
                "Status", "Special Request",
                "Item ID", "Item Name", "Price",
                "Category", "Availability", "Quantity"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (QueueElement ele : orderQueue) {
            Order order=ele.getOrder();

            Object[] rowData = {
                    order.getOrderId(),
                    order.getDate(),   ele.getTime() ,
                    order.getCustomer() != null ?
                            order.getCustomer().getName() : "N/A",
                    order.getDeliveryAddress(),
                    order.getPaymentMode(),
                    order.getStatus(),
                    order.getSpecialRequest(),
                    order.getId(),
                    order.getName(),
                    order.getPrice(),
                    order.getCategory(),
                    order.isAvailable() ? "Available" : "Unavailable",
                    order.getQuantity()
            };
            tableModel.addRow(rowData);
        }

        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(200);
        table.getColumnModel().getColumn(5).setPreferredWidth(120);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);
        table.getColumnModel().getColumn(7).setPreferredWidth(150);
        table.getColumnModel().getColumn(8).setPreferredWidth(80);
        table.getColumnModel().getColumn(9).setPreferredWidth(150);
        table.getColumnModel().getColumn(10).setPreferredWidth(80);
        table.getColumnModel().getColumn(11).setPreferredWidth(100);
        table.getColumnModel().getColumn(12).setPreferredWidth(100);
        table.getColumnModel().getColumn(13).setPreferredWidth(80);

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setPreferredSize(new Dimension(1000, 400));


        JOptionPane.showMessageDialog(null, scrollPane, "Pending Orders", JOptionPane.INFORMATION_MESSAGE);
    }
    public void displayCompletedOrders() {
        if (completedOrders.isEmpty()) {
            System.out.println("No completed orders.");
        } else {
            System.out.println("Completed Orders:");
            for (Order order : completedOrders) {
                System.out.println(order);
            }
        }
    }
    public void addToAllOrders(Integer id,ArrayList<Order>orders){
        orderHistory.put(id, orders);
    }
    public void updateDeletedOrders(String itemName) {
        for (QueueElement element : orderQueue) {
            Order order = element.getOrder();
            if (order.getName().equals(itemName)) {
                try{
                    order.setStatus("denied");
                }
                catch (Exception e){
                    System.out.println(e);
                }

                removeOrderByName(itemName);
            }
        }
    }
    public void generateReport() {
        double totalSales = 0.0;
        int totalOrders = 0;
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> itemPopularity = new HashMap<>();
        System.out.println("ENTER DATE");
        Integer cdate = scanner.nextInt();
        scanner.nextLine();
        for (Map.Entry<Integer, ArrayList<Order>> entry : orderHistory.entrySet()) {
            ArrayList<Order> currOrders = entry.getValue();
            if (currOrders != null) {
                for (Order order : currOrders) {
                    if(order.getDate().equals(cdate) && order.getStatus().equals("delivered")){
                        double orderTotal = order.getPrice() * order.getQuantity();
                        totalSales += orderTotal;
                        totalOrders++;

                        String itemName = order.getName();
                        itemPopularity.put(itemName, itemPopularity.getOrDefault(itemName, 0) + order.getQuantity());
                    }

                }
            }
        }

        String mostPopularItem = null;
        int maxPopularity = 0;
        for (Map.Entry<String, Integer> entry : itemPopularity.entrySet()) {
            if (entry.getValue() > maxPopularity) {
                maxPopularity = entry.getValue();
                mostPopularItem = entry.getKey();
            }
        }
        System.out.println("Daily Sales Report");
        System.out.println("==================");
        System.out.println("Total Sales: $" + totalSales);
        System.out.println("Total Orders Processed: " + totalOrders);
        if (mostPopularItem != null) {
            System.out.println("Most Popular Item: " + mostPopularItem + " (Quantity Sold: " + maxPopularity + ")");
        } else {
            System.out.println("Most Popular Item: None");
        }
    }
    public void giveRefund(){
        System.out.println("RefundAsked");
        for(Order ord:completedOrders){
            if(ord.getStatus().equals("RefundAsked")){
                System.out.println(ord);
                System.out.println("give refund (yes/no)");
                String ans=scanner.nextLine();
                if(ans.equals("yes")){
                    try{
                        ord.setStatus("RefundGiven");
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }
                    System.out.println("customer amount "+ord.getCustomer().getWallet());
                    System.out.println("refund initiated of amount"+(ord.getPrice()*ord.getQuantity()));
                    ord.getCustomer().increaseWallet((ord.getPrice()*ord.getQuantity()));
                    System.out.println("customer new amount "+ord.getCustomer().getWallet());
                }
                else{
                    System.out.println("no refund");
                }
            }
        }

    }
    public void viewOrderStatus(Integer id,String custName){
        ArrayList<Order>cOrders=orderHistory.get(id);
        if(cOrders!=null){
            for(Order ord:cOrders){
                if(ord.getCustomer().getName().equals(custName)){
                    System.out.println(ord);
                }
            }
        }
    }
    public void startUI() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nOrder Management System");
            System.out.println("1. View Pending Orders");
            System.out.println("2. Complete Next Order");
            System.out.println("3. View Completed Orders");
            System.out.println("4. generateReport");
            System.out.println("5:update order status\n 6:increase date\n 7-give refund");

            System.out.println("8:exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayPendingOrders();
                    break;
                case 2:
                    completeOrder();
                    break;
                case 3:
                    displayCompletedOrders();
                    break;
                case 4:
                    generateReport();
                    break;
                case 5:
                    updateStatus();
                    break;
                case 6:
                    increaseDate();
                    break;
                case 7:
                    giveRefund();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 8);
    }
}
