package com.cupme.service;

import com.cupme.repository.AddressRepository;
import com.cupme.service.dto.AddressDTO;
import com.cupme.service.mapper.AddressMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing addresses.
 */
@Service
@Transactional
public class AddressService {

    private final Logger log = LoggerFactory.getLogger(AddressService.class);

    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    private final CacheManager cacheManager;

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper, CacheManager cacheManager) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.cacheManager = cacheManager;
    }

    public List<AddressDTO> getAddresses() {
        return addressMapper.addressesToAddressDTOs(addressRepository.findAll());
    }

    public AddressDTO getAddress(long id) {
        return addressMapper.addressToAddressDTO(addressRepository.findById(id).get());
    }

    public AddressDTO createAddress(AddressDTO addressDTO) {
        return addressMapper.addressToAddressDTO(addressRepository.save(addressMapper.addressDTOToAddress(addressDTO)));
    }

    public AddressDTO updateAddress(AddressDTO addressDTO) {
        return addressMapper.addressToAddressDTO(addressRepository.save(addressMapper.addressDTOToAddress(addressDTO)));
    }

    public void deleteAddress(long id) {
        addressRepository.deleteById(id);
    }
}
