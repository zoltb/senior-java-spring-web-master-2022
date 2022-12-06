package hu.ponte.hr.dao;

import hu.ponte.hr.dto.ImageMetaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageMetaRepository extends JpaRepository<ImageMetaDTO, Long> {
    List<ImageMetaDTO> findAll();

    Optional<ImageMetaDTO> findById(Long aLong);

    <S extends ImageMetaDTO> S saveAndFlush(S s);
}
