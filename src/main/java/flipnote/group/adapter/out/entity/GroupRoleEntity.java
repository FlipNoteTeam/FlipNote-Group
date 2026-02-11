package flipnote.group.adapter.out.entity;

import flipnote.group.domain.model.member.GroupMemberRole;
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
@Table(name = "group_roles",
	uniqueConstraints = @UniqueConstraint(
		name = "uk_group_roles_group_name",
		columnNames = {"group_id", "name"}
	)
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupRoleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "group_id", nullable = false)
	private Long groupId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private GroupMemberRole name;

	@Builder
	private GroupRoleEntity(Long groupId, GroupMemberRole name) {
		this.groupId = groupId;
		this.name = name;
	}
}

