package entidades;

import static auxiliar.Ficheiro.getFileName;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

/**
 *
 * @author Rui Malemba
 */
public class ModoAutomatico extends Simulacao {

    //ATRIBUTOS
    private int tempoMaximoEntreClientes;

    //CONSTRUTOR
    public ModoAutomatico(int tempoMaximoEntreClientes, int tempoAtendimentoProduto, int numeroCaixas) {
        super(tempoAtendimentoProduto, numeroCaixas);
        this.tempoMaximoEntreClientes = tempoMaximoEntreClientes;
        if (tempoMaximoEntreClientes == 0) {
            this.tempoMaximoEntreClientes = 15;
        }
    }

    public ModoAutomatico(int tempoMaximoEntreClientes) {
        this.tempoMaximoEntreClientes = tempoMaximoEntreClientes;
        if (tempoMaximoEntreClientes == 0) {
            this.tempoMaximoEntreClientes = 15;
        }
    }

    public ModoAutomatico() {
    }

    //GETERS AND SETERS
    public int getTempoMaximoEntreClientes() {
        return tempoMaximoEntreClientes;
    }

    public void setTempoMaximoEntreClientes(int tempoMaximoEntreClientes) {
        this.tempoMaximoEntreClientes = tempoMaximoEntreClientes;
        if (tempoMaximoEntreClientes == 0) {
            this.tempoMaximoEntreClientes = 15;
        }
    }

    public void executar() {
        boolean controller = true;
        int operacao;
        Scanner input = new Scanner(System.in);

        do {
            //gerar o um número aleatório entre 1 e o  tempo máximo entre clientes
            int x = this.sortNumber(1, this.getTempoMaximoEntreClientes());
            //Operações automáticas
            this.mostrarFilas();

            this.adicionarCaixa();

            this.criarCliente();

            this.atender(x);

            //INTERACÇÃO COM O USER PARA SABER SE CONTINUAR OU NÃO
            System.out.print("\n\nContinuar? 1:SIM - <outro_número>:NÃO R: ");
            operacao = input.nextInt();
            System.out.println("\n\n================###########========================\n\n");
            if (operacao != 1) {
                controller = false;
                //Guardar no ficheiro de texto
                this.escreverFicheiroDeSaida();
                System.out.println("********************************\n<<PROGRAMA TERMINADO>>\n********************************");
            }

            //*******************************************************//
        } while (controller);
    }

    //METODO GRAVAR NO FICHEIRO
    @Override
    public void escreverFicheiroDeSaida() {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(getFileName(), false));

            //Guardar os paramêtros da simulação automática
            writer.write("TEMPO DE ATENDIMENTO: " + this.getTempoAtendimentoProduto());
            writer.newLine();
            writer.write("NUMERO DE CAIXA: " + this.getNumeroCaixas());
            writer.newLine();
            writer.write("TEMPO MAXIMO ENTRE CLIENTES: " + this.getTempoMaximoEntreClientes());
            writer.newLine();
            writer.newLine();

            //Guardar as informações restantes
            for (int i = 0; i < this.getContarCaixa(); i++) {
                //MOSTRA INFORMAÇÃO SOBRE A CAIXA
                writer.write("CAIXA Nº: " + this.getCaixas()[i].getId());
                writer.newLine();
                writer.write("CLIENTES NA FILA: " + this.getCaixas()[i].getClienteEmFilas());
                writer.newLine();
                writer.write("TEMPO RESTANTE PARA ATENDER CLIENTE TOPO: " + this.getCaixas()[i].getTempoClienteTopo());
                writer.newLine();
                writer.write("CLIENTES ATENDIDOS: " + this.getCaixas()[i].getClientesAtendidos());
                writer.newLine();
                writer.write("TEMPO TOTAL DE ATENDIMENTO: " + this.getCaixas()[i].getTempoTotalAtendimento());
                writer.newLine();
                writer.write("TEMPO MÉDIO DE ATENDIMENTO: " + this.getCaixas()[i].getTempoMedioAtendimentoPorCliente());
                writer.newLine();
                writer.newLine();
                //MOSTRA INFORMAÇÃO SOBRE OS CLIENTES DA CAIXA ATUAL
                for (Cliente c : this.getCaixas()[i].getFilaDeClientes()) {
                    writer.write("CLIENTE Nº: " + c.getId());
                    writer.newLine();
                    writer.write("PRODUTOS DO CARRINHO: " + c.getProdutosCarregados());
                    //espaço entre os clientes
                    writer.newLine();
                }
                //espaço entre as caixas
                writer.newLine();
                writer.newLine();
                writer.newLine();
            }

            writer.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //METODO LER DO FICHEIRO
    @Override
    public void lerFicheiro() {
        BufferedReader reader;
        String text = "";
        int boxCounter = 0;
        int indiceCliente = 0;
        try {

            reader = new BufferedReader(new FileReader(getFileName()));

            do {
                text = reader.readLine();

                if (text != null) {
                    if (!text.isEmpty()) {

                        //SE O TEXTO A SER LIDO CONTER a palavra "TEMPO DE ATENDIMENTO:", então fazer o set ao atributo
                        if (text.toUpperCase().contains("TEMPO DE ATENDIMENTO:")) {
                            String str = "";
                            for (int i = 0; i < text.length(); i++) {
                                if (Character.isDigit(text.charAt(i))) {
                                    str = str + text.charAt(i);
                                }
                            }

                            if (str.length() > 0) {

                                int n = Integer.parseInt(str);
                                this.setTempoAtendimentoProduto(n);
                            }
                        }

                        //SE O TEXTO A SER LIDO CONTER a palavra "NUMERO DE CAIXA:", então fazer o set ao atributo
                        if (text.toUpperCase().contains("NUMERO DE CAIXA:")) {
                            String str = "";
                            for (int i = 0; i < text.length(); i++) {
                                if (Character.isDigit(text.charAt(i))) {
                                    str += text.charAt(i);
                                }
                            }
                            if (str.length() > 0) {
                                int n = Integer.parseInt(str);
                                this.setNumeroCaixas(n);
                                this.setCaixas(new Caixa[this.getNumeroCaixas()]);
                            }
                        }

                        //SE O TEXTO A SER LIDO CONTER a palavra "TEMPO MAXIMO ENTRE CLIENTES:", então fazer o set ao atributo
                        if (text.toUpperCase().contains("TEMPO MAXIMO ENTRE CLIENTES:")) {
                            String str = "";
                            for (int i = 0; i < text.length(); i++) {
                                if (Character.isDigit(text.charAt(i))) {
                                    str += text.charAt(i);
                                }
                            }
                            if (str.length() > 0) {
                                int n = Integer.parseInt(str);
                                this.setTempoMaximoEntreClientes(n);
                            }
                        }

                        //SE O TEXTO A SER LIDO CONTER a palavra "CAIXA Nº:", 
                        //então criar caixa e fazer o set aos seus atributos
                        //=============> número da caixa
                        if (text.toUpperCase().contains("CAIXA Nº:")) {
                            String str = "";
                            for (int i = 0; i < text.length(); i++) {
                                if (Character.isDigit(text.charAt(i))) {
                                    str += text.charAt(i);
                                }
                            }

                            if (str.length() > 0) {
                                int n = Integer.parseInt(str);

                                //construir caixa da posição corrente do vetor caixas
                                this.getCaixas()[this.getContarCaixa()] = new Caixa();

                                this.getCaixas()[this.getContarCaixa()].setId(n);
                                Caixa.counterID = n;
                                if (this.getContarCaixa() < this.getNumeroCaixas()) {
                                    this.setContarCaixa(this.getContarCaixa() + 1);
                                }
                                indiceCliente = 0;
                            }
                        }
                        //=============> Clientes em fila
                        if (text.toUpperCase().contains("CLIENTES NA FILA:")) {
                            String str = "";
                            for (int i = 0; i < text.length(); i++) {
                                if (Character.isDigit(text.charAt(i))) {
                                    str += text.charAt(i);
                                }
                            }
                            if (str.length() > 0) {

                                int n = Integer.parseInt(str);
                                this.getCaixas()[this.getContarCaixa() - 1].setClienteEmFilas(n);
                            }
                        }
                        //=============> Tempo cliente de topo
                        if (text.toUpperCase().contains("TEMPO RESTANTE PARA ATENDER CLIENTE TOPO:")) {
                            String str = "";
                            for (int i = 0; i < text.length(); i++) {
                                if (Character.isDigit(text.charAt(i))) {
                                    str += text.charAt(i);
                                }
                            }
                            if (str.length() > 0) {

                                int n = Integer.parseInt(str);
                                this.getCaixas()[this.getContarCaixa() - 1].setTempoClienteTopo(n);
                            }
                        }
                        //=============> CLIENTES ATENDIDOS
                        if (text.toUpperCase().contains("CLIENTES ATENDIDOS:")) {
                            String str = "";
                            for (int i = 0; i < text.length(); i++) {
                                if (Character.isDigit(text.charAt(i))) {
                                    str += text.charAt(i);
                                }
                            }
                            if (str.length() > 0) {

                                int n = Integer.parseInt(str);
                                this.getCaixas()[this.getContarCaixa() - 1].setClientesAtendidos(n);
                            }
                        }
                        //=============> TEMPO TOTAL DE ATENDIMENTO: 
                        if (text.toUpperCase().contains("TEMPO TOTAL DE ATENDIMENTO:")) {
                            String str = "";
                            for (int i = 0; i < text.length(); i++) {
                                if (Character.isDigit(text.charAt(i))) {
                                    str += text.charAt(i);
                                }
                            }
                            if (str.length() > 0) {

                                int n = Integer.parseInt(str);
                                this.getCaixas()[this.getContarCaixa() - 1].setTempoTotalAtendimento(n);
                            }
                        }
                        //=============> TEMPO MÉDIO DE ATENDIMENTO: 
                        if (text.toUpperCase().contains("TEMPO MÉDIO DE ATENDIMENTO:")) {
                            String str = "";
                            for (int i = 0; i < text.length(); i++) {
                                if (Character.isDigit(text.charAt(i))) {
                                    str += text.charAt(i);
                                }
                            }
                            if (str.length() > 0) {
                                int n = Integer.parseInt(str);
                                this.getCaixas()[this.getContarCaixa() - 1].setTempoMedioAtendimentoPorCliente(n);

                            }
                        }
                        //=============> CLIENTE Nº: 
                        if (text.toUpperCase().contains("CLIENTE Nº:")) {
                            String str = "";
                            for (int i = 0; i < text.length(); i++) {
                                if (Character.isDigit(text.charAt(i))) {
                                    str += text.charAt(i);
                                }
                            }
                            if (str.length() > 0) {

                                int n = Integer.parseInt(str);
                                Cliente c = new Cliente();
                                c.setId(n);
                                Cliente.counterIdCliente = n;
                                this.getCaixas()[this.getContarCaixa() - 1].addCliente(c);
                            }
                        }
                        //=============> PRODUTOS DO CARRINHO: 
                        if (text.toUpperCase().contains("PRODUTOS DO CARRINHO:")) {
                            String str = "";
                            for (int i = 0; i < text.length(); i++) {
                                if (Character.isDigit(text.charAt(i))) {
                                    str += text.charAt(i);
                                }
                            }
                            if (str.length() > 0) {

                                int n = Integer.parseInt(str);
                                this.getCaixas()[this.getContarCaixa() - 1].getFilaDeClientes().get(indiceCliente).setProdutosCarregados(n);
                                indiceCliente++;
                            }
                        }
                    }
                }
            } while (text != null);
            reader.close();
        } catch (Exception e) {
            System.out.println("Erro ao ler do ficheiro: " + e.getMessage());
        }
    }

}
