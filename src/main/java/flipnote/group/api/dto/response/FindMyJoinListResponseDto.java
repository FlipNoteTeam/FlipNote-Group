package flipnote.group.api.dto.response;

import java.util.List;

import flipnote.group.application.port.in.result.FindMyJoinListResult;
import flipnote.group.domain.model.join.JoinDomain;

public record FindMyJoinListResponseDto(
	List<JoinDomain> joinList
) {
	public static FindMyJoinListResponseDto from(FindMyJoinListResult result) {
		return new FindMyJoinListResponseDto(result.joinDomainList());
	}
}
