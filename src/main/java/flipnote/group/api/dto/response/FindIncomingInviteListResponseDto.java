package flipnote.group.api.dto.response;

import java.util.List;

import flipnote.group.application.port.in.result.FindIncomingInviteListResult;
import flipnote.group.domain.model.invite.InviteMyInfo;

public record FindIncomingInviteListResponseDto(
	List<InviteMyInfo> inviteList
) {
	public static FindIncomingInviteListResponseDto from(FindIncomingInviteListResult result) {
		return new FindIncomingInviteListResponseDto(result.inviteList());
	}
}
