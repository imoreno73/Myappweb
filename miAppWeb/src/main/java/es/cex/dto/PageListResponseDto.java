package es.cex.dto;

import java.util.List;

/**
 * Class with PageListResponseDto DTO
 */

public class PageListResponseDto extends ErrorDto {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = -4776680751103597127L;

	private List<PageDto> response;

	public List<PageDto> getResponse() {
		return response;
	}

	public void setResponse(List<PageDto> response) {
		this.response = response;
	}

}
