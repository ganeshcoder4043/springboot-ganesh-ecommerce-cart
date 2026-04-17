package com.ganesh.ecommerce.cart.service;

import com.ganesh.ecommerce.cart.dto.UserAddressDTO;
import com.ganesh.ecommerce.cart.entity.AddressType;
import com.ganesh.ecommerce.cart.entity.User;
import com.ganesh.ecommerce.cart.entity.UserAddress;
import com.ganesh.ecommerce.cart.exception.ResourceNotFoundException;
import com.ganesh.ecommerce.cart.mapper.UserAddressMapper;
import com.ganesh.ecommerce.cart.repository.UserAddressRepository;
import com.ganesh.ecommerce.cart.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserAddressService {

    private UserAddressRepository userAddressRepository;
    private UserRepository userRepository;

    @Transactional
    public UserAddressDTO addAddress(Long userId, UserAddressDTO userAddressDTO) {
        User user = getUserEntity(userId);

        if (Boolean.TRUE.equals(userAddressDTO.getIsDefault())) {
            clearDefaultAddress(userId);
        }

        UserAddress userAddress = UserAddressMapper.toUserAddressEntity(userAddressDTO, user);
        userAddress.setIsDefault(Boolean.TRUE.equals(userAddress.getIsDefault()));

        UserAddress savedAddress = userAddressRepository.save(userAddress);
        return UserAddressMapper.toUserAddressDTO(savedAddress);
    }

    @Transactional(readOnly = true)
    public List<UserAddressDTO> getUserAddresses(Long userId) {
        getUserEntity(userId);

        return userAddressRepository.findByUserId(userId)
                .stream()
                .map(UserAddressMapper::toUserAddressDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserAddressDTO getAddressById(Long addressId) {
        return UserAddressMapper.toUserAddressDTO(getAddressEntity(addressId));
    }

    @Transactional
    public UserAddressDTO updateAddress(Long addressId, UserAddressDTO userAddressDTO) {
        UserAddress userAddress = getAddressEntity(addressId);

        if (Boolean.TRUE.equals(userAddressDTO.getIsDefault())) {
            clearDefaultAddress(userAddress.getUser().getId());
        }

        userAddress.setStreet(userAddressDTO.getStreet());
        userAddress.setCity(userAddressDTO.getCity());
        userAddress.setState(userAddressDTO.getState());
        userAddress.setZip_code(userAddressDTO.getZip_code());
        userAddress.setIsDefault(Boolean.TRUE.equals(userAddressDTO.getIsDefault()));
        userAddress.setAddressType(userAddressDTO.getAddressType());

        return UserAddressMapper.toUserAddressDTO(userAddressRepository.save(userAddress));
    }

    @Transactional
    public void deleteAddress(Long addressId) {
        UserAddress userAddress = getAddressEntity(addressId);
        userAddressRepository.delete(userAddress);
    }

    @Transactional(readOnly = true)
    public UserAddressDTO getDefaultAddress(Long userId) {
        getUserEntity(userId);

        return userAddressRepository.findByUserIdAndIsDefaultTrue(userId)
                .stream()
                .findFirst()
                .map(UserAddressMapper::toUserAddressDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Default address not found for user id: " + userId));
    }

    @Transactional(readOnly = true)
    public List<UserAddressDTO> getAddressesByType(Long userId, AddressType addressType) {
        getUserEntity(userId);

        return userAddressRepository.findByUserIdAndAddressType(userId, addressType)
                .stream()
                .map(UserAddressMapper::toUserAddressDTO)
                .toList();
    }

    private User getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    private UserAddress getAddressEntity(Long addressId) {
        return userAddressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));
    }

    private void clearDefaultAddress(Long userId) {
        userAddressRepository.findByUserIdAndIsDefaultTrue(userId)
                .forEach(address -> address.setIsDefault(false));
    }
}
