package com.manhattan.demo.Controllers.ProductType;

import com.manhattan.demo.Dtos.General.CountResponseDto;
import com.manhattan.demo.Dtos.ProductType.ProductTypeRequestDto;
import com.manhattan.demo.Dtos.ProductType.ProductTypeResponseDto;
import com.manhattan.demo.Dtos.ProductType.ProductTypeUpdateDto;
import com.manhattan.demo.Services.ProductType.ProductTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipo-produto")
public class ProductTypeController {
    @Autowired
    private ProductTypeService productTypeService;

    @GetMapping
    public ResponseEntity<List<ProductTypeResponseDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "items", defaultValue = "10") int items
    ){
        return ResponseEntity.status(HttpStatus.OK).body(this.productTypeService.findAll(page, items));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductTypeResponseDto> findById(@PathVariable  String id){
        return ResponseEntity.status(HttpStatus.OK).body(this.productTypeService.findByIdMap(id));
    }

    @GetMapping("/count")
    public ResponseEntity<CountResponseDto> count(){
        return ResponseEntity.status(HttpStatus.OK).body(this.productTypeService.count());
    }

    @PostMapping
    public ResponseEntity<ProductTypeResponseDto> save(@RequestBody @Valid ProductTypeRequestDto body){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.productTypeService.save(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductTypeResponseDto> update(
            @RequestBody @Valid ProductTypeUpdateDto body,
            @PathVariable String id
    ){
        return ResponseEntity.status(HttpStatus.OK).body(this.productTypeService.update(id, body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        this.productTypeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
