package flipnote.group.application.port.in.command;

import flipnote.group.domain.model.group.Category;
import flipnote.group.domain.model.group.JoinPolicy;

public record CreateGroupCommand(
	String name,
	Category category,
	String description,
	JoinPolicy joinPolicy,
	boolean visibility,
	int maxMember,
	String imageUrl
) {
}
