package flipnote.group.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flipnote.group.application.port.in.JoinRespondUseCase;
import flipnote.group.application.port.in.command.JoinRespondCommand;
import flipnote.group.application.port.in.result.JoinRespondResult;
import flipnote.group.application.port.out.GroupMemberRepositoryPort;
import flipnote.group.application.port.out.GroupRepositoryPort;
import flipnote.group.application.port.out.GroupRoleRepositoryPort;
import flipnote.group.application.port.out.JoinRepositoryPort;
import flipnote.group.domain.model.join.JoinDomain;
import flipnote.group.domain.model.join.JoinStatus;
import flipnote.group.domain.model.member.GroupMemberRole;
import flipnote.group.domain.model.permission.GroupPermission;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JoinRespondService implements JoinRespondUseCase {

	private final JoinRepositoryPort joinRepository;
	private final GroupRoleRepositoryPort groupRoleRepository;
	private final GroupRepositoryPort groupRepository;
	private final GroupMemberRepositoryPort groupMemberRepository;

	private static final GroupPermission MANAGE = GroupPermission.JOIN_REQUEST_MANAGE;

	/**
	 * 가입 신청 응답
	 * @param cmd
	 * @return
	 */
	@Override
	@Transactional
	public JoinRespondResult joinRespond(JoinRespondCommand cmd) {

		//권한 체크
		boolean existPermission = groupRoleRepository.checkPermission(cmd.userId(), cmd.groupId(), MANAGE);
		
		if(!existPermission) {
			throw new IllegalArgumentException("not permission");
		}

		JoinDomain joinDomain = joinRepository.findJoin(cmd.joinId());

		joinDomain.updateStatus(cmd.status());

		//거절일 경우
		if(cmd.status().equals(JoinStatus.REJECT)) {
			JoinDomain domain = joinRepository.updateJoin(joinDomain);

			return new JoinRespondResult(domain);
		}

		//수락일 경우
		boolean joinable = groupRepository.checkJoinable(cmd.groupId());
		
		//꽉찼을 경우
		if(joinable) {
			throw new IllegalArgumentException("max member");
		}
		
		//가입 가능한 경우
		groupMemberRepository.save(cmd.groupId(), cmd.userId(), GroupMemberRole.MEMBER);

		JoinDomain domain = joinRepository.updateJoin(joinDomain);

		return new JoinRespondResult(domain);
	}
}
