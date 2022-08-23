package com.example.prodList.service;

import com.example.prodList.model.entity.Price;
import com.example.prodList.model.entity.Product;
import com.example.prodList.repository.PriceRepository;
import com.example.prodList.repository.ProductRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductLoad {

  private static final Logger LOGGER = LogManager.getLogger();
  private final ProductRepository productRepository;
  private final PriceRepository priceRepository;
  @Value("${productLoad.path}")
  private String path;

  @Scheduled(fixedRateString = "${productLoad.period}", initialDelay = 5000)
  public void loadProducts() {
    if (isFolderExists(path)) {
      List<File> CSVList = getCSVFromFolder(path);
      if (CSVList.size() != 0) {
        for (File file : CSVList) {
          LOGGER.info("Start loading file " + file);
          parseCSV(file);
          file.delete();
        }
      } else {
        LOGGER.info("Folder '" + path + "' doesn't contain any .csv files!");
      }
    } else {
      LOGGER.info("Folder '" + path + "' doesn't exists!");
    }
  }

  private void parseCSV(File file) {
    try {
      FileReader filereader = new FileReader(file);
      CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
      CSVReader csvReader = new CSVReaderBuilder(filereader)
          .withCSVParser(csvParser)
          .build();
      List<String[]> allData = csvReader.readAll();
      int productCounter = 0, priceCounter = 0;
      for (String[] row : allData) {
        String[] cell = row[0].split(";");
        long productId = Long.parseLong(cell[0]);
        Product product;
        try {
          product = productRepository.getProductById(productId);
        } catch (DataAccessException e) {
          product = new Product(productId, cell[1]);
          productRepository.saveProduct(product);
          productCounter++;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Price price = new Price(Long.parseLong(cell[2]),
            Integer.parseInt(cell[3]),
            formatter.parse(cell[4]),
            product.getId());
        priceCounter++;
        priceRepository.savePrice(price);
      }
      LOGGER.info("Loaded " + productCounter + " products, and "
          + priceCounter + " prices.");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private boolean isFolderExists(String path) {
    return Files.exists(Paths.get(path)) && Files.isDirectory(Paths.get(path));
  }

  private List<File> getCSVFromFolder(String path) {
    List<File> out = new ArrayList<>();
    for (File file : Objects.requireNonNull(new File(path).listFiles())) {
      if (file.getName().matches(".+\\.csv")) {
        out.add(file);
      }
    }
    return out;
  }
}
