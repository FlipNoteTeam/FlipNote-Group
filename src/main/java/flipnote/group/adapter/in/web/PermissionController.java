package flipnote.group.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import flipnote.group.api.dto.request.AddPermissionRequestDto;
import flipnote.group.api.dto.response.AddPermissionResponseDto;
import flipnote.group.application.port.in.AddPermissionUseCase;
import flipnote.group.application.port.in.command.AddPermissionCommand;
import flipnote.group.application.port.in.result.AddPermissionResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/groups/{groupId}")
public class PermissionController {
	
	private final AddPermissionUseCase addPermissionUseCase;

	/**
	 * 하위 권한 추가
	 */
	@PostMapping("/permissions")
	public ResponseEntity<AddPermissionResponseDto> addDownPermission(
		@RequestHeader("X-USER-ID") Long userId,
		@PathVariable("groupId") Long groupId,
		@Valid @RequestBody AddPermissionRequestDto req) {

		AddPermissionCommand cmd = new AddPermissionCommand(userId, groupId, req.changeRole(), req.permission());

		AddPermissionResult result = addPermissionUseCase.addPermission(cmd);

		AddPermissionResponseDto res = AddPermissionResponseDto.from(result);

		return ResponseEntity.ok(res);
	}


	//todo 그룹 멤버 추방

	//todo 특정 그룹의 내 권한 확인
}
