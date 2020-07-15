package com.crosby.soethbyshipping.service;

    import com.crosby.soethbyshipping.domain.Address;

    import java.util.List;
    import java.util.Optional;

/**
 * Service Interface for managing {@link com.crosby.soethbyshipping.domain.Address}.
 */
public interface AddressService {

    /**
     * Save a quote.
     *
     * @param Address the entity to save.
     * @return the persisted entity.
     */
    Address save(Address Address);

    /**
     * Get all the Addresss.
     *
     * @return the list of entities.
     */
    List<Address> findAll();


    /**
     * Get the "id" Address.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Address> findOne(Long id);

    /**
     * Delete the "id" Address.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
