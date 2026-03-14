package flipnote.group.application.port.in.result;

import java.util.List;

import flipnote.group.domain.model.invite.InviteInfo;

public record FindOutgoingInviteListResult(
	List<InviteInfo> inviteInfoList
) {
}
