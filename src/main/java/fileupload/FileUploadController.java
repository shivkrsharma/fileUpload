package fileupload;

import fileupload.service.FileStorageException;
import fileupload.service.FileStorageService;
import fileupload.service.UploadFileResponse;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    FileStorageProperties fileStorageProperties;

    @GetMapping("/uploader")
    public String uploadFile() {
        System.out.println("tests===");
        return "servere working";
    }

    @PostMapping("/UplaodFile")
    @ApiResponses(value =
                  {
                          @ApiResponse(code=200, message="ok", response=UploadFileResponse.class),
                          @ApiResponse(code=204, message="No content", response=FileStorageException.class),
                          @ApiResponse(code=500, message="Server Failure", response=FileStorageException.class)
                  }
                 )
    public UploadFileResponse upLoadFile(@RequestParam("file")  MultipartFile file)
    {
        int filesize=fileStorageProperties.getMaxFileSize();


     String fileName = fileStorageService.storeFile(file);

     System.out.println("file names=="+fileName);

     String fileDownLoadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
             .path("/downloadFile")
             .path(fileName)
             .toUriString();
     return new UploadFileResponse(fileName,fileDownLoadUri, file.getContentType(), file.getSize());

    }

      @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> upLoadMultipleFiles(@RequestParam("files") MultipartFile[] files){
       {

          Arrays.asList(files).stream().forEach(x->System.out.print(x.getName()));

         return Arrays.asList(files)
                .stream()
                 .map(file -> upLoadFile(file))
                .collect(Collectors.toList());

       }
   }


    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String filename, HttpServletRequest requeste){

        Resource resource =fileStorageService.loadFileAsResource(filename);
        String contentType =null;

        try{
            contentType = requeste.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch(IOException ex)
        {

        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachement ; file =\"" + resource, resource.getFilename()+"\"")
                .body(resource);




    }




}
