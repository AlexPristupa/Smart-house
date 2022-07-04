package ru.prooftechit.smh.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.prooftechit.smh.domain.model.Session;

/**
 * @author Andrey Kovalenko
 */
@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {

    Page<Session> findAllByUser_IdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    Session findByTokenUuid(String tokenUuid);
}
