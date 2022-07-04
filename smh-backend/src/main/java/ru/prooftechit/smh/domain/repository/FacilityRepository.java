package ru.prooftechit.smh.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.prooftechit.smh.domain.model.Facility;

/**
 * @author Roman Zdoronok
 */
@Repository
public interface FacilityRepository extends PagingAndSortingRepository<Facility, Long>, JpaSpecificationExecutor<Facility> {
    @Query("select case when count(distinct id) > 0 then true else false end "
            + "from Facility f where f.name = :name and f.address = :address and "
            + "(:id is null or f.id != :id)")
    boolean existsByNameAndAddressExceptItself(String name, String address, Long id);
}
