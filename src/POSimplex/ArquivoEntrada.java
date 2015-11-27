package POSimplex;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ArquivoEntrada {
	public ProblemaSimplex lerArquivo(String arquivo) {
		JSONObject jsonObject; 
		JSONParser parser = new JSONParser();
		ProblemaSimplex problema = new ProblemaSimplex();
		Long qtdRestricoes;
		Long qtdVariaveis;
		JSONArray z;
		Boolean maximizarZ;
		JSONArray ld;
		JSONObject restricoes;
		JSONObject var;

		ArrayList<Long> ladoDireito = new ArrayList<Long>();
		ArrayList<Long> funcaoZ = new ArrayList<Long>();
		ArrayList<ArrayList<Long>> variaveis = new ArrayList<ArrayList<Long>>();
		ArrayList<Long> varMenorIgual = new ArrayList<Long>();
		ArrayList<Long> varMaiorIgual = new ArrayList<Long>();
		ArrayList<Long> varIrrestritas = new ArrayList<Long>();
		ArrayList<ArrayList<ArrayList<Long>>> restricao = new ArrayList<ArrayList<ArrayList<Long>>>();
		ArrayList<ArrayList<Long>> resMenorIgual = new ArrayList<ArrayList<Long>>();
		ArrayList<ArrayList<Long>> resMaiorIgual = new ArrayList<ArrayList<Long>>();
		ArrayList<ArrayList<Long>> resIrestriata = new ArrayList<ArrayList<Long>>();
		try {
			jsonObject = (JSONObject) parser.parse(new FileReader(arquivo));
			qtdRestricoes = (Long) jsonObject.get("qtdRestricoes");
			qtdVariaveis = (Long) jsonObject.get("qtdVariaveis");
			maximizarZ = (boolean) jsonObject.get("maximizarZ");

			ld = (JSONArray) jsonObject.get("ld");
			for (int i = 0; i < ld.size(); i++) {
				ladoDireito.add((Long) ld.get(i));
			}

			z = (JSONArray) jsonObject.get("z");
			for (int i = 0; i < z.size(); i++) {
				funcaoZ.add((Long) z.get(i));
			}

			var = (JSONObject) jsonObject.get("var");
			JSONArray jvMenorIgualZero = (JSONArray) var.get("menorIgualZero");
			for (int i = 0; i < jvMenorIgualZero.size(); i++) {
				varMenorIgual.add((Long) jvMenorIgualZero.get(i));
			}

			JSONArray jvMaiorIgualZero = (JSONArray) var.get("maiorIgualZero");
			for (int i = 0; i < jvMaiorIgualZero.size(); i++) {
				varMaiorIgual.add((Long) jvMaiorIgualZero.get(i));
			}

			JSONArray jvIrrestrita = (JSONArray) var.get("irrestrita");
			for (int i = 0; i < jvIrrestrita.size(); i++) {
				varIrrestritas.add((Long) jvIrrestrita.get(i));
			}
			variaveis.add(varMenorIgual);
			variaveis.add(varMaiorIgual);
			variaveis.add(varIrrestritas);

			restricoes = (JSONObject) jsonObject.get("restricoes");
			JSONObject jrMenorIgual = (JSONObject) restricoes.get("menorIgual");
			for (int i = 0; i < jrMenorIgual.size(); i++) {
				JSONArray jr = (JSONArray) jrMenorIgual.get("me" + (i + 1));
				if (jr != null) {
					ArrayList<Long> r = new ArrayList<Long>();
					for (int j = 0; j < jr.size(); j++) {
						r.add((Long) jr.get(j));
					}
					resMenorIgual.add(r);
				}
			}
			JSONObject jrMaiorIgual = (JSONObject) restricoes.get("maiorIgual");
			for (int i = 0; i < jrMaiorIgual.size(); i++) {
				JSONArray jr = (JSONArray) jrMaiorIgual.get("ma" + (i + 1));
				if (jr != null) {
					ArrayList<Long> r = new ArrayList<Long>();
					for (int j = 0; j < jr.size(); j++) {
						r.add((Long) jr.get(j));
					}
					resMaiorIgual.add(r);
				}

			}
			JSONObject jrIrrestrita = (JSONObject) restricoes.get("igual");
			for (int i = 0; i < jrIrrestrita.size(); i++) {
				JSONArray jr = (JSONArray) jrIrrestrita.get("i" + (i + 1));
				if (jr != null) {
					ArrayList<Long> r = new ArrayList<Long>();
					for (int j = 0; j < jr.size(); j++) {
						r.add((Long) jr.get(j));
					}
					resIrestriata.add(r);
				}
			}

			restricao.add(resMenorIgual);
			restricao.add(resMaiorIgual);
			restricao.add(resIrestriata);

			problema.maximizarZ = maximizarZ;
			problema.qtdRestricoes = qtdRestricoes;
			problema.qtdVariaveis = qtdVariaveis;
			problema.z = funcaoZ;
			problema.ld = ladoDireito;
			problema.var = variaveis;
			problema.restricoes = restricao;
		} catch (FileNotFoundException e) {
			System.out.println("Erro no arquivo\n" + e.getMessage());
			return null;
		} catch (IOException e) {
			System.out.println("Erro no arquivo\n" + e.getMessage());
			return null;
		} catch (ParseException e) { // TODO Auto-generated catch block
			System.out.println("Erro no arquivo\n" + e.getMessage());
			return null;
		}
		return problema;
	}

	public boolean verificar(ProblemaSimplex problema) {
		int qtdRestricoes = 0;
		int qtdVariaveis = 0;
		ArrayList<Long> lista = new ArrayList<Long>();
		for (int i = 0; i < problema.restricoes.size(); i++) {
			qtdRestricoes += problema.restricoes.get(i).size();
			for (int j = 0; j < problema.restricoes.get(i).size(); j++) {
				if (problema.restricoes.get(i).get(j).size() != problema.qtdVariaveis) {
					return false;
				}
			}

		}
		if (qtdRestricoes != problema.qtdRestricoes) {
			return false;
		}
		for (int i = 0; i < problema.var.size(); i++) {
			qtdVariaveis += problema.var.get(i).size();
			for (int j = 0; j < problema.var.get(i).size(); j++) {
				lista.add(problema.var.get(i).get(j));
			}
		}
		if (qtdVariaveis != problema.qtdVariaveis) {
			return false;
		}
		lista.sort(null);
		for(int i=0;i<lista.size();i++){
			if((i+1)!=lista.get(i)){
				return false;
			}
		}
		return true;
	}

	public void validarProvlemaArquivo(ProblemaSimplex problema) {
		if ((problema.z.size() != problema.qtdVariaveis)
				|| (problema.ld.size() != problema.qtdRestricoes)
				|| !this.verificar(problema)) {
			System.out.println("O problema está mal formulado no arquivo!");
		} else {
			System.out.println("Problema OK");
		}
	}
}
