package POSimplex;

import java.util.ArrayList;

public class ProblemaSimplex {
	public Long qtdRestricoes = 0l;
	public Long qtdVariaveis = 0l;
	public ArrayList<Long> z = new ArrayList<Long>();
	public Boolean maximizarZ = false;
	public ArrayList<Long> ld = new ArrayList<Long>();
	public ArrayList<ArrayList<Long>> var = new ArrayList<ArrayList<Long>>();
	public ArrayList<ArrayList<ArrayList<Long>>> restricoes = new ArrayList<ArrayList<ArrayList<Long>>>();

	public ProblemaSimplex() {
	}

	public void mostrarProblema() {
		System.out.println("qtdRestricoes: " + qtdRestricoes + ",");
		System.out.println("qtdVariaveis: " + qtdVariaveis + ",");
		System.out.println("maximizarZ:" + maximizarZ + ",");
		System.out.println("z: [");
		for (int i = 0; i < z.size(); i++) {
			System.out.println("  " + i + ":" + z.get(i) + ",");
		}
		System.out.println("],");
		System.out.println("ld: [");
		for (int i = 0; i < ld.size(); i++) {
			System.out.println("  " + i + ":" + ld.get(i));
		}
		System.out.println("],");
		System.out.println("var: {");
		for (int i = 0; i < var.size(); i++) {
			System.out.println("  " + i + ": [");
			for (int j = 0; j < var.get(i).size(); j++) {
				System.out
						.println("    " + j + " : " + var.get(i).get(j) + ",");
			}
			System.out.println("  ],");
		}
		System.out.println("},");
		System.out.println("restrições: {");
		for (int i = 0; i < restricoes.size(); i++) {
			System.out.println("  " + i + " :{");
			for (int j = 0; j < restricoes.get(i).size(); j++) {
				System.out.println("    " + j + " :[");
				for (int k = 0; k < restricoes.get(i).get(j).size(); k++) {
					System.out.println("      " + k + ": "
							+ restricoes.get(i).get(j).get(k) + ",");
				}
				System.out.println("    ],");
			}
			System.out.println("  },");
		}
		System.out.println("}");
	}

	@SuppressWarnings("unchecked")
	public ProblemaSimplex transformarDual() {
		ArrayList<Long> todasRestricoes = new ArrayList<Long>();
		ArrayList<Long> restricoesDual = new ArrayList<Long>();
		Long contador = 1l;
		ArrayList<ArrayList<Long>> novasVar = new ArrayList<ArrayList<Long>>();
		ArrayList<Long> nVarMenor = new ArrayList<Long>();
		ArrayList<Long> nVarMaior = new ArrayList<Long>();
		ArrayList<Long> nVarIrrestrita = new ArrayList<Long>();
		novasVar.add(nVarMenor);
		novasVar.add(nVarMaior);
		novasVar.add(nVarIrrestrita);
		ArrayList<ArrayList<ArrayList<Long>>> novasRestricoes = new ArrayList<ArrayList<ArrayList<Long>>>();
		ArrayList<ArrayList<Long>> nRestMenor = new ArrayList<ArrayList<Long>>();
		ArrayList<ArrayList<Long>> nRestMaior = new ArrayList<ArrayList<Long>>();
		ArrayList<ArrayList<Long>> nRestIgual = new ArrayList<ArrayList<Long>>();
		novasRestricoes.add(nRestMenor);
		novasRestricoes.add(nRestMaior);
		novasRestricoes.add(nRestIgual);
		ArrayList<Long> novoLd = new ArrayList<Long>();
		for (int i = 0; i < restricoes.size(); i++) {
			for (int j = 0; j < restricoes.get(i).size(); j++) {
				for (int k = 0; k < restricoes.get(i).get(j).size(); k++) {
					todasRestricoes.add(this.restricoes.get(i).get(j).get(k));
				}
			}
		}

		for (int j = 0; j < this.qtdVariaveis; j++) {
			for (int i = 0; i < this.qtdRestricoes; i++) {
				restricoesDual.add(todasRestricoes.get((int) (i
						* this.qtdVariaveis + j)));
			}
		}

		if (this.maximizarZ) {
			for (int i = 0; i < this.restricoes.size(); i++) {
				for (int j = 0; j < this.restricoes.get(i).size(); j++) {
					switch (i) {
					case 0:// MenorIgual
						nVarMaior.add(contador);
						contador++;
						break;
					case 1:// MaiorIgial
						nVarMenor.add(contador);
						contador++;
						break;
					case 2:// Irrestrita
						nVarIrrestrita.add(contador);
						contador++;
						break;
					default:
						contador++;
						System.out.println("Erro, valores incorretos!");
						return null;
					}
				}
			}

			for (int i = 0; i < this.var.size(); i++) {
				for (int j = 0; j < this.var.get(i).size(); j++) {
					ArrayList<Long> aux1 = new ArrayList<Long>();
					for (int k = 0; k < this.qtdRestricoes; k++) {
						aux1.add(restricoesDual.get((int) (this.qtdRestricoes
								* (this.var.get(i).get(j) - 1) + k)));
					}
					novoLd.add(this.z.get((Integer.parseInt(""
							+ (this.var.get(i).get(j)-1)))));
					switch (i) {
					case 0:// MenorIgualZero
						nRestMenor.add(aux1);
						break;
					case 1:// MaiorIgialZero
						nRestMaior.add(aux1);
						break;
					case 2:// IgualZero
						nRestIgual.add(aux1);
						break;
					default:
						System.out.println("Erro, valores incorretos!");
						return null;
					}
				}
			}
		} else {//Minimização
			for (int i = 0; i < this.restricoes.size(); i++) {
				for (int j = 0; j < this.restricoes.get(i).size(); j++) {
					switch (i) {
					case 0:// MenorIgual
						nVarMenor.add(contador);
						contador++;
						break;
					case 1:// MaiorIgial
						nVarMaior.add(contador);
						contador++;
						break;
					case 2:// Igual
						nVarIrrestrita.add(contador);
						contador++;
						break;
					default:
						contador++;
						System.out.println("Erro, valores incorretos!");
						return null;
					}
				}
			}
			
			for (int i = 0; i < this.var.size(); i++) {
				for (int j = 0; j < this.var.get(i).size(); j++) {
					ArrayList<Long> aux1 = new ArrayList<Long>();
					for (int k = 0; k < this.qtdRestricoes; k++) {
						aux1.add(restricoesDual.get((int) (this.qtdRestricoes
								* (this.var.get(i).get(j) - 1) + k)));
					}
					switch (i) {
					case 0:// MenorIgualZero
						nRestMaior.add(aux1);
						/*novoLd.add(this.z.get((Integer.parseInt(""
								+ (this.var.get(i).get(j)-1)))));*/
						break;
					case 1:// MaiorIgialZero
						nRestMenor.add(aux1);
						/*novoLd.add(0,this.z.get((Integer.parseInt(""
								+ (this.var.get(i).get(j)-1)))));*/
						break;
					case 2:// IgualZero
						nRestIgual.add(aux1);
						/*novoLd.add(this.z.get((Integer.parseInt(""
								+ (this.var.get(i).get(j)-1)))));*/
						break;
					default:
						System.out.println("Erro, valores incorretos!");
						return null;
					}
				}
			}
			novoLd = (ArrayList<Long>) this.z.clone();
		}
		ProblemaSimplex problemaDual = new ProblemaSimplex();
		problemaDual.z = this.ld;
		problemaDual.ld = novoLd;

		Long valorAuxiliar = this.qtdRestricoes;
		problemaDual.qtdRestricoes = this.qtdVariaveis;
		problemaDual.qtdVariaveis = valorAuxiliar;

		problemaDual.var = novasVar;
		problemaDual.maximizarZ = !this.maximizarZ;
		problemaDual.restricoes = novasRestricoes;
		return problemaDual;
	}
}
