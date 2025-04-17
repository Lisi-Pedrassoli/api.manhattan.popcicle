package com.manhattan.demo.Controllers.Product;

import com.manhattan.demo.Dtos.General.CountResponseDto;
import com.manhattan.demo.Dtos.Product.ProductRequestDto;
import com.manhattan.demo.Dtos.Product.ProductResponseDto;
import com.manhattan.demo.Dtos.Product.ProductUpdateDto;
import com.manhattan.demo.Services.Product.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProductController {
    @Autowired
    private ProductService service;

    @PostMapping
    public ResponseEntity<ProductResponseDto> save(@RequestBody @Valid ProductRequestDto body){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(body));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "items", defaultValue = "10") int items){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findAll(page, items));
    }

    @GetMapping("/no-recipe")
    public ResponseEntity<List<ProductResponseDto>> findAllWithoutRecipe(){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findAllWithoutRecipe());
    }

    @GetMapping("/count")
    public ResponseEntity<CountResponseDto> count(){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.count());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findByIdMap(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(
            @RequestBody @Valid ProductUpdateDto body,
            @PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.update(body, id));
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable String id){
//        this.service.delete(id);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }

    @GetMapping("/report")
    public ResponseEntity<List<ProductResponseDto>> findReport(
            @RequestParam(value = "start") int start,
            @RequestParam(value = "end") int end
    ){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findReport(start, end));
    }
}
