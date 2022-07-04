package ru.prooftechit.smh.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.prooftechit.smh.domain.model.Facility;
import ru.prooftechit.smh.domain.model.ServiceWork;

/**
 * @author Roman Zdoronok
 */
@Repository
public interface ServiceWorkRepository extends PagingAndSortingRepository<ServiceWork, Long>, JpaSpecificationExecutor<ServiceWork> {
    Page<ServiceWork> findAllByFacility(Facility facility, Pageable pageable);
    boolean existsByTypeId(Long serviceWorkTypeId);
}
