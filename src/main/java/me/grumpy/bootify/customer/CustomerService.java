package me.grumpy.bootify.customer;

import io.github.perplexhub.rsql.RSQLJPASupport;
import me.grumpy.bootify.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(final CustomerRepository customerRepository,
            final CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public Page<CustomerDTO> findAll(final String search, final Pageable pageable) {
        Specification<Customer> specification = RSQLJPASupport.toSpecification(search);
        Page<Customer> page = customerRepository.findAll(specification, pageable);
        return new PageImpl<>(page.getContent()
                .stream()
                .map(customer -> customerMapper.updateCustomerDTO(customer, new CustomerDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    public CustomerDTO get(final Long id) {
        return customerRepository.findById(id)
                .map(customer -> customerMapper.updateCustomerDTO(customer, new CustomerDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CustomerDTO customerDTO) {
        final Customer customer = new Customer();
        customerMapper.updateCustomer(customerDTO, customer);
        return customerRepository.save(customer).getId();
    }

    public void update(final Long id, final CustomerDTO customerDTO) {
        final Customer customer = customerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        customerMapper.updateCustomer(customerDTO, customer);
        customerRepository.save(customer);
    }

    public void delete(final Long id) {
        customerRepository.deleteById(id);
    }

}
