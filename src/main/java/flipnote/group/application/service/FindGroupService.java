package flipnote.group.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flipnote.group.adapter.out.entity.GroupEntity;
import flipnote.group.api.dto.request.GroupListRequestDto;
import flipnote.group.api.dto.response.CursorPagingResponseDto;
import flipnote.group.application.port.in.FindGroupUseCase;
import flipnote.group.application.port.in.command.FindGroupCommand;
import flipnote.group.application.port.in.result.FindGroupResult;
import flipnote.group.application.port.out.GroupMemberRepositoryPort;
import flipnote.group.application.port.out.GroupRepositoryPort;
import flipnote.image.grpc.v1.GetUrlByReferenceRequest;
import flipnote.image.grpc.v1.GetUrlByReferenceResponse;
import flipnote.image.grpc.v1.ImageCommandServiceGrpc;
import flipnote.image.grpc.v1.Type;
import io.grpc.StatusRuntimeException;
import flipnote.group.domain.model.group.GroupInfo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindGroupService implements FindGroupUseCase {

	private final GroupRepositoryPort groupRepository;
	private final GroupMemberRepositoryPort groupMemberRepository;
	private final ImageCommandServiceGrpc.ImageCommandServiceBlockingStub imageCommandServiceStub;

	/**
	 * 하나의 그룹에 대한 정보 조회
	 * @param cmd
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public FindGroupResult findGroup(FindGroupCommand cmd) {

		// 유저가 그룹 내에 존재하는지 확인
		groupMemberRepository.existsUserInGroup(cmd.groupId(), cmd.userId());

		GroupEntity group = groupRepository.findById(cmd.groupId());

		// gRPC로 image 서비스에 url 조회
		GetUrlByReferenceRequest request = GetUrlByReferenceRequest.newBuilder()
			.setReferenceType(Type.GROUP)
			.setReferenceId(cmd.groupId())
			.build();

		String imageUrl;
		try {
			GetUrlByReferenceResponse response = imageCommandServiceStub.getUrlByReference(request);
			imageUrl = response.getImageUrl();
		} catch (StatusRuntimeException e) {
			switch (e.getStatus().getCode()) {
				case NOT_FOUND -> throw new IllegalArgumentException("이미지를 찾을 수 없습니다.");
				case INTERNAL -> throw new IllegalArgumentException("이미지 서버 내부 오류입니다.");
				default -> throw new IllegalArgumentException("이미지 서비스 오류: " + e.getStatus().getDescription());
			}
		}

		return FindGroupResult.of(group, imageUrl);
	}

	/**
	 * 전체 그룹 조회
	 * @param userId
	 * @param req
	 * @return
	 */
	@Override
	public CursorPagingResponseDto<GroupInfo> findAllGroup(Long userId, GroupListRequestDto req) {

		List<GroupInfo> groups = groupRepository.findAllByCursor(
			req.getCursorId(),
			req.getCategory(),
			req.getSize());

		return createGroupInfoCursorPagingResponse(req, groups);
	}

	/**
	 * 내가 가입한 그룹 전체 조회
	 * @param userId
	 * @param req
	 * @return
	 */
	@Override
	public CursorPagingResponseDto<GroupInfo> findMyGroup(Long userId, GroupListRequestDto req) {

		List<GroupInfo> groups = groupRepository.findAllByCursorAndUserId(
			req.getCursorId(),
			req.getCategory(),
			req.getSize(),
			userId);

		return createGroupInfoCursorPagingResponse(req, groups);
	}

	/**
	 * 내가 생성한 그룹 전체 조회
	 * @param userId
	 * @param req
	 * @return
	 */
	@Override
	public CursorPagingResponseDto<GroupInfo> findCreatedGroup(Long userId, GroupListRequestDto req) {

		List<GroupInfo> groups = groupRepository.findAllByCursorAndCreatedUserId(
			req.getCursorId(),
			req.getCategory(),
			req.getSize(),
			userId);

		return createGroupInfoCursorPagingResponse(req, groups);
	}

	/**
	 * 리스트 조회시 response 생성
	 */
	private CursorPagingResponseDto<GroupInfo> createGroupInfoCursorPagingResponse(GroupListRequestDto req,
		List<GroupInfo> groups) {
		boolean hasNext = groups.size() > req.getSize();

		if (hasNext) {
			groups = groups.subList(0, req.getSize());
		}

		Long nextCursor = hasNext ? groups.get(groups.size() - 1).groupId() : null;

		return CursorPagingResponseDto.of(groups, hasNext, nextCursor);
	}
}
