package com.javaweb.model.response.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChartItemDTO {
  private String name;
  private Long value;
}