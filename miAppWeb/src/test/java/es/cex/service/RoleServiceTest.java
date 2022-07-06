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
import es.cex.dto.PaginatedListDto;
import es.cex.dto.RoleDto;
import es.cex.dto.RolePaginatedRequestDto;
import es.cex.service.impl.AuthentiationServiceImpl;
import es.cex.service.impl.RestClientServiceImpl;
import es.cex.service.impl.RoleServiceImpl;

@SpringBootTest(classes = { Application.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebApplicationContext.class })
@TestPropertySource(locations="classpath:application-test.properties")
public class RoleServiceTest {

	@InjectMocks
	@Autowired
	private RoleServiceImpl roleServiceImpl;

	@MockBean
	private RestClientServiceImpl restClientServiceImpl;
	
	@MockBean
	private AuthentiationServiceImpl authentiationServiceImpl;

	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getRoleListTest() throws ServiceException {
		
		Mockito.when(restClientServiceImpl.getRestService(Mockito.any())).thenReturn(new ArrayList<RoleDto>());
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());
		
		List<RoleDto> response = roleServiceImpl.getRoleList();
		
		Assert.assertNotNull(response);
	}
	
	@Test
	public void getPaginatedRoleListTest() throws ServiceException {
		
		RolePaginatedRequestDto rolePaginatedRequestDto = new RolePaginatedRequestDto("", true, true, 1L, 10, 1);
		
		Mockito.when(restClientServiceImpl.getRestService(Mockito.any())).thenReturn(new PaginatedListDto<>());
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());
		
		PaginatedListDto<RoleDto> response = roleServiceImpl.getPaginatedRoleList(rolePaginatedRequestDto);
		
		Assert.assertNotNull(response);
	}
	
	@Test
	public void getRoleBySlugTest() throws ServiceException {
		
		Mockito.when(restClientServiceImpl.getRestService(Mockito.any())).thenReturn(new RoleDto());
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());
		
		RoleDto roleDto = roleServiceImpl.getRoleBySlug("");
		
		Assert.assertNotNull(roleDto);
	}
	
	@Test
	public void saveTest() throws ServiceException {
		
		Mockito.when(restClientServiceImpl.postRestService(Mockito.any())).thenReturn(new RoleDto());
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());
		
		RoleDto roleDto = roleServiceImpl.save(new RoleDto());
		
		Assert.assertNotNull(roleDto);
	}
	
	@Test
	public void updateTest() throws ServiceException {
		
		RoleDto roleDto = new RoleDto();
		roleDto.setId(1L);
		
		Mockito.when(restClientServiceImpl.putRestService(Mockito.any())).thenReturn(roleDto);
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());

		
		RoleDto updatedRoleDto = roleServiceImpl.update(roleDto);
		
		Assert.assertNotNull(updatedRoleDto);
		Assert.assertNotNull(updatedRoleDto.getId());
	}
	
	@Test(expected = ServiceException.class)
	public void deleteTest() throws ServiceException {
		
		Mockito.when(restClientServiceImpl.deleteRestService(Mockito.any())).thenThrow(ServiceException.class);
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());
		
		roleServiceImpl.delete(1L);
		
	}
	

}
