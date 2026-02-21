package flipnote.group.adapter.out.persistence.mapper;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import flipnote.group.adapter.out.entity.JoinEntity;
import flipnote.group.domain.model.join.JoinDomain;
import flipnote.group.domain.model.join.JoinStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinMapper {

	public static JoinEntity createNewEntity(JoinDomain domain) {
		return JoinEntity.builder()
			.groupId(domain.getGroupId())
			.userId(domain.getUserId())
			.form(domain.getForm())
			.status(domain.getStatus())
			.build();
	}

	public static JoinDomain createNewDomain(Long groupId, Long userId, String form, JoinStatus status) {
		return JoinDomain.builder()
			.groupId(groupId)
			.userId(userId)
			.form(form)
			.status(status)
			.build();
	}

	public static JoinDomain toDomain(JoinEntity entity) {
		return JoinDomain.builder()
			.id(entity.getId())
			.groupId(entity.getGroupId())
			.userId(entity.getUserId())
			.form(entity.getForm())
			.status(entity.getStatus())
			.build();
	}

	public static List<JoinDomain> toDomains(List<JoinEntity> entities) {
		if (entities == null || entities.isEmpty()) {
			return Collections.emptyList();
		}

		return entities.stream()
			.map(JoinMapper::toDomain)
			.toList();
	}

	public static JoinEntity toEntity(JoinDomain domain) {
		return JoinEntity.builder()
			.id(domain.getId())
			.groupId(domain.getGroupId())
			.userId(domain.getUserId())
			.form(domain.getForm())
			.status(domain.getStatus())
			.build();
	}
}
