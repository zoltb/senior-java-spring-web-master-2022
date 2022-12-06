package hu.ponte.hr.services;

import hu.ponte.hr.dao.ImageMetaDAO;
import hu.ponte.hr.dto.ImageMetaDTO;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Base64;

@Service
public class SignService {

    private SignCreator signCreator;
    private ImageMetaDAO imageMetaDAO;

    //The path of the PrivateKey
    private String pathOfThePrivateKey = "src\\main\\resources\\config\\keys\\key.private";

    @Autowired
    public SignService(SignCreator signCreator, ImageMetaDAO imageMetaDAO) {
        this.signCreator = signCreator;
        this.imageMetaDAO = imageMetaDAO;
    }



    ImageMetaDTO imageSaver(MultipartFile imageFile) throws InvalidContentTypeException, FileSizeLimitExceededException, IOException, NullPointerException {
        ImageMetaDTO imageMetaDTO = new ImageMetaDTO();
        ImageMetaDTO imageMetaBeforeSigning;
        imageMetaBeforeSigning = imageMetaDAO.imageObjectCreator(imageFile, imageMetaDTO);
        return imageSigner(imageMetaBeforeSigning);
    }

    ImageMetaDTO imageSigner(ImageMetaDTO imageMetaToSign) {
        byte[] byteFormatOfTheImage = imageMetaToSign.getByteFormat();
        byte[] signatureInByteArray = "".getBytes();
        try{
            signatureInByteArray = signCreator.createASHA256RSASignWithPrivateKey(pathOfThePrivateKey, byteFormatOfTheImage);
        } catch (GeneralSecurityException |
                IOException e)
        {
            System.out.println(Base64.getEncoder().encodeToString(signatureInByteArray));
            e.printStackTrace();
        }

        //If there is generated signature create the entity
        if (signatureInByteArray.length == 0)
            throw new NullPointerException("No Signature!");

        String generatedSignature = Base64.getEncoder().encodeToString(signatureInByteArray);
        imageMetaToSign.setDigitalSign(generatedSignature);
        return imageMetaToSign;
    }
}
