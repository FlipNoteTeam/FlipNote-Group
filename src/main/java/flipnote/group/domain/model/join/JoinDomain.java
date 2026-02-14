package flipnote.group.domain.model.join;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinDomain {

	private Long id;

	private Long userId;

	private Long groupId;

	private JoinStatus status;

	private String form;

	@Builder
	private JoinDomain(Long id, Long userId, Long groupId, JoinStatus status, String form) {
		this.id = id;
		this.userId = userId;
		this.groupId = groupId;
		this.status = status;
		this.form = form;
	}
}
