package flipnote.group.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import flipnote.group.adapter.out.entity.JoinEntity;

public interface JoinRepository extends JpaRepository<JoinEntity, Long> {

	boolean existsByUserIdAndGroupId(Long userId, Long groupId);

}
