package flipnote.group.application.port.in.result;

import java.util.List;

import flipnote.group.adapter.out.entity.JoinEntity;
import flipnote.group.domain.model.join.JoinMyInfo;

public record FindMyJoinListResult(
	List<JoinMyInfo> joinList
) {
	public static FindMyJoinListResult of(List<JoinEntity> joinList) {
		List<JoinMyInfo> joinInfoList = joinList.stream()
			.map(JoinMyInfo::of)
			.toList();
		return new FindMyJoinListResult(joinInfoList);
	}
}
