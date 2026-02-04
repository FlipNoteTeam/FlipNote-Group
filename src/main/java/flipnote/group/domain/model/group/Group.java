package flipnote.group.domain.model.group;

import flipnote.group.application.port.in.command.CreateGroupCommand;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Group {

	private Long id;

	private String name;
	private Category category;
	private String description;
	private JoinPolicy joinPolicy;
	private Visibility visibility;

	private int maxMember;
	private String imageUrl;
	private int memberCount;

	/**
	 * 신규로 그룹 생성
	 * @param cmd
	 * @return
	 */
	public static Group create(CreateGroupCommand cmd) {
		validate(cmd);

		Group group = new Group();
		group.name = cmd.name();
		group.category = cmd.category();
		group.description = cmd.description();
		group.joinPolicy = cmd.joinPolicy();
		group.visibility = cmd.visibility();
		group.maxMember = cmd.maxMember();
		group.imageUrl = cmd.imageUrl();

		group.memberCount = 1;

		return group;
	}

	/**
	 * DB 가져오기
	 * @param id
	 * @param name
	 * @param category
	 * @param description
	 * @param joinPolicy
	 * @param visibility
	 * @param maxMember
	 * @param imageUrl
	 * @param memberCount
	 * @return
	 */
	public static Group getGroup(
		Long id,
		String name,
		Category category,
		String description,
		JoinPolicy joinPolicy,
		Visibility visibility,
		int maxMember,
		String imageUrl,
		int memberCount
	) {
		Group g = new Group();
		g.id = id;
		g.name = name;
		g.category = category;
		g.description = description;
		g.joinPolicy = joinPolicy;
		g.visibility = visibility;
		g.maxMember = maxMember;
		g.imageUrl = imageUrl;
		g.memberCount = memberCount;
		return g;
	}

	/**
	 * 파라미터 검증
	 * @param cmd
	 */
	private static void validate(CreateGroupCommand cmd) {
		if (cmd.name() == null || cmd.name().isBlank()) {
			throw new IllegalArgumentException("name required");
		}
		if (cmd.maxMember() < 1 || cmd.maxMember() > 100) {
			throw new IllegalArgumentException("maxMember invalid");
		}
		if (cmd.category() == null) {
			throw new IllegalArgumentException("category required");
		}
		if (cmd.joinPolicy() == null) {
			throw new IllegalArgumentException("join required");
		}
		if (cmd.visibility() == null) {
			throw new IllegalArgumentException("visibility required");
		}
		if (cmd.description() == null || cmd.description().isBlank()) {
			throw new IllegalArgumentException("description required");
		}
	}
}
