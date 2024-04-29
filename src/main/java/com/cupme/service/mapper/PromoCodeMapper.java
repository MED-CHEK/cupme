package com.cupme.service.mapper;

import com.cupme.domain.PromoCode;
import com.cupme.service.dto.PromoCodeDTO;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link PromoCode} and its DTO called {@link PromoCode}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class PromoCodeMapper {

    public List<PromoCodeDTO> promoCodesToPromoCodeDTOs(List<PromoCode> promoCodes) {
        return promoCodes.stream().filter(Objects::nonNull).map(this::promoCodeToPromoCodeDTO).collect(Collectors.toList());
    }

    public PromoCodeDTO promoCodeToPromoCodeDTO(PromoCode promoCode) {
        return new PromoCodeDTO(promoCode);
    }

    public List<PromoCode> promoCodeDTOsToPromoCodes(List<PromoCodeDTO> promoCodeDTOS) {
        return promoCodeDTOS.stream().filter(Objects::nonNull).map(this::promoCodeDTOToPromoCode).collect(Collectors.toList());
    }

    public PromoCode promoCodeDTOToPromoCode(PromoCodeDTO promoCodeDTO) {
        if (promoCodeDTO == null) {
            return null;
        } else {
            PromoCode promoCode = new PromoCode();
            promoCode.setId(promoCodeDTO.getId());
            promoCode.setCode(promoCodeDTO.getCode());
            promoCode.setDiscount(promoCodeDTO.getDiscount());
            promoCode.setCreatedDate(promoCodeDTO.getCreatedDate());
            promoCode.setActivated(promoCodeDTO.isActivated());

            return promoCode;
        }
    }
}
