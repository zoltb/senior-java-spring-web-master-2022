package hu.ponte.hr.controller;

import hu.ponte.hr.dao.ImageMetaRepository;
import hu.ponte.hr.dto.ImageMetaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("api/images")
public class ImagesController {

    private final ImageMetaRepository imageMetaRepository;

    @Autowired
    public ImagesController(ImageMetaRepository imageMetaRepository) {
        this.imageMetaRepository = imageMetaRepository;
    }

    @GetMapping("meta")
    public List<ImageMetaDTO> listImages() {
        return imageMetaRepository.findAll();
    }

    @GetMapping("preview/{id}")
    public byte[] getImage(@PathVariable("id") String id) {
        Optional<ImageMetaDTO> optionalImageMetaDTO = imageMetaRepository.findById(Long.parseLong(id));
        ImageMetaDTO imageMetaDTOByID = new ImageMetaDTO();
        if (optionalImageMetaDTO.isPresent()) {
            imageMetaDTOByID = optionalImageMetaDTO.get();
        }
        return imageMetaDTOByID.getByteFormat();
    }
}
