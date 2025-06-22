package com.manhattan.demo.Controllers.Production;

import com.manhattan.demo.Dtos.General.CountResponseDto;
import com.manhattan.demo.Dtos.Production.ProductionDto;
import com.manhattan.demo.Entities.Production.ProductionEntity;
import com.manhattan.demo.Entities.User.UserEntity;
import com.manhattan.demo.Services.Production.ProductionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producao")
public class ProductionController {

    @Autowired
    private ProductionService service;

    @PostMapping
    public ResponseEntity<ProductionEntity> save(
            @RequestBody @Valid ProductionDto body,
            @AuthenticationPrincipal UserEntity usuario) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.service.save(body, usuario.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductionEntity> findById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductionEntity>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "items", defaultValue = "10") int items
    ){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findAll(page, items));
    }

    @GetMapping("/report")
    public ResponseEntity<List<ProductionEntity>> findReport(
            @RequestParam(value = "start") String start,
            @RequestParam(value = "end") String end
    ){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findReport(start, end));
    }

    @GetMapping("/count")
    public ResponseEntity<CountResponseDto> count(){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.count());
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<ProductionEntity> updateStatus(
            @PathVariable String id,
            @PathVariable boolean status,
            @AuthenticationPrincipal UserEntity usuario) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.service.updateStatus(id, status, usuario.getId()));
    }
}
