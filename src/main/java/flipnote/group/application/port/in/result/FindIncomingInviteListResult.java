package flipnote.group.application.port.in.result;

import java.util.List;

import flipnote.group.domain.model.invite.InviteMyInfo;

public record FindIncomingInviteListResult(
	List<InviteMyInfo> inviteList
) {
}
