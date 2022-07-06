package es.cex.common.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import es.cex.common.types.TimeZonesTypes;

/**
 * Util for date logics
 */
@Component("dateUtils")
public class DateUtils {

	private static final String DEFAULT_FORMAT = "dd-MM-yyyy HH:mm:ssZ";

	public String format(ZonedDateTime zonedDateTime) {

		return this.format(zonedDateTime, DEFAULT_FORMAT, TimeZonesTypes.EUROPE_MADRID.getTimeZone());

	}

	public String format(ZonedDateTime zonedDateTime, String zoneId) {

		return this.format(zonedDateTime, DEFAULT_FORMAT, zoneId);

	}

	public String format(ZonedDateTime zonedDateTime, String outFormatString, String zoneId) {

		String formattedString = null;
		ZonedDateTime newZonedDateTime = null;

		if (zonedDateTime != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(outFormatString);
			newZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of(zoneId));
			formattedString = newZonedDateTime.format(formatter);

		}

		return formattedString;

	}

}
