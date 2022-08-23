package com.example.prodList.model.mapper;

import com.example.prodList.model.response.FrequencyByDateDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class FrequencyByDateMapper implements RowMapper<FrequencyByDateDTO> {

  @Override
  public FrequencyByDateDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    FrequencyByDateDTO mapper = new FrequencyByDateDTO();
    mapper.setFrequency(rs.getInt("frequency"));
    mapper.setDatetime(rs.getDate("date"));
    return mapper;
  }
}
