package com.br.ed2.tdd.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DataUtil {
	
	public static boolean dataNaoVencida(LocalDate dataDevolucao, LocalDate dataPrevista) {
		return dataDevolucao.isBefore(dataPrevista) || dataDevolucao.isEqual(dataPrevista);
	}
	
	public static long quantidadeDeDiasAposDataPrevista(LocalDate dataDevolucao, LocalDate dataPrevista) {
		return ChronoUnit.DAYS.between(dataPrevista, dataDevolucao);
	}

}
