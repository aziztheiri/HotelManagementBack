package com.aziz.booksocialnetwork.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RoomDto {
    private Long id;
    private String name;
    private String type;
    private Long price;
    private boolean available;
    private String imageUrl;

}
