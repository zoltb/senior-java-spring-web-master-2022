package hu.ponte.hr.dao;

import hu.ponte.hr.dto.ImageMetaDTO;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class ImageMetaDAO {

    public ImageMetaDTO imageObjectCreator(MultipartFile imageFile, ImageMetaDTO imageMetaDTO)
            throws InvalidContentTypeException, FileSizeLimitExceededException, IOException, NullPointerException {

        long fileSize = imageFile.getSize();
        String mimeTypeOfTheFile = imageFile.getContentType();
        imageMetaDTO.setByteFormat(imageFile.getBytes());
        imageMetaDTO.setName(imageFile.getOriginalFilename());


        //fileUpload restrictions
        //max file size is 2MB = 2,000,000 byte
        long fileUploadLimitInByte = 2000000;
        //upload only image type
        String requiredUploadType = "image/";

        if (mimeTypeOfTheFile == null || mimeTypeOfTheFile.isEmpty())
            throw new InvalidContentTypeException("MIME Type is not recognized!");

        if (!mimeTypeOfTheFile.startsWith(requiredUploadType))
            throw new InvalidContentTypeException("MIME Type is not an image!");

        if (fileSize > fileUploadLimitInByte)
            throw new FileSizeLimitExceededException("The file size is greater than the upload limit: " + String.valueOf(fileSize), fileSize, fileUploadLimitInByte);

        imageMetaDTO.setMimeType(mimeTypeOfTheFile);
        imageMetaDTO.setSize(fileSize);

        return imageMetaDTO;
    }
}
