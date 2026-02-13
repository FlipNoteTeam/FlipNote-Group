package flipnote.group.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flipnote.group.application.port.in.DeleteGroupUseCase;
import flipnote.group.application.port.in.command.DeleteGroupCommand;
import flipnote.group.application.port.out.GroupRepositoryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteGroupService implements DeleteGroupUseCase {

	private final GroupRepositoryPort groupRepository;

	@Override
	@Transactional
	public void deleteGroup(DeleteGroupCommand cmd) {
		groupRepository.delete(cmd.groupId());
	}

}
