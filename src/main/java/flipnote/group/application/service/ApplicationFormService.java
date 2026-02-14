package flipnote.group.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flipnote.group.adapter.out.persistence.GroupMemberRepositoryAdapter;
import flipnote.group.adapter.out.persistence.GroupRepositoryAdapter;
import flipnote.group.adapter.out.persistence.JoinRepositoryAdapter;
import flipnote.group.adapter.out.persistence.mapper.JoinMapper;
import flipnote.group.application.port.in.JoinUseCase;
import flipnote.group.application.port.in.command.ApplicationFormCommand;
import flipnote.group.application.port.in.result.ApplicationFormResult;
import flipnote.group.domain.model.group.Group;
import flipnote.group.domain.model.group.JoinPolicy;
import flipnote.group.domain.model.group.Visibility;
import flipnote.group.domain.model.join.JoinDomain;
import flipnote.group.domain.model.join.JoinStatus;
import flipnote.group.domain.model.member.GroupMemberRole;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationFormService implements JoinUseCase {
	
	private final GroupRepositoryAdapter groupRepository;
	private final JoinRepositoryAdapter joinRepository;
	private final GroupMemberRepositoryAdapter groupMemberRepository;

	/**
	 * 가입 신청 요청
	 * @param cmd
	 * @return
	 */
	@Override
	@Transactional
	public ApplicationFormResult joinRequest(ApplicationFormCommand cmd) {

		//그룹 조회
		Group group = groupRepository.findById(cmd.groupId());
		
		checkJoinable(group);

		//이미 가입 신청 여부
		if(joinRepository.existsJoin(cmd.groupId(), cmd.userId())) {
			throw new IllegalArgumentException("already join");
		}

		JoinStatus status = JoinStatus.ACCEPT;
		if(group.getJoinPolicy().equals(JoinPolicy.OPEN)) {
			status = JoinStatus.PENDING;
		}

		JoinDomain domain = JoinMapper.createNewDomain(cmd.groupId(), cmd.userId(), cmd.joinIntro(), status);

		JoinDomain join = joinRepository.save(domain);

		if(join.getStatus().equals(JoinStatus.ACCEPT)) {
			groupMemberRepository.save(cmd.groupId(), cmd.userId(), GroupMemberRole.MEMBER);
		}

		return new ApplicationFormResult(join);
	}

	private void checkJoinable(Group group) {
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
