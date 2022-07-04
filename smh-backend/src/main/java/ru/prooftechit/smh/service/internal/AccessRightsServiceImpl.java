package ru.prooftechit.smh.service.internal;

import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.prooftechit.smh.api.enums.UserRole;
import ru.prooftechit.smh.domain.model.*;
import ru.prooftechit.smh.domain.repository.*;
import ru.prooftechit.smh.exceptions.RecordNotFoundException;
import ru.prooftechit.smh.exceptions.RestrictedAccessException;

/**
 * @author Roman Zdoronok
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AccessRightsServiceImpl implements AccessRightsService {

    private static final Predicate<User> ADMIN_PREDICATE = user -> UserRole.ADMIN.equals(user.getRole());

    private final FacilityRepository facilityRepository;
    private final HardwareRepository hardwareRepository;
    private final ServiceWorkRepository serviceWorkRepository;
    private final ServiceWorkTypeRepository serviceWorkTypeRepository;
    private final FileSystemNodeRepository fileSystemNodeRepository;
    private final UserRepository userRepository;

    @Override
    public Facility getFacility(long facilityId, User user) {
        return getEntity(facilityRepository, facilityId);
    }

    @Override
    public FileSystemNode getFileSystemNode(long nodeId, User user) {
        return getEntity(fileSystemNodeRepository, nodeId);
    }

    @Override
    public Hardware getHardware(long hardwareId, User user) {
        return getEntity(hardwareRepository, hardwareId);
    }

    @Override
    public ServiceWork getServiceWork(long serviceWorkId, User user) {
        return getEntity(serviceWorkRepository, serviceWorkId);
    }

    @Override
    public ServiceWorkType getServiceWorkType(long serviceWorkTypeId, User user) {
        return getEntity(serviceWorkTypeRepository, serviceWorkTypeId);
    }

    @Override
    public User getUser(long userId, User user) {
        return userRepository.findActiveById(userId).orElseThrow(RecordNotFoundException::new);
    }

    private <E, I> E getEntity(CrudRepository<E, I> repository, I id) {
        return repository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    protected Predicate<User> getAdminPredicate() {
        return ADMIN_PREDICATE;
    }

    private void checkAccessRights(User user, Supplier<User> ownerSupplier){
        if(getAdminPredicate().or(owner -> owner.getId().equals(ownerSupplier.get().getId())).negate().test(user)) {
            throw new RestrictedAccessException();
        }
    }
}
