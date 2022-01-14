package org.generation.italy.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Ingredienti {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	private String ingrediente;

	@ManyToMany(mappedBy = "ingredienti")
	List<Pizza> pizze;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return ingrediente;
	}

	public void setName(String ingrediente) {
		this.ingrediente = ingrediente;
	}
}
