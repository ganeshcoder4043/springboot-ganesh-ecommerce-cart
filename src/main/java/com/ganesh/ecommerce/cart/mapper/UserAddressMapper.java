package com.ganesh.ecommerce.cart.mapper;

import com.ganesh.ecommerce.cart.dto.UserAddressDTO;
import com.ganesh.ecommerce.cart.entity.User;
import com.ganesh.ecommerce.cart.entity.UserAddress;

public class UserAddressMapper {

                      /* entity to DTO */
    public static UserAddressDTO toUserAddressDTO(UserAddress userAddress){
        if (userAddress == null) return null;

        UserAddressDTO userAddressDTO = new UserAddressDTO();
        userAddressDTO.setId(userAddress.getId());
        userAddressDTO.setStreet(userAddress.getStreet());
        userAddressDTO.setCity(userAddress.getCity());
        userAddressDTO.setState(userAddress.getState());
        userAddressDTO.setZip_code(userAddress.getZip_code());
        userAddressDTO.setIsDefault(userAddress.getIsDefault());
        userAddressDTO.setAddressType(userAddress.getAddressType());
        return userAddressDTO;
    }

                    /* DTO to Entity*/

      public static UserAddress toUserAddressEntity(UserAddressDTO userAddressDTO, User user){
          if (userAddressDTO == null) return null;

          UserAddress userAddress = new UserAddress();
          userAddress.setId(userAddressDTO.getId());
          userAddress.setStreet(userAddressDTO.getStreet());
          userAddress.setCity(userAddressDTO.getCity());
          userAddress.setState(userAddressDTO.getState());
          userAddress.setZip_code(userAddressDTO.getZip_code());
          userAddress.setIsDefault(userAddressDTO.getIsDefault());
          userAddress.setAddressType(userAddressDTO.getAddressType());
          userAddress.setUser(user);
             return userAddress;
      }
}
