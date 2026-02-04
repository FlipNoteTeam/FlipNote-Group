package flipnote.group.application.port.out;

import flipnote.group.adapter.out.entity.GroupEntity;
import flipnote.group.application.port.in.command.CreateGroupCommand;

public interface GroupRepositoryPort {
    Long saveNewGroup(GroupEntity groupEntity);
}
