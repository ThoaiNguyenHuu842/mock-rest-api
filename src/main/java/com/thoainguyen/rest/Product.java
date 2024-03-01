package com.thoainguyen.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Product {
  private String name;
  private String category;
  private Integer companyId;
  private Integer id;
}
