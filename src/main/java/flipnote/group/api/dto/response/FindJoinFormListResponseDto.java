package flipnote.group.api.dto.response;

import java.util.List;

import flipnote.group.application.port.in.result.FindJoinFormListResult;
import flipnote.group.domain.model.join.JoinDomain;
import flipnote.group.domain.model.join.JoinStatus;

public record FindJoinFormListResponseDto(
	List<JoinDomain> joinList
) {
	public static FindJoinFormListResponseDto from(FindJoinFormListResult result) {
		return new FindJoinFormListResponseDto(result.joinDomainList());
	}
}
