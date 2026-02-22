package flipnote.group.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flipnote.group.application.port.in.AddPermissionUseCase;
import flipnote.group.application.port.in.command.AddPermissionCommand;
import flipnote.group.application.port.in.result.AddPermissionResult;
import flipnote.group.application.port.out.GroupRoleRepositoryPort;
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

		boolean isRole = groupRoleRepository.checkRole(cmd.userId(), cmd.groupId(), cmd.hostRole());

		//호스트의 역할이 일치 한지
		if(!isRole) {
			throw new IllegalArgumentException("not equals role");
		}

		//호스트의 권한이 있는지
		boolean existHostPermission = groupRoleRepository.checkPermission(cmd.userId(), cmd.groupId(), cmd.permission());

		if(!existHostPermission) {
			throw new IllegalArgumentException("host not exist permission");
		}

		boolean existPermission = groupRoleRepository.existPermission(cmd.changeRole(), cmd.groupId(), cmd.permission());

		if(existPermission) {
			throw new IllegalArgumentException("already exist permission");
		}

		//권한이 낮을 경우
		if(!cmd.hostRole().isHigherThan(cmd.changeRole())) {
			throw new IllegalArgumentException("host lower than changeRole");
		}

		List<GroupPermission> groupPermissions = groupRoleRepository.addPermission(cmd.groupId(), cmd.changeRole(),
			cmd.permission());

		return new AddPermissionResult(groupPermissions);
	}
}
