package flipnote.group.application.port.in;

import flipnote.group.application.port.in.command.FindGroupCommand;
import flipnote.group.application.port.in.result.FIndGroupResult;

public interface FindGroupUseCase {
	FIndGroupResult findGroup(FindGroupCommand cmd);
}
