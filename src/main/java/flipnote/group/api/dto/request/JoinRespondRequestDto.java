package flipnote.group.api.dto.request;

import flipnote.group.domain.model.join.JoinStatus;
import jakarta.validation.constraints.NotNull;

public record JoinRespondRequestDto(
	@NotNull(message = "그룹 신청 상태를 선택해주세요.")
	JoinStatus status
) {
}
