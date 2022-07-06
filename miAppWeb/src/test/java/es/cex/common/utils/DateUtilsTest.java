package es.cex.common.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;

import es.cex.Application;
import es.cex.arq.exceptions.ServiceException;
import es.cex.common.types.TimeZonesTypes;

@SpringBootTest(classes = { Application.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebApplicationContext.class })
@TestPropertySource(locations="classpath:application-test.properties")
public class DateUtilsTest {
	
	@Autowired
	private DateUtils dateUtils;

	@Test
	public void formatWithZonedDateTimeTest() throws ServiceException {
		
		ZonedDateTime now = ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneId.of(TimeZonesTypes.UTC.getTimeZone()));
		
		// Por defecto, format sin zona horaria transforma a la zona horaria de Madrid
		String formatted = this.dateUtils.format(now);
		String formattedMadrid = this.dateUtils.format(now, TimeZonesTypes.EUROPE_MADRID.getTimeZone());
		
		Assert.assertEquals(formatted, formattedMadrid);
	}
	
}
