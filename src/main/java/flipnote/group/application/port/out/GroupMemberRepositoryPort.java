package flipnote.group.application.port.out;

public interface GroupMemberRepositoryPort {
    void save(Long groupId, Long userId, Long roleId);

    boolean existsUserInGroup(Long groupId, Long userId);
}
