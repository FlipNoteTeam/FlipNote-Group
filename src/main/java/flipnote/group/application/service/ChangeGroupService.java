package flipnote.group.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flipnote.group.adapter.out.entity.GroupEntity;
import flipnote.group.adapter.out.persistence.mapper.GroupMapper;
import flipnote.group.application.port.in.ChangeGroupUseCase;
import flipnote.group.application.port.in.command.ChangeGroupCommand;
import flipnote.group.application.port.in.result.ChangeGroupResult;
import flipnote.group.infrastructure.persistence.jpa.GroupRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChangeGroupService implements ChangeGroupUseCase {

	private final GroupRepository jpaGroupRepository;

	/**
	 * 그룹 수정
	 * @param cmd
	 * @return
	 */
	@Override
	@Transactional
	public ChangeGroupResult change(ChangeGroupCommand cmd) {

		GroupEntity entity = jpaGroupRepository.findById(cmd.groupId()).orElseThrow(
			() -> new IllegalArgumentException("group not Exists")
		);

		entity.change(cmd);

		return new ChangeGroupResult(GroupMapper.toDomain(entity));
	}
}
