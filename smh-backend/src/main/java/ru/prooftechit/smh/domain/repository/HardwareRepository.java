package ru.prooftechit.smh.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.prooftechit.smh.domain.model.Facility;
import ru.prooftechit.smh.domain.model.Hardware;

/**
 * @author Roman Zdoronok
 */
@Repository
public interface HardwareRepository extends PagingAndSortingRepository<Hardware, Long>, JpaSpecificationExecutor<Hardware> {
    Page<Hardware> findAllByFacility(Facility facility, Pageable pageable);
    @Query("select case when count(distinct h.id) > 0 then true else false end "
            + "from Hardware h where h.model = :model and h.serialNumber = :serialNumber and "
            + "(:id is null or h.id != :id)")
    boolean existsByModelAndSerialNumberExceptItself(String model, String serialNumber, Long id);
}
