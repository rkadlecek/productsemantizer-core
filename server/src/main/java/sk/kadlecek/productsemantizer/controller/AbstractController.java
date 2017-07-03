package sk.kadlecek.productsemantizer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import sk.kadlecek.productsemantizer.controller.validation.ValidationErrorResponse;
import sk.kadlecek.productsemantizer.exception.UnsupportedKnowledgeBaseException;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Callable;

public abstract class AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    class ResourceNotFoundException extends RuntimeException {
        ResourceNotFoundException() {
            super();
        }
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private @ResponseBody String ResourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        return "Requested resource was not found!";
    }

    @ExceptionHandler(UnsupportedKnowledgeBaseException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    private @ResponseBody String UnsupportedKnowledgeBaseExceptionHandler(Exception e) {
        logger.error("{}", e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ValidationErrorResponse MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return fromBindingErrors(e.getBindingResult());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    private @ResponseBody String ExceptionHandler(Exception e) {
        logger.error("{}", e.getMessage());
        return "Unexpected internal error occured!";
    }

    @SuppressWarnings("unchecked")
    <T> T respondWithCreated(HttpServletResponse response, Callable function) throws Exception {
        response.setStatus(HttpServletResponse.SC_CREATED);
        return (T) function.call();
    }

    public ValidationErrorResponse fromBindingErrors(Errors errors) {
        ValidationErrorResponse error = new ValidationErrorResponse("Validation failed: " + errors.getErrorCount() + " error(s)");
        for (ObjectError objectError : errors.getAllErrors()) {
            error.addValidationError(objectError.getDefaultMessage());
        }
        return error;
    }

    /*@ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }*/
}
