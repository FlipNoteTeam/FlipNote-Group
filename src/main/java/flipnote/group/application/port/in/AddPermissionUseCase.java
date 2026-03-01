package flipnote.group.application.port.in;

import flipnote.group.application.port.in.command.AddPermissionCommand;
import flipnote.group.application.port.in.result.AddPermissionResult;

public interface AddPermissionUseCase {
	AddPermissionResult addPermission(AddPermissionCommand cmd);
}
