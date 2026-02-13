package flipnote.group.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import flipnote.group.adapter.out.entity.GroupEntity;
import flipnote.group.adapter.out.persistence.mapper.GroupMapper;
import flipnote.group.application.port.in.FindGroupUseCase;
import flipnote.group.application.port.in.command.FindGroupCommand;
import flipnote.group.application.port.in.result.FindGroupResult;
import flipnote.group.application.port.out.GroupMemberRepositoryPort;
import flipnote.group.application.port.out.GroupRepositoryPort;
import flipnote.group.domain.model.group.Group;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindGroupService implements FindGroupUseCase {

	private final GroupRepositoryPort groupRepository;
	private final GroupMemberRepositoryPort groupMemberRepository;

	/**
	 * 하나의 그룹에 대한 정보 조회
	 * @param cmd
	 * @return
	 */
	@Override
	public FindGroupResult findGroup(FindGroupCommand cmd) {

		// 유저가 그룹 내에 존재하는지 확인
		boolean isMember = groupMemberRepository.existsUserInGroup(cmd.groupId(), cmd.userId());

		if(!isMember) {
			throw new IllegalArgumentException("user not in Group");
		}

		Group group = groupRepository.findById(cmd.groupId());

		return new FindGroupResult(group);
	}
}
