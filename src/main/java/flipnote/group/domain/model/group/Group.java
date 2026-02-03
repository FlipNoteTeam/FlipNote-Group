package flipnote.group.domain.model.group;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Group {

	private GroupId id;
	private String name;
	private String description;
	private int maxMember;
	private int memberCount;

	public static Group create(String name, String description, int maxMember) {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("group name is blank");
		}
		if (maxMember < 1 || maxMember > 100) {
			throw new IllegalArgumentException("group is max");
		}

		Group group = new Group();
		group.name = name;
		group.description = description;
		group.maxMember = maxMember;

		group.memberCount = 1;
		return group;
	}

	public void validateJoinable() {
		if (memberCount >= maxMember) {
			throw new IllegalArgumentException("group is max");
		}
	}

	public void increaseMemberCount() {
		validateJoinable();
		memberCount++;
	}
}
