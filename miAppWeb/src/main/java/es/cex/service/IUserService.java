package es.cex.service;


import java.util.List;

import es.cex.arq.exceptions.ServiceException;
import es.cex.dto.PaginatedListDto;
import es.cex.dto.UserDto;
import es.cex.dto.UserPaginatedDto;
import es.cex.dto.UserPaginatedRequestDto;

/**
 * Interface for User Manager service
 */
public interface IUserService {

	public List<UserDto> getUserList() throws ServiceException;

	public PaginatedListDto<UserPaginatedDto> getPaginatedUserList(UserPaginatedRequestDto userPaginatedRequestDto) throws ServiceException;

	public UserDto getUserByLogin(String login)throws ServiceException;

	public UserDto save(UserDto userDto)throws ServiceException;

	public UserDto update(UserDto userDto)throws ServiceException;

	public void delete(Long id)throws ServiceException;
}
