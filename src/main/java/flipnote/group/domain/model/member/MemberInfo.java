package flipnote.group.domain.model.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberInfo {
	private Long userId;
	private GroupMemberRole role;

	@Builder
	private MemberInfo(Long userId, GroupMemberRole role) {
		this.userId = userId;
		this.role = role;
	}
}
