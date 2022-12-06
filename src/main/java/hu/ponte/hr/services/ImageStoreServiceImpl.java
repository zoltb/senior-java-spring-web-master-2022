package hu.ponte.hr.services;

import hu.ponte.hr.dao.ImageMetaRepository;
import hu.ponte.hr.dto.ImageMetaDTO;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class ImageStoreServiceImpl implements ImageStoreService{

    private final Path root = Paths.get("uploads");
    private SignService signService;
    private ImageMetaRepository imageMetaRepository;

    @Autowired
    public ImageStoreServiceImpl(SignService signService, ImageMetaRepository imageMetaRepository) {
        this.signService = signService;
        this.imageMetaRepository = imageMetaRepository;
    }


    @Override
    public void save(MultipartFile imageFile)
         throws InvalidContentTypeException, FileSizeLimitExceededException, IOException, NullPointerException {

            ImageMetaDTO newImageToSave = signService.imageSaver(imageFile);
            imageMetaRepository.saveAndFlush(newImageToSave);
    }

    @Override
    public void delete(Long id) {
        imageMetaRepository.deleteById(id);
    }

/*
    public ImageStoreService(SignService signService, ImageMetaRepository imageMetaRepository) {
        this.signService = signService;
        this.imageMetaRepository = imageMetaRepository;
    }

    public void saveImage(MultipartFile imageFile)
            throws InvalidContentTypeException, FileSizeLimitExceededException, IOException, NullPointerException {

        ImageMetaDTO newImageToSave = signService.imageSaver(imageFile);
        imageMetaRepository.saveAndFlush(newImageToSave);
    }

    public boolean delete(String filename);
    */

}
