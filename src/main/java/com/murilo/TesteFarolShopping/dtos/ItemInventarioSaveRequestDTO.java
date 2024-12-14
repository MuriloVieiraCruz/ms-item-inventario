package com.murilo.TesteFarolShopping.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class ItemInventarioSaveRequestDTO implements Serializable {

    @NotBlank(message = "O código é obrigatório")
    @Size(max = 7, min = 1, message = "O código do item deve conter entre 1 e 7 caracteres")
    private String codigo;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 100, min = 3, message = "A descrição deve possuir entre 3 e 100 caracteres")
    private String descricao;

    @NotBlank(message = "A localização é obrigatória")
    @Size(max = 250, min = 3, message = "A localização deve possuir entre 3 e 250 caracteres")
    private String localizacao;

    public ItemInventarioSaveRequestDTO() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
