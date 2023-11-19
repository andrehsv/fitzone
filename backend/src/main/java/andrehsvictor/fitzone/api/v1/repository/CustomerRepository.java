package andrehsvictor.fitzone.api.v1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import andrehsvictor.fitzone.api.v1.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long>{
    Customer findByFullName(String fullName);
    Customer findByAccessCode(String accessCode);
    @Query("SELECT c FROM Customer c WHERE c.role = 'USER'")
    List<Customer> findAllUsers();
}
