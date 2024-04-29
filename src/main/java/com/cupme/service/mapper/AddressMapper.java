package com.cupme.service.mapper;

import com.cupme.domain.Address;
import com.cupme.service.dto.AddressDTO;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link Address} and its DTO called {@link Address}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class AddressMapper {

    public Set<AddressDTO> addressesToAddressDTOs(Set<Address> addresses) {
        return addresses.stream().filter(Objects::nonNull).map(this::addressToAddressDTO).collect(Collectors.toSet());
    }

    public AddressDTO addressToAddressDTO(Address address) {
        return new AddressDTO(address);
    }

    public Set<Address> addressDTOsToAddresses(Set<AddressDTO> addressDTOS) {
        return addressDTOS.stream().filter(Objects::nonNull).map(this::addressDTOToAddress).collect(Collectors.toSet());
    }

    public Address addressDTOToAddress(AddressDTO addressDTO) {
        if (addressDTO == null) {
            return null;
        } else {
            Address address = new Address();
            address.setId(addressDTO.getId());
            address.setAddress(addressDTO.getAddress());
            address.setCity(addressDTO.getCity());
            address.setCountry(addressDTO.getCountry());
            address.setPostalCode(addressDTO.getPostalCode());
            address.setType(addressDTO.getType());
            address.setUser(addressDTO.getUser());

            return address;
        }
    }
}
