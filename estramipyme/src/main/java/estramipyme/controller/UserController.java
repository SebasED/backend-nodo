package estramipyme.controller;

import estramipyme.dto.LoginRequestDto;
import estramipyme.dto.LoginResponseDto;
import estramipyme.dto.UserResponseDto;
import estramipyme.model.User;
import estramipyme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping
    public ResponseEntity<?> resgisterUser(@Validated @RequestBody User user) {
        return this.userService.resgisterUser(user);
    }

    @PutMapping
    public ResponseEntity<?> updatedUser(@Validated @RequestBody User user) {
        return this.userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser (@PathVariable Long id ) {
        return this.userService.deleteUser(id);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto login = this.userService.login(loginRequestDto);
        if (!login.isStatus()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(login);
        }
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }
}
