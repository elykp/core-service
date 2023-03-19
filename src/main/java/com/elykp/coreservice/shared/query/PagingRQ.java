package com.elykp.coreservice.shared.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingRQ {

  private String sortBy;
  private String direction;
  private int page = 0;
  private int limit = 0;
}
