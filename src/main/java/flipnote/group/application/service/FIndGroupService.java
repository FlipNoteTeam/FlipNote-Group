package flipnote.group.application.service;

import org.springframework.stereotype.Service;

import flipnote.group.application.port.in.FindGroupUseCase;
import flipnote.group.application.port.in.command.FindGroupCommand;
import flipnote.group.application.port.in.result.FIndGroupResult;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FIndGroupService implements FindGroupUseCase {
	@Override
	public FIndGroupResult findGroup(FindGroupCommand cmd) {
		return null;
	}
}
