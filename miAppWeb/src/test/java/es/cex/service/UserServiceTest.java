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
import es.cex.dto.UserDto;
import es.cex.dto.UserPaginatedDto;
import es.cex.dto.UserPaginatedRequestDto;
import es.cex.service.impl.AuthentiationServiceImpl;
import es.cex.service.impl.RestClientServiceImpl;
import es.cex.service.impl.UserServiceImpl;

@SpringBootTest(classes = { Application.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebApplicationContext.class })
@TestPropertySource(locations="classpath:application-test.properties")
public class UserServiceTest {

	@InjectMocks
	@Autowired
	private UserServiceImpl userServiceImpl;

	@MockBean
	private RestClientServiceImpl restClientServiceImpl;
	
	@MockBean
	private AuthentiationServiceImpl authentiationServiceImpl;

	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getPaginatedUserListTest() throws ServiceException {
		
		UserPaginatedRequestDto userPaginatedRequestDto = new UserPaginatedRequestDto("", 1L, 1L, true, 10, 1);
		
		Mockito.when(restClientServiceImpl.getRestService(Mockito.any())).thenReturn(new PaginatedListDto<>());
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());
		
		PaginatedListDto<UserPaginatedDto> response = userServiceImpl.getPaginatedUserList(userPaginatedRequestDto);
		
		Assert.assertNotNull(response);
	}
	
	@Test
	public void getUserListTest() throws ServiceException {
		
		Mockito.when(restClientServiceImpl.getRestService(Mockito.any())).thenReturn(new ArrayList<RoleDto>());
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());
		
		List<UserDto> response = userServiceImpl.getUserList();
		
		Assert.assertNotNull(response);
	}
	
	@Test
	public void getUserByLoginTest() throws ServiceException {
		
		Mockito.when(restClientServiceImpl.getRestService(Mockito.any())).thenReturn(new UserDto());
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());
		
		UserDto userDto = userServiceImpl.getUserByLogin("");
		
		Assert.assertNotNull(userDto);
	}
	
	@Test
	public void saveTest() throws ServiceException {
		
		Mockito.when(restClientServiceImpl.postRestService(Mockito.any())).thenReturn(new UserDto());
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());
		
		UserDto userDto = userServiceImpl.save(new UserDto());
		
		Assert.assertNotNull(userDto);
	}
	
	@Test
	public void updateTest() throws ServiceException {
		
		UserDto userDto = new UserDto();
		userDto.setId(1L);
		
		Mockito.when(restClientServiceImpl.putRestService(Mockito.any())).thenReturn(userDto);
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());
		
		UserDto updatedUuserDto = userServiceImpl.update(userDto);
		
		Assert.assertNotNull(updatedUuserDto);
		Assert.assertNotNull(updatedUuserDto.getId());
	}
	
	@Test(expected = ServiceException.class)
	public void deleteTest() throws ServiceException {
		
		Mockito.when(restClientServiceImpl.deleteRestService(Mockito.any())).thenThrow(ServiceException.class);
		Mockito.doNothing().when(authentiationServiceImpl).addTokenToHeader(Mockito.any());
		
		userServiceImpl.delete(1L);
		
	}

}
