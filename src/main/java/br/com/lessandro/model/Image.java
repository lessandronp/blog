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

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of = { "name" }, callSuper = false)
public class Image extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "S_IMAGE_GENERATOR")
	@SequenceGenerator(name = "S_IMAGE_GENERATOR", sequenceName = "SEQ_IMAGE", initialValue = 1)
	@Column(name = "id_image", nullable = false)
	private Long id;

	@NotBlank
	@Column(name = "name", nullable = false)
	private String name;

	@NotBlank
	@Column(name = "url", nullable = false)
	private String url;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "album_id")
	private Album album;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;
}
