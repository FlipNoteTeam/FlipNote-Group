package flipnote.group.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import flipnote.group.adapter.out.entity.GroupEntity;
import flipnote.group.domain.model.group.Group;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupMapper {

	/**
	 * 새로운 엔티티 생성
	 * @param domain
	 * @return
	 */
	public static GroupEntity createNewEntity(Group domain) {
		return GroupEntity.builder()
			.name(domain.getName())
			.category(domain.getCategory())
			.description(domain.getDescription())
			.joinPolicy(domain.getJoinPolicy())
			.visibility(domain.getVisibility())
			.maxMember(domain.getMaxMember())
			.imageRefId(domain.getImageRefId())
			.memberCount(domain.getMemberCount())
			.build();
	}
}
