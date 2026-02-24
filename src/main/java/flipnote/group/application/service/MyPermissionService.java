package flipnote.group.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import flipnote.group.adapter.out.entity.RoleEntity;
import flipnote.group.application.port.in.MyPermissionUseCase;
import flipnote.group.application.port.in.command.MyPermissionCommand;
import flipnote.group.application.port.in.result.MyPermissionResult;
import flipnote.group.application.port.out.GroupMemberRepositoryPort;
import flipnote.group.application.port.out.GroupRoleRepositoryPort;
import flipnote.group.domain.model.member.GroupMember;
import flipnote.group.domain.model.permission.GroupPermission;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPermissionService implements MyPermissionUseCase {

	private final GroupMemberRepositoryPort groupMemberRepository;
	private final GroupRoleRepositoryPort groupRoleRepository;

	@Override
	public MyPermissionResult findMyPermission(MyPermissionCommand cmd) {

		GroupMember groupMember = groupMemberRepository.findMyRole(cmd.groupId(), cmd.userId());

		List<GroupPermission> permissions = groupRoleRepository.findMyRolePermission(groupMember.getGroupId(), groupMember.getRole().getRole());

		return new MyPermissionResult(groupMember.getRole().getRole(), permissions);
	}
}
