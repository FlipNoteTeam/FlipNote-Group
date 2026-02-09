package flipnote.group.application.port.in;

import flipnote.group.application.port.in.command.FindGroupCommand;
import flipnote.group.application.port.in.result.FindGroupResult;

public interface FindGroupUseCase {
	FindGroupResult findGroup(FindGroupCommand cmd);
}
