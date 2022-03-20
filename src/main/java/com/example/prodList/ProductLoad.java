package com.example.prodList;

import com.example.prodList.model.Price;
import com.example.prodList.model.PriceRepo;
import com.example.prodList.model.Product;
import com.example.prodList.model.ProductRepo;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductLoad {

  private static final Logger LOGGER = LogManager.getLogger();

  @Value("${productLoad.path}")
  private String path;

  private final ProductRepo productRepo;
  private final PriceRepo priceRepo;

  public ProductLoad(ProductRepo productRepo, PriceRepo priceRepo){
    this.productRepo = productRepo;
    this.priceRepo = priceRepo;
  }

  @Scheduled(fixedRateString = "${productLoad.period}")
  public void loadProducts(){
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      LOGGER.catching(e);
    }
    if (!Files.exists(Paths.get(path)) && !Files.isDirectory(Paths.get(path))) {
      LOGGER.info("Folder '" + path + "' doesn't exists!");
    } else {
      File dir = new File(path);
      File[] arrFiles = dir.listFiles();
      List<File> lst = new ArrayList<>();
      for(File file : arrFiles){
        if(file.getName().matches(".+\\.csv")){
          lst.add(file);
        }
      }
      if(lst.size() == 0) {
        LOGGER.info("Folder '" + path + "' doesn't contain any .csv files!");
      } else {
        for(File file : lst) {
          LOGGER.info("Start loading file " + file);
          parseCSV(file.toPath());
          file.delete();
        }
      }
    }
  }

  private void parseCSV(Path path){
    try {
      List<String> lines = Files.readAllLines(path);
      Set<Product> products = new HashSet<>();
      List<Price> prices = new ArrayList<>();
      for (String line: lines){
        String[] prod = line.split(";");
        if(prod.length != 5) {
          LOGGER.info("Wrong line");
          continue;
        }
        Product product;
        Optional<Product> optionalProduct = productRepo.findById(Long.parseLong(prod[0]));
        if(optionalProduct.isPresent()){
          product = optionalProduct.get();
          product.setName(prod[1]);
        } else{
          product = new Product(Long.parseLong(prod[0]), prod[1]);
        }
        products.add(product);
        try {
          SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
          Price price = new Price(Long.parseLong(prod[2]), Integer.parseInt(prod[3]),
              formatter.parse(prod[4]), product);
          prices.add(price);
        } catch (ParseException e) {
          LOGGER.catching(e);
        }
      }
      savePrices(products, prices);
    } catch (IOException e) {
      LOGGER.catching(e);
    }
  }

  private void savePrices(Set<Product> products, List<Price> prices){
    for(Product product : products){
      productRepo.save(product);
    }
    for(Price price : prices){
      priceRepo.save(price);
    }
    LOGGER.info("Loaded " + products.size() + " products, and "
     + prices.size() + " prices.");
  }
}
