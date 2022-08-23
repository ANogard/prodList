package com.example.prodList.model.response;

import lombok.Data;

@Data
public class FrequencyByProductDTO {

  private String productName;
  private Integer frequency;

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public Integer getFrequency() {
    return frequency;
  }

  public void setFrequency(Integer frequency) {
    this.frequency = frequency;
  }
}
