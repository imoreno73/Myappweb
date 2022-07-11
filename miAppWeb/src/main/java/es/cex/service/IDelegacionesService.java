package es.cex.service;


import java.util.List;

import es.cex.arq.exceptions.ServiceException;
import es.cex.dto.DelegacionesDto;
import es.cex.dto.DelegacionesPaginatedRequestDto;
import es.cex.dto.PaginatedListDto;

/**
 * Interface for functional group Manager service
 */
public interface IDelegacionesService {

	public List<DelegacionesDto> getDelegacionesList() throws ServiceException;

	public PaginatedListDto<DelegacionesDto> getPaginatedList(DelegacionesPaginatedRequestDto DelegacionesPaginatedRequestDto) throws ServiceException;

	public DelegacionesDto getDelegacionesBySlug(String slug)throws ServiceException;

	public DelegacionesDto save(DelegacionesDto DelegacionesDto)throws ServiceException;

	public DelegacionesDto update(DelegacionesDto DelegacionesDto)throws ServiceException;

	public void delete(Long id)throws ServiceException;

}
