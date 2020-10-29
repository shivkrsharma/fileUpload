package fileupload.service;

import fileupload.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties){

        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        System.out.print("i reahcede here");


        try{
            Files.createDirectories(this.fileStorageLocation);
        }catch(MaxUploadSizeExceededException e){
            throw new FileStorageException("File size exceded");
        }
        catch(Exception e){
            throw new FileStorageException("Could no create diretcory");

        }

    }

    public String storeFile(MultipartFile file){

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {

            if (fileName.contains("..")) {
                throw new FileStorageException(" Invalid file name");
            }

             Path targetlocation = this.fileStorageLocation.resolve(fileName);
             Files.copy(file.getInputStream(),targetlocation, StandardCopyOption.REPLACE_EXISTING);

             return fileName;


        }
          catch(IOException e){

            throw new FileStorageException("Could now store file");

            }
    }


    public Resource loadFileAsResource(String filename){

        try{

            Path filepath = this.fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filepath.toUri());

            if(resource.exists()){
                return resource;
            }else{
                throw new MyFileNowFoundException("File now found "+ filename);
            }

        }catch(MalformedURLException ex){
            throw new MyFileNowFoundException("File Not found exception");
        }

    }


}
