package com.example.prodList.model.mapper;

import com.example.prodList.model.entity.Price;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PriceMapper implements RowMapper<Price> {

  @Override
  public Price mapRow(ResultSet rs, int rowNum) throws SQLException {
    Price mapper = new Price();
    mapper.setId(rs.getInt("id"));
    mapper.setPrice(rs.getInt("price"));
    mapper.setProductId(rs.getInt("product_id"));
    mapper.setDatetime(rs.getDate("datetime"));
    return mapper;
  }
}
