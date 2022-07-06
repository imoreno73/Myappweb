package es.cex.common.types;

import java.util.HashMap;
import java.util.Map;

/**
 * Zona horaria
 *
 */
public enum TimeZonesTypes {

  /**
   * EUROPE_MADRID
   */
  EUROPE_MADRID("Europe/Madrid"),

  /**
   * ATLANTIC_CANARY
   */
  ATLANTIC_CANARY("Atlantic/Canary"),

  /**
   * EXCEEDED THE MAXIMUN NUMBER OF RETRIES
   */
  EUROPE_LISBON("Europe/Lisbon"),

  /**
   * GMT
   */
  UTC("UTC");

  private static Map<String, TimeZonesTypes> map = new HashMap<>();

  private final String timeZone;

  static {
    for (TimeZonesTypes timeZones : TimeZonesTypes.values()) {
      map.put(timeZones.timeZone, timeZones);
    }
  }

  private TimeZonesTypes(String timeZone) {

    this.timeZone = timeZone;
  }

  /**
   * Status code of an email
   *
   * @return Status code
   */
  public String getTimeZone() {

    return this.timeZone;
  }

  /**
   * String with the status code value
   *
   * @return String with the status code value
   */
  public String getName() {

    return name();
  }

  /**
   * Recupera un TimeZonesTypes a partir del código
   *
   * @param timeZone zona horaria
   * @return TimeZonesTypes a partir del código
   */
  public static TimeZonesTypes get(String timeZone) {

    return map.get(timeZone);
  }

}
