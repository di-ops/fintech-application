package ru.example.fintech.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.example.fintech.dto.CustomerDto;
import ru.example.fintech.service.CustomerService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public List<CustomerDto> getAll(){
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public CustomerDto getById(@PathVariable("id") int id){
        return customerService.findById(id);
    }

    @PostMapping
    public void create (@RequestBody CustomerDto customerDto){
        customerService.create(customerDto);
    }

    @PatchMapping("/{id}")
    public void update (@PathVariable("id") int id, @RequestBody CustomerDto customerDto){
        customerService.update(id, customerDto);
    }

    @DeleteMapping("/{id}")
    public void delete (@PathVariable("id") int id){
        customerService.delete(id);
    }
}
