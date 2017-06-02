package br.com.cmsystems.trabalho031;

/**
 * Created by Claudio on 31/05/2017.
 */

public class Produto {

    private int id;
    private String nome;
    private String descricao;
    private float valor;

    public Produto() {

    }

    public Produto(int id, String nome, String descricao, float valor) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
}
