package com.aziz.booksocialnetwork.services.admin.rooms;

import com.aziz.booksocialnetwork.dto.RoomDto;
import com.aziz.booksocialnetwork.dto.RoomResponseDto;
import com.aziz.booksocialnetwork.entities.Room;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RoomsService {
    public boolean postRoom(RoomDto roomDto, MultipartFile image);
    public RoomResponseDto getAllRooms(int pageNumber);
    public RoomDto getRoomById(Long id);
    public boolean updateRoom(Long id, RoomDto roomDto, MultipartFile image) throws IOException;
    public void deleteRoom(Long id);
    public RoomResponseDto getAvailableRooms(int pageNumber);

}
