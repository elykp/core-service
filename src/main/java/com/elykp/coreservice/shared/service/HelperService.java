package com.elykp.coreservice.shared.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

public class HelperService {

  private HelperService() {

  }

  public static String generateNanoId() {
    return NanoIdUtils.randomNanoId(
        NanoIdUtils.DEFAULT_NUMBER_GENERATOR,
        NanoIdUtils.DEFAULT_ALPHABET, 8);
  }

  public static String generateNanoId(Integer size) {
    return NanoIdUtils.randomNanoId(
        NanoIdUtils.DEFAULT_NUMBER_GENERATOR,
        NanoIdUtils.DEFAULT_ALPHABET, size);
  }
}
