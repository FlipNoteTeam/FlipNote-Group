package flipnote.group.application.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import flipnote.group.adapter.out.entity.GroupEntity;
import flipnote.group.application.port.in.FindGroupNameUseCase;
import flipnote.group.application.port.out.GroupRepositoryPort;
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

		GroupEntity group = groupRepository.findById(groupId);

		if (group == null) {
			throw new NoSuchElementException("Group not found: " + groupId);
		}

		return group.getName();
	}
}
