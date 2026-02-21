package flipnote.group.adapter.in.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/groups/{groupId}")
public class PermissionController {
	//todo 하위 권한 수정 -> 유저 아이디, 하위 그거, 하위 권한

	//todo 그룹 멤버 추방

	//todo 특정 그룹의 내 권한 확인
}
