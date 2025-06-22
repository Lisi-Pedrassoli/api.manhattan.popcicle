package com.manhattan.demo.Controllers.ProductType;

import com.manhattan.demo.Dtos.General.CountResponseDto;
import com.manhattan.demo.Dtos.ProductType.ProductTypeRequestDto;
import com.manhattan.demo.Dtos.ProductType.ProductTypeResponseDto;
import com.manhattan.demo.Dtos.ProductType.ProductTypeUpdateDto;
import com.manhattan.demo.Entities.User.UserEntity;
import com.manhattan.demo.Services.Log.LogService;
import com.manhattan.demo.Services.ProductType.ProductTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipo-produto")
public class ProductTypeController {
    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private LogService logService;

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
    public ResponseEntity<ProductTypeResponseDto> save(@RequestBody @Valid ProductTypeRequestDto body,
                                                       @AuthenticationPrincipal UserEntity usuario){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.productTypeService.save(body, usuario.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductTypeResponseDto> update(
            @RequestBody @Valid ProductTypeUpdateDto body,
            @PathVariable String id,
            @AuthenticationPrincipal UserEntity usuario
    ){
        return ResponseEntity.status(HttpStatus.OK).body(this.productTypeService.update(id, body, usuario.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable String id,
            @AuthenticationPrincipal UserEntity usuario
    ) {
        this.productTypeService.delete(id, usuario.getId());
        logService.registrar(usuario.getId(), "Exclusão de tipo de produto", "ID do tipo de produto: " + id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
