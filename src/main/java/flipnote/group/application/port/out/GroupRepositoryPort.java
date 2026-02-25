package flipnote.group.application.port.out;

import flipnote.group.adapter.out.entity.GroupEntity;

public interface GroupRepositoryPort {
    Long saveNewGroup(GroupEntity group);

    Group findById(Long id);

    void delete(Long id);
}
