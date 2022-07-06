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
import es.cex.controller.form.FunctionalGroupForm;
import es.cex.dto.FunctionalGroupDto;
import es.cex.dto.PaginatedListDto;
import es.cex.dto.PaginationDto;
import es.cex.service.impl.FunctionalGroupServiceImpl;

@SpringBootTest(classes = { Application.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebApplicationContext.class })
@TestPropertySource(locations="classpath:application-test.properties")
public class FunctionalGroupControllerTest {
	
	@InjectMocks
	@Autowired
	private FunctionalGroupController functionalGroupController;
	
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
		this.mockMvc = MockMvcBuilders.standaloneSetup(functionalGroupController)
				.setSingleView(mockView).build();
	}

	@Test
	public void searchTest() throws Exception {
		
		mockMvc
			.perform(get(MappingConstant.FUNCTIONAL_GROUP_ROOT))
			.andExpect(status().isOk());

	}
	
	@Test
	public void searchFunctionalGroupsTest() throws Exception {
		
		PaginatedListDto<FunctionalGroupDto> list = new PaginatedListDto<>(new ArrayList<>(), new PaginationDto(10, 1, 0L), 0);
		Mockito.when(this.functionalGroupService.getPaginatedList(Mockito.any())).thenReturn(list);
		
		mockMvc
			.perform(get(MappingConstant.FUNCTIONAL_GROUP_ROOT+MappingConstant.FUNCTIONAL_GROUP_SEARCH))
			.andExpect(status().isOk());

	}
	
	@Test
	public void detailTest() throws Exception {
		
		Mockito.when(this.functionalGroupService.getFunctionalGroupBySlug(Mockito.any())).thenReturn(new FunctionalGroupDto());
		
		mockMvc
			.perform(get(MappingConstant.FUNCTIONAL_GROUP_ROOT+MappingConstant.FUNCTIONAL_GROUP_DETAIL.replace("{slug}", "test")))
			.andExpect(status().isOk());

	}
	
	@Test
	public void listTest() throws Exception {
		
		List<FunctionalGroupDto> list = new ArrayList<>();
		Mockito.when(this.functionalGroupService.getFunctionalGroupList()).thenReturn(list);
		
		mockMvc
			.perform(get(MappingConstant.FUNCTIONAL_GROUP_ROOT+MappingConstant.FUNCTIONAL_GROUP_LIST))
			.andExpect(status().isOk());

	}
	
	@Test
	public void newTest() throws Exception {
		
		mockMvc
			.perform(get(MappingConstant.FUNCTIONAL_GROUP_ROOT+MappingConstant.FUNCTIONAL_GROUP_NEW))
			.andExpect(status().isOk());

	}
	
	@Test
	public void createWithErorsTest() throws Exception {
		
		mockMvc
			.perform(post(MappingConstant.FUNCTIONAL_GROUP_ROOT+MappingConstant.FUNCTIONAL_GROUP_CREATE))
			.andExpect(status().isOk());

	}
	
	@Test
	public void createWithoutErorsTest() throws Exception {
		
		FunctionalGroupForm form = new FunctionalGroupForm();
		form.setName("test");
		
		Mockito.when(this.functionalGroupService.save(Mockito.any())).thenReturn(new FunctionalGroupDto());
		
		mockMvc
			.perform(
					post(MappingConstant.FUNCTIONAL_GROUP_ROOT+MappingConstant.FUNCTIONAL_GROUP_CREATE)
					.contentType(MediaType.APPLICATION_FORM_URLENCODED).
					flashAttr(Constant.FUNCTIONAL_GROUP_FORM_ATTRIBUTE_KEY, form)
					)
			.andExpect(status().isOk());

	}
	
	@Test
	public void editTest() throws Exception {
		
		FunctionalGroupDto fgResponse = new FunctionalGroupDto();
		fgResponse.setSlug("test");
		
		Mockito.when(this.functionalGroupService.getFunctionalGroupBySlug(Mockito.any())).thenReturn(fgResponse);
		
		mockMvc
			.perform(get(MappingConstant.FUNCTIONAL_GROUP_ROOT+MappingConstant.FUNCTIONAL_GROUP_EDIT.replace("{slug}", fgResponse.getSlug())))
			.andExpect(status().isOk());

	}
	
	@Test
	public void updateWithErorsTest() throws Exception {
		
		FunctionalGroupDto fgResponse = new FunctionalGroupDto();
		fgResponse.setSlug("test");
		
		mockMvc
			.perform(post(MappingConstant.FUNCTIONAL_GROUP_ROOT+MappingConstant.FUNCTIONAL_GROUP_UPDATE.replace("{slug}", fgResponse.getSlug())))
			.andExpect(status().isOk());

	}
	
	@Test
	public void updateWithoutErorsTest() throws Exception {
		
		FunctionalGroupForm form = new FunctionalGroupForm();
		form.setName("test");
		form.setId(1L);
		
		Mockito.when(this.functionalGroupService.update(Mockito.any())).thenReturn(new FunctionalGroupDto());
		
		mockMvc
			.perform(
					post(MappingConstant.FUNCTIONAL_GROUP_ROOT+MappingConstant.FUNCTIONAL_GROUP_UPDATE.replace("{slug}", "test"))
					.contentType(MediaType.APPLICATION_FORM_URLENCODED).
					flashAttr(Constant.FUNCTIONAL_GROUP_FORM_ATTRIBUTE_KEY, form)
					)
			.andExpect(status().isOk());

	}
	
	@Test
	public void deleteUserOkTest() throws Exception {
		
		Mockito.doNothing().when(this.functionalGroupService).delete(Mockito.any());
		
		mockMvc
			.perform(delete(MappingConstant.FUNCTIONAL_GROUP_ROOT+MappingConstant.FUNCTIONAL_GROUP_DELETE.replace("{id}", "1")))
			.andExpect(status().isOk());

	}
	
	@Test
	public void deleteUserKOTest() throws Exception {
		
		Mockito.doThrow(ServiceException.class).when(this.functionalGroupService).delete(Mockito.any());
		
		mockMvc
			.perform(delete(MappingConstant.FUNCTIONAL_GROUP_ROOT+MappingConstant.FUNCTIONAL_GROUP_DELETE.replace("{id}", "1")))
			.andExpect(status().isNotFound());

	}
	
}
