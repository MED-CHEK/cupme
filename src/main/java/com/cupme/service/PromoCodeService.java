package com.cupme.service;

import com.cupme.domain.PromoCode;
import com.cupme.repository.PromoCodeRepository;
import com.cupme.service.dto.PromoCodeDTO;
import com.cupme.service.mapper.PromoCodeMapper;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing promoCodes.
 */
@Service
@Transactional
public class PromoCodeService {

    private final Logger log = LoggerFactory.getLogger(PromoCodeService.class);

    private final PromoCodeRepository promoCodeRepository;

    private final PromoCodeMapper promoCodeMapper;
    private final CacheManager cacheManager;

    public PromoCodeService(PromoCodeRepository promoCodeRepository, PromoCodeMapper promoCodeMapper, CacheManager cacheManager) {
        this.promoCodeRepository = promoCodeRepository;
        this.promoCodeMapper = promoCodeMapper;
        this.cacheManager = cacheManager;
    }

    public Page<PromoCodeDTO> getPromoCodes(Pageable pageable) {
        return promoCodeRepository.findAll(pageable).map(PromoCodeDTO::new);
    }

    public PromoCodeDTO getPromoCode(long id) {
        return promoCodeMapper.promoCodeToPromoCodeDTO(promoCodeRepository.findById(id).get());
    }

    public PromoCodeDTO getPromoCodeByCode(String code) {
        return promoCodeMapper.promoCodeToPromoCodeDTO(promoCodeRepository.findByCode(code));
    }

    @Transactional
    public PromoCodeDTO createPromoCode(PromoCodeDTO promoCodeDTO) {
        PromoCode promoCode = promoCodeMapper.promoCodeDTOToPromoCode(promoCodeDTO);
        promoCode.setCreatedDate(Instant.now());
        if (promoCodeRepository.countAllByCode(promoCode.getCode()) > 0) {
            return null;
        }

        return promoCodeMapper.promoCodeToPromoCodeDTO(promoCodeRepository.save(promoCode));
    }

    public PromoCodeDTO updatePromoCode(PromoCodeDTO promoCodeDTO) {
        return promoCodeMapper.promoCodeToPromoCodeDTO(promoCodeRepository.save(promoCodeMapper.promoCodeDTOToPromoCode(promoCodeDTO)));
    }

    public void deletePromoCode(long id) {
        promoCodeRepository.deleteById(id);
    }
}
