package flipnote.group.adapter.out.persistence;

import org.springframework.stereotype.Repository;

import flipnote.group.adapter.out.persistence.mapper.GroupMemberMapper;
import flipnote.group.application.port.out.GroupMemberRepositoryPort;
import flipnote.group.domain.model.member.GroupMemberRole;
import flipnote.group.infrastructure.persistence.jpa.GroupMemberRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GroupMemberRepositoryAdapter implements GroupMemberRepositoryPort {

	private final GroupMemberRepository groupMemberRepository;

	/**
	 *
	 * @param groupId
	 * @param userId
	 */
	@Override
	public void save(Long groupId, Long userId, Long roleId) {
		groupMemberRepository.save(GroupMemberMapper.create(groupId, userId, roleId));
	}
}
