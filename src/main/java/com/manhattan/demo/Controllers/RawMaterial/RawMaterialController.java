package com.manhattan.demo.Controllers.RawMaterial;

import com.manhattan.demo.Dtos.General.CountResponseDto;
import com.manhattan.demo.Dtos.RawMaterial.RawMaterialRequestDto;
import com.manhattan.demo.Dtos.RawMaterial.RawMaterialResponseDto;
import com.manhattan.demo.Dtos.RawMaterial.RawMaterialUpdateDto;
import com.manhattan.demo.Services.RawMaterial.RawMaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materia-prima")
public class RawMaterialController {
    @Autowired
    private RawMaterialService service;

    @GetMapping("/count")
    public ResponseEntity<CountResponseDto> count(){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.count());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RawMaterialResponseDto> findById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findByIdMap(id));
    }

    @GetMapping
    public ResponseEntity<List<RawMaterialResponseDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "items", defaultValue = "10") int items){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findAll(page, items));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RawMaterialResponseDto> update(
            @RequestBody @Valid RawMaterialUpdateDto body,
            @PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.update(body, id));
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable String id){
//        this.service.delete(id);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }

    @PostMapping
    public ResponseEntity<RawMaterialResponseDto> save(@RequestBody @Valid RawMaterialRequestDto body){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(body));
    }

    @GetMapping("/report")
    public ResponseEntity<List<RawMaterialResponseDto>> findReport(
            @RequestParam(value = "start") int start,
            @RequestParam(value = "end") int end){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findReport(start, end));
    }
}