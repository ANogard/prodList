package com.example.prodList.controllers;

import com.example.prodList.model.response.ProductWithPriceDTO;
import com.example.prodList.service.ProductsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

  private final ProductsService productsService;

  @GetMapping
  public List<ProductWithPriceDTO> prices(@RequestParam String date) {
    return productsService.getPrices(date);
  }

  @GetMapping("/statistic")
  public List<Object> statistic() {
    return productsService.getStatistic();
  }
}
