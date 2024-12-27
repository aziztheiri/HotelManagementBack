package com.aziz.booksocialnetwork.repositories;

import com.aziz.booksocialnetwork.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {
}
