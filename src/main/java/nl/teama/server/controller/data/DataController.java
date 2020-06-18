package nl.teama.server.controller.data;

import nl.teama.server.controller.auth.RegisterController;
import nl.teama.server.controller.enums.AuthResponse;
import nl.teama.server.controller.enums.DataResponse;
import nl.teama.server.entity.Data;
import nl.teama.server.entity.User;
import nl.teama.server.models.DataDTO;
import nl.teama.server.service.DataService;
import nl.teama.server.service.UserService;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/data")
public class DataController {
    private Logger logger = LoggerFactory.getLogger(DataController.class);

    private final DataService dataService;
    private final UserService userService;

    @Autowired
    public DataController(DataService dataService, UserService userService) {
        this.dataService = dataService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity insertData(@Valid @RequestBody DataDTO dto, @AuthenticationPrincipal User user) {
        try {
            Optional<User> foundUser = this.userService.findUserByEmail(user.getEmail());

            if(!foundUser.isPresent()) {
                throw new IllegalArgumentException();
            }

            Data data = new Data();
            data.setAppName(dto.getAppName());
            data.setTimeUsed(dto.getTimeUsed());
            data.setUser(user);

            Data createdData = this.dataService.createOrUpdate(data);

            if(createdData == null) {
                throw new IllegalArgumentException();
            }

            return ResponseEntity.ok(createdData);

        } catch(IllegalArgumentException ex) {
            logger.error("ERROR: insert data %s", ex);
            return new ResponseEntity<>(DataResponse.UNEXPECTED_ERROR.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{name}/app")
    public ResponseEntity getDataByAppName(@PathVariable String name, @AuthenticationPrincipal User user) {
        List<Data> dataList = this.dataService.findAllByAppNameAndUser(name, user);

        if(dataList.isEmpty()) {
            return new ResponseEntity<>(DataResponse.UNEXPECTED_ERROR.toString(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(dataList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getData(@PathVariable String id) {
        Optional<Data> data = this.dataService.findById(UUID.fromString(id));

        if(!data.isPresent()) {
            return new ResponseEntity<>(DataResponse.UNEXPECTED_ERROR.toString(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(data.get());
    }

    @GetMapping
    public ResponseEntity getAllData(@AuthenticationPrincipal User user) {
        List<Data> allData = this.dataService.getAllByUser(user);

        if(allData.isEmpty()) {
            return new ResponseEntity<>(DataResponse.UNEXPECTED_ERROR.toString(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(allData);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteData(@PathVariable String id) {
        try {
            Optional<Data> data = this.dataService.findById(UUID.fromString(id));

            if(!data.isPresent()) {
                throw new IllegalArgumentException();
            }

            this.dataService.deleteById(data.get().getId());

            return ResponseEntity.ok("Successfully deleted");

        } catch (IllegalArgumentException ex) {
            logger.error("ERROR: delete data %s", ex);
            return new ResponseEntity<>(DataResponse.UNEXPECTED_ERROR.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
