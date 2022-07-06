package es.cex.common.utils;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import es.cex.arq.exceptions.ServiceException;
import es.cex.common.constant.Constant;
import es.cex.common.entity.DataTableResults;
import es.cex.dto.PaginatedListDto;
import es.cex.dto.PaginationDto;

/**
 * Util for common logics
 */
@Component("commonUtils")
public class CommonUtils {

	public <T> DataTableResults<T> prepareResponse(PaginatedListDto<T> paginatedListDto, String paginationDrawKey) throws ServiceException {

		if (paginatedListDto == null) {
			throw new ServiceException("Error al preparar la respuesta porque los datos recibidos son nulos");
		}

		DataTableResults<T> dataTableResult = new DataTableResults<>();
		dataTableResult.setListOfDataObjects(paginatedListDto.getResult());
		dataTableResult.setRecordsTotal(String.valueOf(paginatedListDto.getTotal()));
		dataTableResult.setDraw(paginationDrawKey);
		dataTableResult.setRecordsFiltered(Integer.toString(paginatedListDto.getTotal()));

		return dataTableResult;
	}

	public PaginationDto getPaginationData(HttpServletRequest request) throws ServiceException {

		if(request == null) {
			throw new ServiceException("request es nulo");
		}

		PaginationDto paginationDto = new PaginationDto();

		int pageStart = StringUtils.isBlank(request.getParameter(Constant.PAGINATION_START_KEY)) ? 0
				: Integer.parseInt(request.getParameter(Constant.PAGINATION_START_KEY));
		int pageSize = StringUtils.isBlank(request.getParameter(Constant.PAGINATION_PAGE_LENGTH_KEY))
				? Integer.parseInt(Constant.DEFAULT_PAGE_SIZE)
				: Integer.parseInt(request.getParameter(Constant.PAGINATION_PAGE_LENGTH_KEY));
		int pageNum = pageStart != 0 ? ((pageStart + 1) / pageSize) + 1 : 1;

		paginationDto.setPage(pageNum);
		paginationDto.setSize(pageSize);

		return paginationDto;
	}

	/**
	 * Verificar si una cadena de texto es distinta de null y tiene contenido
	 *
	 * @param cadena
	 *            Cadena de texto para verificar que no es null ni vacía
	 * @return True si la cadena no es null ni vacía. False en caso contrario
	 */
	public boolean isAvailable(String cadena) {

		return cadena != null && !cadena.isEmpty();
	}

	/**
	 * Verificar si una colección es distinta de null y tiene contenido
	 *
	 * @param collection
	 *            Colección para verificar que no es null ni vacía
	 * @return True si la colección no es null ni vacía. False en caso contrario
	 */
	public boolean isAvailable(Collection<?> collection) {

		return collection != null && !collection.isEmpty();
	}

}
