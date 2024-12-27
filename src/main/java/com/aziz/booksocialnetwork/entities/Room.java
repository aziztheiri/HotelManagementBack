package com.aziz.booksocialnetwork.entities;

import com.aziz.booksocialnetwork.dto.RoomDto;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private Long price;
    private boolean available;
    private String imageUrl;

    public RoomDto getRoomDto(){
        RoomDto roomDto = new RoomDto();
        roomDto.setId(id);
        roomDto.setName(name);
        roomDto.setType(type);
        roomDto.setPrice(price);
        roomDto.setAvailable(available);
        roomDto.setImageUrl(imageUrl);
        return  roomDto;
    }

}
