package flipnote.group.application.port.in.result;

import java.util.List;

import flipnote.group.adapter.out.entity.JoinEntity;
import flipnote.group.domain.model.join.JoinInfo;

public record FindMyJoinListResult(
	List<JoinInfo> joinList
) {
	public static FindMyJoinListResult of(List<JoinEntity> joinList) {
		List<JoinInfo> joinInfoList = joinList.stream()
			.map(JoinInfo::of)
			.toList();
		return new FindMyJoinListResult(joinInfoList);
	}
}
