package kg.kyzmat.skud.repository;

import kg.kyzmat.skud.entity.AcsEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AcsEventRepository extends JpaRepository<AcsEventEntity, Long> {

    @Query("SELECT e FROM AcsEventEntity e WHERE e.deviceName IN ('Кызмат вход', 'Кызмат выход') ORDER BY e.insertTime ASC")
    List<AcsEventEntity> findAllSkudEvents();
}