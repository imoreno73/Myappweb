package es.cex.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.View;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import es.cex.Application;
import es.cex.arq.exceptions.ServiceException;
import es.cex.common.constant.Constant;
import es.cex.common.constant.MappingConstant;
import es.cex.controller.form.UserForm;
import es.cex.dto.FunctionalGroupDto;
import es.cex.dto.PaginatedListDto;
import es.cex.dto.PaginationDto;
import es.cex.dto.RoleDto;
import es.cex.dto.UserDto;
import es.cex.dto.UserPaginatedDto;
import es.cex.service.impl.FunctionalGroupServiceImpl;
import es.cex.service.impl.RoleServiceImpl;
import es.cex.service.impl.UserServiceImpl;

@SpringBootTest(classes = { Application.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebApplicationContext.class })
@TestPropertySource(locations="classpath:application-test.properties")
public class UserControllerTest {
	
	@InjectMocks
	@Autowired
	private UserController userController;
	
	@Mock
	private UserServiceImpl userService;
	
	@Mock
	private RoleServiceImpl roleService;
	
	@Mock
	private FunctionalGroupServiceImpl functionalGroupService;
	
	private MockMvc mockMvc;

	@Mock
	private View mockView;

	@Mock
    HttpServletRequest request;

	@Before
	public void setupMock() throws JsonParseException, JsonMappingException, IOException {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
				.setSingleView(mockView).build();
	}

	@Test
	public void searchTest() throws Exception {
		
		Mockito.when(this.functionalGroupService.getFunctionalGroupList()).thenReturn(new ArrayList<FunctionalGroupDto>());
		
		mockMvc
			.perform(get(MappingConstant.USER_ROOT))
			.andExpect(status().isOk());

	}
	
	@Test
	public void searchUsersTest() throws Exception {
		
		PaginatedListDto<UserPaginatedDto> list = new PaginatedListDto<>(new ArrayList<>(), new PaginationDto(10, 1, 0L), 0);
		Mockito.when(this.userService.getPaginatedUserList(Mockito.any())).thenReturn(list);
		
		mockMvc
			.perform(get(MappingConstant.USER_ROOT+MappingConstant.USER_SEARCH))
			.andExpect(status().isOk());

	}
	
	@Test
	public void detailTest() throws Exception {
		
		Mockito.when(this.userService.getUserByLogin(Mockito.any())).thenReturn(new UserDto());
		
		mockMvc
			.perform(get(MappingConstant.USER_ROOT+MappingConstant.USER_DETAIL.replace("{login}", "test")))
			.andExpect(status().isOk());

	}
	
	@Test
	public void newTest() throws Exception {
		
		Mockito.when(this.roleService.getRoleList()).thenReturn(new ArrayList<RoleDto>());
		Mockito.when(this.functionalGroupService.getFunctionalGroupList()).thenReturn(new ArrayList<FunctionalGroupDto>());
		
		mockMvc
			.perform(get(MappingConstant.USER_ROOT+MappingConstant.USER_NEW))
			.andExpect(status().isOk());

	}
	
	@Test
	public void createWithErorsTest() throws Exception {
		
		Mockito.when(this.roleService.getRoleList()).thenReturn(new ArrayList<RoleDto>());
		Mockito.when(this.functionalGroupService.getFunctionalGroupList()).thenReturn(new ArrayList<FunctionalGroupDto>());
		
		mockMvc
			.perform(post(MappingConstant.USER_ROOT+MappingConstant.USER_CREATE))
			.andExpect(status().isOk());

	}
	
	@Test
	public void createWithoutErorsTest() throws Exception {
		
		UserForm userForm = new UserForm();
		userForm.setLogin("test");
		userForm.setEmail("test@correosexpress.com");
		userForm.setActive(true);
		
		List<Long> roles = new ArrayList<>();
		roles.add(1L);
		userForm.setRoles(roles);
		
		Mockito.when(this.userService.save(Mockito.any())).thenReturn(new UserDto());
		Mockito.when(this.functionalGroupService.getFunctionalGroupList()).thenReturn(new ArrayList<FunctionalGroupDto>());
		
		mockMvc
			.perform(
					post(MappingConstant.USER_ROOT+MappingConstant.USER_CREATE)
					.contentType(MediaType.APPLICATION_FORM_URLENCODED).
					flashAttr(Constant.USER_FORM_ATTRIBUTE_KEY, userForm)
					)
			.andExpect(status().isOk());

	}
	
	@Test
	public void editTest() throws Exception {
		
		Mockito.when(this.roleService.getRoleList()).thenReturn(new ArrayList<RoleDto>());
		Mockito.when(this.userService.getUserByLogin(Mockito.any())).thenReturn(new UserDto());
		
		mockMvc
			.perform(get(MappingConstant.USER_ROOT+MappingConstant.USER_EDIT.replace("{login}", "test")))
			.andExpect(status().isOk());

	}
	
	@Test
	public void updateWithErorsTest() throws Exception {
		
		Mockito.when(this.roleService.getRoleList()).thenReturn(new ArrayList<RoleDto>());
		Mockito.when(this.functionalGroupService.getFunctionalGroupList()).thenReturn(new ArrayList<FunctionalGroupDto>());
		
		mockMvc
			.perform(post(MappingConstant.USER_ROOT+MappingConstant.USER_UPDATE.replace("{login}", "test")))
			.andExpect(status().isOk());

	}
	
	@Test
	public void updateWithoutErorsTest() throws Exception {
		
		UserForm userForm = new UserForm();
		userForm.setId(1L);
		userForm.setLogin("test");
		userForm.setEmail("test@correosexpress.com");
		userForm.setActive(true);
		
		List<Long> roles = new ArrayList<>();
		roles.add(1L);
		userForm.setRoles(roles);
		
		Mockito.when(this.userService.update(Mockito.any())).thenReturn(new UserDto());
		Mockito.when(this.functionalGroupService.getFunctionalGroupList()).thenReturn(new ArrayList<FunctionalGroupDto>());
		
		mockMvc
			.perform(
					post(MappingConstant.USER_ROOT+MappingConstant.USER_UPDATE.replace("{login}", "test"))
					.contentType(MediaType.APPLICATION_FORM_URLENCODED).
					flashAttr(Constant.USER_FORM_ATTRIBUTE_KEY, userForm)
					)
			.andExpect(status().isOk());

	}
	
	@Test
	public void deleteUserOkTest() throws Exception {
		
		Mockito.doNothing().when(this.userService).delete(Mockito.any());
		
		mockMvc
			.perform(delete(MappingConstant.USER_ROOT+MappingConstant.USER_DELETE.replace("{id}", "1")))
			.andExpect(status().isOk());

	}
	
	@Test
	public void deleteUserKOTest() throws Exception {
		
		Mockito.doThrow(ServiceException.class).when(this.userService).delete(Mockito.any());
		
		mockMvc
			.perform(delete(MappingConstant.USER_ROOT+MappingConstant.USER_DELETE.replace("{id}", "1")))
			.andExpect(status().isNotFound());

	}
	
}
