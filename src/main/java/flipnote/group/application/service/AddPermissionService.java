package flipnote.group.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flipnote.group.application.port.in.AddPermissionUseCase;
import flipnote.group.application.port.in.command.AddPermissionCommand;
import flipnote.group.application.port.in.result.AddPermissionResult;
import flipnote.group.application.port.out.GroupRoleRepositoryPort;
import flipnote.group.domain.model.member.GroupMemberRole;
import flipnote.group.domain.model.permission.GroupPermission;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddPermissionService implements AddPermissionUseCase {

	private final GroupRoleRepositoryPort groupRoleRepository;

	/**
	 * 권한 추가
	 * @param cmd
	 * @return
	 */
	@Override
	@Transactional
	public AddPermissionResult addPermission(AddPermissionCommand cmd) {

		GroupMemberRole role = groupRoleRepository.findRole(cmd.userId(), cmd.groupId());

		//호스트의 권한이 있는지
		boolean existHostPermission = groupRoleRepository.checkPermission(cmd.userId(), cmd.groupId(), cmd.permission());

		if(!existHostPermission) {
			throw new IllegalArgumentException("host not exist permission");
		}

		//권한이 낮을 경우
		if(!role.isHigherThan(cmd.changeRole())) {
			throw new IllegalArgumentException("host lower than changeRole");
		}

		boolean existPermission = groupRoleRepository.existPermission(cmd.changeRole(), cmd.groupId(), cmd.permission());

		if(existPermission) {
			throw new IllegalArgumentException("already exist permission");
		}

		List<GroupPermission> groupPermissions = groupRoleRepository.addPermission(cmd.groupId(), cmd.changeRole(),
			cmd.permission());

		return AddPermissionResult.of(groupPermissions, cmd.changeRole());
	}
}
