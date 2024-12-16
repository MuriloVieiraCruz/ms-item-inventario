package com.murilo.TesteFarolShopping.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ItemInventarioUpdateRequestDTO implements Serializable {

    @NotNull(message = "O id do item é obrigatório")
    private Long id;

    @NotBlank(message = "O código é obrigatório")
    private String codigo;

    @NotNull(message = "A disponibilidade é obrigatória")
    private char disponibilidade;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotBlank(message = "A localização é obrigatória")
    private String localizacao;

    public ItemInventarioUpdateRequestDTO() {
    }

    public ItemInventarioUpdateRequestDTO(Long id, String codigo, char disponibilidade, String descricao, String localizacao) {
        this.id = id;
        this.codigo = codigo;
        this.disponibilidade = disponibilidade;
        this.descricao = descricao;
        this.localizacao = localizacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public char getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(char disponibilidade) {
        this.disponibilidade = disponibilidade;
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
