package flipnote.group.adapter.out.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import flipnote.group.adapter.out.entity.GroupEntity;
import flipnote.group.application.port.out.GroupRepositoryPort;
import flipnote.group.domain.model.group.Category;
import flipnote.group.domain.model.group.GroupInfo;
import flipnote.group.infrastructure.persistence.querydsl.GroupRepository;
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

	@Override
	public List<GroupInfo> findAllByCursor(Long cursorId, Category category, int size) {
		return groupRepository.findAllByCursor(cursorId, category, size);
	}

	@Override
	public List<GroupInfo> findAllByCursorAndUserId(Long cursorId, Category category, int size, Long userId) {
		return groupRepository.findAllByCursorAndUserId(cursorId, category, size, userId);
	}

	@Override
	public List<GroupInfo> findAllByCursorAndCreatedUserId(Long cursorId, Category category, int size, Long userId) {
		return findAllByCursorAndCreatedUserId(cursorId, category, size, userId);
	}
}
