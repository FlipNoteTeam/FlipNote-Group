package flipnote.group.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import flipnote.group.adapter.out.entity.GroupEntity;
import flipnote.group.adapter.out.persistence.mapper.GroupMapper;
import flipnote.group.application.port.in.FindGroupUseCase;
import flipnote.group.application.port.in.command.FindGroupCommand;
import flipnote.group.application.port.in.result.FindGroupResult;
import flipnote.group.application.port.out.GroupRepositoryPort;
import flipnote.group.domain.model.group.Group;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindGroupService implements FindGroupUseCase {

	private final GroupRepositoryPort groupRepository;

	/**
	 * 하나의 그룹에 대한 정보 조회
	 * @param cmd
	 * @return
	 */
	@Override
	public FindGroupResult findGroup(FindGroupCommand cmd) {

		GroupEntity groupEntity = groupRepository.findById(cmd.groupId()).orElseThrow(
			() -> new IllegalArgumentException("Group not Exists")
		);

		Group group = GroupMapper.toDomain(groupEntity);

		return new FindGroupResult(group);
	}
}
