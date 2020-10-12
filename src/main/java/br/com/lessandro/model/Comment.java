package br.com.lessandro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of = { "text", "user" }, callSuper = false)
public class Comment extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "S_COMMENT_GENERATOR")
	@SequenceGenerator(name = "S_COMMENT_GENERATOR", sequenceName = "SEQ_COMMENT", initialValue = 1)
	@Column(name = "id_comment", nullable = false)
	private Long id;

	@Column(name = "text", nullable = false)
	@NotBlank
	@Size(min = 4, max = 100)
	private String text;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Comment(String text) {
		this.text = text;
	}

}
