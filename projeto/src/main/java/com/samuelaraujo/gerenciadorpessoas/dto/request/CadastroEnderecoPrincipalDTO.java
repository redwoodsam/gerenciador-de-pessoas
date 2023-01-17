package com.samuelaraujo.gerenciadorpessoas.dto.request;

import java.io.Serializable;

public class CadastroEnderecoPrincipalDTO implements Serializable {
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
