package com.samuelaraujo.gerenciadorpessoas.dto.request;

import java.io.Serializable;

public class CadastroEnderecoDTO implements Serializable {
    private String logradouro;
    private String numero;
    private String cep;
    private String cidade;

    public CadastroEnderecoDTO() {}

    public CadastroEnderecoDTO(String logradouro, String numero, String cep, String cidade) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.cep = cep;
        this.cidade = cidade;
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
}
