package org.example;

import java.util.ArrayList;
    import java.util.List;

    public class Item {
        // Attributes
        private int id;
        private String name;
        private double price;
        private String category;
        private boolean availability;
        private List<Review<?>> reviews;


        public Item(){

        }

        public Item(int id, String name, double price, String category, boolean availability) {
            this.id = id;
            this.name = name;
            this.reviews=new ArrayList<>();
            this.price = price;
            this.category = category;
            this.availability = availability;
            reviews.add(new Review<>(2));
            reviews.add(new Review<>("good"));
        }
        public List<Review<?>> getReviews() {
            return reviews;
        }
        public void addReview(Review<?> rev){
            reviews.add(rev);
        }
        public void setReviews(List<Review<?>> reviews) {
            this.reviews = reviews;
        }

        public int getId() {
            return id;
        }
        public boolean isAvailable(){
            return availability;
        }
        public void setId(int id) {
            this.id = id;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }


        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public boolean isAvailability() {
            return availability;
        }

        public void setAvailability(boolean availability) {
            this.availability = availability;
        }


        @Override
        public String toString() {
            return "Item{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", price=" + price +
                    ", category='" + category + '\'' +
                    ", availability=" + availability +
                    '}';
        }
    }
