package com.elykp.coreservice.shared.query;

import jakarta.ws.rs.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class PageQueryBuilder {

  private static final int MAX_PAGE_SIZE = 50;

  private PageQueryBuilder() {

  }

  public static PageRequest of(PageQueryParams pageQueryParams) {
    if (pageQueryParams.getPage() < 0) {
      throw new BadRequestException("Page must not be less than 0");
    }
    if (pageQueryParams.getSize() < 1) {
      throw new BadRequestException("Page size must not be less than 1");
    }
    if (pageQueryParams.getSize() > MAX_PAGE_SIZE) {
      pageQueryParams.setSize(MAX_PAGE_SIZE);
    }

    String[] sortBy = parseSortBy(pageQueryParams.getSort());

    PageRequest pageRequest = PageRequest.of(pageQueryParams.getPage(), pageQueryParams.getSize());

    if (sortBy.length != 0) {
      return pageRequest.withSort(Sort.by(parseDirection(pageQueryParams.getDirection()), sortBy));
    }

    return pageRequest;
  }

  private static String[] parseSortBy(String sortBy) {
    if (sortBy == null || sortBy.isBlank() || sortBy.isEmpty()) {
      return new String[]{};
    }
    return sortBy.split(",");
  }

  private static Direction parseDirection(String dir) {
    return Direction.fromOptionalString(dir).orElse(Direction.DESC);
  }
}
