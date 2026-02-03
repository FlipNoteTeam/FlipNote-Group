package flipnote.group.domain.model.member;

import flipnote.group.domain.model.group.GroupId;
import flipnote.group.domain.model.user.UserId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupMember {
	private Long id;
	private GroupId groupId;
	private UserId userId;
	private GroupRole role;

	/**
	 * 유저가 오너일 경우
	 * @param groupId
	 * @param ownerUserId
	 * @return
	 */
	public static GroupMember createOwner(GroupId groupId, UserId ownerUserId) {
		GroupMember gm = new GroupMember();
		gm.groupId = groupId;
		gm.userId = ownerUserId;
		gm.role = GroupRole.OWNER;
		return gm;
	}

	/**
	 * 오너가 아닐 경우
	 * @param groupId
	 * @param userId
	 * @return
	 */
	public static GroupMember join(GroupId groupId, UserId userId) {
		GroupMember gm = new GroupMember();
		gm.groupId = groupId;
		gm.userId = userId;
		gm.role = GroupRole.MEMBER;
		return gm;
	}

}
