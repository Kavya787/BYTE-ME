package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Order extends Item implements Serializable {
    private int quantity;
    private Integer orderId;
    private Integer date;
    private Customer customer;
    private String deliveryAddress;
    private String paymentMode;
    private String status;
    private String specialRequest;
    public Order() {
        super();
    }
    public Order(Item item, int quantity, Integer orderId, Integer date, Customer customer, String deliveryAddress, String paymentMode, String status,String special) {
        super(item.getId(), item.getName(), item.getPrice(), item.getCategory(), item.isAvailability());
        this.quantity = quantity;
        this.orderId = orderId;
        this.date = date;
        this.customer = customer;
        this.deliveryAddress = deliveryAddress;
        this.paymentMode = paymentMode;
        this.status = status;
        this.specialRequest=special;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setSpecialRequest(String specialRequest){
        this.specialRequest=specialRequest;
    }
    public String getSpecialRequest(){
        return this.specialRequest;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getStatus() {


        return status;
    }

    public void setStatus(String status) throws IOException {
        String filePath="kavya.csv";
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        List<String> updatedLines = new ArrayList<>();
        System.out.print("Enter new quantity: ");
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts[0].equalsIgnoreCase(String.valueOf(this.orderId)) && parts[1].equalsIgnoreCase(this.customer.getName()) && parts[2].equalsIgnoreCase(this.getName())) {
                parts[6] = String.valueOf(status);
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

        this.status = status;


    }

    @Override
    public String toString() {
        return "Order{" +
                "quantity=" + quantity +
                ", orderId='" + orderId + '\'' +
                ", date='" + date + '\'' +
                ", customer=" + customer +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", paymentMode='" + paymentMode + '\'' +
                ", status='" + status + '\'' +
                ", itemId=" + getId() +
                ", itemName='" + getName() + '\'' +
                ", itemPrice=" + getPrice() +
                ", itemCategory='" + getCategory() + '\'' +
                ", itemAvailability=" + isAvailability() + " special "+specialRequest+
                '}';
    }
}
