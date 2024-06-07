package anda.selectlesson.repo;

import anda.selectlesson.model.po.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {
    Room getByBuilding(String building);
}
