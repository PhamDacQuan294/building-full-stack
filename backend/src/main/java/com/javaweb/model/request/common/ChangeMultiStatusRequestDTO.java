package com.javaweb.model.request.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChangeMultiStatusRequestDTO {
  private List<Long> ids;
  private String status;
}
