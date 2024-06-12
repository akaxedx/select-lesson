package anda.selectlesson.repo;

import anda.selectlesson.model.po.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {
    List<Room> getRoomsByBuilding(String building);
    List<Room> getRoomsByUsedIsNull();
}
