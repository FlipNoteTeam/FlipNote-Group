package flipnote.group.adapter.out.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import flipnote.group.adapter.out.entity.JoinEntity;
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
	public void save(JoinEntity join) {
		joinRepository.save(join);
	}

	@Override
	public List<JoinEntity> findFormList(Long groupId) {

		List<JoinEntity> joinList = joinRepository.findAllByGroupId(groupId);

		return joinList;
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
