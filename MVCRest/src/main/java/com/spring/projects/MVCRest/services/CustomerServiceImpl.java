package com.spring.projects.MVCRest.services;

import com.spring.projects.MVCRest.api.v1.mapper.CustomerMapper;
import com.spring.projects.MVCRest.api.v1.model.CustomerDTO;
import com.spring.projects.MVCRest.controllers.v1.CustomerController;
import com.spring.projects.MVCRest.domain.Customer;
import com.spring.projects.MVCRest.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper:: customerToCustomerDTO)
                .map(customerDTO -> {
                    customerDTO.setCustomerUrl(getCustomerUrl(id));
                    return customerDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<CustomerDTO> getAllCustomer() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                                  customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
                                  return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {

        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);

        return saveAndReturn(customer);
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {

        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);

        customer.setId(id);

        return saveAndReturn(customer);
    }

    private CustomerDTO saveAndReturn(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO savedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);

        savedCustomerDTO.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));

        return savedCustomerDTO;
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {

            if(customerDTO.getFirstName() != null){
                customer.setFirstName(customerDTO.getFirstName());
            }

            if(customerDTO.getLastName() != null){
                customer.setLastName(customerDTO.getLastName());
            }
            return saveAndReturn(customer);
        }).orElseThrow(ResourceNotFoundException::new);
    }

    private String getCustomerUrl(Long id){
        return CustomerController.BASE_URL + "/" + id;
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

}
