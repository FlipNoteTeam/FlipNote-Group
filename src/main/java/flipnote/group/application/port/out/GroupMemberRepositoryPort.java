package flipnote.group.application.port.out;

import java.util.List;

import flipnote.group.adapter.out.entity.RoleEntity;
import flipnote.group.domain.model.member.GroupMember;
import flipnote.group.domain.model.member.GroupMemberRole;
import flipnote.group.domain.model.member.MemberInfo;

public interface GroupMemberRepositoryPort {
    void save(Long groupId, Long userId, GroupMemberRole role);

    void existsUserInGroup(Long groupId, Long userId);

    List<MemberInfo> findMemberInfo(Long groupId);

    GroupMember findMyRole(Long groupId, Long userId);
}
