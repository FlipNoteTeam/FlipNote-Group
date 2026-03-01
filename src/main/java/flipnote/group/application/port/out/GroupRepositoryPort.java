package flipnote.group.application.port.out;


import java.util.List;

import flipnote.group.domain.model.group.Category;
import flipnote.group.domain.model.group.GroupInfo;
import flipnote.group.adapter.out.entity.GroupEntity;

public interface GroupRepositoryPort {
    Long saveNewGroup(GroupEntity group);

    GroupEntity findById(Long id);

    void delete(Long id);

	List<GroupInfo> findAllByCursor(Long cursorId, Category category, int size);

	List<GroupInfo> findAllByCursorAndUserId(Long cursorId, Category category, int size, Long userId);

	List<GroupInfo> findAllByCursorAndCreatedUserId(Long cursorId, Category category, int size, Long userId);

	boolean checkJoinable(Long groupId);
}
