package flipnote.group.application.port.out;

import flipnote.group.adapter.out.entity.RoleEntity;
import flipnote.group.domain.model.member.GroupMemberRole;
import flipnote.group.domain.model.permission.GroupPermission;

public interface GroupRoleRepositoryPort {
	RoleEntity create(Long groupId);

	boolean checkRole(Long userId, Long groupId, GroupMemberRole groupMemberRole);

	boolean checkPermission(Long userId, Long groupId, GroupPermission permission);

	RoleEntity findByIdAndRole(Long id, GroupMemberRole groupMemberRole);
}
