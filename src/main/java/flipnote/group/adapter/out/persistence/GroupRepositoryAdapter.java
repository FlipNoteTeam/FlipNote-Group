package flipnote.group.adapter.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import flipnote.group.adapter.out.entity.GroupEntity;
import flipnote.group.adapter.out.persistence.mapper.GroupMapper;
import flipnote.group.application.port.out.GroupRepositoryPort;
import flipnote.group.domain.model.group.Group;
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
	public Optional<GroupEntity> findById(Long id) {
		return groupRepository.findById(id);
	}

	/**
	 * 그룹 수정
	 * @param group
	 */
	@Override
	public Group update(Group group, GroupEntity groupEntity) {
		groupEntity.change(
			group.getName(),
			group.getCategory(),
			group.getDescription(),
			group.getJoinPolicy(),
			group.getVisibility(),
			group.getMaxMember(),
			group.getImageRefId()
		);

		return GroupMapper.toDomain(groupEntity);
	}

}
