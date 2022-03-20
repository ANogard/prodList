package com.example.prodList.model;

public class ProductWithPrice {

  private String product;
  private int price;

  public ProductWithPrice(String product, int price) {
    this.product = product;
    this.price = price;
  }

  public String getProduct() {
    return product;
  }
  public int getPrice(){
    return price;
  }
  public void setProduct(String product) {
    this.product = product;
  }
  public void setPrice(int price) {
    this.price = price;
  }
}
