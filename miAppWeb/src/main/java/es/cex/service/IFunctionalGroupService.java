package es.cex.service;


import java.util.List;

import es.cex.arq.exceptions.ServiceException;
import es.cex.dto.FunctionalGroupDto;
import es.cex.dto.FunctionalGroupPaginatedRequestDto;
import es.cex.dto.PaginatedListDto;

/**
 * Interface for functional group Manager service
 */
public interface IFunctionalGroupService {

	public List<FunctionalGroupDto> getFunctionalGroupList() throws ServiceException;

	public PaginatedListDto<FunctionalGroupDto> getPaginatedList(FunctionalGroupPaginatedRequestDto functionalGroupPaginatedRequestDto) throws ServiceException;

	public FunctionalGroupDto getFunctionalGroupBySlug(String slug)throws ServiceException;

	public FunctionalGroupDto save(FunctionalGroupDto functionalGroupDto)throws ServiceException;

	public FunctionalGroupDto update(FunctionalGroupDto functionalGroupDto)throws ServiceException;

	public void delete(Long id)throws ServiceException;

}
