package flipnote.group.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import flipnote.group.adapter.out.entity.GroupEntity;
import flipnote.group.adapter.out.persistence.mapper.GroupMapper;
import flipnote.group.application.port.out.GroupRepositoryPort;
import flipnote.group.domain.model.group.Category;
import flipnote.group.domain.model.group.Group;
import flipnote.group.domain.model.group.GroupInfo;
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
	public Long saveNewGroup(Group group) {
		GroupEntity entity = GroupMapper.createNewEntity(group);
		return groupRepository.save(entity).getId();
	}

	@Override
	public Group findById(Long id) {
		GroupEntity groupEntity = groupRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("Group not Exist")
		);
		return GroupMapper.toDomain(groupEntity);
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
		return List.of();
	}

	@Override
	public List<GroupInfo> findAllByCursorAndUserId(Long cursorId, Category category, int size, Long userId) {
		return List.of();
	}

	@Override
	public List<GroupInfo> findAllByCursorAndCreatedUserId(Long cursorId, Category category, int size, Long userId) {
		return List.of();
	}

	@Override
	public boolean checkJoinable(Long groupId) {

		GroupEntity groupEntity = groupRepository.findByIdForUpdate(groupId).orElseThrow(
			() -> new IllegalArgumentException("not exists")
		);

		 int maxMember = groupEntity.getMaxMember();
		 int count = groupEntity.getMemberCount();

		 boolean joinable = maxMember > count;

		return joinable;
	}
}
