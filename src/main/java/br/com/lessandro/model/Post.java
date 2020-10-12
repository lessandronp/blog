package br.com.lessandro.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
 
@Entity
@Data
@EqualsAndHashCode(of = {"title", "content"}, 
	callSuper =  false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class Post extends GenericEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "S_POST_GENERATOR")
	@SequenceGenerator(name = "S_POST_GENERATOR", sequenceName = "SEQ_POST", initialValue = 1)
    @Column(name = "id_post", nullable = false)
	private Long id;
	
	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "content", nullable = false)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@JsonIgnore
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments;
	
	@JsonIgnore
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Image> images;
	
	@JsonIgnore
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Link> links;

}
