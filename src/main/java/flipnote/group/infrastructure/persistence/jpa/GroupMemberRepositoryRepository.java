package flipnote.group.infrastructure.persistence.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import flipnote.group.adapter.out.entity.GroupMemberEntity;
import flipnote.group.adapter.out.entity.RoleEntity;

public interface GroupMemberRepositoryRepository
	extends JpaRepository<GroupMemberEntity, Long> {
	boolean existsByUserIdAndRole_Id(Long userId, Long roleId);

	boolean existsByGroupIdAndUserId(Long groupId, Long userId);

	List<GroupMemberEntity> findAllByGroupId(Long groupId);
}
