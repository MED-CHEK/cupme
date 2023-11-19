package com.cupme.service;

import com.cupme.repository.ProtocolReviewRepository;
import com.cupme.service.dto.ProtocolReviewDTO;
import com.cupme.service.mapper.ProtocolReviewMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing protocol reviews.
 */
@Service
@Transactional
public class ProtocolReviewService {

    private final Logger log = LoggerFactory.getLogger(ProtocolReviewService.class);

    private final ProtocolReviewRepository protocolReviewRepository;

    private final ProtocolReviewMapper protocolReviewMapper;
    private final CacheManager cacheManager;

    public ProtocolReviewService(
        ProtocolReviewRepository protocolReviewRepository,
        ProtocolReviewMapper protocolReviewMapper,
        CacheManager cacheManager
    ) {
        this.protocolReviewRepository = protocolReviewRepository;
        this.protocolReviewMapper = protocolReviewMapper;
        this.cacheManager = cacheManager;
    }

    public List<ProtocolReviewDTO> getProtocolReviews() {
        return protocolReviewMapper.protocolReviewsToProtocolReviewDTOs(protocolReviewRepository.findAll());
    }

    public ProtocolReviewDTO getProtocolReview(long id) {
        return protocolReviewMapper.protocolReviewToProtocolReviewDTO(protocolReviewRepository.findById(id).get());
    }

    public ProtocolReviewDTO createProtocolReview(ProtocolReviewDTO protocolReviewDTO) {
        return protocolReviewMapper.protocolReviewToProtocolReviewDTO(
            protocolReviewRepository.save(protocolReviewMapper.protocolReviewDTOToProtocolReview(protocolReviewDTO))
        );
    }

    public ProtocolReviewDTO updateProtocolReview(ProtocolReviewDTO protocolReviewDTO) {
        return protocolReviewMapper.protocolReviewToProtocolReviewDTO(
            protocolReviewRepository.save(protocolReviewMapper.protocolReviewDTOToProtocolReview(protocolReviewDTO))
        );
    }

    public void deleteProtocolReview(long id) {
        protocolReviewRepository.deleteById(id);
    }
}
