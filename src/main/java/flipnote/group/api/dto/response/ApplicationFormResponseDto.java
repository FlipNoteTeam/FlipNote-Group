package flipnote.group.api.dto.response;

import flipnote.group.domain.model.join.JoinDomain;
import flipnote.group.domain.model.join.JoinStatus;

public record ApplicationFormResponseDto(
	Long groupJoinId,
	JoinStatus status
) {
	public static ApplicationFormResponseDto from(JoinDomain domain) {
		return new ApplicationFormResponseDto(domain.getId(), domain.getStatus());
	}
}
