package org.example;

class QueueElement {
    private int customerType;
    private int time;
    private Order order;

    public QueueElement(int firstPriority, int secondPriority, Order order) {
        this.customerType = firstPriority;
        this.time = secondPriority;
        this.order = order;
    }



    public int getCustomerType() {
        return customerType;
    }

    public int getTime() {
        return time;
    }

    public Order getOrder() {
        return order;
    }


    @Override
    public String toString() {
        return "order details:" +
                "customer=" + order.getCustomer() +
                ", time=" + time +
                ", order=" + order +
                '}';
    }
}