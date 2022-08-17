package es.cex.service;


import java.util.List;

import es.cex.arq.exceptions.ServiceException;
import es.cex.dto.RegisterDto;
import es.cex.dto.RegistersPaginatedRequestDto;
import es.cex.dto.PaginatedListDto;

/**
 * Interface for functional group Manager service
 */
public interface IRegistersService {

	public List<RegisterDto> getRegisterList() throws ServiceException;

	public PaginatedListDto<RegisterDto> getPaginatedList(RegistersPaginatedRequestDto RegsitersPaginatedRequestDto) throws ServiceException;

	public RegisterDto getRegisterBySlug(String slug)throws ServiceException;

	public RegisterDto save(RegisterDto RegistersDto)throws ServiceException;

	public RegisterDto update(RegisterDto RegistersDto)throws ServiceException;

	public void delete(Long id)throws ServiceException;

}
