package flipnote.group.application.port.out;

import java.util.List;

import flipnote.group.adapter.out.entity.GroupMemberEntity;
import flipnote.group.domain.model.member.GroupMember;
import flipnote.group.domain.model.member.MemberInfo;

public interface GroupMemberRepositoryPort {
    void save(GroupMemberEntity groupMember);

    void existsUserInGroup(Long groupId, Long userId);

    List<MemberInfo> findMemberInfo(Long groupId);

    GroupMember findMyRole(Long groupId, Long userId);

    boolean checkOwner(Long groupId, Long userId);
}
