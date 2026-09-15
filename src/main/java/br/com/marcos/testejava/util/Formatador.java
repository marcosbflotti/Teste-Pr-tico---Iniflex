package br.com.marcos.testejava.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Formatador {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final NumberFormat FORMATO_MOEDA = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private static final NumberFormat FORMATO_NUMERO = criarFormatoNumero();

    private Formatador() {
    }

    private static NumberFormat criarFormatoNumero() {
        NumberFormat formato = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        formato.setMinimumFractionDigits(2);
        formato.setMaximumFractionDigits(2);
        return formato;
    }

    public static String formatarData(LocalDate data) {
        return data.format(FORMATO_DATA);
    }

    public static String formatarMoeda(BigDecimal valor) {
        return FORMATO_MOEDA.format(valor);
    }

    public static String formatarNumero(BigDecimal valor) {
        return FORMATO_NUMERO.format(valor);
    }
}
