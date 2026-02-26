package flipnote.group.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flipnote.group.adapter.out.entity.GroupEntity;
import flipnote.group.application.port.in.FindGroupUseCase;
import flipnote.group.application.port.in.command.FindGroupCommand;
import flipnote.group.application.port.in.result.FindGroupResult;
import flipnote.group.application.port.out.GroupMemberRepositoryPort;
import flipnote.group.application.port.out.GroupRepositoryPort;
import flipnote.image.grpc.v1.GetUrlByReferenceRequest;
import flipnote.image.grpc.v1.GetUrlByReferenceResponse;
import flipnote.image.grpc.v1.ImageCommandServiceGrpc;
import flipnote.image.grpc.v1.Type;
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

		GetUrlByReferenceResponse response = imageCommandServiceStub.getUrlByReference(request);
		String imageUrl = response.getImageUrl();

		return FindGroupResult.of(group, imageUrl);
	}
}
