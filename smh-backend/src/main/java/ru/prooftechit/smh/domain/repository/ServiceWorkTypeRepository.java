package ru.prooftechit.smh.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.prooftechit.smh.domain.model.ServiceWorkType;

/**
 * @author Roman Zdoronok
 */
@Repository
public interface ServiceWorkTypeRepository extends PagingAndSortingRepository<ServiceWorkType, Long>, JpaSpecificationExecutor<ServiceWorkType> {

    @Query("select case when count(t) > 0 then true else false end "
        + "from ServiceWorkType t where lower(t.name) = lower(:name) and "
        + "(:id is null or t.id != :id)")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    boolean existsByNameExceptItself(Long id, String name);
}
