package flipnote.group.api.dto.response;

import java.util.List;

import flipnote.group.application.port.in.result.FindOutgoingInviteListResult;
import flipnote.group.domain.model.invite.InviteInfo;

public record FindOutgoingInviteListResponseDto(
	List<InviteInfo> inviteInfoList
) {
	public static FindOutgoingInviteListResponseDto from(FindOutgoingInviteListResult result) {
		return new FindOutgoingInviteListResponseDto(result.inviteInfoList());
	}
}
