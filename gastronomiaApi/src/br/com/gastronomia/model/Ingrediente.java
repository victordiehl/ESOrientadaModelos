package br.com.gastronomia.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe modelo para o acesso ao banco de dados.
 * 
 * @author Luis Santana - luis.arseno@acad.pucrs.br
 * @since 11/08/2017
 * 
 **/
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name= "Ingrediente")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ingrediente implements Serializable {

	private static final long serialVersionUID = -789863172532826108L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IdIngrediente")
	private long id;

	@Column(name = "Nome")
	private String nome;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "IdUsuario", nullable = false)
	private Usuario criador;

	@Column(name = "Origem")
	private String origem;

    //Relacionamento implementado -- lado forte
    @OneToMany(
    		mappedBy = "ingrediente",
			fetch = FetchType.EAGER,
			cascade = {
					CascadeType.ALL
			})
    @JsonManagedReference
	private Set<IngredienteAtributo> ingredienteAtributo = new HashSet<>();

	@Column(name= "Status")
	private boolean status;

	public Ingrediente() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Usuario getCriador() { return criador; }

	public void setCriador(Usuario criador) {
		this.criador = criador;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public Set<IngredienteAtributo> getIngredienteAtributo() {
		return ingredienteAtributo;
	}

	public void setIngredienteAtributo(Set<IngredienteAtributo> ingredienteAtributo) {
		this.ingredienteAtributo = ingredienteAtributo;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

    @Override
    public String toString() {
        return "Ingrediente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", criador=" + criador +
                ", origem='" + origem + '\'' +
                ", ingredienteAtributo=" + ingredienteAtributo +
                ", status=" + status +
                '}';
    }
}