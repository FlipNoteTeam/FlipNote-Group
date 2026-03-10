package flipnote.group.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flipnote.group.application.port.in.FindGroupMemberUseCase;
import flipnote.group.application.port.in.command.FindGroupMemberCommand;
import flipnote.group.application.port.in.result.FindGroupMemberResult;
import flipnote.group.application.port.out.GroupMemberRepositoryPort;
import flipnote.group.domain.model.member.MemberInfo;
import flipnote.group.domain.policy.BusinessException;
import flipnote.group.domain.policy.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindGroupMemberService implements FindGroupMemberUseCase {

	private final GroupMemberRepositoryPort groupMemberRepository;

	/**
	 * 그룹 멤버 조회
	 * @param cmd
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public FindGroupMemberResult findGroupMember(FindGroupMemberCommand cmd) {

		boolean isMember = groupMemberRepository.existsUserInGroup(cmd.groupId(), cmd.userId());

		if(!isMember) {
			throw new BusinessException(ErrorCode.USER_NOT_IN_GROUP);
		}

		List<MemberInfo> memberInfoList = groupMemberRepository.findMemberInfo(cmd.groupId());
		
		//todo 멤버 정보에서 유저 아이디를 grpc를 통해 가져온 후 반환

		return new FindGroupMemberResult(memberInfoList);
	}
}
