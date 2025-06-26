package com.unb.fair_management_system.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {
  private static final Dotenv dotenv = Dotenv.configure()
                                           .ignoreIfMissing()
                                           .load();

  public static String get(final String key) {
    final String sysValue = System.getenv(key);
    return sysValue != null ? sysValue : dotenv.get(key);
  }

  private EnvConfig() {}
}
