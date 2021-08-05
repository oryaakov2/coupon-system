package com.or_yaakov.couponsystem.repo;

import com.or_yaakov.couponsystem.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    /**
     * Find and retrieve {@link Company} from data-base.
     *
     * @param email    The email of {@link Company}.
     * @param password The password of {@link Company}.
     * @return A {@link Optional} of {@link Company}.
     */
    Optional<Company> findByEmailAndPassword(String email, String password);
}