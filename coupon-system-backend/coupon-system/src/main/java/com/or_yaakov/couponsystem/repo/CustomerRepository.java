package com.or_yaakov.couponsystem.repo;

import com.or_yaakov.couponsystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Find and retrieve {@link Customer} from data-base.
     *
     * @param email    The email of {@link Customer}.
     * @param password The password of {@link Customer}.
     * @return A {@link Optional} of {@link Customer}.
     */
    Optional<Customer> findByEmailAndPassword(String email, String password);
}