package flipnote.group.application.port.out;

import java.util.Optional;

import flipnote.group.adapter.out.entity.GroupEntity;
import flipnote.group.domain.model.group.Group;

public interface GroupRepositoryPort {
    Long saveNewGroup(Group group);

    Optional<GroupEntity> findById(Long id);

    Group update(Group group, GroupEntity groupEntity);
}
