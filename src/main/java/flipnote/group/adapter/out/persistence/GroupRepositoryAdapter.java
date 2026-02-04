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

	@Override
	public Long saveNewGroup(GroupEntity groupEntity) {

		GroupEntity group = groupRepository.save(groupEntity);

		return group.getId();
	}
}
