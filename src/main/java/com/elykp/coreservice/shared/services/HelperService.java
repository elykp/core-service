package com.elykp.coreservice.shared.services;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.springframework.stereotype.Service;

@Service
public class HelperService {

  public String generateNanoId() {
    return NanoIdUtils.randomNanoId(
        NanoIdUtils.DEFAULT_NUMBER_GENERATOR,
        NanoIdUtils.DEFAULT_ALPHABET, 8);
  }

  public String generateNanoId(Integer size) {
    return NanoIdUtils.randomNanoId(
        NanoIdUtils.DEFAULT_NUMBER_GENERATOR,
        NanoIdUtils.DEFAULT_ALPHABET, size);
  }
}
