package flipnote.group.application.port.in;

import flipnote.group.application.port.in.command.FindOutgoingInviteCommand;
import flipnote.group.application.port.in.result.FindIncomingInviteListResult;
import flipnote.group.application.port.in.result.FindOutgoingInviteListResult;

public interface FindInviteUseCase {
	FindOutgoingInviteListResult findOutgoingInvites(FindOutgoingInviteCommand cmd);

	FindIncomingInviteListResult findIncomingInvites(Long inviteeUserId);
}
