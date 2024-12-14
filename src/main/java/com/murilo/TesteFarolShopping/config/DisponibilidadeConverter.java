package com.murilo.TesteFarolShopping.config;

import com.murilo.TesteFarolShopping.domains.enums.Disponibilidade;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DisponibilidadeConverter implements AttributeConverter<Disponibilidade, String> {

    @Override
    public String convertToDatabaseColumn(Disponibilidade disponibilidade) {
        if (disponibilidade != null) {
            return String.valueOf(disponibilidade.getDescricaoFormatada());
        }

        return null;
    }

    @Override
    public Disponibilidade convertToEntityAttribute(String descricaoFormatada) {
        if (descricaoFormatada != null && !descricaoFormatada.isBlank()) {
            return Disponibilidade.encontrarPelaDescricao(descricaoFormatada.charAt(0));
        }

        return null;
    }
}
