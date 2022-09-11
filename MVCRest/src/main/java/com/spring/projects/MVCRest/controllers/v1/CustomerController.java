package com.spring.projects.MVCRest.controllers.v1;

import com.spring.projects.MVCRest.api.v1.model.CustomerDTO;
import com.spring.projects.MVCRest.api.v1.model.CustomerListDTO;
import com.spring.projects.MVCRest.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers";
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private CustomerListDTO getAllCustomers(){
        return new CustomerListDTO(customerService.getAllCustomer());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomerByID(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO){
        return customerService.createNewCustomer(customerDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable Long id,
                                                      @RequestBody CustomerDTO customerDTO){
        return customerService.saveCustomerByDTO(id,customerDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO patchCustomer(@PathVariable Long id,
                                                     @RequestBody CustomerDTO customerDTO){
        return customerService.patchCustomer(id, customerDTO);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomerById(id);
    }
}
