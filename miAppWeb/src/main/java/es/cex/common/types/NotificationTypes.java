package es.cex.common.types;

import java.util.HashMap;
import java.util.Map;

/**
 * notification types
 *
 */
public enum NotificationTypes {

  /**
   * INFO
   */
  INFO("info"),

  /**
   * NOTICE
   */
  NOTICE("notice"),

  /**
   * SUCCESS
   */
  SUCCESS("success"),

  /**
   * ERROR
   */
  ERROR("error");

  private static Map<String, NotificationTypes> map = new HashMap<>();

  private final String notificationType;

  static {
    for (NotificationTypes notificationTypes : NotificationTypes.values()) {
      map.put(notificationTypes.notificationType, notificationTypes);
    }
  }

  private NotificationTypes(String notificationType) {

    this.notificationType = notificationType;
  }

  /**
   * notification type
   *
   * @return notification type
   */
  public String getNotificationType() {

    return this.notificationType;
  }

  /**
   * String with the notification type
   *
   * @return String with the notification type
   */
  public String getName() {

    return name();
  }

  /**
   * Get notification types
   *
   * @param notificationType notification type
   * @return @{link notificationType}
   */
  public static NotificationTypes get(String notificationType) {

    return map.get(notificationType);
  }

}
