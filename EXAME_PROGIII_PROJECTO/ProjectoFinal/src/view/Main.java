package view;

import auxiliar.Ficheiro;
import java.util.Scanner;
import entidades.*;

/**
 *
 * @author Rui Malemba
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int op, tempo, numCaixa, tempoCliente;

        Simulacao s = null;

        System.out.println("1 - MODO MANUAL");
        System.out.println("2 - MODO AUTOMÁTICO");
        System.out.println("<outro-número> - SAIR\n");
        System.out.print("Escolha a operação: ");
        op = input.nextInt();

        /*try{
            
        }catch(Exception e){
            System.out.println("Ocorreu um erro insesperado"+ e.getMessage());
        }*/
        switch (op) {
            case 1: {
                //Solicitar dados ao user
                if (Ficheiro.isEmpty()) {
                    System.out.println("NOTA: Inserir número maior que 0\nNO CASO DE OMISSÃO: Inserir 0");
                    System.out.print("Inserir tempo de atendimento de um produto: ");
                    tempo = input.nextInt();
                    System.out.print("Inserir número de caixas: ");
                    numCaixa = input.nextInt();

                    //validar parâmetros
                    if (tempo < 0 || numCaixa < 0) {
                        System.out.println("<<TODOS PARAMETROS DEVEM SER >= 0>>");
                        return;
                    }

                    ModoManual simulacao = (ModoManual) s;
                    simulacao = new ModoManual(tempo, numCaixa);
                    simulacao.menuUtilizador();
                } else {
                    ModoManual simulacao = (ModoManual) s;
                    simulacao = new ModoManual();
                    simulacao.menuUtilizador();
                }
                break;
            }
            case 2: {
                //Solicitar dados ao user
                if (Ficheiro.isEmpty()) {
                    System.out.println("NOTA: Inserir número maior que 0\nNO CASO DE OMISSÃO: Inserir 0");
                    System.out.print("Inserir tempo de atendimento de um produto: ");
                    tempo = input.nextInt();
                    System.out.print("Inserir número de caixas: ");
                    numCaixa = input.nextInt();
                    System.out.print("Inserir intervalo de tempo máximo entre clientes: ");
                    tempoCliente = input.nextInt();

                    //validar parâmetros
                    if (tempo < 0 || numCaixa < 0 || tempoCliente < 0) {
                        System.out.println("<<TODOS PARAMETROS DEVEM SER >= 0>>");
                        return;
                    }

                    ModoAutomatico simulacao = (ModoAutomatico) s;
                    simulacao = new ModoAutomatico(tempoCliente, tempo, numCaixa);
                    simulacao.executar();
                } else {
                    ModoAutomatico simulacao = (ModoAutomatico) s;
                    simulacao = new ModoAutomatico();

                    if (simulacao.getTempoMaximoEntreClientes() == 0) {
                        System.out.println("NOTA: Inserir número maior que 0\nNO CASO DE OMISSÃO: Inserir 0");
                        System.out.print("Inserir intervalo de tempo máximo entre clientes: ");
                        tempoCliente = input.nextInt();

                        //validar parâmetro
                        if (tempoCliente < 0) {
                            System.out.println("<<Tempo máximo entre clientes deve ser >= 0>>");
                            return;
                        }

                        simulacao.setTempoMaximoEntreClientes(tempoCliente);
                    }
                    simulacao.executar();
                }
                break;
            }
            default:
                System.out.println("********************************\n<<PROGRAMA TERMINADO>>\n********************************");
        }
    }

}
