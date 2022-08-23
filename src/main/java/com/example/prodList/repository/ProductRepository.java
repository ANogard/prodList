package com.example.prodList.repository;

import com.example.prodList.model.entity.Product;
import com.example.prodList.model.mapper.ProductMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

  private final JdbcTemplate jdbc;

  public List<Product> getAll() {
    String sql = "SELECT * FROM product";
    return jdbc.query(sql, new ProductMapper());
  }

  public Product getProductById(long id) {
    String sql = "SELECT * FROM product WHERE id = ?";
    return jdbc.queryForObject(sql, new ProductMapper(), id);
  }

  public void saveProduct(Product product) {
    String sql = "insert into product (id, name) " +
        "values (?, ?)";
    jdbc.update(sql,
        product.getId(),
        product.getName());
  }
}
