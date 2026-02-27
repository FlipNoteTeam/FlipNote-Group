package flipnote.group.application.port.in.result;

import java.util.List;

import flipnote.group.adapter.out.entity.JoinEntity;
import flipnote.group.domain.model.join.JoinInfo;

public record FindJoinFormListResult(
	List<JoinInfo> joinInfoList
) {
	public static FindJoinFormListResult of(List<JoinEntity> joinDomainList) {

		List<JoinInfo> joinInfoList = joinDomainList.stream()
			.map(JoinInfo::of)
			.toList();

		return new FindJoinFormListResult(joinInfoList);
	}
}
