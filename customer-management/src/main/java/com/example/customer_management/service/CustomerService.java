package com.example.customer_management.service;



import com.example.customer_management.entity.Customer;
import com.example.customer_management.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get customer by id
    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    // Create customer
    public Customer createCustomer(Customer customer) {

        customer.setCreatedAt(LocalDateTime.now());

        return customerRepository.save(customer);
    }

    // Update customer
    public Customer updateCustomer(Integer id, Customer updatedCustomer) {

        Customer customer = getCustomerById(id);

        customer.setName(updatedCustomer.getName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setPhone(updatedCustomer.getPhone());

        return customerRepository.save(customer);
    }

    // Delete customer
    public void deleteCustomer(Integer id) {

        Customer customer = getCustomerById(id);

        customerRepository.delete(customer);
    }
}
