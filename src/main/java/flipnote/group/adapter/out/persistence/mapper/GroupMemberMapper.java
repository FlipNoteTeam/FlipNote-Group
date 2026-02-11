package flipnote.group.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import flipnote.group.adapter.out.entity.GroupMemberEntity;
import flipnote.group.domain.model.member.GroupMemberRole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupMemberMapper {
	public static GroupMemberEntity create(Long groupId, Long userId, Long roleId) {
		return GroupMemberEntity.create(groupId, userId, roleId);
	}
}
