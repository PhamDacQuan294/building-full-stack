package com.javaweb.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ResponseDTO<T> {
  private T data;
  private String message;
  private String detail;
  private Long totalItem;
}

