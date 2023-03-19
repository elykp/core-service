package com.elykp.coreservice.shared.query;

import jakarta.ws.rs.QueryParam;
import lombok.Data;

@Data
public class PageQueryParams {

  @QueryParam("q")
  private String q;

  @QueryParam("sort")
  private String sort;

  @QueryParam("direction")
  private String direction;

  @QueryParam("page")
  private int page = 0;

  @QueryParam("size")
  private int size = 10;
}
