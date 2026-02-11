package flipnote.group.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flipnote.group.adapter.out.persistence.mapper.GroupMapper;
import flipnote.group.application.port.in.CreateGroupUseCase;
import flipnote.group.application.port.in.command.CreateGroupCommand;
import flipnote.group.application.port.in.result.CreateGroupResult;
import flipnote.group.application.port.out.GroupMemberRepositoryPort;
import flipnote.group.application.port.out.GroupRepositoryPort;
import flipnote.group.domain.model.group.Group;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateGroupService implements CreateGroupUseCase {

	private final GroupRepositoryPort groupRepository;
	private final GroupMemberRepositoryPort groupMemberRepository;

	/**
	 * 그룹 생성
	 * @param cmd
	 * @param userId
	 * @return
	 */
	@Override
	@Transactional
	public CreateGroupResult create(CreateGroupCommand cmd, Long userId) {

		//도메인 생성 및 검증
		var domainGroup = Group.create(cmd);
		
		//그룹 도메인 -> 엔티티 변환 후 저장
		Long groupId = groupRepository.saveNewGroup(domainGroup);

		//그룹 멤버 저장
		groupMemberRepository.saveOwner(groupId, userId);
		
		return new CreateGroupResult(groupId);
	}
}
