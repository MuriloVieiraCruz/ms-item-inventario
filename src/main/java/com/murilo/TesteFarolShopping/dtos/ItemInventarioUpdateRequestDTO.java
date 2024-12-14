package com.murilo.TesteFarolShopping.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class ItemInventarioUpdateRequestDTO implements Serializable {

    @NotNull(message = "O id do item é obrigatório")
    private Long id;

    @NotBlank(message = "O código é obrigatório")
    @Size(max = 7, min = 1, message = "O código do item deve conter entre 1 e 7 caracteres")
    private String codigo;

    @NotNull(message = "A disponibilidade é obrigatória")
    private char disponibilidade;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 100, min = 3, message = "A localização deve possuir entre 3 e 250 caracteres")
    private String descricao;

    @NotBlank(message = "A localização é obrigatória")
    @Size(max = 250, min = 3, message = "A localização deve possuir entre 3 e 250 caracteres")
    private String localizacao;

    public ItemInventarioUpdateRequestDTO() {
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
