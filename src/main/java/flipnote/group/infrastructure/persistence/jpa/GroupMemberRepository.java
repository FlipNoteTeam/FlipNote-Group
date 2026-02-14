package flipnote.group.infrastructure.persistence.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import flipnote.group.adapter.out.entity.GroupMemberEntity;

public interface GroupMemberRepository
	extends JpaRepository<GroupMemberEntity, Long> {
	boolean existsByUserIdAndRole_Id(Long userId, Long roleId);

	boolean existsByGroupIdAndUserId(Long groupId, Long userId);

	List<GroupMemberEntity> findAllByGroupId(Long groupId);
}
