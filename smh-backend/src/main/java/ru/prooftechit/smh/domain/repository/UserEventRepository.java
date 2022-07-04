package ru.prooftechit.smh.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.prooftechit.smh.domain.model.UserEvent;
import ru.prooftechit.smh.domain.repository.custom.UserEventWithMetadataRepository;

@Repository
public interface UserEventRepository extends PagingAndSortingRepository<UserEvent, Long>,
    JpaSpecificationExecutor<UserEvent>,
    UserEventWithMetadataRepository {

}
