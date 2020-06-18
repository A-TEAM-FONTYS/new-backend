package nl.teama.server.repository;

import nl.teama.server.entity.Data;
import nl.teama.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DataRepository extends JpaRepository<Data, UUID> {
    List<Data> getAllByUser(User user);
    List<Data> getAllByAppNameAndUser(String appName, User user);
}
