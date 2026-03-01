package flipnote.group.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flipnote.group.application.port.in.RemovePermissionUseCase;
import flipnote.group.application.port.in.command.PermissionCommand;
import flipnote.group.application.port.in.result.AddPermissionResult;
import flipnote.group.application.port.in.result.RemovePermissionResult;
import flipnote.group.application.port.out.GroupRoleRepositoryPort;
import flipnote.group.domain.model.member.GroupMemberRole;
import flipnote.group.domain.model.permission.GroupPermission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemovePermissionService implements RemovePermissionUseCase {

	private final GroupRoleRepositoryPort groupRoleRepository;

	@Override
	@Transactional
	public RemovePermissionResult removePermission(PermissionCommand cmd) {
		GroupMemberRole role = groupRoleRepository.findRole(cmd.userId(), cmd.groupId());

		//권한이 낮을 경우

		log.debug("{} {}", role, cmd.changeRole());

		if(!role.isHigherThan(cmd.changeRole())) {
			throw new IllegalArgumentException("host lower than changeRole");
		}

		//호스트의 권한이 있는지
		boolean existHostPermission = groupRoleRepository.checkPermission(cmd.userId(), cmd.groupId(), cmd.permission());

		if(!existHostPermission) {
			throw new IllegalArgumentException("host not exist permission");
		}

		//바꿀 역할의 권한이 있는지 확인
		boolean existPermission = groupRoleRepository.existPermission(cmd.changeRole(), cmd.groupId(), cmd.permission());

		if(!existPermission) {
			throw new IllegalArgumentException("role not exist permission");
		}

		List<GroupPermission> groupPermissions = groupRoleRepository.removePermission(cmd.groupId(), cmd.changeRole(),
			cmd.permission());

		return RemovePermissionResult.of(groupPermissions, cmd.changeRole());
	}
}
