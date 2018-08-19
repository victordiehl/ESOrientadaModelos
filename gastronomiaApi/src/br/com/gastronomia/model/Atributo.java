package br.com.gastronomia.model;

import java.io.Serializable;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * Classe modelo para Atributo.
 * 
 * @author Eduardo Jos� Silva (eduardo.silva.006@acad.pucrs.br), baseado no modelo de Rodrigo Machado (rodrigo.domingos@acad.pucrs.br) para cadastro de usu�rio
 * @since 07/9/2017
 * 
 **/

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name= "Atributo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Atributo implements Serializable {

	private static final long serialVersionUID = -789863172532826108L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdAtributo")
	private long id;
	
	@Column(name = "Nome")
	private String nome;
	
	@Column(name = "Unidade")
	private String unidade;
	
	@Column(name = "Multiplicador")
	private long multiplicador;

	@Column(name= "Obrigatorio")
	private boolean obrigatorio;

	@Column(name= "Status")
	private boolean status;

	/**
	 * Construtor vazio.
	 * 
	 **/
	public Atributo() {
		
	}

	public Atributo(String nome, long multiplicador) {
		this.nome = nome;
		this.multiplicador = multiplicador;
		this.unidade = "g";				
	}
	
	public long getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getUnidade() {
		return unidade;
	}

	public boolean getObrigatorio() {
		return obrigatorio;
	}

	public boolean getStatus() {
		return status;
	}

    public long getMultiplicador() {
        return multiplicador;
    }

	public void setNome(String nome) {
		this.nome = nome;
	}

    public void setMultiplicador(long multiplicador) { this.multiplicador = multiplicador; }

	public void setObrigatorio(boolean obrigatorio) {
		this.obrigatorio = obrigatorio;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * Valida se o usuario esta ativo ou nao.
	 * 
	 * @return String
	 **/
	public boolean isActive() {
		return status;
	}

	@Override
	public String toString() {
		return "Atributo " + nome + " ID: " + id + " unidade: " + unidade 
				+ " multiplicador: " + multiplicador + " obrigatório:"
				+ obrigatorio + " status:" + status;
	}
}