package andrehsvictor.fitzone.api.v1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import andrehsvictor.fitzone.api.v1.model.Customer;
import andrehsvictor.fitzone.api.v1.repository.CustomerRepository;

@Service
public class CustomerService implements UserDetailsService {

    @Autowired
    private CustomerRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByFullName(username);
    }

    public UserDetails loadByAccessCode(String accessCode) {
        return repository.findByAccessCode(accessCode);
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public List<Customer> findAllUsers() {
        return repository.findAllUsers();
    }

    public void save(Customer customer) {
        repository.save(customer);
    }

}
