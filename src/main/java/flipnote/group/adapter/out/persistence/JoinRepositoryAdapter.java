package flipnote.group.adapter.out.persistence;

import org.springframework.stereotype.Repository;

import flipnote.group.adapter.out.entity.JoinEntity;
import flipnote.group.adapter.out.persistence.mapper.JoinMapper;
import flipnote.group.application.port.out.JoinRepositoryPort;
import flipnote.group.domain.model.group.Group;
import flipnote.group.domain.model.join.JoinDomain;
import flipnote.group.infrastructure.persistence.jpa.JoinRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JoinRepositoryAdapter implements JoinRepositoryPort {

	private final JoinRepository joinRepository;

	@Override
	public boolean existsJoin(Long groupId, Long userId) {
		return joinRepository.existsByUserIdAndGroupId(userId, groupId);
	}

	@Override
	public JoinDomain save(JoinDomain domain) {

		JoinEntity entity = JoinMapper.createNewEntity(domain);

		joinRepository.save(entity);

		return JoinMapper.toDomain(entity);
	}
}
