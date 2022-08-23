package com.example.prodList.model.mapper;

import com.example.prodList.model.response.FrequencyByProductDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class FrequencyByProductMapper implements RowMapper<FrequencyByProductDTO> {

  @Override
  public FrequencyByProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    FrequencyByProductDTO mapper = new FrequencyByProductDTO();
    mapper.setFrequency(rs.getInt("frequency"));
    mapper.setProductName(rs.getString("product_name"));
    return mapper;
  }
}
