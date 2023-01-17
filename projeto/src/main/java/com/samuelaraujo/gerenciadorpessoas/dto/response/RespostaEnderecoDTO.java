package com.samuelaraujo.gerenciadorpessoas.dto.response;

import com.samuelaraujo.gerenciadorpessoas.entity.Endereco;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class RespostaEnderecoDTO implements Serializable {

    private String uuid;
    private String logradouro;
    private String numero;
    private String cep;
    private String cidade;

    public RespostaEnderecoDTO() {}

    public RespostaEnderecoDTO(String uuid, String logradouro, String numero, String cep, String cidade) {
        this.uuid = uuid;
        this.logradouro = logradouro;
        this.numero = numero;
        this.cep = cep;
        this.cidade = cidade;
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

    public static RespostaEnderecoDTO from(Endereco endereco) {
        return new RespostaEnderecoDTO(endereco.getUuid(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getCep(),
                endereco.getCidade());
    }

    public static List<RespostaEnderecoDTO> from(List<Endereco> enderecos) {
        return enderecos
                .stream()
                .map(endereco -> new RespostaEnderecoDTO(endereco.getUuid(),
                    endereco.getLogradouro(),
                    endereco.getNumero(),
                    endereco.getCep(),
                    endereco.getCidade())
                )
                .collect(Collectors.toList());
    }
}
