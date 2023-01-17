package com.samuelaraujo.gerenciadorpessoas.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class CadastroPessoaDTO implements Serializable {

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;
    @NotBlank(message = "O campo data de nascimento é obrigatório")
    private String dataNascimento;
    private CadastroEnderecoDTO endereco;
    @JsonIgnore
    private DateTimeFormatter formatadorDeDatas = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public CadastroPessoaDTO() {}

    public CadastroPessoaDTO(String nome, String dataNascimento, CadastroEnderecoDTO endereco) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
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

    public CadastroEnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(CadastroEnderecoDTO endereco) {
        this.endereco = endereco;
    }
}
