package me.juliarn.smartmirror;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
class BackendTest {

  @Inject
  EmbeddedApplication<?> application;

  @Test
  void testItWorks() {
    Assertions.assertTrue(this.application.isRunning());
  }
}
