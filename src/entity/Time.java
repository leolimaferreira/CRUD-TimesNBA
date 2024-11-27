package entity;

import java.time.LocalDate;

public class Time {

	private long id = 0;
	private String nome = "";
	private String cidade = "";
	private String nomeMascote = "";
	private LocalDate dataCriacao = LocalDate.now();
	
	public Time() {
	}

	public Time(long id, String nome, String cidade, String nomeMascote, LocalDate dataCriacao) {
		this.id = id;
		this.nome = nome;
		this.cidade = cidade;
		this.nomeMascote = nomeMascote;
		this.dataCriacao = dataCriacao;
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

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getNomeMascote() {
		return nomeMascote;
	}

	public void setNomeMascote(String nomeMascote) {
		this.nomeMascote = nomeMascote;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
}
