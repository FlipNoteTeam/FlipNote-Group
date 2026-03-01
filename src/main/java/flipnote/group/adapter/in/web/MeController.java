package flipnote.group.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import flipnote.group.api.dto.response.FindMyJoinListResponseDto;
import flipnote.group.application.port.in.FindMyJoinListUseCase;
import flipnote.group.application.port.in.result.FindMyJoinListResult;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class MeController {

	private final FindMyJoinListUseCase findMyJoinListUseCase;

	/**
	 * 내가 신청한 가입신청 리스트 조회
	 * @param userId
	 * @return
	 */
	@GetMapping("/joins/me")
	public ResponseEntity<FindMyJoinListResponseDto> findGroupJoinMe(
		@RequestHeader("X-USER-ID") Long userId
	) {
		FindMyJoinListResult result = findMyJoinListUseCase.findMyJoinList(userId);

		FindMyJoinListResponseDto res = FindMyJoinListResponseDto.from(result);

		return ResponseEntity.ok(res);
	}
}
