package POSimplex;

import java.util.ArrayList;

public class ProblemaSimplex {
	public Long qtdRestricoes=0l;
	public Long qtdVariaveis=0l;
	public ArrayList<Long> z=new ArrayList<Long>();
	public Boolean maximizarZ=false;
	public ArrayList<Long> ld=new ArrayList<Long>();
	public ArrayList<ArrayList<Long>> var = new ArrayList<ArrayList<Long>>();
	public ArrayList<ArrayList<ArrayList<Long>>> restricoes=new ArrayList<ArrayList<ArrayList<Long>>>();

	public ProblemaSimplex() {
	}

	public void mostrarProblema() {
		System.out.println("qtdRestricoes: " + qtdRestricoes+",");
		System.out.println("qtdVariaveis: " + qtdVariaveis+",");
		System.out.println("maximizarZ:" + maximizarZ+",");
		System.out.println("z: [");
		for (int i =0;i<z.size();i++) {
			System.out.println("  "+i+":"+z.get(i)+",");
		}
		System.out.println("],");
		System.out.println("ld: [");
		for (int i =0;i<ld.size();i++) {
			System.out.println("  "+i+":"+ld.get(i));
		}
		System.out.println("],");
		System.out.println("var: {");
		for(int i=0;i<var.size();i++){
			System.out.println("  "+i+": [");
			for(int j=0; j<var.get(i).size();j++){
				System.out.println("    "+j+" : "+ var.get(i).get(j)+",");
			}
			System.out.println("  ],");
		}
		System.out.println("},");
		System.out.println("restrições: {");
		for(int i=0;i<restricoes.size();i++){
			System.out.println("  "+i+" :{");
			for(int j=0;j<restricoes.get(i).size();j++){
				System.out.println("    "+j+" :[");
				for(int k=0;k<restricoes.get(i).get(j).size();k++){
					System.out.println("      "+k+": "+restricoes.get(i).get(j).get(k)+",");
				}
				System.out.println("    ],");
			}
			System.out.println("  },");
		}
		System.out.println("}");
	}
}
