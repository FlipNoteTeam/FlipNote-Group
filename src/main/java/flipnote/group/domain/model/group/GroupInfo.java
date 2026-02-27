package flipnote.group.domain.model.group;

public record GroupInfo(
	Long groupId,
	String name,
	String description,
	Category category,
	Long imageRefId) {
	public static GroupInfo from(Long groupId, String name, String description, Category category, Long imageRefId) {
		return new GroupInfo(groupId, name, description, category, imageRefId);
	}
}
