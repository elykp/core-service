package com.elykp.coreservice;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class CoreServiceApplicationTests {

  @Test
  void contextLoads(ApplicationContext context) {
    assert context != null;
    assertThat(context, notNullValue());
  }

}
