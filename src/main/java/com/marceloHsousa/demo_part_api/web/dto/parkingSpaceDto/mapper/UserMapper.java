package com.marceloHsousa.demo_part_api.web.dto.parkingSpaceDto.mapper;

import com.marceloHsousa.demo_part_api.entities.User;
import com.marceloHsousa.demo_part_api.web.dto.userDTO.UserDto;
import com.marceloHsousa.demo_part_api.web.dto.userDTO.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<UserResponseDto> toListDto(List <User> users){

        return users.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }
}
