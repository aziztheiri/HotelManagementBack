package com.aziz.booksocialnetwork.restControllers.admin;

import com.aziz.booksocialnetwork.dto.RoomDto;
import com.aziz.booksocialnetwork.services.admin.rooms.RoomsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class RoomRestController {
    private final RoomsService roomsService;
    @PostMapping("/room")
    public ResponseEntity<?> postRoom(@RequestParam("roomDto") String roomDtoJson,
                                      @RequestParam("image") MultipartFile image) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            RoomDto roomDto = mapper.readValue(roomDtoJson, RoomDto.class);
            boolean success = roomsService.postRoom(roomDto, image);
            if (success) {
                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @GetMapping("/rooms/{pageNumber}")
    public ResponseEntity<?> getAllRooms(@PathVariable int pageNumber){
return ResponseEntity.ok(roomsService.getAllRooms(pageNumber));
    }
    @GetMapping("/room/{id}")
    public  ResponseEntity<?> getRoomById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(roomsService.getRoomById(id));
        }catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }catch (Exception e){
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }
    @PutMapping("/room/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable Long id,
                                        @RequestParam("roomDto") String roomDtoJson,
                                        @RequestParam("image") MultipartFile image) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            RoomDto roomDto = mapper.readValue(roomDtoJson, RoomDto.class);
            boolean success = roomsService.updateRoom(id, roomDto, image);
            if (success) {
                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
