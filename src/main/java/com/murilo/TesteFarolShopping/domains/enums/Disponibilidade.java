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

    public static Disponibilidade encontrarPeloCodigo(int codigo) {
        for (Disponibilidade disponibilidade : Disponibilidade.values()) {
            if (disponibilidade.getCodigo() == codigo) {
                return disponibilidade;
            }
        }

        throw new IllegalArgumentException("Disponibilidade inexistente: " + codigo);
    }

    public int getCodigo() {
        return codigo;
    }

    public char getDescricaoFormatada() {
        return descricaoFormatada;
    }
}
