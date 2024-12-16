package com.murilo.TesteFarolShopping.domains.enums;

public enum Disponibilidade {

    DISPONIVEL(0, 'D'),
    INDISPONIVEL(1, 'I');

    private final int codigo;
    private final char descricaoFormatada;

    Disponibilidade(int codigo, char descricaoFormatada) {
        this.codigo = codigo;
        this.descricaoFormatada = descricaoFormatada;
    }

    public static Disponibilidade encontrarPelaDescricao(char descricaoFormatada) {
        if (descricaoFormatada == '\u0000') {
            throw new IllegalArgumentException("A disponibilidade é obrigatória");
        }

        if (!String.valueOf(descricaoFormatada).isBlank()) {
            for (Disponibilidade disponibilidade : Disponibilidade.values()) {
                if (disponibilidade.getDescricaoFormatada() == descricaoFormatada) {
                    return disponibilidade;
                }
            }
        }

        throw new IllegalArgumentException("Disponibilidade inexistente: " + descricaoFormatada + ", informar I para indisponível ou D para disponível");
    }

    public int getCodigo() {
        return codigo;
    }

    public char getDescricaoFormatada() {
        return descricaoFormatada;
    }
}
