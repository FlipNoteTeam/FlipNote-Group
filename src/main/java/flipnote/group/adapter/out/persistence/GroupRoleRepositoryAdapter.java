package flipnote.group.adapter.out.persistence;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import flipnote.group.adapter.out.entity.PermissionEntity;
import flipnote.group.adapter.out.entity.RoleEntity;
import flipnote.group.application.port.out.GroupRoleRepositoryPort;
import flipnote.group.domain.model.member.GroupMemberRole;
import flipnote.group.domain.model.permission.GroupPermission;
import flipnote.group.infrastructure.persistence.jpa.GroupMemberRepositoryRepository;
import flipnote.group.infrastructure.persistence.jpa.GroupRolePermissionRepository;
import flipnote.group.infrastructure.persistence.jpa.GroupRoleRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GroupRoleRepositoryAdapter implements GroupRoleRepositoryPort {

	private final GroupRoleRepository groupRoleRepository;
	private final GroupRolePermissionRepository groupRolePermissionRepository;
	private final GroupMemberRepositoryRepository groupMemberRepository;

	private static final Map<GroupMemberRole, List<GroupPermission>> DEFAULT_PERMS_BY_ROLE =
		Map.of(
			GroupMemberRole.OWNER, List.of(
				GroupPermission.KICK,
				GroupPermission.JOIN_REQUEST_MANAGE
			),
			GroupMemberRole.HEAD_MANAGER, List.of(
				GroupPermission.KICK,
				GroupPermission.JOIN_REQUEST_MANAGE
			),
			GroupMemberRole.MANAGER, List.of(
				GroupPermission.KICK,
				GroupPermission.JOIN_REQUEST_MANAGE
			),
			GroupMemberRole.MEMBER, List.of()
		);

	/**
	 * 그룹 생성시 역할도 추가
	 * @param groupId
	 * @return
	 */
	@Override
	public RoleEntity create(Long groupId) {
		// 역할 생성
		Map<GroupMemberRole, RoleEntity> roleEntityByRole =
			Arrays.stream(new GroupMemberRole[]{
				GroupMemberRole.OWNER,
				GroupMemberRole.HEAD_MANAGER,
				GroupMemberRole.MANAGER,
				GroupMemberRole.MEMBER
			}).collect(Collectors.toMap(
				role -> role,
				role -> groupRoleRepository.save(RoleEntity.create(groupId, role))
			));

		// 권한 매핑 생성
		List<PermissionEntity> perms = DEFAULT_PERMS_BY_ROLE.entrySet().stream()
			.flatMap(e -> e.getValue().stream()
				.map(p -> PermissionEntity.create(
					roleEntityByRole.get(e.getKey()).getId(),  // roleId 사용
					p
				))
			)
			.toList();

		groupRolePermissionRepository.saveAll(perms);

		// 그룹 생성자에게 OWNER roleId 리턴 (바깥에서 group_members 생성할 때 사용)
		return roleEntityByRole.get(GroupMemberRole.OWNER);
	}

	/**
	 * 해당 유저가 그룹 내에 역할인지 확인
	 * 오너 여부에서 사용
	 * @param userId
	 * @param groupId
	 * @param groupMemberRole
	 * @return
	 */
	@Override
	public boolean checkRole(Long userId, Long groupId, GroupMemberRole groupMemberRole) {
		RoleEntity roleEntity = groupRoleRepository.findByGroupIdAndRole(groupId, groupMemberRole);

		return groupMemberRepository.existsByUserIdAndGroupRoleId(userId, roleEntity.getId());
	}
}
