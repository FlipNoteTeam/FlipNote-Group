package flipnote.group.application.port.out;

import java.util.List;

import flipnote.group.domain.model.join.JoinDomain;
import flipnote.group.domain.model.join.JoinStatus;

public interface JoinRepositoryPort {
	boolean existsJoin(Long groupId, Long userId);

	JoinDomain save(JoinDomain domain);

	List<JoinDomain> findFormList(Long groupId);

	JoinDomain findJoin(Long joinId);

	JoinDomain updateJoin(JoinDomain joinDomain);
}
