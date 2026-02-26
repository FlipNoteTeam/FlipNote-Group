package flipnote.group.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flipnote.group.adapter.out.entity.GroupEntity;
import flipnote.group.adapter.out.entity.GroupMemberEntity;
import flipnote.group.adapter.out.entity.RoleEntity;
import flipnote.group.application.port.in.CreateGroupUseCase;
import flipnote.group.application.port.in.command.CreateGroupCommand;
import flipnote.group.application.port.in.result.CreateGroupResult;
import flipnote.group.application.port.out.GroupMemberRepositoryPort;
import flipnote.group.application.port.out.GroupRepositoryPort;
import flipnote.group.application.port.out.GroupRoleRepositoryPort;
import flipnote.image.grpc.v1.ActivateImageRequest;
import flipnote.image.grpc.v1.ActivateImageResponse;
import flipnote.image.grpc.v1.GetUrlByReferenceRequest;
import flipnote.image.grpc.v1.GetUrlByReferenceResponse;
import flipnote.image.grpc.v1.ImageCommandServiceGrpc;
import flipnote.image.grpc.v1.Type;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateGroupService implements CreateGroupUseCase {

	private final GroupRepositoryPort groupRepository;
	private final GroupMemberRepositoryPort groupMemberRepository;
	private final GroupRoleRepositoryPort groupRoleRepository;
	private final ImageCommandServiceGrpc.ImageCommandServiceBlockingStub imageCommandServiceStub;

	/**
	 * 그룹 생성
	 * @param cmd
	 * @return
	 */
	@Override
	@Transactional
	public CreateGroupResult create(CreateGroupCommand cmd) {

		GroupEntity group = GroupEntity.create(cmd);

		//그룹 도메인 -> 엔티티 변환 후 저장
		Long groupId = groupRepository.saveNewGroup(group);
		
		//그룹 역할 생성
		RoleEntity role = groupRoleRepository.create(groupId);

		//생성자 오너 역할로 저장
		GroupMemberEntity groupMember = GroupMemberEntity.create(groupId, cmd.userId(), role);

		groupMemberRepository.save(groupMember);

		// gRPC로 image 서비스에 url 조회
		ActivateImageRequest request = ActivateImageRequest.newBuilder()
			.setImageRefId(cmd.imageRefId())
			.setReferenceType(Type.GROUP)
			.setReferenceId(groupId)
			.build();

		try {
			imageCommandServiceStub.activateImage(request);
		} catch (StatusRuntimeException e) {
			switch (e.getStatus().getCode()) {
				case NOT_FOUND -> throw new IllegalArgumentException("이미지를 찾을 수 없습니다.");
				case INVALID_ARGUMENT -> throw new IllegalArgumentException("잘못된 요청입니다.");
				case INTERNAL -> throw new IllegalArgumentException("이미지 서버 내부 오류입니다.");
				default -> throw new IllegalArgumentException("이미지 서비스 오류: " + e.getStatus().getDescription());
			}
		}

		return CreateGroupResult.of(groupId);
	}
}
