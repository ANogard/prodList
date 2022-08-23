package com.example.prodList.repository;

import com.example.prodList.model.entity.Price;
import com.example.prodList.model.mapper.FrequencyByDateMapper;
import com.example.prodList.model.mapper.FrequencyByProductMapper;
import com.example.prodList.model.mapper.PriceMapper;
import com.example.prodList.model.response.FrequencyByDateDTO;
import com.example.prodList.model.response.FrequencyByProductDTO;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PriceRepository {

  private final JdbcTemplate jdbc;

  public void savePrice(Price price) {
    String sql = "INSERT INTO price (id, price, datetime, product_id) values (?, ?, ?, ?)";
    jdbc.update(sql,
        price.getId(),
        price.getPrice(),
        price.getDatetime(),
        price.getProductId());
  }

  public Price getPriceByProductAndDate(Date datetime, long productId) {
    String sql = "SELECT * FROM price "
        + "WHERE datetime <= ? AND product_id = ? "
        + "ORDER BY datetime DESC "
        + "LIMIT 1";
    return jdbc.queryForObject(sql, new PriceMapper(), datetime, productId);
  }

  public List<FrequencyByDateDTO> getFrequencyByDate() {
    String sql = "SELECT p.datetime AS date, "
        + "COUNT(p.id) AS frequency "
        + "FROM Price AS p "
        + "GROUP BY p.datetime "
        + "ORDER BY p.datetime ASC";
    return jdbc.query(sql, new FrequencyByDateMapper());
  }

  public List<FrequencyByProductDTO> getFrequencyByProduct() {
    String sql = "SELECT p.product_id, product.name as product_name, COUNT(p.id) AS frequency "
        + "FROM price AS p "
        + "JOIN product ON p.product_id=product.id "
        + "GROUP BY product_name, p.product_id "
        + "ORDER BY p.product_id ASC";
    return jdbc.query(sql, new FrequencyByProductMapper());
  }
}
