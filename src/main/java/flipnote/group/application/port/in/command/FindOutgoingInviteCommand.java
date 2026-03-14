package flipnote.group.application.port.in.command;

public record FindOutgoingInviteCommand(
	Long userId,
	Long groupId
) {
}
