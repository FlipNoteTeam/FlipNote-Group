package flipnote.group.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import flipnote.group.adapter.out.entity.RoleEntity;
import flipnote.group.domain.model.member.GroupMemberRole;

public interface GroupRoleRepository extends JpaRepository<RoleEntity, Long> {
	RoleEntity findByGroupIdAndRole(Long groupId, GroupMemberRole groupMemberRole);
}
