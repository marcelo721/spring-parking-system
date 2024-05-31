package com.marceloHsousa.demo_part_api.web.dto.mapper;

import com.marceloHsousa.demo_part_api.entities.User;
import com.marceloHsousa.demo_part_api.web.dto.UserDto;
import com.marceloHsousa.demo_part_api.web.dto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class UserMapper {

    public static User toUser(UserDto userDto){
        return  new ModelMapper().map(userDto, User.class);
    }

    public static UserResponseDto toDto(User user){

        String role = user.getRole().name().substring("ROLE_".length());
        PropertyMap<User, UserResponseDto> props = new PropertyMap<User, UserResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(user, UserResponseDto.class);
    }
}
