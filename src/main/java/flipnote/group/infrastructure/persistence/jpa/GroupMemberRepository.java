package flipnote.group.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import flipnote.group.adapter.out.entity.GroupMemberEntity;

public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, Long> {
	boolean existsByUserIdAndGroupRoleId(Long userId, Long id);

	boolean existsByGroupIdAndUserId(Long groupId, Long userId);
}
