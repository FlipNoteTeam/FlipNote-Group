package flipnote.group.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import flipnote.group.api.dto.response.FindGroupMemberResponseDto;
import flipnote.group.application.port.in.FindGroupMemberUseCase;
import flipnote.group.application.port.in.command.FindGroupMemberCommand;
import flipnote.group.application.port.in.result.FindGroupMemberResult;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/groups/{groupId}")
public class MemberController {

	private final FindGroupMemberUseCase findGroupMemberUseCase;

	/**
	 * 그룹 내 멤버 전체 조회
	 * @param userId
	 * @param groupId
	 * @return
	 */
	@GetMapping("/members")
	public ResponseEntity<FindGroupMemberResponseDto> findGroupMembers(
		@RequestHeader("X-USER-ID") Long userId,
		@PathVariable("groupId") Long groupId) {

		FindGroupMemberCommand cmd = new FindGroupMemberCommand(userId, groupId);

		FindGroupMemberResult result = findGroupMemberUseCase.findGroupMember(cmd);

		FindGroupMemberResponseDto res = FindGroupMemberResponseDto.from(result);

		return ResponseEntity.ok(res);
	}

	//todo 하위 권한 수정
}
