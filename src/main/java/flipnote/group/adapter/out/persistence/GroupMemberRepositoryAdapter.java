package flipnote.group.adapter.out.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import flipnote.group.adapter.out.entity.GroupMemberEntity;
import flipnote.group.adapter.out.entity.RoleEntity;
import flipnote.group.adapter.out.persistence.mapper.GroupMemberMapper;
import flipnote.group.application.port.out.GroupMemberRepositoryPort;
import flipnote.group.domain.model.member.GroupMember;
import flipnote.group.domain.model.member.MemberInfo;
import flipnote.group.infrastructure.persistence.jpa.GroupMemberRepositoryRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GroupMemberRepositoryAdapter implements GroupMemberRepositoryPort {

	private final GroupMemberRepositoryRepository groupMemberRepository;

	/**
	 * 그룹 멤버 저장
	 * @param groupId
	 * @param userId
	 */
	@Override
	public void save(Long groupId, Long userId, RoleEntity role) {
		groupMemberRepository.save(GroupMemberMapper.create(groupId, userId, role));
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

		List<MemberInfo> memberInfo = GroupMemberMapper.toMemberInfo(entities);

		return memberInfo;
	}
}
