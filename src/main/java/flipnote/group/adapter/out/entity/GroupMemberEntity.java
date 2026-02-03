package flipnote.group.adapter.out.entity;

import flipnote.group.domain.model.group.GroupId;
import flipnote.group.domain.model.member.GroupRole;
import flipnote.group.domain.model.user.UserId;
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

@Getter
@Entity
@Table(
	name = "group_members",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "uk_group_members_group_user",
			columnNames = {"group_id", "user_id"}
		)
	}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMemberEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "group_id", nullable = false)
	private Long groupId;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private GroupRole role;

	@Builder
	private GroupMemberEntity(Long groupId, Long userId, GroupRole role) {
		this.groupId = groupId;
		this.userId = userId;
		this.role = (role != null) ? role : GroupRole.MEMBER;
	}

	/**
	 * 오너인 경우
	 * @param groupId
	 * @param userId
	 * @return
	 */
	public static GroupMemberEntity createOwner(Long groupId, Long userId) {
		return GroupMemberEntity.builder()
			.groupId(groupId)
			.userId(userId)
			.role(GroupRole.OWNER)
			.build();
	}

	/**
	 * 오너가 아닌 경우
	 * @param groupId
	 * @param userId
	 * @return
	 */
	public static GroupMemberEntity join(Long groupId, Long userId) {
		return GroupMemberEntity.builder()
			.groupId(groupId)
			.userId(userId)
			.role(GroupRole.MEMBER)
			.build();
	}
}
