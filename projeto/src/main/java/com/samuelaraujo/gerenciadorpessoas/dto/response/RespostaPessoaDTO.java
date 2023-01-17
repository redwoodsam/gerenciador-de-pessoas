package com.samuelaraujo.gerenciadorpessoas.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.samuelaraujo.gerenciadorpessoas.entity.Pessoa;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class RespostaPessoaDTO implements Serializable {
    private String uuid;
    private String nome;
    private String dataNascimento;
    private RespostaEnderecoDTO endereco;

    @JsonIgnore
    private static DateTimeFormatter formatadorDeDatas = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RespostaPessoaDTO() {}

    public RespostaPessoaDTO(String uuid, String nome, String dataNascimento, RespostaEnderecoDTO endereco) {
        this.uuid = uuid;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public RespostaEnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(RespostaEnderecoDTO endereco) {
        this.endereco = endereco;
    }

    public static RespostaPessoaDTO from(Pessoa pessoa) {
        return new RespostaPessoaDTO(pessoa.getUuid(),
                pessoa.getNome(),
                pessoa.getDataNascimento().format(formatadorDeDatas),
                RespostaEnderecoDTO.from(pessoa.getEnderecoPrincipal()));
    }
}
