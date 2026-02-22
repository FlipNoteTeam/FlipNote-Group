package flipnote.group.api.dto.request;

import flipnote.group.domain.model.member.GroupMemberRole;
import flipnote.group.domain.model.permission.GroupPermission;

public record AddPermissionRequestDto(
	GroupMemberRole hostRole,
	GroupMemberRole changeRole,
	GroupPermission permission
) {
}
