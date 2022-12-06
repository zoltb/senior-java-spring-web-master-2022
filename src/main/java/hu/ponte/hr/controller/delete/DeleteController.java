package hu.ponte.hr.controller.delete;


import hu.ponte.hr.dto.ImageMetaDTO;
import hu.ponte.hr.services.ImageStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RequestMapping("api/images/preview")
public class DeleteController {

    private final ImageStoreService imageStoreService;

    @Autowired
    public DeleteController(ImageStoreService imageStoreService) {
        this.imageStoreService = imageStoreService;
    }
    //dronezone should call this endpoint to delete
    @GetMapping(value = ("/{id}/delete"))
    public String delete(@PathVariable Long id) {
        imageStoreService.delete(id);
        return "redirect:/";
    }
}
