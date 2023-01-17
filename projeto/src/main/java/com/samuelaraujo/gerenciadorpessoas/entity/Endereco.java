package com.samuelaraujo.gerenciadorpessoas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.samuelaraujo.gerenciadorpessoas.exception.InformacaoInvalidaException;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "enderecos")
public class Endereco implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    private String uuid;
    @NotBlank
    private String logradouro;
    @NotBlank
    private String numero;
    @NotBlank
    private String cep;
    @NotBlank
    private String cidade;

    @JsonIgnore
    @ManyToOne
    private Pessoa pessoa;

    public Endereco() {}

    public Endereco(String uuid, String logradouro, String numero, String cep, String cidade, Pessoa pessoa) {
        validaCampos( uuid, logradouro, numero, cep, cidade);
        this.uuid = uuid;
        this.logradouro = logradouro;
        this.numero = numero;
        this.cep = cep;
        this.cidade = cidade;
        this.pessoa = pessoa;
    }

    private void validaCampos(String uuid, String logradouro, String numero, String cep, String cidade) {

        List<String> semNumeroOpcoes = Arrays.asList("SEM NÚMERO, SEM NUMERO, SN, S/N");

        if(!cep.matches("[0-9]{5}-?[0-9]{3}")) throw new InformacaoInvalidaException("CEP inválido");
        try {
            Integer.parseInt(numero);
        } catch (NumberFormatException ex) {
            if(!semNumeroOpcoes.contains(numero)) throw new InformacaoInvalidaException("Número inválido");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(logradouro, endereco.logradouro) && Objects.equals(numero, endereco.numero) && Objects.equals(cep, endereco.cep) && Objects.equals(cidade, endereco.cidade) && Objects.equals(pessoa, endereco.pessoa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logradouro, numero, cep, cidade, pessoa);
    }
}
