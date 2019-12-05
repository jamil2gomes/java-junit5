package com.br.ed2.tdd.modelo.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DataUtil {

	
	public static boolean dataNaoVencida(LocalDate dataPrevista, LocalDate dataDevolucao) {
		return dataDevolucao.isBefore(dataPrevista) || dataDevolucao.isEqual(dataPrevista);
	}
	
	public static long quantidadeDeDiasAposVencimento(LocalDate dataPrevista, LocalDate dataDevolucao) {
		return ChronoUnit.DAYS.between(dataPrevista, dataDevolucao);
	}
}
