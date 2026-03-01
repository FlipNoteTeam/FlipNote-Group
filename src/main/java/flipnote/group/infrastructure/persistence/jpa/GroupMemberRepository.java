package flipnote.group.infrastructure.persistence.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import flipnote.group.adapter.out.entity.GroupMemberEntity;

public interface GroupMemberRepository
	extends JpaRepository<GroupMemberEntity, Long> {

	boolean existsByGroupIdAndUserId(Long groupId, Long userId);

	@Query("""
    select gm
    from GroupMemberEntity gm
    join fetch gm.role gr
    where gm.groupId = :groupId
""")
	List<GroupMemberEntity> findAllByGroupId(Long groupId);

	Optional<GroupMemberEntity> findByGroupIdAndUserId(Long groupId, Long userId);

	boolean existsByUserIdAndRole_Id(Long userId, Long id);
}
