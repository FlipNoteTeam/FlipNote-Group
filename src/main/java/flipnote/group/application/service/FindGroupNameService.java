package flipnote.group.application.service;

import org.springframework.stereotype.Service;

import flipnote.group.application.port.in.FindGroupNameUseCase;
import flipnote.group.application.port.out.GroupRepositoryPort;
import flipnote.group.domain.model.group.Group;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindGroupNameService implements FindGroupNameUseCase {

	private final GroupRepositoryPort groupRepository;

	/**
	 * 그룹 명 조회
	 * @param groupId
	 * @return
	 */
	@Override
	public String findGroupName(Long groupId) {

		Group group = groupRepository.findById(groupId);

		return group.getName();
	}
}
