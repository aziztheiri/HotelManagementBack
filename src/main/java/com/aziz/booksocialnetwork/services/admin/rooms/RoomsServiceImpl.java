package com.aziz.booksocialnetwork.services.admin.rooms;

import com.aziz.booksocialnetwork.dto.RoomDto;
import com.aziz.booksocialnetwork.dto.RoomResponseDto;
import com.aziz.booksocialnetwork.entities.Room;
import com.aziz.booksocialnetwork.repositories.RoomRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomsServiceImpl implements  RoomsService{

    private final RoomRepository roomRepository;
    private Cloudinary getCloudinaryInstance() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dmwttu9lu",
                "api_key", "974645429429234",
                "api_secret", "XIhfcEzguJ_ZcZ1RDaD9am8r4bU"
        ));
    }

    public boolean postRoom(RoomDto roomDto, MultipartFile image) {
        try {
            Room room = new Room();
            room.setName(roomDto.getName());
            room.setPrice(roomDto.getPrice());
            room.setType(roomDto.getType());
            room.setAvailable(true);

            // Upload image if present
            if (image != null && !image.isEmpty()) {
                String imageUrl = uploadImageToCloud(image);
                room.setImageUrl(imageUrl);
            }

            roomRepository.save(room);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private String uploadImageToCloud(MultipartFile image) throws IOException {
        Cloudinary cloudinary = getCloudinaryInstance();
        Map<String, Object> uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();
    }
    public RoomResponseDto getAllRooms(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber,1);
        Page<Room> roomPage= roomRepository.findAll(pageable);
        RoomResponseDto roomResponseDto =new RoomResponseDto();
        roomResponseDto.setPageNumber(roomPage.getPageable().getPageNumber());
        roomResponseDto.setTotalPages(roomPage.getTotalPages());
        roomResponseDto.setRoomDtoList(roomPage.stream().map(Room::getRoomDto).collect(Collectors.toList()));
        return roomResponseDto;
    }
public RoomDto getRoomById(Long id){
    Optional<Room>  optionalRoom = roomRepository.findById(id);
    if(optionalRoom.isPresent()){
        return optionalRoom.get().getRoomDto();
    }else{
        throw new EntityNotFoundException("Romm not present.");
    }
}
    public boolean updateRoom(Long id, RoomDto roomDto, MultipartFile image) throws IOException {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            Room existingRoom = optionalRoom.get();
            existingRoom.setName(roomDto.getName());
            existingRoom.setPrice(roomDto.getPrice());
            existingRoom.setType(roomDto.getType());

            // If image is provided, upload it and set the URL
            if (image != null && !image.isEmpty()) {
                String imageUrl = uploadImageToCloud(image); // Assuming you have this method to upload images
                existingRoom.setImageUrl(imageUrl);
            } else {
                // If no image is provided, keep the existing image URL
                existingRoom.setImageUrl(existingRoom.getImageUrl());
            }

            roomRepository.save(existingRoom);
            return true;
        }
        return false;
    }

}
