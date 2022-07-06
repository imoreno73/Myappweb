package es.cex.service;


import java.util.List;

import es.cex.arq.exceptions.ServiceException;
import es.cex.dto.PageDto;

/**
 * Interface for Page Manager service
 */
public interface IPageService {

	public List<PageDto> getPageList() throws ServiceException;

	public List<PageDto> getPageListParent(Long parent) throws ServiceException;

	public PageDto getPageById(Long id)throws ServiceException;

	public PageDto save(PageDto pageDto)throws ServiceException;

	public PageDto update(PageDto pageDto)throws ServiceException;

	public PageDto updateParentOrden(PageDto pageDto)throws ServiceException;

	public void delete(Long id)throws ServiceException;
}
