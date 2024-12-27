package com.aziz.booksocialnetwork.dto;

import com.aziz.booksocialnetwork.entities.Room;
import lombok.Data;

import java.util.List;

@Data
public class RoomResponseDto {
    private List<RoomDto> roomDtoList;
    private Integer totalPages;
    private Integer pageNumber;
}
