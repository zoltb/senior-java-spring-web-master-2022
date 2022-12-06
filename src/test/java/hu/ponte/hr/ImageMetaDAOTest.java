package hu.ponte.hr;

import hu.ponte.hr.dao.ImageMetaDAO;
import hu.ponte.hr.dto.ImageMetaDTO;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * @author zoltan
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class ImageMetaDAOTest {

    private ImageMetaDAO imageMetaDAO = new ImageMetaDAO();
    private ImageMetaDTO imageMetaDTO = new ImageMetaDTO();
    private File fileCat = new File("src\\test\\resources\\images\\cat.jpg");

    @Test
    public void testIfIncomingImageAttributesWereReadFromTheImage() throws Exception {
        //Given
            InputStream stream = new FileInputStream(fileCat);
            MultipartFile multiPFileCat = new MockMultipartFile("cat", fileCat.getName(), MediaType.IMAGE_JPEG_VALUE, stream);

            byte[] byteFormatOfTheCatImage = Files.readAllBytes(fileCat.toPath());
            long fileSize = multiPFileCat.getSize();
            String mimeTypeOfTheFile = multiPFileCat.getContentType();
            String name = multiPFileCat.getOriginalFilename();

            //When
            imageMetaDAO.imageObjectCreator(multiPFileCat, imageMetaDTO);
            //Then
            Assert.assertArrayEquals(imageMetaDTO.getByteFormat(), byteFormatOfTheCatImage);
            Assert.assertEquals(imageMetaDTO.getSize(), fileSize);
            Assert.assertTrue(imageMetaDTO.getName().equals(name));
            Assert.assertTrue(imageMetaDTO.getMimeType().equals(mimeTypeOfTheFile));
    }

    @Test
    public void testExceptionIfMimeTypeIsNotAnImage() {
        //Given
        //using empty try catch to create necessary files and to avoid to add throws to the method
        try {
            InputStream stream = new FileInputStream(fileCat);
            MultipartFile multiPFileCat = new MockMultipartFile("cat", "cat.xml", MediaType.TEXT_XML_VALUE, stream);
            //Then
            Assert.assertThrows(InvalidContentTypeException.class, () -> imageMetaDAO.imageObjectCreator(multiPFileCat, imageMetaDTO));
        } catch (Exception e) {
        }
    }

    @Test
    public void testExceptionIfFileSizeLimitExceeds() {
        //Given
        File fileBigCat = new File("src\\test\\resources\\images\\bigcat.gif");
        //using empty try catch to create necessary files and to avoid to add throws to the method
        try {
            InputStream stream = new FileInputStream(fileBigCat);
            MultipartFile multiPFileBigCat = new MockMultipartFile("bigcat", fileBigCat.getName(), MediaType.IMAGE_GIF_VALUE, stream);

            //Then
            Assert.assertThrows(FileSizeLimitExceededException.class, () -> imageMetaDAO.imageObjectCreator(multiPFileBigCat, imageMetaDTO));
        } catch (Exception e) {
        }
    }
}
