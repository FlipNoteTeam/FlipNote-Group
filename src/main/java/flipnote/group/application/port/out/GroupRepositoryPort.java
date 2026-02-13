package flipnote.group.application.port.out;

import java.util.List;

import flipnote.group.domain.model.group.Category;
import flipnote.group.domain.model.group.Group;
import flipnote.group.domain.model.group.GroupInfo;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public interface GroupRepositoryPort {
    Long saveNewGroup(Group group);

    Group findById(Long id);

    void delete(Long id);

	List<GroupInfo> findAllByCursor(Long cursorId, Category category, int size);

	List<GroupInfo> findAllByCursorAndUserId(Long cursorId, Category category, int size, Long userId);

	List<GroupInfo> findAllByCursorAndCreatedUserId(Long cursorId, Category category, int size, Long userId);
}
