package flipnote.group.application.port.in;

import flipnote.group.application.port.in.command.ApplicationFormCommand;
import flipnote.group.application.port.in.result.ApplicationFormResult;

public interface JoinUseCase {
	ApplicationFormResult joinRequest(ApplicationFormCommand cmd);
}
