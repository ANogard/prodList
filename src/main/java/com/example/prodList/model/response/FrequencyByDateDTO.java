package com.example.prodList.model.response;

import java.util.Date;
import lombok.Data;

@Data
public class FrequencyByDateDTO {

  private Date datetime;
  private Integer frequency;

  public Date getDatetime() {
    return datetime;
  }

  public void setDatetime(Date datetime) {
    this.datetime = datetime;
  }

  public Integer getFrequency() {
    return frequency;
  }

  public void setFrequency(Integer frequency) {
    this.frequency = frequency;
  }
}
