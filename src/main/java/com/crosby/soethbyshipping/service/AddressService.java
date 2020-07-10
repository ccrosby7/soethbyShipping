package com.crosby.soethbyshipping.service;

    import com.crosby.soethbyshipping.service.dto.AddressDTO;

    import java.util.List;
    import java.util.Optional;

/**
 * Service Interface for managing {@link com.crosby.soethbyshipping.domain.Address}.
 */
public interface AddressService {

    /**
     * Save a quote.
     *
     * @param AddressDTO the entity to save.
     * @return the persisted entity.
     */
    AddressDTO save(AddressDTO AddressDTO);

    /**
     * Get all the Addresss.
     *
     * @return the list of entities.
     */
    List<AddressDTO> findAll();


    /**
     * Get the "id" Address.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AddressDTO> findOne(Long id);

    /**
     * Delete the "id" Address.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
