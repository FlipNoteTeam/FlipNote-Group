package flipnote.group.application.port.out;

import flipnote.group.domain.model.group.Group;

public interface GroupRepositoryPort {
    Long saveNewGroup(Group group);
}
