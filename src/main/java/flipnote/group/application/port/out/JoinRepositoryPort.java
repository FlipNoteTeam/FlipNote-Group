package flipnote.group.application.port.out;

import flipnote.group.domain.model.join.JoinDomain;

public interface JoinRepositoryPort {
	boolean existsJoin(Long groupId, Long userId);

	JoinDomain save(JoinDomain domain);
}
