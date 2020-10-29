package fileupload.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class FileExceptionAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Object> handleFileNotFoundException(FileNotFoundException exc) {

        List<String> details = new ArrayList<String>();
        details.add(exc.getMessage());
        ResponseError err = new ResponseError(LocalDateTime.now(), "File Not Found" ,details);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException exc) {

        List<String> details = new ArrayList<String>();
        details.add(exc.getMessage());
        ResponseError err = new ResponseError(LocalDateTime.now(), "File size excced" ,details);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(err);
    }

}
