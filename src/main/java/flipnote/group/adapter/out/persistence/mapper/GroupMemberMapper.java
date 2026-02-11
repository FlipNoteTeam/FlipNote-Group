package flipnote.group.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import flipnote.group.adapter.out.entity.GroupMemberEntity;
import flipnote.group.domain.model.member.GroupMemberRole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupMemberMapper {
	public static GroupMemberEntity createOwner(Long groupId, Long userId) {
		return GroupMemberEntity.builder()
			.groupId(groupId)
			.userId(userId)
			.role(GroupMemberRole.OWNER)
			.build();
	}
}
