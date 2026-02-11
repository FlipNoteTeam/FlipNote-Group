package flipnote.group.application.port.out;

import flipnote.group.domain.model.member.GroupMemberRole;

public interface GroupMemberRepositoryPort {
    void saveOwner(Long groupId, Long userId);
}
