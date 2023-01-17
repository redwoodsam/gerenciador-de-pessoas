package com.samuelaraujo.gerenciadorpessoas.dto.request;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ConsultaDTO implements Serializable {

    @NotBlank
    private String consulta;

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }
}
