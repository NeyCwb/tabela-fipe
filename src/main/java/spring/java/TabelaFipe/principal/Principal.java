package spring.java.TabelaFipe.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import spring.java.TabelaFipe.model.Dados;
import spring.java.TabelaFipe.model.Modelos;
import spring.java.TabelaFipe.model.Veiculo;
import spring.java.TabelaFipe.service.ConsumoApi;
import spring.java.TabelaFipe.service.ConverteDados;

public class Principal {
	
	private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1";
	private String endPoint;
	private ConsumoApi consumoApi = new ConsumoApi();
	private ConverteDados conversor = new ConverteDados();
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
		scan.nextLine();
		
		if(opcaoDeVeiculo == 1) {
			endPoint = URL_BASE + "/carros/marcas";
		} else if (opcaoDeVeiculo == 2) {
			endPoint = URL_BASE + "/motos/marcas";
		} else {
			endPoint = URL_BASE + "/caminhoes/marcas";
		}
		
		var json = consumoApi.obterDados(endPoint);
		
		System.out.println(json);
		
		var marcas = conversor.obterLista(json, Dados.class);
		
		marcas.stream()
				.sorted(Comparator.comparing(Dados::codigo))
				.forEach(System.out::println);
		
		System.out.print("\nCódigo da marca: ");
		var codigoMarca = scan.nextLine(); 
		
		endPoint += "/" + codigoMarca + "/modelos";
		
		json = consumoApi.obterDados(endPoint);
		
		var modeloLista = conversor.obterDados(json, Modelos.class);
		
		System.out.println("\nLISTA DE MODELOS DA MARCA:");
		modeloLista.modelos().stream()
							 .sorted(Comparator.comparing(Dados::codigo))
							 .forEach(System.out::println);
		
		System.out.print("\nDigite o nome do modelo do veículo: ");
		var nomeVeiculo = scan.nextLine();
		
		List<Dados> modelosFiltrados = modeloLista.modelos().stream()
				.filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
				.collect(Collectors.toList());
		
		System.out.println("\nMODELOS FILTRADOS:");
		
		modelosFiltrados.forEach(System.out::println);
		
		System.out.print("\nDigite o código do modelo específico: ");
		
		var codigoModelo = scan.nextLine();
		
		endPoint += "/" + codigoModelo + "/anos";
		
		json = consumoApi.obterDados(endPoint);
		
		List<Dados> anos = conversor.obterLista(json, Dados.class);
		
		List<Veiculo> veiculos = new ArrayList<>();
		
		for (int i = 0; i < anos.size(); i++) {
			var endPointPorAno = endPoint + "/" + anos.get(i).codigo();
			json = consumoApi.obterDados(endPointPorAno);
			Veiculo veiculo  = conversor.obterDados(json, Veiculo.class);
			veiculos.add(veiculo);
		}
		
		System.out.println("\nVEÍCULOS FILTRADOS AVALIADOS POR ANO:");
		veiculos.forEach(System.out::println);
		
		scan.close();
	}
}
