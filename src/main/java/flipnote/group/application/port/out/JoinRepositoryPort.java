package flipnote.group.application.port.out;

import java.util.List;

import flipnote.group.adapter.out.entity.JoinEntity;
import flipnote.group.domain.model.join.JoinDomain;
import flipnote.group.domain.model.join.JoinStatus;

public interface JoinRepositoryPort {
	boolean existsJoin(Long groupId, Long userId);

	void save(JoinEntity join);

	List<JoinEntity> findFormList(Long groupId);

	JoinDomain findJoin(Long joinId);

	JoinDomain updateJoin(JoinDomain joinDomain);
}
