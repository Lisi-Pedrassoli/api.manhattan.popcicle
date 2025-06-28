package com.manhattan.demo.Controllers.User;

import com.manhattan.demo.Dtos.General.CountResponseDto;
import com.manhattan.demo.Dtos.User.UserResponseDto;
import com.manhattan.demo.Dtos.User.UserUpdateDto;
import com.manhattan.demo.Entities.User.UserEntity;
import com.manhattan.demo.Services.User.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "items", defaultValue = "10") int items
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.findAll(page, items));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.findByIdMap(id));
    }

    @GetMapping("/count")
    public ResponseEntity<CountResponseDto> count() {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.count());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(
            @PathVariable String id,
            @RequestBody @Valid UserUpdateDto body,
            @AuthenticationPrincipal UserEntity usuario
    ) {
        return ResponseEntity.ok(this.userService.update(id, body, usuario.getId()));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id,
                                       @AuthenticationPrincipal UserEntity usuario) {
        this.userService.delete(id, usuario.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
