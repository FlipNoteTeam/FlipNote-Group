package flipnote.group.domain.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AutoCloseable.class)
public abstract class BaseEntity {

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime modifiedAt;

	private LocalDateTime deletedAt;

	/**
	 * 삭제 표시
	 * 삭제시간 == 현재 시간
	 */
	protected void markDeleted() {
		this.deletedAt = LocalDateTime.now();
	}

	/**
	 * 삭제되었는지 확인하는 메서드
	 * @return
	 */
	public boolean isDeleted() {
		return this.deletedAt != null;
	}

	/**
	 * 삭제 복구
	 */
	protected void restore() {
		this.deletedAt = null;
	}
}
