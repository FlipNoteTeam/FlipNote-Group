package flipnote.group.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import flipnote.group.adapter.out.entity.RoleEntity;

public interface GroupRoleRepository extends JpaRepository<RoleEntity, Long> {
}
