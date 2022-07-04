package ru.prooftechit.smh.domain.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.prooftechit.smh.api.enums.UserRole;
import ru.prooftechit.smh.api.enums.UserStatus;
import ru.prooftechit.smh.domain.model.User;

/**
 * @author Roman Zdoronok
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("select user from User user where lower(user.email) = lower(:username)")
    Optional<User> findOneByUsername(@Param("username") String username);

    @Query("select user from User user "
        + "where lower(user.email) = lower(:username) "
        + "and user.status = :status")
    Optional<User> findOneByUsernameAndStatus(@Param("username") String username, @Param("status") UserStatus status);

    @Query("select case when count(u) > 0 then true else false end "
        + "from User u where lower(u.email) = lower(:email) and "
        + "(:userId is null or u.id != :userId)")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    boolean existsByEmailExceptUser(@Param("email") String email, @Param("userId") Long userId);

    @Query("select email from User where status = :status and (:role is null or role = :role)")
    Page<String> findAllUsersEmails(@Param("status") UserStatus status, @Param("role") UserRole role, Pageable pageable);

    default Page<String> findAllActiveUsersEmails(Pageable pageable) {
        return findAllUsersEmails(UserStatus.ACTIVE, null, pageable);
    }

    default Page<String> findAllActiveUsersEmailsByRole(UserRole role, Pageable pageable) {
        return findAllUsersEmails(UserStatus.ACTIVE, role, pageable);
    }

    @Query("select u from User u where u.status <> :status and (:id is not null and u.id = :id)")
    Optional<User> findOneExcludeByStatus(@Param("status") UserStatus status, @Param("id") Long userId);

    @Query("select u from User u where u.status <> :status")
    Page<User> findAllExcludeByStatus(@Param("status") UserStatus status, Pageable pageable);

    default Optional<User> findActiveById(Long id) {
        return findOneExcludeByStatus(UserStatus.DELETED, id);
    };

}
