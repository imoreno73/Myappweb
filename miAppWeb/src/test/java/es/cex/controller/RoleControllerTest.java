package es.cex.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;

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
import es.cex.controller.form.RoleForm;
import es.cex.dto.FunctionalGroupDto;
import es.cex.dto.PaginatedListDto;
import es.cex.dto.PaginationDto;
import es.cex.dto.RoleDto;
import es.cex.service.IFunctionalGroupService;
import es.cex.service.impl.RoleServiceImpl;

@SpringBootTest(classes = { Application.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebApplicationContext.class })
@TestPropertySource(locations="classpath:application-test.properties")
public class RoleControllerTest {
	
	@InjectMocks
	@Autowired
	private RoleController roleController;
	
	@Mock
	private RoleServiceImpl roleService;
	
	@Mock
	private IFunctionalGroupService functionalGroupService;
	
	private MockMvc mockMvc;

	@Mock
	private View mockView;

	@Mock
    HttpServletRequest request;

	@Before
	public void setupMock() throws JsonParseException, JsonMappingException, IOException {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(roleController)
				.setSingleView(mockView).build();
	}

	@Test
	public void searchTest() throws Exception {
		
		Mockito.when(this.functionalGroupService.getFunctionalGroupList()).thenReturn(new ArrayList<FunctionalGroupDto>());
		
		mockMvc
			.perform(get(MappingConstant.ROLE_ROOT))
			.andExpect(status().isOk());

	}
	
	@Test
	public void searchUsersTest() throws Exception {
		
		PaginatedListDto<RoleDto> list = new PaginatedListDto<>(new ArrayList<>(), new PaginationDto(10, 1, 0L), 0);
		Mockito.when(this.roleService.getPaginatedRoleList(Mockito.any())).thenReturn(list);
		Mockito.when(this.functionalGroupService.getFunctionalGroupList()).thenReturn(new ArrayList<FunctionalGroupDto>());
		
		mockMvc
			.perform(get(MappingConstant.ROLE_ROOT+MappingConstant.ROLE_SEARCH))
			.andExpect(status().isOk());

	}
	
	@Test
	public void detailTest() throws Exception {
		
		Mockito.when(this.roleService.getRoleBySlug(Mockito.any())).thenReturn(new RoleDto());
		
		mockMvc
			.perform(get(MappingConstant.ROLE_ROOT+MappingConstant.ROLE_DETAIL.replace("{slug}", "test")))
			.andExpect(status().isOk());

	}
	
	@Test
	public void newTest() throws Exception {

		Mockito.when(this.functionalGroupService.getFunctionalGroupList()).thenReturn(new ArrayList<FunctionalGroupDto>());
		
		mockMvc
			.perform(get(MappingConstant.ROLE_ROOT+MappingConstant.ROLE_NEW))
			.andExpect(status().isOk());

	}
	
	@Test
	public void createWithErorsTest() throws Exception {

		Mockito.when(this.functionalGroupService.getFunctionalGroupList()).thenReturn(new ArrayList<FunctionalGroupDto>());
		
		mockMvc
			.perform(post(MappingConstant.ROLE_ROOT+MappingConstant.ROLE_CREATE))
			.andExpect(status().isOk());

	}
	
	@Test
	public void createWithoutErorsTest() throws Exception {
		
		RoleForm roleForm = new RoleForm();
		roleForm.setName("test");
		roleForm.setActive(true);
		roleForm.setControlTotalMenu(true);

		Mockito.when(this.functionalGroupService.getFunctionalGroupList()).thenReturn(new ArrayList<FunctionalGroupDto>());
		Mockito.when(this.roleService.save(Mockito.any())).thenReturn(new RoleDto());
		
		mockMvc
			.perform(
					post(MappingConstant.ROLE_ROOT+MappingConstant.ROLE_CREATE)
					.contentType(MediaType.APPLICATION_FORM_URLENCODED).
					flashAttr(Constant.ROLE_FORM_ATTRIBUTE_KEY, roleForm)
					)
			.andExpect(status().isOk());

	}
	
	@Test
	public void editTest() throws Exception {

		Mockito.when(this.functionalGroupService.getFunctionalGroupList()).thenReturn(new ArrayList<FunctionalGroupDto>());
		Mockito.when(this.roleService.getRoleBySlug(Mockito.any())).thenReturn(new RoleDto());
		
		mockMvc
			.perform(get(MappingConstant.ROLE_ROOT+MappingConstant.ROLE_EDIT.replace("{slug}", "1")))
			.andExpect(status().isOk());

	}
	
	@Test
	public void updateWithErorsTest() throws Exception {

		Mockito.when(this.functionalGroupService.getFunctionalGroupList()).thenReturn(new ArrayList<FunctionalGroupDto>());
		
		mockMvc
			.perform(post(MappingConstant.ROLE_ROOT+MappingConstant.ROLE_UPDATE.replace("{slug}", "1")))
			.andExpect(status().isOk());

	}
	
	@Test
	public void updateWithoutErorsTest() throws Exception {
		
		RoleForm roleForm = new RoleForm();
		roleForm.setName("test");
		roleForm.setActive(true);
		roleForm.setControlTotalMenu(true);

		Mockito.when(this.functionalGroupService.getFunctionalGroupList()).thenReturn(new ArrayList<FunctionalGroupDto>());
		Mockito.when(this.roleService.update(Mockito.any())).thenReturn(new RoleDto());
		
		mockMvc
			.perform(
					post(MappingConstant.ROLE_ROOT+MappingConstant.ROLE_UPDATE.replace("{slug}", "test"))
					.contentType(MediaType.APPLICATION_FORM_URLENCODED).
					flashAttr(Constant.ROLE_FORM_ATTRIBUTE_KEY, roleForm)
					)
			.andExpect(status().isOk());

	}
	
	@Test
	public void deleteUserOkTest() throws Exception {
		
		Mockito.doNothing().when(this.roleService).delete(Mockito.any());
		
		mockMvc
			.perform(delete(MappingConstant.ROLE_ROOT+MappingConstant.ROLE_DELETE.replace("{id}", "1")))
			.andExpect(status().isOk());

	}
	
	@Test
	public void deleteUserKOTest() throws Exception {
		
		Mockito.doThrow(ServiceException.class).when(this.roleService).delete(Mockito.any());
		
		mockMvc
			.perform(delete(MappingConstant.ROLE_ROOT+MappingConstant.ROLE_DELETE.replace("{id}", "1")))
			.andExpect(status().isNotFound());

	}
	
}
