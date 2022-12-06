package hu.ponte.hr.controller.upload;

import hu.ponte.hr.dto.ImageMetaDTO;
import hu.ponte.hr.services.ImageStoreService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequestMapping("api/file")
public class UploadController {

    private final ImageStoreService imageStoreService;

    @Autowired
    public UploadController(ImageStoreService imageStoreService) {
        this.imageStoreService = imageStoreService;
    }

    @PostMapping(value = "post")
    @ResponseBody
    public ResponseEntity<ImageMetaDTO> handleFormUpload(@RequestParam("file") MultipartFile file) {

        try {
            imageStoreService.save(file);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidContentTypeException | FileSizeLimitExceededException | IOException | NullPointerException e) {
            e.getMessage();
            e.printStackTrace();

            if (e instanceof InvalidContentTypeException)
                return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
            if (e instanceof FileSizeLimitExceededException)
                return new ResponseEntity<>(HttpStatus.PAYLOAD_TOO_LARGE);
            if (e instanceof NullPointerException)
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
