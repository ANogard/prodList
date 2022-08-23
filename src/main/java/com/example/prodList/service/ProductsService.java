package com.example.prodList.service;

import com.example.prodList.model.entity.Price;
import com.example.prodList.model.entity.Product;
import com.example.prodList.model.response.FrequencyByDateDTO;
import com.example.prodList.model.response.FrequencyByProductDTO;
import com.example.prodList.model.response.ProductWithPriceDTO;
import com.example.prodList.repository.PriceRepository;
import com.example.prodList.repository.ProductRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductsService {

  private static final Logger LOGGER = LogManager.getLogger();
  private final ProductRepository productRepository;
  private final PriceRepository priceRepository;

  public List<ProductWithPriceDTO> getPrices(String date) {
    List<ProductWithPriceDTO> out = new ArrayList<>();
    List<Product> productsList = productRepository.getAll();

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date datetime = null;
    try {
      datetime = formatter.parse(date);
    } catch (ParseException e) {
      LOGGER.catching(e);
    }

    for (Product product : productsList) {
      try {
        Price price = priceRepository.getPriceByProductAndDate(datetime, product.getId());
        ProductWithPriceDTO productWithPrice = new ProductWithPriceDTO(product, price);
        out.add(productWithPrice);
      } catch (DataAccessException e) {
        LOGGER.catching(e);
      }
    }
    return out;
  }

  public List<Object> getStatistic() {
    List<Object> out = new ArrayList<>();
    out.add(productRepository.getAll().size());

    List<FrequencyByProductDTO> products = priceRepository.getFrequencyByProduct();
    out.add(products);

    List<FrequencyByDateDTO> dates = priceRepository.getFrequencyByDate();
    out.add(dates);
    return out;
  }
}
