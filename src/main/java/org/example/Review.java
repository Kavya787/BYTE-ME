package org.example;

class Review<T>{
    private T review;
    public Review(T r) {
        this.review = r;
    }
    public T getReview() {
        return review;
    }
}