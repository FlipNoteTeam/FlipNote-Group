package flipnote.group.application.port.out;

import flipnote.group.adapter.out.entity.RoleEntity;
import flipnote.group.domain.model.member.GroupMemberRole;

public interface GroupRoleRepositoryPort {
	RoleEntity create(Long groupId);

	boolean checkRole(Long userId, Long groupId, GroupMemberRole groupMemberRole);
}
