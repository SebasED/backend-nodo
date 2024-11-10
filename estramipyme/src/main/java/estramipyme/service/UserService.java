package estramipyme.service;

import estramipyme.model.User;
import estramipyme.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    HashMap<String, Object> response_data;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public ResponseEntity<?> getUser(Long id) {
        response_data = new HashMap<>();
        Optional<User> optionalUser = this.userRepository.findById(id);

        if (optionalUser.isPresent()){
            return ResponseEntity.ok(optionalUser.get());
        }

        response_data.put("error", true);
        response_data.put("message", String.format("A person with the id %d does not exist", id));
        response_data.put("status", HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response_data);
    }

    @Transactional
    public ResponseEntity<?> resgisterUser(User user) {
        response_data = new HashMap<>();

        // Verify if the email already exists
        boolean emailExists = this.userRepository.existsByEmail(user.getEmail());
        if (emailExists){
            response_data.put("error", true);
            response_data.put("message", String.format("The email %s already exists", user.getEmail()));
            response_data.put("status", HttpStatus.CONFLICT.value());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response_data);
        }

        // Verify if the docNumber already exists
        boolean docNumberExists = this.userRepository.existsByDocnumber(user.getDocnumber());
        if (docNumberExists){
            response_data.put("error", true);
            response_data.put("message", String.format("The docnumber %s already exists", user.getDocnumber()));
            response_data.put("status", HttpStatus.CONFLICT.value());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response_data);
        }

        // Encrypt the password before saving
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);



        try {
            User userSaved = this.userRepository.save(user);
            URI userLocation = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(userSaved.getId())
                    .toUri();
            return ResponseEntity.created(userLocation).body(userSaved);
        } catch (Exception e) {
            response_data.put("error", true);
            response_data.put("message", "Error del servidor: " + e.getMessage());
            response_data.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response_data);
        }
    }

    public ResponseEntity<?> updateUser(User user) {
        response_data = new HashMap<>();

        // verify if id is in the request
        if (user.getId() == null) {
            response_data.put("error", true);
            response_data.put("message", "The id is mandatory for update a user");
            response_data.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response_data);
        }

        // verify if id exists in the database
        boolean idExists = this.userRepository.existsById(user.getId());
        if (!idExists) {
            response_data.put("error", true);
            response_data.put("message", String.format("The id %d does not exists", user.getId()));
            response_data.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response_data);
        }

        try {
            User userUpdated = this.userRepository.save(user);
            return ResponseEntity.ok().body(userUpdated);
        } catch (Exception e) {
            response_data.put("error", true);
            response_data.put("message", "Error del servidor: " + e.getMessage());
            response_data.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response_data);
        }

    }

    public ResponseEntity<?> deleteUser (Long id) {
        response_data = new HashMap<>();

        // Get user by id and verify if id exists in the database
        Optional<User> optionalUser = this.userRepository.findById(id);

        if (!optionalUser.isPresent()){
            response_data.put("error", true);
            response_data.put("message", String.format("The id %d does not exists",id));
            response_data.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response_data);
        }

        try {
            this.userRepository.deleteById(id);
            return ResponseEntity.ok().body(optionalUser.get());
        } catch (Exception e) {
            response_data.put("error", true);
            response_data.put("message", "Error del servidor: " + e.getMessage());
            response_data.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response_data);
        }
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return this.passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
