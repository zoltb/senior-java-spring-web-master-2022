

package hu.ponte.hr;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

/**
 * @author zoltan
 */
@RunWith(SpringRunner.class)
@SpringBootTest()

public class SignCreatorTest {


    private SignCreatorToTest signCreatorToTest;
    private String pathOfThePrivateKeyForTest;

    private byte[] byteFormatOfTheCatImage;

    public SignCreatorTest() throws IOException {
        this.pathOfThePrivateKeyForTest = "src\\main\\resources\\config\\keys\\key.private";
        File fileCat = new File("src\\test\\resources\\images\\cat.jpg");
        this.byteFormatOfTheCatImage = Files.readAllBytes(fileCat.toPath());
        signCreatorToTest = new SignCreatorToTest();
    }

    @Test
    public void testIfSignServiceGetNotAPrivateKeyFormat() {

        //Given
        String badKey = "";
        //When
        Assert.assertThrows(IOException.class, () -> signCreatorToTest.createASHA256RSASignWithPrivateKeyToTest(badKey, byteFormatOfTheCatImage));
    }

    @Test
    public void testIfPrivateKeyValueIsTheRightValue() throws Exception {

        String signature = "XYZ+wXKNd3Hpnjxy4vIbBQVD7q7i0t0r9tzpmf1KmyZAEUvpfV8AKQlL7us66rvd6eBzFlSaq5HGVZX2DYTxX1C5fJlh3T3QkVn2zKOfPHDWWItdXkrccCHVR5HFrpGuLGk7j7XKORIIM+DwZKqymHYzehRvDpqCGgZ2L1Q6C6wjuV4drdOTHps63XW6RHNsU18wHydqetJT6ovh0a8Zul9yvAyZeE4HW7cPOkFCgll5EZYZz2iH5Sw1NBNhDNwN2KOxrM4BXNUkz9TMeekjqdOyyWvCqVmr5EgssJe7FAwcYEzznZV96LDkiYQdnBTO8jjN25wlnINvPrgx9dN/Xg==";
        //When
        byte[] testSignatureInByteArray = signCreatorToTest.createASHA256RSASignWithPrivateKeyToTest(pathOfThePrivateKeyForTest, byteFormatOfTheCatImage);
        //Then

        Assert.assertTrue(Base64.getEncoder().encodeToString(testSignatureInByteArray).equals(signature));

    }


}
