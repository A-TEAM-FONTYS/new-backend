package nl.teama.server.service;

import nl.teama.server.entity.Data;
import nl.teama.server.entity.User;
import nl.teama.server.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DataService {

    private final DataRepository dataRepository;

    @Autowired
    public DataService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<Data> getAllByUser(User user) {
        return this.dataRepository.getAllByUser(user);
    }

    public Data createOrUpdate(Data data) {
        return this.dataRepository.save(data);
    }

    public Optional<Data> findById(UUID id) {
        return this.dataRepository.findById(id);
    }

    public void deleteById(UUID id) {
        this.dataRepository.deleteById(id);
    }

    public List<Data> findAllByAppNameAndUser(String appName, User user) {
        return this.dataRepository.getAllByAppNameAndUser(appName, user);
    }
}
