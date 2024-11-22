package estramipyme.controller;

import estramipyme.dto.LoginRequestDto;
import estramipyme.dto.LoginResponseDto;
import estramipyme.dto.UserResponseDto;
import estramipyme.model.Users;
import estramipyme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public  ResponseEntity<List<UserResponseDto>> getUsers() {
        return ResponseEntity.ok(this.userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return this.userService.getUser(id);
    }

    @PostMapping("/register")
    public ResponseEntity<?> resgisterUser(@Validated @RequestBody Users user) {
        return this.userService.resgisterUser(user);
    }

    @PutMapping
    public ResponseEntity<?> updatedUser(@Validated @RequestBody Users user) {
        return this.userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser (@PathVariable Long id ) {
        return this.userService.deleteUser(id);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.ok("Login successful");
    }
}
