package flipnote.group.application.port.in.result;

import java.util.List;

import flipnote.group.domain.model.permission.GroupPermission;

public record AddPermissionResult(
	List<GroupPermission> groupPermissions
) {
}
