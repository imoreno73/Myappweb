package es.cex.common.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;

import es.cex.Application;
import es.cex.arq.exceptions.ServiceException;
import es.cex.common.constant.Constant;
import es.cex.common.entity.DataTableResults;
import es.cex.dto.PaginatedListDto;
import es.cex.dto.PaginationDto;

@SpringBootTest(classes = { Application.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebApplicationContext.class })
@TestPropertySource(locations="classpath:application-test.properties")
public class CommonUtilsTest {

	@Autowired
	private CommonUtils commonUtils;
	
	@Mock
    HttpServletRequest request;
	
	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void prepareResponseTest() throws ServiceException {
		
		PaginatedListDto<String> paginatedListDto = new PaginatedListDto<>(new ArrayList<>(), new PaginationDto(), 0);
		String paginationDrawKey = "display";
		
		DataTableResults<String> result = this.commonUtils.prepareResponse(paginatedListDto, paginationDrawKey);
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getDraw());
		Assert.assertNotNull(result.getRecordsTotal());
		Assert.assertNotNull(result.getListOfDataObjects());
		Assert.assertNotNull(result.getRecordsFiltered());
		
	}
	
	@Test(expected = ServiceException.class)
	public void getPaginationDataKOTest() throws ServiceException {
		
		this.commonUtils.getPaginationData(null);
		
	}
	
	@Test
	public void getPaginationDataOKTest() throws ServiceException {
		
		PaginationDto paginationDto = this.commonUtils.getPaginationData(request);
		Assert.assertNotNull(paginationDto);
		Assert.assertNotNull(paginationDto.getPage());
		Assert.assertEquals(1, paginationDto.getPage().intValue());
		Assert.assertNotNull(paginationDto.getSize());
		Assert.assertEquals(Integer.parseInt(Constant.DEFAULT_PAGE_SIZE), paginationDto.getSize().intValue());
	}
	
	@Test
	public void isAvailableStringTest() {
		
		Assert.assertTrue(commonUtils.isAvailable("test"));
		Assert.assertFalse(commonUtils.isAvailable(""));
		
		String nulo = null;
		Assert.assertFalse(commonUtils.isAvailable(nulo));
		
	}
	
	@Test
	public void isAvailableCollection() {
		
		List<String> items = new ArrayList<>();
		items.add("uno");
		Assert.assertTrue(commonUtils.isAvailable(items));
		
		Assert.assertFalse(commonUtils.isAvailable(new ArrayList<String>()));
		
		List<String> nula = null;
		Assert.assertFalse(commonUtils.isAvailable(nula));
		
	}
	
	

}
