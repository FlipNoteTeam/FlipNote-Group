package flipnote.group.application.port.out;

import flipnote.group.domain.model.member.GroupMemberRole;

public interface GroupRoleRepositoryPort {
	Long create(Long groupId);

	boolean checkRole(Long userId, Long groupId, GroupMemberRole groupMemberRole);
}
