package flipnote.group.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import flipnote.group.adapter.out.entity.GroupEntity;
import flipnote.group.infrastructure.persistence.querydsl.GroupRepositoryCustom;

public interface GroupRepository extends JpaRepository<GroupEntity, Long>, GroupRepositoryCustom {
}
