package flipnote.group.application.port.out;

public interface GroupMemberRepositoryPort {
    void saveOwner(Long groupId, Long userId);
}
