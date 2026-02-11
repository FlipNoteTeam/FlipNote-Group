package flipnote.group.domain.model.group;

import java.time.LocalDateTime;

import flipnote.group.application.port.in.command.ChangeGroupCommand;
import flipnote.group.application.port.in.command.CreateGroupCommand;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Group {

	private Long id;

	private String name;
	private Category category;
	private String description;
	private JoinPolicy joinPolicy;
	private Visibility visibility;

	private int maxMember;
	private Long imageRefId;
	private int memberCount;

	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

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
		group.imageRefId = cmd.imageRefId();

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
	 * @param imageRefId
	 * @param memberCount
	 * @return
	 */
	public static Group restore(
		Long id,
		String name,
		Category category,
		String description,
		JoinPolicy joinPolicy,
		Visibility visibility,
		int maxMember,
		Long imageRefId,
		int memberCount,
		LocalDateTime createdAt,
		LocalDateTime modifiedAt
	) {
		Group g = new Group();
		g.id = id;
		g.name = name;
		g.category = category;
		g.description = description;
		g.joinPolicy = joinPolicy;
		g.visibility = visibility;
		g.maxMember = maxMember;
		g.imageRefId = imageRefId;
		g.memberCount = memberCount;
		g.createdAt = createdAt;
		g.modifiedAt = modifiedAt;
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
		if (cmd.name().length() > 50) {
			throw new IllegalArgumentException("name too long");
		}
	}

	public void change(ChangeGroupCommand cmd) {
		// 권한 검증 등 비즈니스 로직 추가 가능
		this.name = cmd.name();
		this.category = cmd.category();
		this.description = cmd.description();
		this.joinPolicy = cmd.joinPolicy();
		this.visibility = cmd.visibility();
		this.maxMember = cmd.maxMember();
		this.imageRefId = cmd.imageRefId();
	}
}
