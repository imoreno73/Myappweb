package es.cex.service.impl;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.cex.arq.exceptions.ServiceException;
import es.cex.dto.PageDto;
import es.cex.service.IAuthenticationService;
import es.cex.service.IPageService;
import es.cex.service.IRestClientService;
import es.cex.service.RestServiceParameter;

/**
 * Implementation of {@link IPageService}
 */
@Service("pageService")
public class PageServiceImpl implements IPageService {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(PageServiceImpl.class);

	@Value("${ws.get.pages.list.url:test}")
	private String wsGetPagesListUrl;

	@Value("${ws.get.pages.list.parent.url:test}")
	private String wsGetPagesListParentUrl;

	@Value("${ws.get.page.url:test}")
	private String wsGetPageUrl;

	@Value("${ws.post.page.url:test}")
	private String wsPostPageUrl;

	@Value("${ws.put.page.url:test}")
	private String wsPutPageUrl;

	@Value("${ws.patch.page.url:test}")
	private String wsPatchPageUrl;

	@Value("${ws.delete.page.url:test}")
	private String wsDeletePageUrl;

	@Value("${REST_AUTH:test}")
	private String restAuth;

	@Autowired
	private IRestClientService restClientService;

	@Autowired
	private IAuthenticationService authenticationService;

	@Override
	public List<PageDto> getPageList() throws ServiceException {

		PageDto[] pages = null;

		final RestServiceParameter<Object, PageDto[]> restServiceParameter = RestServiceParameter
				.create(this.wsGetPagesListUrl, PageDto[].class).setAuth(this.restAuth);

		this.authenticationService.addTokenToHeader(restServiceParameter);

		try {
			pages = this.restClientService.getRestService(restServiceParameter);
		} catch (Exception e) {
			LOG.error("Error al recuperar la lista completa de páginas", e);
			throw new ServiceException("Error al recuperar la lista completa de páginas", e);
		}

		return Arrays.asList(pages);

	}

	@Override
	public List<PageDto> getPageListParent(Long parent) throws ServiceException {

		PageDto[] pages = null;
		String urlFrmt = null;


		try {
			urlFrmt = MessageFormat.format(this.wsGetPagesListParentUrl, parent.toString());
			final RestServiceParameter<Object, PageDto[]> restServiceParameter = RestServiceParameter
					.create(urlFrmt, PageDto[].class).setAuth(this.restAuth);

			this.authenticationService.addTokenToHeader(restServiceParameter);

			pages = this.restClientService.getRestService(restServiceParameter);
		} catch (Exception e) {
			LOG.error("Error al recuperar la lista completa de páginas por padre {}", parent, e);
			throw new ServiceException("Error al recuperar la lista completa de páginas por padre "+ parent, e);
		}

		return Arrays.asList(pages);

	}

	@Override
	public PageDto getPageById(Long id) throws ServiceException {

		PageDto pageDto = null;
		String urlFrmt = null;

		try {
			urlFrmt = MessageFormat.format(this.wsGetPageUrl, id.toString());

			final RestServiceParameter<Object, PageDto> restServiceParameter = RestServiceParameter
					.create(urlFrmt, PageDto.class).setAuth(this.restAuth);

			this.authenticationService.addTokenToHeader(restServiceParameter);

			pageDto = this.restClientService.getRestService(restServiceParameter);
		} catch (Exception e) {
			LOG.error("Error al recuperar la página por id: {}", id, e);
			throw new ServiceException("Error al recuperar la página por id: " + id, e);
		}

		return pageDto;

	}

	@Override
	public PageDto save(PageDto pageDto) throws ServiceException {

		PageDto newPageDto = null;

		try {

			final RestServiceParameter<PageDto, PageDto> restServiceParameter = RestServiceParameter.create(this.wsPostPageUrl, pageDto, PageDto.class)
					.setAuth(this.restAuth);

			this.authenticationService.addTokenToHeader(restServiceParameter);

			newPageDto = this.restClientService.postRestService(restServiceParameter);
		} catch (Exception e) {
			LOG.error("Error al almacenar la página: {}", pageDto.getId(), e);
			throw new ServiceException("Error al almacenar la página: " + pageDto.getId(), e);
		}

		return newPageDto;

	}

	@Override
	public PageDto update(PageDto pageDto) throws ServiceException {

		PageDto newPageDto = null;
		String urlFrmt = null;

		try {
			urlFrmt = MessageFormat.format(this.wsPutPageUrl, pageDto.getId().toString());

			final RestServiceParameter<PageDto, PageDto> restServiceParameter = RestServiceParameter.create(urlFrmt, pageDto, PageDto.class)
					.setAuth(this.restAuth);

			this.authenticationService.addTokenToHeader(restServiceParameter);

			newPageDto = this.restClientService.putRestService(restServiceParameter);
		} catch(Exception e) {
			LOG.error("Error al actualizar la página: {}", pageDto.getId(), e);
			throw new ServiceException("Error al actualizar la página: " + pageDto.getId(), e);
		}

		return newPageDto;

	}

	@Override
	public PageDto updateParentOrden(PageDto pageDto) throws ServiceException {

		PageDto newPageDto = null;
		String urlFrmt = null;

		try {
			urlFrmt = MessageFormat.format(this.wsPatchPageUrl, pageDto.getId().toString());

			final RestServiceParameter<PageDto, PageDto> restServiceParameter = RestServiceParameter.create(urlFrmt, pageDto, PageDto.class)
					.setAuth(this.restAuth);

			this.authenticationService.addTokenToHeader(restServiceParameter);

			newPageDto = this.restClientService.patchRestService(restServiceParameter);
		} catch(Exception e) {
			LOG.error("Error al actualizar la página: {}", pageDto.getId().toString(), pageDto.getParent().toString(), pageDto.getOrden(), e);
			throw new ServiceException("Error al actualizar la página: " + pageDto.getId().toString() + "-" + pageDto.getParent().toString() +"-"+ pageDto.getOrden(), e);
		}

		return newPageDto;

	}

	@Override
	public void delete(Long id) throws ServiceException {

		String urlFrmt = null;

		try {
			urlFrmt = MessageFormat.format(this.wsDeletePageUrl, id.toString());

			final RestServiceParameter<Object, PageDto> restServiceParameter = RestServiceParameter
					.create(urlFrmt, PageDto.class).setAuth(this.restAuth);

			this.authenticationService.addTokenToHeader(restServiceParameter);

			this.restClientService.deleteRestService(restServiceParameter);

		} catch(Exception e) {
			LOG.error("Error al eliminar la página: {}", id, e);
			throw new ServiceException("Error al eliminar la página: " + id, e);
		}

	}

}