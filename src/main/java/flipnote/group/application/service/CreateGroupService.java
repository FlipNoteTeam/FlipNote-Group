package flipnote.group.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flipnote.group.adapter.out.entity.RoleEntity;
import flipnote.group.adapter.out.persistence.mapper.GroupMapper;
import flipnote.group.application.port.in.CreateGroupUseCase;
import flipnote.group.application.port.in.command.CreateGroupCommand;
import flipnote.group.application.port.in.result.CreateGroupResult;
import flipnote.group.application.port.out.GroupMemberRepositoryPort;
import flipnote.group.application.port.out.GroupRepositoryPort;
import flipnote.group.application.port.out.GroupRoleRepositoryPort;
import flipnote.group.domain.model.group.Group;
import flipnote.group.domain.model.member.GroupMemberRole;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateGroupService implements CreateGroupUseCase {

	private final GroupRepositoryPort groupRepository;
	private final GroupMemberRepositoryPort groupMemberRepository;
	private final GroupRoleRepositoryPort groupRoleRepository;

	/**
	 * 그룹 생성
	 * @param cmd
	 * @return
	 */
	@Override
	@Transactional
	public CreateGroupResult create(CreateGroupCommand cmd) {

		//도메인 생성 및 검증
		var domainGroup = Group.create(cmd);
		
		//그룹 도메인 -> 엔티티 변환 후 저장
		Long groupId = groupRepository.saveNewGroup(domainGroup);
		
		//그룹 역할 생성
		groupRoleRepository.create(groupId);

		//생성자 오너 역할로 저장
		groupMemberRepository.save(groupId, cmd.userId(), GroupMemberRole.OWNER);
		
		return new CreateGroupResult(groupId);
	}
}
