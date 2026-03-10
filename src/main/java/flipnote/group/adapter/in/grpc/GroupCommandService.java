package flipnote.group.adapter.in.grpc;

import org.springframework.grpc.server.service.GrpcService;

import flipnote.group.application.port.in.CheckUserInGroupUseCase;
import flipnote.group.application.port.in.FindGroupNameUseCase;
import flipnote.group.grpc.v1.CheckUserInGroupRequest;
import flipnote.group.grpc.v1.CheckUserInGroupResponse;
import flipnote.group.grpc.v1.GetGroupNameRequest;
import flipnote.group.grpc.v1.GetGroupNameResponse;
import flipnote.group.grpc.v1.GroupCommandServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;

@GrpcService
@RequiredArgsConstructor
public class GroupCommandService extends GroupCommandServiceGrpc.GroupCommandServiceImplBase {

	private final FindGroupNameUseCase findGroupNameUseCase;
	private final CheckUserInGroupUseCase checkUserInGroupUseCase;

	@Override
	public void getGroupName(GetGroupNameRequest request, StreamObserver<GetGroupNameResponse> responseObserver) {
		try {
			String groupName = findGroupNameUseCase.findGroupName(request.getGroupId());

			GetGroupNameResponse res = GetGroupNameResponse.newBuilder()
				.setGroupName(groupName)
				.build();

			responseObserver.onNext(res);
			responseObserver.onCompleted();
		} catch (Exception e) {
			responseObserver.onError(e);
		}
	}

	@Override
	public void checkUserInGroup(CheckUserInGroupRequest request,
		StreamObserver<CheckUserInGroupResponse> responseObserver) {
		try {

			boolean isMember = checkUserInGroupUseCase.checkUserInGroup(request.getUserId(), request.getGroupId());

			CheckUserInGroupResponse res = CheckUserInGroupResponse.newBuilder()
				.setExists(isMember)
				.build();

			responseObserver.onNext(res);
			responseObserver.onCompleted();
		} catch (Exception e) {
			responseObserver.onError(e);
		}
	}
}
