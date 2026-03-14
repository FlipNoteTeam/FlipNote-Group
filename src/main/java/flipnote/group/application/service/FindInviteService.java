package flipnote.group.application.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flipnote.group.adapter.out.entity.InviteEntity;
import flipnote.group.application.port.in.FindInviteUseCase;
import flipnote.group.application.port.in.command.FindOutgoingInviteCommand;
import flipnote.group.application.port.in.result.FindIncomingInviteListResult;
import flipnote.group.application.port.in.result.FindOutgoingInviteListResult;
import flipnote.group.application.port.out.GroupRoleRepositoryPort;
import flipnote.group.application.port.out.InviteRepositoryPort;
import flipnote.group.domain.model.invite.InviteInfo;
import flipnote.group.domain.model.invite.InviteMyInfo;
import flipnote.group.domain.model.permission.GroupPermission;
import flipnote.group.domain.policy.BusinessException;
import flipnote.group.domain.policy.ErrorCode;
import flipnote.user.grpc.GetUserResponse;
import flipnote.user.grpc.GetUsersRequest;
import flipnote.user.grpc.GetUsersResponse;
import flipnote.user.grpc.UserQueryServiceGrpc;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindInviteService implements FindInviteUseCase {

	private final InviteRepositoryPort inviteRepository;
	private final GroupRoleRepositoryPort groupRoleRepository;
	private final UserQueryServiceGrpc.UserQueryServiceBlockingStub userQueryServiceStub;

	private static final GroupPermission INVITE_PERMISSION = GroupPermission.INVITE;

	@Override
	public FindOutgoingInviteListResult findOutgoingInvites(FindOutgoingInviteCommand cmd) {
		// INVITE 권한 체크
		boolean hasPermission = groupRoleRepository.checkPermission(cmd.userId(), cmd.groupId(), INVITE_PERMISSION);
		if (!hasPermission) {
			throw new BusinessException(ErrorCode.INVITE_PERMISSION_DENIED);
		}

		List<InviteEntity> inviteList = inviteRepository.findAllByGroupId(cmd.groupId());

		// inviteeUserId 목록 추출 (null 제외 - 비회원 초대)
		List<Long> inviteeUserIds = inviteList.stream()
			.map(InviteEntity::getInviteeUserId)
			.filter(Objects::nonNull)
			.toList();

		// gRPC로 유저 닉네임 한번에 조회
		Map<Long, String> idAndNicknames = Map.of();
		if (!inviteeUserIds.isEmpty()) {
			GetUsersResponse usersResponse = userQueryServiceStub.getUsers(
				GetUsersRequest.newBuilder()
					.addAllUserIds(inviteeUserIds)
					.build()
			);
			idAndNicknames = usersResponse.getUsersList().stream()
				.collect(Collectors.toMap(GetUserResponse::getId, GetUserResponse::getNickname));
		}

		Map<Long, String> finalIdAndNicknames = idAndNicknames;
		List<InviteInfo> inviteInfoList = inviteList.stream()
			.map(invite -> InviteInfo.of(
				invite,
				finalIdAndNicknames.getOrDefault(invite.getInviteeUserId(), "")
			))
			.toList();

		return new FindOutgoingInviteListResult(inviteInfoList);
	}

	@Override
	public FindIncomingInviteListResult findIncomingInvites(Long inviteeUserId) {
		List<InviteEntity> inviteList = inviteRepository.findAllByInviteeUserId(inviteeUserId);

		List<InviteMyInfo> inviteMyInfoList = inviteList.stream()
			.map(InviteMyInfo::of)
			.toList();

		return new FindIncomingInviteListResult(inviteMyInfoList);
	}
}
