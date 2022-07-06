package es.cex.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;

import es.cex.Application;
import es.cex.arq.exceptions.ServiceException;
import es.cex.dto.PageDto;
import es.cex.service.impl.AuthentiationServiceImpl;
import es.cex.service.impl.PageServiceImpl;
import es.cex.service.impl.RestClientServiceImpl;

@SpringBootTest(classes = { Application.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebApplicationContext.class })
@TestPropertySource(locations="classpath:application-test.properties")
public class PageServiceTest {

	@InjectMocks
	@Autowired
	private PageServiceImpl pageServiceImpl;

	@MockBean
	private RestClientServiceImpl restClientServiceImpl;
	
	@MockBean
	private AuthentiationServiceImpl authentiationServiceImpl;

	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getPageListTest() throws ServiceException {
		
		Mockito.when(restClientServiceImpl.getRestService(Mockito.any())).thenReturn(new PageDto[0]);
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());
		
		List<PageDto> pages = pageServiceImpl.getPageList();
		
		Assert.assertNotNull(pages);
	}
	
	@Test
	public void getPageByIdTest() throws ServiceException {
		
		Mockito.when(restClientServiceImpl.getRestService(Mockito.any())).thenReturn(new PageDto());
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());
		
		PageDto pageDto = pageServiceImpl.getPageById(1L);
		
		Assert.assertNotNull(pageDto);
	}
	
	@Test
	public void saveTest() throws ServiceException {
		
		Mockito.when(restClientServiceImpl.postRestService(Mockito.any())).thenReturn(new PageDto());
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());
		
		PageDto pageDto = pageServiceImpl.save(new PageDto());
		
		Assert.assertNotNull(pageDto);
	}
	
	@Test
	public void updateTest() throws ServiceException {
		
		PageDto pageDto = new PageDto();
		pageDto.setId(1L);
		
		Mockito.when(restClientServiceImpl.putRestService(Mockito.any())).thenReturn(pageDto);
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());

		
		PageDto updatedPageDto = pageServiceImpl.update(pageDto);
		
		Assert.assertNotNull(updatedPageDto);
	}
	
	@Test(expected = ServiceException.class)
	public void deleteTest() throws ServiceException {
		
		Mockito.when(restClientServiceImpl.deleteRestService(Mockito.any())).thenThrow(ServiceException.class);
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());
		
		pageServiceImpl.delete(1L);
		
	}

}
