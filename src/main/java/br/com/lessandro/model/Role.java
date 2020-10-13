package br.com.lessandro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(generator = "S_ROLE_GENERATOR")
	@SequenceGenerator(name = "S_ROLE_GENERATOR", sequenceName = "SEQ_ROLE", initialValue = 1)
	@Column(name = "role_id", nullable = false)
	private Long id;

	@Enumerated(EnumType.STRING)
	@NaturalId
	@Column(name = "name", nullable = false)
	private RoleName name;

	public Role(RoleName name) {
		this.name = name;
	}
}
