package flipnote.group.adapter.in.grpc;

import org.springframework.grpc.server.service.GrpcService;

import flipnote.group.application.port.in.FindGroupNameUseCase;
import flipnote.group.grpc.v1.GetGroupNameRequest;
import flipnote.group.grpc.v1.GetGroupNameResponse;
import flipnote.group.grpc.v1.GroupCommandServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;

@GrpcService
@RequiredArgsConstructor
public class GroupCommandService extends GroupCommandServiceGrpc.GroupCommandServiceImplBase {

	private final FindGroupNameUseCase findGroupNameUseCase;

	@Override
	public void getGroupName(GetGroupNameRequest request, StreamObserver<GetGroupNameResponse> responseObserver) {



		super.getGroupName(request, responseObserver);
	}
}
