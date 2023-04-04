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
public class ModoManual extends Simulacao {

    //CONSTRUTORES
    public ModoManual(int tempoAtendimentoProduto, int numeroCaixa) {
        super(tempoAtendimentoProduto, numeroCaixa);
    }

    public ModoManual() {
    }

    public void menuUtilizador() {

        System.out.println("---------------------ModoManual: MENU----------------------");
        System.out.println("1.) Mostrar fila das caixas");
        System.out.println("2.) Criar cliente");
        System.out.println("3.) Adicionar caixa");
        System.out.println("4.) Retirar caixa de atendimento");
        System.out.println("5.) Atender T tempo");
        System.out.println("6.) Fechar aplicação");

        boolean controller = true;
        int operacao;
        Scanner input = new Scanner(System.in);
        do {
            System.out.print("ESCOLHA A OPERAÇÃO: ");
            operacao = input.nextInt();

            switch (operacao) {
                case 1:
                    this.mostrarFilas();
                    break;
                case 2:
                    this.criarCliente();
                    break;
                case 3:
                    this.adicionarCaixa();
                    break;
                case 4: {
                    if (this.getContarCaixa() == 0) {
                        System.out.println("<<NÃO EXISTE CAIXA NA SIMULAÇÃO!!!!!!!>>");
                        break;
                    }

                    Caixa c = this.retornarCaixaVazia();
                    if (c != null) {
                        this.retirarCaixa(c);
                    } else {
                        System.out.println("<<Todas as caixas possuem cliente(s)!!!!!!!>>");
                    }

                    break;
                }
                case 5: {
                    //pedir do user o valor de t
                    System.out.print("INSIRA O VALOR DE TEMPO: ");
                    int tempo = input.nextInt();
                    this.atender(tempo);
                    break;
                }
                case 6: {
                    //Guardar no ficheiro de texto
                    this.escreverFicheiroDeSaida();
                    //Terminar o programa
                    System.out.println("********************************\n<<PROGRAMA TERMINADO>>\n********************************");
                    controller = false;
                    break;
                }
                default:
                    System.out.println("OPERAÇÃO INVÁLIDA!!!");

            }

            System.out.println("\n\n\n================##############========================\n\n\n");
        } while (controller);

    }

    //METODO GRAVAR NO FICHEIRO
    @Override
    public void escreverFicheiroDeSaida() {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(getFileName(), false));

            //Guardar os paramêtros da simulação manual
            writer.write("TEMPO DE ATENDIMENTO: " + this.getTempoAtendimentoProduto());
            writer.newLine();
            writer.write("NUMERO DE CAIXA: " + this.getNumeroCaixas());
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
