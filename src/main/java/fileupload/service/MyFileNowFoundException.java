package fileupload.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNowFoundException extends RuntimeException {

    public MyFileNowFoundException(String message) {
       super(message);

    }

    public MyFileNowFoundException(String message, Throwable error){
        super(message, error);
    }
}
