package flipnote.group.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flipnote.group.adapter.out.entity.GroupEntity;
import flipnote.group.adapter.out.entity.GroupMemberEntity;
import flipnote.group.adapter.out.entity.JoinEntity;
import flipnote.group.adapter.out.entity.RoleEntity;
import flipnote.group.application.port.in.JoinUseCase;
import flipnote.group.application.port.in.command.ApplicationFormCommand;
import flipnote.group.application.port.in.command.FindJoinFormCommand;
import flipnote.group.application.port.in.result.ApplicationFormResult;
import flipnote.group.application.port.in.result.FindJoinFormListResult;
import flipnote.group.application.port.out.GroupMemberRepositoryPort;
import flipnote.group.application.port.out.GroupRepositoryPort;
import flipnote.group.application.port.out.GroupRoleRepositoryPort;
import flipnote.group.application.port.out.JoinRepositoryPort;
import flipnote.group.domain.model.group.JoinPolicy;
import flipnote.group.domain.model.group.Visibility;
import flipnote.group.domain.model.join.JoinStatus;
import flipnote.group.domain.model.member.GroupMemberRole;
import flipnote.group.domain.model.permission.GroupPermission;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationFormService implements JoinUseCase {
	
	private final GroupRepositoryPort groupRepository;
	private final JoinRepositoryPort joinRepository;
	private final GroupMemberRepositoryPort groupMemberRepository;
	private final GroupRoleRepositoryPort groupRoleRepository;

	private static final GroupPermission JOIN_MANAGE = GroupPermission.JOIN_REQUEST_MANAGE;

	/**
	 * 가입 신청 요청
	 * @param cmd
	 * @return
	 */
	@Override
	@Transactional
	public ApplicationFormResult joinRequest(ApplicationFormCommand cmd) {

		//그룹 조회
		GroupEntity group = groupRepository.findById(cmd.groupId());
		
		checkJoinable(group);

		//이미 가입 신청 여부
		if(joinRepository.existsJoin(cmd.groupId(), cmd.userId())) {
			throw new IllegalArgumentException("already join");
		}

		JoinStatus status = JoinStatus.ACCEPT;
		if(group.getJoinPolicy().equals(JoinPolicy.APPROVAL)) {
			status = JoinStatus.PENDING;
		}

		JoinEntity join = JoinEntity.create(cmd.groupId(), cmd.userId(), cmd.joinIntro(), status);

		joinRepository.save(join);

		if(join.getStatus().equals(JoinStatus.ACCEPT)) {
			RoleEntity role = groupRoleRepository.findByIdAndRole(group.getId(), GroupMemberRole.MEMBER);

			GroupMemberEntity groupMember = GroupMemberEntity.create(group.getId(), cmd.userId(), role);
			groupMemberRepository.save(groupMember);
		}

		return ApplicationFormResult.of(join);
	}

	/**
	 * 가입 신청 리스트 조회
	 * @param cmd
	 * @return
	 */
	@Override
	public FindJoinFormListResult findJoinFormList(FindJoinFormCommand cmd) {

		boolean checkPermission = groupRoleRepository.checkPermission(cmd.userId(), cmd.groupId(), JOIN_MANAGE);

		if(!checkPermission) {
			throw new IllegalArgumentException("not permission");
		}

		List<JoinEntity> joinDomainList = joinRepository.findFormList(cmd.groupId());


		return FindJoinFormListResult.of(joinDomainList);
	}

	private void checkJoinable(GroupEntity group) {
		//비공개 그룹 인지 확인
		if(group.getVisibility().equals(Visibility.PRIVATE)) {
			throw new IllegalArgumentException("private group");
		}

		//멤버가 최대인 경우
		if(group.getMemberCount() >= group.getMaxMember()) {
			throw new IllegalArgumentException("max member");
		}
	}
}
