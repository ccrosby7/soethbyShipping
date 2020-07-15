package com.crosby.soethbyshipping.repository;

import com.crosby.soethbyshipping.domain.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Quote entity.
 */
@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
}
