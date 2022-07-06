package es.cex.service;


import java.util.List;

import es.cex.arq.exceptions.ServiceException;
import es.cex.dto.PaginatedListDto;
import es.cex.dto.RoleDto;
import es.cex.dto.RoleListRequestDto;
import es.cex.dto.RolePaginatedRequestDto;

/**
 * Interface for Role Manager service
 */
public interface IRoleService {

	public List<RoleDto> getRoleList() throws ServiceException;

	public List<RoleDto> getRoleList(RoleListRequestDto roleRequestDto) throws ServiceException;

	public PaginatedListDto<RoleDto> getPaginatedRoleList(RolePaginatedRequestDto rolePaginatedRequestDto) throws ServiceException;

	public RoleDto getRoleBySlug(String slug)throws ServiceException;

	public RoleDto save(RoleDto roleDto)throws ServiceException;

	public RoleDto update(RoleDto roleDto)throws ServiceException;

	public void delete(Long id)throws ServiceException;

}
