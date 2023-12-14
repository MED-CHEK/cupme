package com.cupme.service;

import com.cupme.repository.PictureRepository;
import com.cupme.service.dto.PictureDTO;
import com.cupme.service.mapper.PictureMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PictureService {

    private final PictureRepository pictureRepository;

    private final PictureMapper pictureMapper;

    public PictureService(PictureRepository pictureRepository, PictureMapper pictureMapper) {
        this.pictureRepository = pictureRepository;
        this.pictureMapper = pictureMapper;
    }

    public List<PictureDTO> findAll() {
        return pictureRepository.findAll().stream().map(PictureDTO::new).collect(Collectors.toList());
    }

    public Long save(PictureDTO pictureDTO) {
        return pictureRepository.save(pictureMapper.pictureDtoToPicture(pictureDTO)).getId();
    }
}
