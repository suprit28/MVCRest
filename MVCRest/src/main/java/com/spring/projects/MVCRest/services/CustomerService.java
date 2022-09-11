package com.spring.projects.MVCRest.services;

import com.spring.projects.MVCRest.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO getCustomerById(Long id);

    List<CustomerDTO> getAllCustomer();

    CustomerDTO createNewCustomer(CustomerDTO customerDTO);

    CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO);

    CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);

    void deleteCustomerById(Long id);
}
