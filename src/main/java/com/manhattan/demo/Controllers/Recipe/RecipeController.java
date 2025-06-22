package com.manhattan.demo.Controllers.Recipe;

import com.manhattan.demo.Dtos.General.CountResponseDto;
import com.manhattan.demo.Dtos.Recipe.RecipeRequestDto;
import com.manhattan.demo.Entities.Recipe.RecipeEntity;
import com.manhattan.demo.Entities.User.UserEntity;
import com.manhattan.demo.Services.Recipe.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receita")
public class RecipeController {
    @Autowired
    private RecipeService service;

    @PostMapping
    public ResponseEntity<RecipeEntity> save(
            @RequestBody @Valid RecipeRequestDto body,
            @AuthenticationPrincipal UserEntity usuario
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.service.save(body, usuario.getId()));
    }

    @GetMapping
    public ResponseEntity<List<RecipeEntity>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "items", defaultValue = "10") int items
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findAll(page, items));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeEntity> update(
            @RequestBody @Valid RecipeRequestDto body,
            @PathVariable String id,
            @AuthenticationPrincipal UserEntity usuario
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.service.update(id, body, usuario.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeEntity> findById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findById(id));
    }

    @GetMapping("/count")
    public ResponseEntity<CountResponseDto> count() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.count());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable String id,
            @AuthenticationPrincipal UserEntity usuario
    ) {
        this.service.delete(id, usuario.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}