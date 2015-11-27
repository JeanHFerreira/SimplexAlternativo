package POSimplex;

public class Principal {
	public static void main(String[] args) {
		ArquivoEntrada arquivo = new ArquivoEntrada();
        ProblemaSimplex problema = arquivo.lerArquivo("ArquivoSimplex.json");
        arquivo.validarProvlemaArquivo(problema);
        //problema.mostrarProblema();
	}
}
