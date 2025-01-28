package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Customer {
    private double wallet;
    private String name;

    private String password;
    private Integer isVip; // 1 for no vip, 2 for vip
    private Cart cart;
    private Map<Integer, ArrayList<Order>> orderHistory;
    private OrderManagement orderManagement;
    Scanner scanner;
    private MenuManagement menuManagement;
    // Constructor
    public Customer(double wallet, String name,String password, Integer isVip, OrderManagement orderManagement,MenuManagement menuManagement) {
        this.wallet = wallet;
        this.name = name;
        this.password=password;
        scanner = new Scanner(System.in);
        this.isVip = isVip;
        this.cart = new Cart();
        this.orderHistory = new HashMap<>();
        this.orderManagement=orderManagement;
        this.menuManagement=menuManagement;
    }

    public String getPassword() {
        return password;
    }

    // Getters
    public double getWallet() {
        return wallet;
    }

    public String getName() {
        return name;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public Cart getCart() {
        return cart;
    }

    public Map<Integer, ArrayList<Order>> getOrderHistory() {
        return orderHistory;
    }

    // Setters
    public void setWallet(double wallet) {
        this.wallet = wallet;
    }
    public void increaseWallet(double wallet){
        this.wallet+=wallet;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public void setCart(Cart currentCart) {
        this.cart = currentCart;
    }
    public void saveOrderHistoryToFile(String filePath,Order order) throws IOException {
        File file = new File(filePath);

        boolean isFileEmpty = !file.exists() || file.length() == 0;
        try (FileWriter writer = new FileWriter(filePath,true)) {
            if (isFileEmpty) {
                writer.append("OrderId,CustomerName,ProductName,Quantity,Price,Date,Status,SpecialRequest\n");
            }
//            writer.append("OrderId,CustomerName,ProductName,Quantity,Price,Date,Status,SpecialRequest\n");
                    writer.append(String.format("%d,%s,%s,%d,%.2f,%d,%s,%s\n",
                            order.getOrderId(),
                            order.getCustomer().getName(),
                            order.getName(),
                            order.getQuantity(),
                            order.getPrice(),
                            order.getDate(),
                            order.getStatus(),
                            order.getSpecialRequest()));

            writer.flush();
            System.out.println("Order history saved to " + filePath);
        }
    }
    public void addToCartFile(String filePath, Item item, int quant) throws IOException {
        File file = new File(filePath);

        boolean isFileEmpty = !file.exists() || file.length() == 0;
        try (FileWriter writer = new FileWriter(filePath, true)) {
            if (isFileEmpty) {
                writer.append("id,name,price,quantity\n");
            }

            // Write the new item information to the file
            writer.append(String.format("%d,%s,%.2f,%d\n", item.getId(), item.getName(), item.getPrice(), quant));
            writer.flush();
            System.out.println("Item added to " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + filePath, e);
        }
    }

    public void startUI(){
        try{
            Scanner scanner = new Scanner(System.in);
            boolean exit = false;

            while (!exit) {
                System.out.println("\nPlease choose an option:\n1-view all order \n2-serach item by name\n3-filterbycategory\n4-sort asc\n5-sort desc");
                System.out.println("6. Add Item to Cart");
                System.out.println("7. Modify Item Quantity in Cart");
                System.out.println("8. Remove Item from Cart");
                System.out.println("9. View Cart Contents");
                System.out.println("10. View Total Price");
                System.out.println("11 Checkout and Place Order");
                System.out.println("12. view order status \n13-cancel order \n14-past order \n15-reorder\n16-get review\n17-give string review\n18-give int review \n19-ask refund \n20:view wallet\n21:exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        menuManagement.displayMenu();
                        break;
                    case 2:
                        System.out.println("enter name");
                        String item=scanner.nextLine();
                        System.out.println(menuManagement.searchItem(item));
                        break;
                    case 3:
                        menuManagement.filterByCategory();
                        break;
                    case 4:
                        menuManagement.displayItemsByPriceAscending();
                        break;
                    case 5:
                        menuManagement.displayItemsByPriceDescending();
                        break;
                    case 6:
                        addItemToCart();
                        break;
                    case 7:
                        try{
                            modifyItemQuantity();
                        }
                        catch (Exception e){
                            System.out.println(e);
                        }

                        break;
                    case 8:
                        try{
                            removeItemFromCart();
                        }
                        catch (Exception e){
                            System.out.println(e);
                        }
                        break;
                    case 9:
                        cart.displayCart();
                        break;
                    case 10:
                        System.out.println("total : "+cart.viewTotal());
                        break;
                    case 11:
                        checkout();
                        break;
                    case 12:
                        trackOrderStatus();
                        break;
                    case 13:
                        cancelOrder();
                        break;
                    case 14:
                        viewPastOrder();
                        break;
                    case 15:
                        reorder();
                        break;
                    case 16:
                        menuManagement.getReview();
                        break;
                    case 17:
                        System.out.println("enter name then string review");
                        String itemName=scanner.nextLine();
                        String rev=scanner.nextLine();
                        boolean valid=false;
                        for (Map.Entry<Integer, ArrayList<Order>> entry : orderHistory.entrySet()) {
                            Integer pid = entry.getKey();
                            ArrayList<Order> cOrders = entry.getValue();
                            for(Order ord:cOrders){
                                if(Objects.equals(ord.getName(), itemName)  && ord.getStatus().equals("delivered")){
                                    valid=true;
                                    break;
                                }
                            }
                        }
                        if(valid){
                            menuManagement.setStringReview(itemName,rev);
                        }
                        else{
                            System.out.println("product is not ordered by the customer");
                        }
                        break;
                    case 18:
                        System.out.println("enter name then int review");
                        String itemName2=scanner.nextLine();
                        int rev2 = scanner.nextInt();
                        scanner.nextLine();
                        boolean valid2=false;
                        for (Map.Entry<Integer, ArrayList<Order>> entry : orderHistory.entrySet()) {
                            Integer pid = entry.getKey();
                            ArrayList<Order> cOrders = entry.getValue();
                            for(Order ord:cOrders){
                                if(Objects.equals(ord.getName(), itemName2) && ord.getStatus().equals("delivered")){
                                    valid2=true;
                                    break;
                                }
                            }
                        }
                        if(valid2){
                            menuManagement.setIntReview(itemName2,rev2);
                        }
                        else{
                            System.out.println("product is not ordered by the customer");
                        }

                        break;
                    case 19:
                        askRefund();
                        break;
                    case 20:
                        System.out.println("wallet : "+wallet);
                        break;
                    case 21:
                        exit = true;
                        System.out.println("Exiting cart UI...");
                        break;
                    default:
                        System.out.println("Invalid option, please try again.");
                }
                }
        }
        catch (Exception e){
            System.out.println(e);

        }
    }
    public void reorder(){
        System.out.println("enter pid ");
        Integer pid=scanner.nextInt();
        scanner.nextLine();
        ArrayList<Order>cOrder=orderHistory.get(pid);
        if(cOrder!=null){
                ArrayList<Order>currentOrders=new ArrayList<>();
                for(Order originalOrder :cOrder) {
                    Item it=menuManagement.searchItem(originalOrder.getName());
                    if(it!=null){
                        System.out.println("enter special req for item -" +it);
                        String specialReq=scanner.nextLine();
                        System.out.println("enter payment req for item -" +it);
                        String paym=scanner.nextLine();
                        Order order = new Order(it, originalOrder.getQuantity(), OrderManagement.orderId, OrderManagement.date, this,originalOrder.getDeliveryAddress(),paym,"order_received",specialReq);
                        currentOrders.add(order);
                    }
                    else{
                        System.out.println("one order is deleted cannot proceed further");
                        break;
                    }
                }
                if(!currentOrders.isEmpty()){
                    orderHistory.put(OrderManagement.orderId, currentOrders);
                    orderManagement.addToAllOrders(OrderManagement.orderId,currentOrders);
                    orderManagement.increaseOrderId();
                    System.out.print("Enter the time (as an integer): ");
                    int time = scanner.nextInt();
                    scanner.nextLine();
                    for (Order ord:currentOrders){
                        orderManagement.addOrder(isVip,time,ord);
                    }
                    System.out.println("Order placed successfully order id: "+(OrderManagement.orderId-1));
                }
            }
        else{
            System.out.println("enter valid pid");
        }
    }
    public void viewPastOrder(){
        for (Map.Entry<Integer, ArrayList<Order>> entry : orderHistory.entrySet()) {
            Integer pid = entry.getKey();
            ArrayList<Order> cOrders = entry.getValue();
            for(Order ord:cOrders){
                System.out.println(ord);
            }
        }
    }

    public void addItemToCart() {
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        Item item = menuManagement.searchItem(itemName);
        if (item != null && item.isAvailable() &&  quantity>=0) {
            cart.addItem(item, quantity);
            try{
                addToCartFile("cart_"+this.name+".csv",item,quantity);
            }
            catch (Exception e){
                System.out.println(e);
            }

        } else {
            System.out.println("Item not valid in the menu.");
        }

    }
    public boolean addItemToCart(String itemName,int quantity) {
        Item item = menuManagement.searchItem(itemName);
        if (item != null && item.isAvailable() &&  quantity>=0) {
            cart.addItem(item, quantity);
            return true;
        } else {
            System.out.println("Item not valid in the menu.");
            return false;
        }
    }


    public void modifyItemQuantity() throws IOException {
        System.out.print("Enter item name to modify quantity: ");
        String itemName = scanner.nextLine();
        String filePath="cart_"+this.name+".csv";
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        List<String> updatedLines = new ArrayList<>();
        Item item = cart.findItemByName(itemName);
        if (item != null) {
            System.out.print("Enter new quantity: ");
            int newQuantity = scanner.nextInt();
            scanner.nextLine();
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts[1].equalsIgnoreCase(itemName)) {
                    cart.modifyQuantity(item, newQuantity);
                    parts[3] = String.valueOf(newQuantity);
                    updatedLines.add(String.join(",", parts));
                } else {
                    updatedLines.add(line);
                }
            }
            try (FileWriter writer = new FileWriter(filePath)) {
                for (String updatedLine : updatedLines) {
                    writer.write(updatedLine + "\n");
                }
                System.out.println("Item quantity updated successfully.");
            } catch (IOException e) {
                throw new RuntimeException("Error updating the file: " + filePath, e);
            }

        } else {
            System.out.println("Item not found in the cart.");
        }
    }
    public void modifyItemQuantity(String itemName,int newQuantity) {
        Item item = cart.findItemByName(itemName);
        if (item != null && newQuantity>=0) {
            cart.modifyQuantity(item, newQuantity);
        } else {
            System.out.println("Item not found in the cart.");
        }
    }
    private void askRefund(){
        viewPastOrder();
        System.out.println("enter id and fname");
        Integer pid=scanner.nextInt();
        scanner.nextLine();
        String name =scanner.nextLine();
        ArrayList<Order>cOrders=orderHistory.get(pid);
        if(cOrders!=null){
            for(Order order:cOrders){
                if(order.getName().equals(name) && order.getStatus().equals("delivered")){
                    try{
                        order.setStatus("RefundAsked");
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }
                    System.out.println("refund asked for order "+order);
                }
            }
        }
        else{
            System.out.println("no valid id");
        }

    }

    private void removeItemFromCart() throws IOException {
        System.out.print("Enter item name to remove: ");
        String itemName = scanner.nextLine();
        Item item = cart.findItemByName(itemName);
        String filePath="cart_"+this.name+".csv";

        List<String> lines = Files.readAllLines(Paths.get(filePath));

        List<String> updatedLines = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");

            if (!parts[1].equalsIgnoreCase(itemName)) {
                updatedLines.add(line);
            }
        }
        System.out.println(updatedLines);
        if (item != null) {
            try (FileWriter writer = new FileWriter(filePath)) {
                for (String line : updatedLines) {
                    writer.write(line + "\n");
                }
                System.out.println("Item removed from the cart file.");
            } catch (IOException e) {
                throw new RuntimeException("Error updating the file: " + filePath, e);
            }
            cart.removeItem(item);
        } else {
            System.out.println("Item not found in the cart.");
        }
    }
    private void cancelOrder(){
        System.out.println("enter id to delete (order can be only cancelled in received stage");
        Integer id=scanner.nextInt();
        scanner.nextLine();
        orderManagement.removeOrderById(id);
    }
    private void checkout() throws IOException {
        double total = cart.viewTotal();
        if (wallet < total) {
            System.out.println("Insufficient wallet balance to place the order.");
            return;
        }
        wallet -= total;
        ArrayList<Order> currentOrders = new ArrayList<>();
        for (Map.Entry<Item, Integer> entry : cart.getCartItems().entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            System.out.println("enter special req for item -" +item);
            String specialReq=scanner.nextLine();
            System.out.println("enter address for item -" +item);
            String addr=scanner.nextLine();
            System.out.println("enter payment req for item -" +item);
            String paym=scanner.nextLine();
            Order order = new Order(item, quantity, OrderManagement.orderId, OrderManagement.date, this,addr,paym,"order_received",specialReq);
            currentOrders.add(order);
            saveOrderHistoryToFile("kavya.csv",order);

        }
        orderHistory.put(OrderManagement.orderId, currentOrders);
        orderManagement.addToAllOrders(OrderManagement.orderId,currentOrders);
        orderManagement.increaseOrderId();
        System.out.print("Enter the time (as an integer): ");
        int time = scanner.nextInt();
        scanner.nextLine();
        for (Order ord:currentOrders){
            orderManagement.addOrder(isVip,time,ord);
        }
        System.out.println("Order placed successfully order id: "+(OrderManagement.orderId-1));
        String filePath="cart_"+this.name+".csv";
        try{
            cart.clearCart(filePath);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public void trackOrderStatus() {
        System.out.print("Enter Order ID to track: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        ArrayList<Order> ordersForKey = orderHistory.get(choice);
        if(ordersForKey!=null){
            for(Order order:ordersForKey){
                System.out.println("Status of Order " + order.getName() + ": " + order.getStatus());
            }
        }
        else{
            System.out.println("Order not found.");
        }
    }

}
