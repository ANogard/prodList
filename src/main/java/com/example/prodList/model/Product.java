package com.example.prodList.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode
public class Product {

  @Id
  private long id;
  private String name;

  public Product(){}

  public Product(long id, String name) {
    this.id = id;
    this.name = name;
  }

  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString(){
    return name;
  }
}
