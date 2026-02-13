package flipnote.group.adapter.out.persistence.mapper;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import flipnote.group.adapter.out.entity.GroupMemberEntity;
import flipnote.group.adapter.out.entity.RoleEntity;
import flipnote.group.domain.model.member.GroupMember;
import flipnote.group.domain.model.member.GroupMemberRole;
import flipnote.group.domain.model.member.MemberInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupMemberMapper {
	public static GroupMemberEntity create(Long groupId, Long userId, RoleEntity role) {
		return GroupMemberEntity.create(groupId, userId, role);
	}

	public static GroupMember toDomain(GroupMemberEntity entity) {

		if (entity == null) {
			return null;
		}

		return GroupMember.builder()
			.id(entity.getId())
			.groupId(entity.getGroupId())
			.role(entity.getRole())
			.build();
	}

	/**
	 * 그룹 멤버 정보 전체 조회
	 * @param entities
	 * @return
	 */
	public static List<MemberInfo> toMemberInfo(List<GroupMemberEntity> entities) {
		if (entities == null || entities.isEmpty()) {
			return Collections.emptyList();
		}

		return entities.stream()
			.map(entity -> MemberInfo.builder()
				.userId(entity.getUserId())
				.role(entity.getRole().getRole())
				.build()
			)
			.toList();
	}

}
