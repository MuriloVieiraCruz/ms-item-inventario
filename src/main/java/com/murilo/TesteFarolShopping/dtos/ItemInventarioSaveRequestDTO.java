package com.murilo.TesteFarolShopping.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class ItemInventarioSaveRequestDTO implements Serializable {

    @NotBlank(message = "O código é obrigatório")
    private String codigo;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotBlank(message = "A localização é obrigatória")
    private String localizacao;

    public ItemInventarioSaveRequestDTO() {
    }

    public ItemInventarioSaveRequestDTO(String codigo, String descricao, String localizacao) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.localizacao = localizacao;
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
