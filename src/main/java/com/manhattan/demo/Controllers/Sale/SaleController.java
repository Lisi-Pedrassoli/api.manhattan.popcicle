package com.manhattan.demo.Controllers.Sale;

import com.manhattan.demo.Dtos.General.CountResponseDto;
import com.manhattan.demo.Dtos.Sale.CloseSaleDto;
import com.manhattan.demo.Dtos.Sale.SaleRequestDto;
import com.manhattan.demo.Entities.Sale.SaleEntity;
import com.manhattan.demo.Entities.User.UserEntity;
import com.manhattan.demo.Services.Log.LogService;
import com.manhattan.demo.Services.Sale.SaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venda")
public class SaleController {

    @Autowired
    private SaleService service;
    @Autowired
    private LogService logService;


    @GetMapping("/count")
    public ResponseEntity<CountResponseDto> count() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.count());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleEntity> findById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<SaleEntity>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "items", defaultValue = "10") int items){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findAll(page, items));
    }

    @PostMapping
    public ResponseEntity<SaleEntity> save(@RequestBody @Valid SaleRequestDto body,
                                           @AuthenticationPrincipal UserEntity usuario){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(body, usuario.getId()));
    }

    @PostMapping("/{id}")
    public ResponseEntity<SaleEntity> close(@PathVariable String id, @RequestBody @Valid CloseSaleDto body){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.close(id, body));
    }

    @GetMapping("/report")
    public ResponseEntity<List<SaleEntity>> findReport(
            @RequestParam(value = "start") Long start,
            @RequestParam(value = "end") Long end
    ){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getReport(start, end));
    }
}
