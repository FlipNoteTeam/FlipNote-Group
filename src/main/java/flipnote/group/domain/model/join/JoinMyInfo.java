package flipnote.group.domain.model.join;

import flipnote.group.adapter.out.entity.JoinEntity;

public record JoinMyInfo(
	Long groupJoinId,
	String joinIntro,
	JoinStatus status
) {
	public static JoinMyInfo of(JoinEntity join) {
		return new JoinMyInfo(join.getId(), join.getForm(), join.getStatus());
	}
}
