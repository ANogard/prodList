package com.example.prodList.controllers;

import com.example.prodList.model.DateWithFrequency;
import com.example.prodList.model.Price;
import com.example.prodList.model.PriceRepo;
import com.example.prodList.model.Product;
import com.example.prodList.model.ProductRepo;
import com.example.prodList.model.ProductWithFrequency;
import com.example.prodList.model.ProductWithPrice;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

  private final ProductRepo productRepo;
  private final PriceRepo priceRepo;

  MainController(ProductRepo productRepo, PriceRepo priceRepo){
    this.productRepo = productRepo;
    this.priceRepo = priceRepo;
  }

  @GetMapping("/products/statistic")
  public List<Object> statistic(){
    List<Object> out = new ArrayList<>();
    out.add(productRepo.count());
    List<ProductWithFrequency> products = priceRepo.countPriceChangeFrequencyByProduct();
    out.add(products);

    List<DateWithFrequency> dates = priceRepo.countPriceChangeFrequencyByDate();
    out.add(dates);
    return out;
  }

  @GetMapping("/products")
  public List<ProductWithPrice> prices(@RequestParam String date){
    Iterable<Product> productIterable = productRepo.findAll();
    List<ProductWithPrice> list = new ArrayList<>();
    for (Product product : productIterable) {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      try {
        Optional<Price> optionalPrice = priceRepo.findFirstByProductAndDatetimeLessThanEqualOrderByDatetimeDesc(product, formatter.parse(date));
        if(optionalPrice.isPresent()){
          Price price = optionalPrice.get();
          ProductWithPrice productWithPrice = new ProductWithPrice(price.getProduct().getName(), price.getPrice());
          list.add(productWithPrice);
        }
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    return list;
  }

}
