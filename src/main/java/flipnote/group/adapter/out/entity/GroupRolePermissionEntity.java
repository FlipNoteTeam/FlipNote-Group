package flipnote.group.adapter.out.entity;

import flipnote.group.domain.model.member.GroupMemberRole;
import flipnote.group.domain.model.permission.GroupPermission;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "group_role_permissions",
	uniqueConstraints = @UniqueConstraint(
		name = "uk_grp_role_perm",
		columnNames = {"group_role_id", "permission"}
	)
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupRolePermissionEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "group_role_id", nullable = false)
	private Long groupRoleId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private GroupPermission permission;

	@Builder
	private GroupRolePermissionEntity(Long groupRoleId, GroupPermission permission) {
		this.groupRoleId = groupRoleId;
		this.permission = permission;
	}
}
