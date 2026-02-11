package flipnote.group.adapter.out.entity;

import flipnote.group.application.port.in.command.CreateGroupCommand;
import flipnote.group.domain.model.BaseEntity;
import flipnote.group.domain.model.group.Category;
import flipnote.group.domain.model.group.JoinPolicy;
import flipnote.group.domain.model.group.Visibility;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "app_groups")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Category category;

	@Column(nullable = false)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private JoinPolicy joinPolicy;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Visibility visibility;

	@Column(nullable = false)
	private Integer maxMember;

	private Long imageRefId;

	@Column(nullable = false)
	private Integer memberCount;

	@Builder
	private GroupEntity(String name, Category category, String description, JoinPolicy joinPolicy, Visibility visibility,
		Integer maxMember, Long imageRefId, Integer memberCount) {
		this.name = name;
		this.category = category;
		this.description = description;
		this.joinPolicy = joinPolicy;
		this.visibility = visibility;
		this.maxMember = maxMember;
		this.imageRefId = imageRefId;
		this.memberCount = memberCount;
	}
}
