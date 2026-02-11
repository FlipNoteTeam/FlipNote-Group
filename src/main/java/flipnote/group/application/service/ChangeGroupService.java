package flipnote.group.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flipnote.group.adapter.out.entity.GroupEntity;
import flipnote.group.adapter.out.persistence.mapper.GroupMapper;
import flipnote.group.application.port.in.ChangeGroupUseCase;
import flipnote.group.application.port.in.command.ChangeGroupCommand;
import flipnote.group.application.port.in.result.ChangeGroupResult;
import flipnote.group.application.port.out.GroupRepositoryPort;
import flipnote.group.domain.model.group.Group;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChangeGroupService implements ChangeGroupUseCase {

	private final GroupRepositoryPort groupRepository;

	/**
	 * 그룹 수정
	 * @param cmd
	 * @return
	 */
	@Override
	@Transactional
	public ChangeGroupResult change(ChangeGroupCommand cmd) {

		Group findGroup = groupRepository.findById(cmd.groupId());

		findGroup.change(cmd);

		Group group = groupRepository.update(findGroup);

		return new ChangeGroupResult(group);
	}
}
