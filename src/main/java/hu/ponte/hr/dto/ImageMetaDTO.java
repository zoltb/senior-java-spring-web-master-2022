package hu.ponte.hr.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author zoltan
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ImageMetaDTO {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String mimeType;
    private long size;

    @Lob
    private String digitalSign;

    @Lob
    private byte[] byteFormat;

    public void setDigitalSign(String digitalSign) {
        this.digitalSign = digitalSign;
    }

    //If later we would like to prevent the upload of the same image
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageMetaDTO)) return false;
        ImageMetaDTO that = (ImageMetaDTO) o;
        return size == that.size &&
                Arrays.equals(byteFormat, that.byteFormat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, byteFormat);
    }

}
