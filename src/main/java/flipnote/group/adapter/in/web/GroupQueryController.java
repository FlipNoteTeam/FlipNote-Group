package flipnote.group.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import flipnote.group.api.dto.request.GroupListRequestDto;
import flipnote.group.api.dto.response.CursorPagingResponseDto;
import flipnote.group.application.port.in.FindGroupUseCase;
import flipnote.group.domain.model.group.GroupInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/groups")
public class GroupQueryController {

	private final FindGroupUseCase findGroupUseCase;

	//그룹 전체 조회
	@GetMapping
	public ResponseEntity<CursorPagingResponseDto<GroupInfo>> findGroup(
		@RequestHeader("X-USER-ID") Long userId,
		@Valid @ModelAttribute GroupListRequestDto req
	) {
		CursorPagingResponseDto<GroupInfo> res = findGroupUseCase.findAllGroup(userId, req);

		return ResponseEntity.ok(res);
	}

	//내 그룹 전체 조회
	@GetMapping("/me")
	public ResponseEntity<CursorPagingResponseDto<GroupInfo>> findMyGroup(
		@RequestHeader("X-USER-ID") Long userId,
		@Valid @ModelAttribute GroupListRequestDto req
	) {
		CursorPagingResponseDto<GroupInfo> res = findGroupUseCase.findMyGroup(userId, req);

		return ResponseEntity.ok(res);
	}

	//내가 생성한 그룹 전체 조회
	@GetMapping("/created")
	public ResponseEntity<CursorPagingResponseDto<GroupInfo>> findCreatedGroup(
		@RequestHeader("X-USER-ID") Long userId,
		@Valid @ModelAttribute GroupListRequestDto req
	) {
		CursorPagingResponseDto<GroupInfo> res = findGroupUseCase.findCreatedGroup(userId, req);

		return ResponseEntity.ok(res);
	}
}
