package flipnote.group.api.dto.response;

import flipnote.group.application.port.in.result.JoinRespondResult;
import flipnote.group.domain.model.join.JoinStatus;

public record JoinRespondResponseDto(
	Long joinId,
	JoinStatus status
) {
	public static JoinRespondResponseDto from(JoinRespondResult result) {

		Long joinId = result.joinDomain().getId();
		JoinStatus status = result.joinDomain().getStatus();

		return new JoinRespondResponseDto(joinId, status);
	}
}
