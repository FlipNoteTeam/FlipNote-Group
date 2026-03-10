package flipnote.group.application.service;

import org.springframework.stereotype.Service;

import flipnote.group.application.port.in.CheckUserInGroupUseCase;
import flipnote.group.application.port.out.GroupMemberRepositoryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckUserInGroupService implements CheckUserInGroupUseCase {

	private final GroupMemberRepositoryPort groupMemberRepository;

	@Override
	public boolean checkUserInGroup(Long userId, Long groupId) {

		boolean isMember = groupMemberRepository.existsUserInGroup(groupId, userId);

		return isMember;
	}
}
