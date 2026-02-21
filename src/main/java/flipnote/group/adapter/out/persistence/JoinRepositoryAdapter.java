package flipnote.group.adapter.out.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import flipnote.group.adapter.out.entity.JoinEntity;
import flipnote.group.adapter.out.persistence.mapper.JoinMapper;
import flipnote.group.application.port.out.JoinRepositoryPort;
import flipnote.group.domain.model.join.JoinDomain;
import flipnote.group.domain.model.join.JoinStatus;
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

	@Override
	public List<JoinDomain> findFormList(Long groupId) {

		List<JoinEntity> joinList = joinRepository.findAllByGroupId(groupId);

		return JoinMapper.toDomains(joinList);
	}

	@Override
	public JoinDomain findJoin(Long joinId) {

		JoinEntity entity = joinRepository.findById(joinId).orElseThrow(
			() -> new IllegalArgumentException("not exist")
		);
		return JoinMapper.toDomain(entity);
	}

	@Override
	public JoinDomain updateJoin(JoinDomain joinDomain) {

		JoinEntity entity = JoinMapper.toEntity(joinDomain);

		joinRepository.save(entity);

		return JoinMapper.toDomain(entity);
	}
}
