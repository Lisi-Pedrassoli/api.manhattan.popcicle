package com.manhattan.demo.Controllers.Customer;

import com.manhattan.demo.Dtos.Customer.CustomerRequestDto;
import com.manhattan.demo.Dtos.Customer.CustomerResponseDto;
import com.manhattan.demo.Dtos.Customer.CustomerUpdateDto;
import com.manhattan.demo.Dtos.General.CountResponseDto;
import com.manhattan.demo.Services.Customer.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class CustomerController {
    @Autowired
    private CustomerService service;

    @GetMapping("/count")
    public ResponseEntity<CountResponseDto> count() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.count());
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "items", defaultValue = "10") int items
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findAll(page, items));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> findById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findByIdMap(id));
    }

    @GetMapping("/active")
    public ResponseEntity<List<CustomerResponseDto>> findActive() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findActive());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> update(@PathVariable String id,
                                                    @RequestBody @Valid CustomerUpdateDto body) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.update(id, body));
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDto> save(@RequestBody @Valid CustomerRequestDto body){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(body));
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable String id) {
//        this.service.delete(id);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
}
