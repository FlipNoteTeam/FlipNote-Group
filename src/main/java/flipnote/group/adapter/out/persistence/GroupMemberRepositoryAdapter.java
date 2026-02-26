package flipnote.group.adapter.out.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import flipnote.group.adapter.out.entity.GroupMemberEntity;
import flipnote.group.application.port.out.GroupMemberRepositoryPort;
import flipnote.group.domain.model.member.MemberInfo;
import flipnote.group.infrastructure.persistence.jpa.GroupMemberRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GroupMemberRepositoryAdapter implements GroupMemberRepositoryPort {

	private final GroupMemberRepository groupMemberRepository;

	/**
	 * 그룹 멤버 저장
	 * @param groupMember
	 */
	@Override
	public void save(GroupMemberEntity groupMember) {
		groupMemberRepository.save(groupMember);
	}

	/**
	 * 유저가 그룹 내에 있는지 체크
	 * @param groupId
	 * @param userId
	 * @return
	 */
	@Override
	public void existsUserInGroup(Long groupId, Long userId) {

		boolean isMember = groupMemberRepository.existsByGroupIdAndUserId(groupId, userId);

		if(!isMember) {
			throw new IllegalArgumentException("user not in Group");
		}
	}

	/**
	 * 그룹 멤버 아이디 조회
	 * @param groupId
	 * @return
	 */
	@Override
	public List<MemberInfo> findMemberInfo(Long groupId) {
		List<GroupMemberEntity> entities = groupMemberRepository.findAllByGroupId(groupId);


		return entities.stream()
			.map(GroupMemberEntity::toMemberInfo)
			.collect(Collectors.toList());
	}
}
