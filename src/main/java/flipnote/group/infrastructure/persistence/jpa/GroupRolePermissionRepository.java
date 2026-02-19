package flipnote.group.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import flipnote.group.adapter.out.entity.PermissionEntity;
import flipnote.group.infrastructure.persistence.querydsl.GroupRolePermissionCustom;

public interface GroupRolePermissionRepository extends JpaRepository<PermissionEntity, Long>,
	GroupRolePermissionCustom {
}
