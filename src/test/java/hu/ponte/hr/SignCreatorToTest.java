package hu.ponte.hr;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

//added to Test folder to Test the package-private class and
// the private PrivateKey because these won't be added to prod version
class SignCreatorToTest {

    byte[] createASHA256RSASignWithPrivateKeyToTest(String pathOfTheKey, byte[] imageByteFormatForSigning)
            throws GeneralSecurityException, IOException {
        byte[] signature;
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get((pathOfTheKey)));
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            try {
                KeyFactory kf = KeyFactory.getInstance("RSA");
                Signature privateSignature = Signature.getInstance("SHA256withRSA");
                privateSignature.initSign(kf.generatePrivate(spec));
                privateSignature.update(imageByteFormatForSigning);
                signature = privateSignature.sign();
            } catch (GeneralSecurityException encryptException) {
                throw new GeneralSecurityException(
                        encryptException.getMessage() + encryptException.getClass());
            }
        } catch (IOException io) {
            throw new IOException(io.getMessage() + io.getClass());
        }
        return signature;
    }
}
