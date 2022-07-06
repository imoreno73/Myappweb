package es.cex.service;

import java.util.ArrayList;
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
import es.cex.dto.FunctionalGroupDto;
import es.cex.dto.FunctionalGroupPaginatedRequestDto;
import es.cex.dto.PaginatedListDto;
import es.cex.service.impl.AuthentiationServiceImpl;
import es.cex.service.impl.FunctionalGroupServiceImpl;
import es.cex.service.impl.RestClientServiceImpl;

@SpringBootTest(classes = { Application.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebApplicationContext.class })
@TestPropertySource(locations="classpath:application-test.properties")
public class FunctionalGroupServiceTest {

	@InjectMocks
	@Autowired
	private FunctionalGroupServiceImpl functionalGroupServiceImpl;

	@MockBean
	private RestClientServiceImpl restClientServiceImpl;
	
	@MockBean
	private AuthentiationServiceImpl authentiationServiceImpl;

	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getFunctionalGroupListTest() throws ServiceException {
		
		Mockito.when(restClientServiceImpl.getRestService(Mockito.any())).thenReturn(new ArrayList<FunctionalGroupDto>());
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());
		
		List<FunctionalGroupDto> response = functionalGroupServiceImpl.getFunctionalGroupList();
		
		Assert.assertNotNull(response);
	}

	@Test
	public void getPaginatedListTest() throws ServiceException {
		
		FunctionalGroupPaginatedRequestDto functionalGroupPaginatedRequestDto = new FunctionalGroupPaginatedRequestDto("", 10, 1);
		
		Mockito.when(restClientServiceImpl.getRestService(Mockito.any())).thenReturn(new PaginatedListDto<>());
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());
		
		PaginatedListDto<FunctionalGroupDto> response = functionalGroupServiceImpl.getPaginatedList(functionalGroupPaginatedRequestDto);
		
		Assert.assertNotNull(response);
	}
	
	@Test
	public void getFunctionalGroupBySlugTest() throws ServiceException {
		
		Mockito.when(restClientServiceImpl.getRestService(Mockito.any())).thenReturn(new FunctionalGroupDto());
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());
		
		FunctionalGroupDto functionalGroupDto = functionalGroupServiceImpl.getFunctionalGroupBySlug("");
		
		Assert.assertNotNull(functionalGroupDto);
	}
	
	@Test
	public void saveTest() throws ServiceException {
		
		Mockito.when(restClientServiceImpl.postRestService(Mockito.any())).thenReturn(new FunctionalGroupDto());
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());
		
		FunctionalGroupDto functionalGroupDto = functionalGroupServiceImpl.save(new FunctionalGroupDto());
		
		Assert.assertNotNull(functionalGroupDto);
	}
	
	@Test
	public void updateTest() throws ServiceException {
		
		Mockito.when(restClientServiceImpl.putRestService(Mockito.any())).thenReturn(new FunctionalGroupDto());
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());
		
		FunctionalGroupDto functionalGroupDtoResponse = new FunctionalGroupDto();
		functionalGroupDtoResponse.setId(1L);
		
		FunctionalGroupDto functionalGroupDto = functionalGroupServiceImpl.update(functionalGroupDtoResponse);
			
		Assert.assertNotNull(functionalGroupDto);
	}
	
	@Test(expected = ServiceException.class)
	public void deleteTest() throws ServiceException {
		
		Mockito.when(restClientServiceImpl.deleteRestService(Mockito.any())).thenThrow(ServiceException.class);
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());
		
		functionalGroupServiceImpl.delete(1L);
		
	}

}
