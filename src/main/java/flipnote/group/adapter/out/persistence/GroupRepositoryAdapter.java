package flipnote.group.adapter.out.persistence;


import org.springframework.stereotype.Repository;

import flipnote.group.adapter.out.entity.GroupEntity;
import flipnote.group.application.port.out.GroupRepositoryPort;
import flipnote.group.infrastructure.persistence.jpa.GroupRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GroupRepositoryAdapter implements GroupRepositoryPort {

	private final GroupRepository groupRepository;

	/**
	 * 그룹 저장
	 * @param group
	 * @return
	 */
	@Override
	public Long saveNewGroup(GroupEntity group) {
		return groupRepository.save(group).getId();
	}

	@Override
	public GroupEntity findById(Long id) {
		GroupEntity group = groupRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("Group not Exist")
		);
		return group;
	}

	@Override
	public void delete(Long groupId) {
		if (!groupRepository.existsById(groupId)) {
			throw new IllegalArgumentException("Group not Exist");
		}
		groupRepository.deleteById(groupId);
	}
}
