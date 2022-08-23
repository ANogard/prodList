package com.example.prodList.model.response;

import com.example.prodList.model.entity.Price;
import com.example.prodList.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductWithPriceDTO {

  private Product product;
  private Price price;

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Price getPrice() {
    return price;
  }

  public void setPrice(Price price) {
    this.price = price;
  }
}
