package com.cupme.service;

import com.cupme.domain.Address;
import com.cupme.domain.User;
import com.cupme.repository.AddressRepository;
import com.cupme.service.dto.AddressDTO;
import com.cupme.service.mapper.AddressMapper;
import java.util.Set;
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
    private final UserService userService;

    public AddressService(
        AddressRepository addressRepository,
        AddressMapper addressMapper,
        CacheManager cacheManager,
        UserService userService
    ) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.cacheManager = cacheManager;
        this.userService = userService;
    }

    public Set<AddressDTO> getAddresses() {
        return addressMapper.addressesToAddressDTOs((Set<Address>) addressRepository.findAll());
    }

    public Set<AddressDTO> getUserAddresses(long id) {
        return addressMapper.addressesToAddressDTOs(addressRepository.findByUserId(id));
    }

    public AddressDTO getAddress(long id) {
        return addressMapper.addressToAddressDTO(addressRepository.findById(id).get());
    }

    public AddressDTO createAddress(AddressDTO addressDTO) {
        User user = userService.getUserWithAuthorities().get();
        addressDTO.setUser(user);
        return addressMapper.addressToAddressDTO(addressRepository.save(addressMapper.addressDTOToAddress(addressDTO)));
    }

    public AddressDTO updateAddress(AddressDTO addressDTO) {
        User user = userService.getUserWithAuthorities().get();
        addressDTO.setUser(user);
        return addressMapper.addressToAddressDTO(addressRepository.save(addressMapper.addressDTOToAddress(addressDTO)));
    }

    public void deleteAddress(long id) {
        addressRepository.deleteById(id);
    }
}
