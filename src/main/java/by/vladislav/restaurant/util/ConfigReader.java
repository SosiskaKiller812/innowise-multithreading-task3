package by.vladislav.restaurant.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ConfigReader {
  private static final String STANDART_PATH = "data/input.txt";

  public static Map<String, Integer> readConfig(String filePath) {
    Map<String, Integer> config = new HashMap<>();
    try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
      lines.forEach(line -> {
        String[] parts = line.split("=");
        if (parts.length == 2) {
          config.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
        }
      });
    } catch (IOException e) {
      throw new RuntimeException("Failed to read configuration file", e);
    }
    return config;
  }

  public static Map<String, Integer> readConfig() {
    return readConfig(STANDART_PATH);
  }
}
