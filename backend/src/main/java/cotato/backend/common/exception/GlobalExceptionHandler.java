package cotato.backend.common.exception;

import cotato.backend.domains.post.exception.PostException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cotato.backend.common.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<Object> handleApiException(ApiException e) {
		log.warn("handleApiException", e);

		return makeErrorResponseEntity(e.getHttpStatus(), e.getMessage(), e.getCode());
	}

	@ExceptionHandler(PostException.class)
	public ResponseEntity<Object> handlePostException(PostException e) {
		log.warn("handlePostException", e);

		return makeErrorResponseEntity(e.getHttpStatus(), e.getMessage(), e.getCode());
	}

	private ResponseEntity<Object> makeErrorResponseEntity(HttpStatus httpStatus, String message, String code) {
		return ResponseEntity
				.status(httpStatus)
				.body(ErrorResponse.of(httpStatus, message, code));
	}
}
