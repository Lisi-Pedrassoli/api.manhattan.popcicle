package com.manhattan.demo.Enums.RawMaterial;

public enum MeasurementUnitEnum {
    QUILOGRAMA_KG("Quilograma (kg)"),
    GRAMA_G("Grama (g)"),
    TONELADA_T("Tonelada (t)"),
    LITRO_L("Litro (L)"),
    MILILITRO_ML("Mililitro (mL)"),
    UNIDADE_UN("Unidade (un)"),
    METRO_M("Metro (m)"),
    CENTIMETRO_CM("Centímetro (cm)"),
    PACOTE_PCT("Pacote (pct)");

    private final String descricao;

    MeasurementUnitEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
    public static boolean validarUnidade(String unidade) {
        try {
            MeasurementUnitEnum.valueOf(unidade);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}