package fileupload.service;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ResponseError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private List erros;


    public ResponseError(LocalDateTime now, String file_not_found, List<String> details) {
        this.timestamp=now;
        this.message=file_not_found;
        this.erros=details;
    }
}