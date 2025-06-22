package com.manhattan.demo.Controllers.Seller;

import com.manhattan.demo.Dtos.General.CountResponseDto;
import com.manhattan.demo.Dtos.Seller.SellerRequestDto;
import com.manhattan.demo.Dtos.Seller.SellerResponseDto;
import com.manhattan.demo.Dtos.Seller.SellerUpdateDto;
import com.manhattan.demo.Entities.User.UserEntity;
import com.manhattan.demo.Services.Log.LogService;
import com.manhattan.demo.Services.Seller.SellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendedor")
public class SellerController {
    @Autowired
    private SellerService service;
    @Autowired
    private LogService logService;


    @GetMapping("/count")
    public ResponseEntity<CountResponseDto> count() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.count());
    }

    @GetMapping
    public ResponseEntity<List<SellerResponseDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "items", defaultValue = "10") int items
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findAll(page, items));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerResponseDto> findById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findByIdMap(id));
    }

    @GetMapping("/active")
    public ResponseEntity<List<SellerResponseDto>> findActive() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findActive());
    }

    @GetMapping("/report/{status}")
    public ResponseEntity<List<SellerResponseDto>> findActive(@PathVariable Boolean status) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getReport(status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SellerResponseDto> update(@PathVariable String id,
                                                    @RequestBody @Valid SellerUpdateDto body,
                                                    @AuthenticationPrincipal UserEntity usuario) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.update(id, body, usuario.getId()));
    }

    @PostMapping
    public ResponseEntity<SellerResponseDto> save(@RequestBody @Valid SellerRequestDto body,
                                                  @AuthenticationPrincipal UserEntity usuario){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(body, usuario.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable String id,
            @AuthenticationPrincipal UserEntity usuario
    ) {
        this.service.delete(id, usuario.getId());
        logService.registrar(usuario.getId(), "Exclusão de vendedor", "ID do vendedor: " + id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
