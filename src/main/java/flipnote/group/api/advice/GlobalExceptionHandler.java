package flipnote.group.api.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import flipnote.group.domain.policy.BusinessException;
import flipnote.group.global.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiResponse<Void>> handle(BusinessException e) {
		return ResponseEntity.status(e.getErrorCode().getStatus()).body(ApiResponse.error(e.getErrorCode()));

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handle(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.internalError());
	}
}
