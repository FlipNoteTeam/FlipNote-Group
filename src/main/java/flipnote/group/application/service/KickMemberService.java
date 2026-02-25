package flipnote.group.application.service;

import org.springframework.stereotype.Service;

import flipnote.group.application.port.in.KickMemberUseCase;
import flipnote.group.application.port.in.command.KickMemberCommand;
import flipnote.group.application.port.out.GroupMemberRepositoryPort;
import flipnote.group.application.port.out.GroupRoleRepositoryPort;
import flipnote.group.domain.model.permission.GroupPermission;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KickMemberService implements KickMemberUseCase {

	private final GroupMemberRepositoryPort groupMemberRepository;
	private final GroupRoleRepositoryPort groupRoleRepository;

	@Override
	public void kickMember(KickMemberCommand cmd) {
		//권한 체크
		boolean hasPermission = groupRoleRepository.checkPermission(cmd.userId(), cmd.groupId(), GroupPermission.MEMBER_MANAGE);
		if(!hasPermission) {
			throw new IllegalArgumentException("not exist permission");
		}

		groupMemberRepository.deleteGroupMember(cmd.memberId());
	}
}
