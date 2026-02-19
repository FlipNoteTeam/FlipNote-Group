package flipnote.group.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import flipnote.group.api.dto.request.ApplicationFormRequestDto;
import flipnote.group.api.dto.response.ApplicationFormResponseDto;
import flipnote.group.api.dto.response.FindJoinFormListResponseDto;
import flipnote.group.application.port.in.JoinUseCase;
import flipnote.group.application.port.in.command.ApplicationFormCommand;
import flipnote.group.application.port.in.command.FindJoinFormCommand;
import flipnote.group.application.port.in.result.ApplicationFormResult;
import flipnote.group.application.port.in.result.FindJoinFormListResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/groups/{groupId}")
@RequiredArgsConstructor
public class JoinController {

	private final JoinUseCase joinUseCase;

	/**
	 * 가입 신청 요청
	 * @param userId
	 * @param groupId
	 * @param req
	 * @return
	 */
	@PostMapping("/joins")
	public ResponseEntity<ApplicationFormResponseDto> joinRequest(
		@RequestHeader("X-USER-ID") Long userId,
		@PathVariable("groupId") Long groupId,
		@Valid @RequestBody ApplicationFormRequestDto req) {

		ApplicationFormCommand cmd = new ApplicationFormCommand(userId, groupId, req.joinIntro());

		ApplicationFormResult result = joinUseCase.joinRequest(cmd);

		ApplicationFormResponseDto res = ApplicationFormResponseDto.from(result.join());

		return ResponseEntity.status(HttpStatus.CREATED).body(res);
	}

	/**
	 * 그룹 가입 신청 리스트 조회
	 * @param userId
	 * @param groupId
	 * @return
	 */
	@GetMapping("/joins")
	public ResponseEntity<FindJoinFormListResponseDto> findGroupJoinList(
		@RequestHeader("X-USER-ID") Long userId,
		@PathVariable("groupId") Long groupId) {

		FindJoinFormCommand cmd = new FindJoinFormCommand(userId, groupId);

		FindJoinFormListResult result = joinUseCase.findJoinFormList(cmd);
		
		FindJoinFormListResponseDto res = FindJoinFormListResponseDto.from(result);

		return ResponseEntity.status(HttpStatus.CREATED).body(res);
	}
	
	//todo 가입신청 응답
	
	//todo 가입신청 삭제
	
	//todo 내가 신청한 가입신청 리스트 조회
}
