package com.example.prodList.model;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PriceRepo extends CrudRepository<Price, Long> {
  Optional<Price> findFirstByProductAndDatetimeLessThanEqualOrderByDatetimeDesc(Product product, Date date);
  @Query("SELECT p.product.id AS product, p.product.name AS name, COUNT(p.id) AS frequency "
      + "FROM Price AS p GROUP BY p.product.name, p.product.id ORDER BY p.product ASC")
  List<ProductWithFrequency> countPriceChangeFrequencyByProduct();
  @Query("SELECT p.datetime AS date, COUNT(p.id) AS frequency "
      + "FROM Price AS p GROUP BY p.datetime ORDER BY p.datetime ASC")
  List<DateWithFrequency> countPriceChangeFrequencyByDate();
}
