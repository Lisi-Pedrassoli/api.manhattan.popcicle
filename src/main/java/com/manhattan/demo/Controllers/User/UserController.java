package com.manhattan.demo.Controllers.User;

import com.manhattan.demo.Dtos.General.CountResponseDto;
import com.manhattan.demo.Dtos.User.UserResponseDto;
import com.manhattan.demo.Dtos.User.UserUpdateDto;
import com.manhattan.demo.Services.User.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PutMapping
    public ResponseEntity<UserResponseDto> update(@RequestBody @Valid UserUpdateDto body) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.update(body));
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable String id) {
//        this.userService.delete(id);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//      }
}