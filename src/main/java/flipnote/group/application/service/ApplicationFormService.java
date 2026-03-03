package flipnote.group.application.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flipnote.group.adapter.out.entity.GroupEntity;
import flipnote.group.adapter.out.entity.GroupMemberEntity;
import flipnote.group.adapter.out.entity.JoinEntity;
import flipnote.group.adapter.out.entity.RoleEntity;
import flipnote.group.application.port.in.JoinUseCase;
import flipnote.group.application.port.in.command.ApplicationFormCommand;
import flipnote.group.application.port.in.command.FindJoinFormCommand;
import flipnote.group.application.port.in.result.ApplicationFormResult;
import flipnote.group.application.port.in.result.FindJoinFormListResult;
import flipnote.group.application.port.out.GroupMemberRepositoryPort;
import flipnote.group.application.port.out.GroupRepositoryPort;
import flipnote.group.application.port.out.GroupRoleRepositoryPort;
import flipnote.group.application.port.out.JoinRepositoryPort;
import flipnote.group.domain.model.group.JoinPolicy;
import flipnote.group.domain.model.group.Visibility;
import flipnote.group.domain.model.join.JoinStatus;
import flipnote.group.domain.model.member.GroupMemberRole;
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
public class ApplicationFormService implements JoinUseCase {
	
	private final GroupRepositoryPort groupRepository;
	private final JoinRepositoryPort joinRepository;
	private final GroupMemberRepositoryPort groupMemberRepository;
	private final GroupRoleRepositoryPort groupRoleRepository;
	private final UserQueryServiceGrpc.UserQueryServiceBlockingStub userQueryServiceStub;

	private static final GroupPermission JOIN_MANAGE = GroupPermission.JOIN_REQUEST_MANAGE;

	/**
	 * 가입 신청 요청
	 * @param cmd
	 * @return
	 */
	@Override
	@Transactional
	public ApplicationFormResult joinRequest(ApplicationFormCommand cmd) {

		//그룹 조회
		GroupEntity group = groupRepository.findById(cmd.groupId());
		
		checkJoinable(group);

		//이미 가입 신청 여부
		if(joinRepository.existsJoin(cmd.groupId(), cmd.userId())) {
			throw new BusinessException(ErrorCode.JOIN_ALREADY_EXISTS);
		}

		JoinStatus status = JoinStatus.ACCEPT;
		if(group.getJoinPolicy().equals(JoinPolicy.APPROVAL)) {
			status = JoinStatus.PENDING;
		}

		JoinEntity join = JoinEntity.create(cmd.groupId(), cmd.userId(), cmd.joinIntro(), status);

		joinRepository.save(join);

		if(join.getStatus().equals(JoinStatus.ACCEPT)) {
			RoleEntity role = groupRoleRepository.findByIdAndRole(group.getId(), GroupMemberRole.MEMBER);

			GroupMemberEntity groupMember = GroupMemberEntity.create(group.getId(), cmd.userId(), role);
			groupMemberRepository.save(groupMember);
		}

		return ApplicationFormResult.of(join);
	}

	/**
	 * 가입 신청 리스트 조회
	 * @param cmd
	 * @return
	 */
	@Override
	public FindJoinFormListResult findJoinFormList(FindJoinFormCommand cmd) {

		boolean checkPermission = groupRoleRepository.checkPermission(cmd.userId(), cmd.groupId(), JOIN_MANAGE);

		if(!checkPermission) {
			throw new BusinessException(ErrorCode.PERMISSION_DENIED);
		}

		List<JoinEntity> joinList = joinRepository.findFormList(cmd.groupId());

		// userId 목록 추출
		List<Long> userIds = joinList.stream()
			.map(JoinEntity::getUserId)
			.toList();

		// gRPC로 유저 정보 한번에 조회
		GetUsersResponse usersResponse = userQueryServiceStub.getUsers(
			GetUsersRequest.newBuilder()
				.addAllUserIds(userIds)
				.build()
		);

		// userId -> UserResponse 맵핑
		Map<Long, GetUserResponse> userMap = usersResponse.getUsersList().stream()
			.collect(Collectors.toMap(GetUserResponse::getId, u -> u));

		return FindJoinFormListResult.of(joinList, userMap);
	}

	private void checkJoinable(GroupEntity group) {
		//비공개 그룹 인지 확인
		if(group.getVisibility().equals(Visibility.PRIVATE)) {
			throw new BusinessException(ErrorCode.GROUP_PRIVATE);
		}

		//멤버가 최대인 경우
		if(group.getMemberCount() >= group.getMaxMember()) {
			throw new BusinessException(ErrorCode.GROUP_MEMBER_LIMIT_EXCEEDED);
		}
	}
}
