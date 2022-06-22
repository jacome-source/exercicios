package com.sincronizacaoreceita;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SincronizacaoReceitaApplication {

	public static void main(String[] args) throws NumberFormatException, RuntimeException, InterruptedException {
		SpringApplication.run(SincronizacaoReceitaApplication.class, args);
		
		try {
			String input = args[0];
			File inputFile = new File(input);
	        Scanner reader = new Scanner(inputFile);
	        
	        String header = null;	        
	        if (reader.hasNextLine()) {
	        	header = reader.nextLine();	   
	        	header += ";resultado\n";
	        } else {
				throw new FileNotFoundException();
	        }	
	        
	        String file = "../result.csv";
	        FileWriter writer = new FileWriter(file);
	        writer.write(header);
	        
	        while (reader.hasNextLine()) {
	        	String data = reader.nextLine();
	        	String[] dataArray = data.split(";");
	          
	        	ReceitaService receitaService = new ReceitaService();
	        	boolean result = receitaService.atualizarConta(dataArray[0],tratarConta(dataArray[1]),tratarSaldo(dataArray[2]),dataArray[3]);
	
	        	writer.write(data+";"+result+"\n");	          
	        }
	        
	        reader.close();
	        writer.close();
	        
	        System.out.println("Arquivo gerado no diretório: "+file);
	        
	      } catch (IOException e) {
	        System.out.println(e.getMessage());
	        e.printStackTrace();
	      }
	}

	private static String tratarConta(String conta) {
		if (conta != null)
			return conta.replace("-", "");
		else return null;
	}
	
	private static double tratarSaldo(String saldo) {
		if (saldo == null) 
			throw new NumberFormatException();
		return Double.valueOf(saldo.replace(",","."));
	}


}
