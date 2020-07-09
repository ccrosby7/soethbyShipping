package com.crosby.soethbyshipping.repository;

import com.crosby.soethbyshipping.domain.Region;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Region entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
}
