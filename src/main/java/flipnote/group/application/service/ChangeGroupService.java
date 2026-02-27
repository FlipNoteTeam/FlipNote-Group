package flipnote.group.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flipnote.group.adapter.out.entity.GroupEntity;
import flipnote.group.adapter.out.persistence.GroupRoleRepositoryAdapter;
import flipnote.group.application.port.in.ChangeGroupUseCase;
import flipnote.group.application.port.in.command.ChangeGroupCommand;
import flipnote.group.application.port.in.result.ChangeGroupResult;
import flipnote.group.domain.model.member.GroupMemberRole;
import flipnote.group.infrastructure.persistence.querydsl.GroupRepository;
import flipnote.image.grpc.v1.GetUrlByReferenceRequest;
import flipnote.image.grpc.v1.GetUrlByReferenceResponse;
import flipnote.image.grpc.v1.ImageCommandServiceGrpc;
import flipnote.image.grpc.v1.Type;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChangeGroupService implements ChangeGroupUseCase {

	private final GroupRepository jpaGroupRepository;
	private final GroupRoleRepositoryAdapter groupRoleRepository;
	private final ImageCommandServiceGrpc.ImageCommandServiceBlockingStub imageCommandServiceStub;

	/**
	 * 그룹 수정
	 * @param cmd
	 * @return
	 */
	@Override
	@Transactional
	public ChangeGroupResult change(ChangeGroupCommand cmd) {

		GroupEntity entity = jpaGroupRepository.findById(cmd.groupId()).orElseThrow(
			() -> new IllegalArgumentException("group not Exists")
		);

		//오너 인지 확인
		boolean isOwner = groupRoleRepository.checkRole(cmd.userId(), entity.getId(), GroupMemberRole.OWNER);

		if(!isOwner) {
			throw new IllegalArgumentException("not owner");
		}

		entity.change(cmd);

		// gRPC로 image 서비스에 url 조회
		GetUrlByReferenceRequest request = GetUrlByReferenceRequest.newBuilder()
			.setReferenceType(Type.GROUP)
			.setReferenceId(cmd.groupId())
			.build();

		GetUrlByReferenceResponse response = imageCommandServiceStub.getUrlByReference(request);
		String imageUrl = response.getImageUrl();

		return ChangeGroupResult.of(entity, imageUrl);
	}
}
