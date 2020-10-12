package br.com.lessandro.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class GenericEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@CreatedDate
	@Column(nullable = false)
	private Instant creationDate;

	@LastModifiedDate
	private Instant updateDate;
}
