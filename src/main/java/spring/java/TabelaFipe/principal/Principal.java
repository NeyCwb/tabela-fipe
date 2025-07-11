package spring.java.TabelaFipe.principal;

import java.util.Scanner;

import spring.java.TabelaFipe.service.ConsumoApi;

public class Principal {
	
	private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1";
	private String endPoint;
	private ConsumoApi consumoApi = new ConsumoApi();
	private Scanner scan = new Scanner(System.in);
	
	
	public void exibeMenu() {
		var menu = """
					OPÇÕES\n======
					
					1. Carro
					2. Moto
					3. Caminhão
				   """;
		
		System.out.print(menu);
		
		System.out.print("\nDigite a opção: ");
		var opcaoDeVeiculo = scan.nextInt();
		
		if(opcaoDeVeiculo == 1) {
			endPoint = URL_BASE + "/carros/marcas";
		} else if (opcaoDeVeiculo == 2) {
			endPoint = URL_BASE + "/motos/marcas";
		} else {
			endPoint = URL_BASE + "/caminhoes/marcas";
		}
		
		var json = consumoApi.obterDados(endPoint);
		
		System.out.println(endPoint);
		System.out.println(json);
		
		scan.close();
	}
}
