package com.example.prodList.model.mapper;

import com.example.prodList.model.entity.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ProductMapper implements RowMapper<Product> {

  @Override
  public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
    Product mapper = new Product();
    mapper.setId(rs.getInt("id"));
    mapper.setName(rs.getString("name"));
    return mapper;
  }
}
