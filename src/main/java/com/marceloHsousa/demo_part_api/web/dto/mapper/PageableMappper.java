package com.marceloHsousa.demo_part_api.web.dto.mapper;

import com.marceloHsousa.demo_part_api.web.dto.PageableDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

public class PageableMappper {

    public static PageableDto toDto(Page page){
        return new ModelMapper().map(page, PageableDto.class);
    }
}
