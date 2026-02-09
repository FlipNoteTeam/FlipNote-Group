package flipnote.group.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import flipnote.group.api.dto.request.ChangeGroupRequestDto;
import flipnote.group.api.dto.request.CreateGroupRequestDto;
import flipnote.group.api.dto.response.ChangeGroupResponseDto;
import flipnote.group.api.dto.response.CreateGroupResponseDto;
import flipnote.group.api.dto.response.FindGroupResponseDto;
import flipnote.group.application.port.in.ChangeGroupUseCase;
import flipnote.group.application.port.in.CreateGroupUseCase;
import flipnote.group.application.port.in.FindGroupUseCase;
import flipnote.group.application.port.in.command.ChangeGroupCommand;
import flipnote.group.application.port.in.command.CreateGroupCommand;
import flipnote.group.application.port.in.command.FindGroupCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/groups")
public class GroupController {

	private final CreateGroupUseCase createGroupUseCase;
	private final ChangeGroupUseCase changeGroupUseCase;
	private final FindGroupUseCase findGroupUseCase;

	/**
	 * 그룹 생성 API
	 * @param userId
	 * @param req
	 * @return
	 */
	@PostMapping("")
	public ResponseEntity<CreateGroupResponseDto> createGroup(
		@RequestHeader("X-USER-ID") Long userId,
	    @RequestBody @Valid CreateGroupRequestDto req) {

		CreateGroupCommand cmd = new CreateGroupCommand(
			userId,
			req.name(),
			req.category(),
			req.description(),
			req.joinPolicy(),
			req.visibility(),
			req.maxMember(),
			req.imageRefId()
		);

		var result = createGroupUseCase.create(cmd);
		CreateGroupResponseDto res = CreateGroupResponseDto.from(result.groupId());
		return ResponseEntity.ok(res);
	}

	/**
	 * 그룹 수정 API
	 * @param userId
	 * @param groupId
	 * @param req
	 * @return
	 */
	@PutMapping("/{groupId}")
	public ResponseEntity<ChangeGroupResponseDto> changeGroup(
		@RequestHeader("X-USER-ID") Long userId,
		@PathVariable Long groupId,
		@RequestBody @Valid ChangeGroupRequestDto req) {

		ChangeGroupCommand cmd = new ChangeGroupCommand(
			groupId,
			userId,
			req.name(),
			req.category(),
			req.description(),
			req.joinPolicy(),
			req.visibility(),
			req.maxMember(),
			req.imageRefId()
		);

		var result = changeGroupUseCase.change(cmd);

		ChangeGroupResponseDto res = ChangeGroupResponseDto.from(result);
		return ResponseEntity.ok(res);
	}

	/**
	 * 그룹 상세 조회 API
	 * @param userId
	 * @param groupId
	 * @return
	 */
	@GetMapping("/{groupId}")
	public ResponseEntity<FindGroupResponseDto> findGroup(
		@RequestHeader("X-USER-ID") Long userId,
		@PathVariable("groupId") Long groupId) {

		FindGroupCommand cmd = new FindGroupCommand(userId, groupId);

		var result = findGroupUseCase.findGroup(cmd);

		FindGroupResponseDto res = FindGroupResponseDto.from(result);

		return ResponseEntity.ok(res);
	}

}
