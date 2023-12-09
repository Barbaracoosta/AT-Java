package at.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pessoa {
	private String nome;
	private Integer idade;
	private String piada;
	private String[] frutas;
}
