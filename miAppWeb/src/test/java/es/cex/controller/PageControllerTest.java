package es.cex.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.fasterxml.jackson.databind.ObjectMapper;

import es.cex.Application;
import es.cex.arq.exceptions.ServiceException;
import es.cex.common.constant.MappingConstant;
import es.cex.controller.form.MenuForm;
import es.cex.controller.form.TranslateMenuRequestForm;
import es.cex.dto.FunctionalGroupDto;
import es.cex.dto.PageDto;
import es.cex.dto.RoleDto;
import es.cex.service.impl.CacheIntranetServiceImpl;
import es.cex.service.impl.FunctionalGroupServiceImpl;
import es.cex.service.impl.PageServiceImpl;
import es.cex.service.impl.RoleServiceImpl;

@SpringBootTest(classes = { Application.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebApplicationContext.class })
@TestPropertySource(locations="classpath:application-test.properties")
public class PageControllerTest {
	
	@InjectMocks
	@Autowired
	private PageController pageController;
	
	@Mock
	private PageServiceImpl pageService;
	
	@Mock
	private RoleServiceImpl roleService;
	
	@Mock
	private FunctionalGroupServiceImpl functionalGroupService;
	
	@Mock
	private CacheIntranetServiceImpl cacheIntranetService;
	
	private MockMvc mockMvc;

	@Mock
	private View mockView;

	@Mock
    HttpServletRequest request;

	@Before
	public void setupMock() throws JsonParseException, JsonMappingException, IOException {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(pageController)
				.setSingleView(mockView).build();
	}

	@Test
	public void searchTest() throws Exception {
		
		Mockito.when(this.functionalGroupService.getFunctionalGroupList()).thenReturn(new ArrayList<FunctionalGroupDto>());
		Mockito.when(this.roleService.getRoleList()).thenReturn(new ArrayList<RoleDto>());
		
		mockMvc
			.perform(get(MappingConstant.PAGE_ROOT))
			.andExpect(status().isOk());

	}
	
	@Test
	public void getMenuTest() throws Exception {
		
		List<PageDto> pages = new ArrayList<>();
		PageDto page = new PageDto();
		page.setId(1L);
		page.setName(new HashMap<String, String>());
		pages.add(page);
		
		Mockito.when(this.pageService.getPageList()).thenReturn(pages);
		
		mockMvc
			.perform(get(MappingConstant.PAGE_ROOT+MappingConstant.PAGE_LIST))
			.andExpect(status().isOk());

	}
	
	@Test
	public void detailTest() throws Exception {
		
		Mockito.when(this.pageService.getPageById(Mockito.any())).thenReturn(new PageDto());
		
		mockMvc
			.perform(get(MappingConstant.PAGE_ROOT+MappingConstant.PAGE_DETAIL.replace("{id}", "1")))
			.andExpect(status().isOk());

	}
	
	@Test
	public void createWithErorsTest() throws Exception {
		
		Mockito.when(this.roleService.getRoleList()).thenReturn(new ArrayList<RoleDto>());
		Mockito.when(this.functionalGroupService.getFunctionalGroupList()).thenReturn(new ArrayList<FunctionalGroupDto>());
		
		mockMvc
			.perform(
					post(MappingConstant.PAGE_ROOT)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(new MenuForm())))
			.andExpect(status().isBadRequest());

	}
	
	@Test
	public void createWithoutErorsTest() throws Exception {
		
		PageDto pageDto = new PageDto();
		
		Mockito.when(this.pageService.save(Mockito.any())).thenReturn(pageDto);
		
		MenuForm menuForm = new MenuForm();
		menuForm.setIdParent(1L);
		menuForm.setUrlIframe("http://www.test.com");
		
		List<Long> roles = new ArrayList<>();
		roles.add(1L);
		menuForm.setRoles(roles);
		
		TranslateMenuRequestForm translateMenuRequestForm = new TranslateMenuRequestForm();
		Map<String, String> translate = new HashMap<>();
		translate.put("es", "español");
		translate.put("pt", "portugues");
		translateMenuRequestForm.setTranslate(translate);
		
		menuForm.setName(translateMenuRequestForm);
		menuForm.setSlug(translateMenuRequestForm);
		
		Mockito.doNothing().when(this.cacheIntranetService).flush();
		
		// Primero creamos
		mockMvc
			.perform(
					post(MappingConstant.PAGE_ROOT)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(menuForm)))
			.andExpect(status().isOk());
		
		
		// ahora editamos
		pageDto.setId(2L);
		menuForm.setId(2L);
		Mockito.when(this.pageService.update(Mockito.any())).thenReturn(pageDto);
		
		mockMvc
		.perform(
				post(MappingConstant.PAGE_ROOT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(menuForm)))
		.andExpect(status().isOk());
		
	}

	
	@Test
	public void deletePageOkTest() throws Exception {
		
		Mockito.doNothing().when(this.pageService).delete(Mockito.any());
		
		mockMvc
			.perform(delete(MappingConstant.PAGE_ROOT+MappingConstant.PAGE_DELETE.replace("{id}", "1")))
			.andExpect(status().isOk());

	}
	
	@Test
	public void deletePageKOTest() throws Exception {
		
		Mockito.doThrow(ServiceException.class).when(this.pageService).delete(Mockito.any());
		
		mockMvc
			.perform(delete(MappingConstant.PAGE_ROOT+MappingConstant.PAGE_DELETE.replace("{id}", "1")))
			.andExpect(status().isNotFound());

	}
	
}
