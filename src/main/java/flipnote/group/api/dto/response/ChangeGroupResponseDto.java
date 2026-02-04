package flipnote.group.api.dto.response;

import java.time.LocalDateTime;

import flipnote.group.application.port.in.result.ChangeGroupResult;
import flipnote.group.domain.model.group.Category;
import flipnote.group.domain.model.group.Group;
import flipnote.group.domain.model.group.JoinPolicy;
import flipnote.group.domain.model.group.Visibility;

public record ChangeGroupResponseDto(
	String name,

	Category category,

	String description,

	JoinPolicy joinPolicy,

	Visibility visibility,

	Integer maxMember,

	Long imageRefId,

	LocalDateTime createdAt,

	LocalDateTime modifiedAt
) {
	public static ChangeGroupResponseDto from(ChangeGroupResult result) {

		Group group = result.group();

		return new ChangeGroupResponseDto(
			group.getName(),
			group.getCategory(),
			group.getDescription(),
			group.getJoinPolicy(),
			group.getVisibility(),
			group.getMaxMember(),
			group.getImageRefId(),
			group.getCreatedAt(),
			group.getModifiedAt()
		);
	}
}
