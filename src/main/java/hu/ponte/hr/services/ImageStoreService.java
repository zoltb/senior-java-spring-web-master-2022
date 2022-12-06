package hu.ponte.hr.services;


import org.apache.commons.fileupload.FileUploadBase;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public interface ImageStoreService {

    public void save(MultipartFile file) throws InvalidContentTypeException, IOException, FileSizeLimitExceededException;

    public void delete(Long id);
}
