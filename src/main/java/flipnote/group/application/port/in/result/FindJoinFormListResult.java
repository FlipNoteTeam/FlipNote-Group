package flipnote.group.application.port.in.result;

import java.util.List;

import flipnote.group.domain.model.join.JoinDomain;

public record FindJoinFormListResult(
	List<JoinDomain> joinDomainList
) {
}
