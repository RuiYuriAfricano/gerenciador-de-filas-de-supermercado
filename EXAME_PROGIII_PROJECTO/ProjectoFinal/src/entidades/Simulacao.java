package entidades;

import static auxiliar.Ficheiro.getFileName;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Rui Malemba
 */
public abstract class Simulacao {

    private int contarCaixa = 0;

    //Atributos
    private int tempoAtendimentoProduto;
    private int numeroCaixas;
    private Caixa[] caixas;

    //CONSTRUTORES
    public Simulacao(int tempoAtendimentoProduto, int numeroCaixas) {
        this.tempoAtendimentoProduto = tempoAtendimentoProduto;
        this.numeroCaixas = numeroCaixas;
        if (tempoAtendimentoProduto == 0) {
            this.tempoAtendimentoProduto = 5;
        }
        if (numeroCaixas == 0) {
            this.numeroCaixas = 4;
        }

        caixas = new Caixa[this.numeroCaixas];
    }

    public Simulacao() {
        this.lerFicheiro();
    }

    //GETERS AND SETERS
    public int getTempoAtendimentoProduto() {
        return tempoAtendimentoProduto;
    }

    public void setTempoAtendimentoProduto(int tempoAtendimentoProduto) {
        this.tempoAtendimentoProduto = tempoAtendimentoProduto;
    }

    public int getNumeroCaixas() {
        return numeroCaixas;
    }

    public void setNumeroCaixas(int numeroCaixa) {
        this.numeroCaixas = numeroCaixa;
    }

    public Caixa[] getCaixas() {
        return caixas;
    }

    public void setCaixas(Caixa[] caixas) {
        this.caixas = caixas;
    }

    public int getContarCaixa() {
        return contarCaixa;
    }

    public void setContarCaixa(int contarCaixa) {
        this.contarCaixa = contarCaixa;
    }

    //MOSTRAR FILAS DAS CAIXAS
    public void mostrarFilas() {

        System.out.println("\n\n================##MOSTRAR FILA##========================\n\n");

        int i;
        for (i = 0; i < this.contarCaixa; i++) {
            //MOSTRA INFORMAÇÃO SOBRE A CAIXA
            System.out.println("CAIXA Nº: " + this.getCaixas()[i].getId());
            System.out.println("CLIENTES NA FILA: " + this.caixas[i].getClienteEmFilas());
            System.out.println("TEMPO RESTANTE PARA ATENDER CLIENTE TOPO: " + caixas[i].getTempoClienteTopo());
            System.out.println("CLIENTES ATENDIDOS: " + this.caixas[i].getClientesAtendidos());
            System.out.println("TEMPO TOTAL DE ATENDIMENTO: " + this.caixas[i].getTempoTotalAtendimento());
            System.out.println("TEMPO MÉDIO DE ATENDIMENTO: " + this.caixas[i].getTempoMedioAtendimentoPorCliente() + "\n");

            //MOSTRA INFORMAÇÃO SOBRE OS CLIENTES DA CAIXA ATUAL
            if (this.getCaixas()[i].getClienteEmFilas() == 0) {
                System.out.println("<<ESTA CAIXA NAO POSSUI CLIENTES EM FILAS>>");
            }
            for (Cliente c : this.caixas[i].getFilaDeClientes()) {

                System.out.println("CLIENTE Nº: " + c.getId());
                System.out.println("PRODUTOS DO CARRINHO: " + c.getProdutosCarregados());
                System.out.println("---------------------------------------------------------------");

            }
            if (i + 1 != this.contarCaixa) {
                System.out.println("___________________________________________________________________\n\n");
            }
        }
        if (i == 0) {
            System.out.println("<<LISTA VAZIA, NENHUMA CAIXA FOI ADICIONADA!!>>");
        }
    }

    //Sortear número
    public int sortNumber(int min, int max) {
        Random r = new Random();
        int result = min + r.nextInt(max - min + 1);
        return result;

    }

    //MÉTODO QUE ADICIONA CAIXA
    public void adicionarCaixa() {
        Caixa caixa = new Caixa();
        if (this.contarCaixa < this.getNumeroCaixas()) {
            this.caixas[contarCaixa++] = caixa;
            System.out.println("<<CAIXA ADICIONADA COM SUCESSO>>");
        } else {
            System.out.println("<<NÃO É POSSIVEL ADICIONAR MAIS CAIXA>>");
        }
        //cada vez que inserir adiciona 1 unidade a variável contarCaixa
        //que é o valor de caixas existentes na simulação
    }

    //MÉTODO QUE ELIMINA CAIXA
    public void retirarCaixa(Caixa caixa) {
        int i;
        //ESSE LOOP PÁRA QUANDO contarcaixa for >= i ou quando caixas[i] == caixa
        //Esse loop tem objectivo de procurar o parametro caixa na no array caixas
        for (i = 0; i < this.contarCaixa && this.caixas[i] != caixa; i++) {
        }
        //encontrou a caixa? se sim: entra no escopo do if asseguir
        if (i < this.contarCaixa && this.caixas[i] == caixa) {
            this.caixas[i] = null; //elimina a caixa colocando null
            //colocar todas caixas com valor null no final do raray
            for (int j = 0; j < this.contarCaixa; j++) {
                if (this.caixas[j] == null && (j + 1) < this.contarCaixa) {
                    Caixa aux = this.caixas[j];
                    this.caixas[j] = this.caixas[j + 1];
                    this.caixas[j + 1] = aux;
                }

            }
            //decrementa o contador de caixa, ou seja ele passa ter o número 
            //válido de caixas(não eliminadas, por sua vez de valor != de null)
            if (this.contarCaixa > 0) {
                this.contarCaixa--;
            }
            System.out.println("<<CAIXA RETIRADA COM SUCESSO>>");
        }
    }

    //MÈTODO QUE CRIA CLIENTE
    public void criarCliente() {
        if (this.getContarCaixa() == 0) {
            System.out.println("<<NÃO É POSSÍVEL CRIAR CLIENTE, CRIE PELOMENOS UMA CAIXA>>");
            return;
        }
        //sortea aquantidade de produtos entre 2 e 120 unidades
        int qtdProdutos = this.sortNumber(2, 120);

        //cria cliente com a qtd de produtos sorteados
        Cliente cliente = new Cliente(qtdProdutos);

        //verificar caixa com o número reduzido de clientes na fila
        int menor = this.getCaixas()[0].getClienteEmFilas();
        int indice = 0;

        for (int i = 1; i < this.getContarCaixa(); i++) {
            if (this.getCaixas()[i].getClienteEmFilas() < menor) {
                menor = this.getCaixas()[i].getClienteEmFilas();
                indice = i;
            }

        }

        //adiciona cliente na fila da caixa corrente
        this.getCaixas()[indice].addCliente(cliente);

        //se o cliente inserido estiver no topo atualizar o tempo restante do cliente de topo.
        if (this.getCaixas()[indice].getClienteEmFilas() == 1) {
            this.getCaixas()[indice].calcularTempoClienteDeTopo(this.getTempoAtendimentoProduto());
        }
        System.out.println("<<CLIENTE CRIADO COM SUCESSO>>");

    }

    //MÉTODO QUE ATENDE T TEMPO A CADA CAIXA
    public void atender(int tempo) {

        //validar parâmetro
        if (tempo < 0) {
            System.out.println("<<O TEMPO DEVE SER >= 0>>");
            return;
        }

        System.out.println("\n\n================##ATENDER " + tempo + " SEGUNDOS##========================\n\n");
        if (this.getContarCaixa() == 0) {
            System.out.println("<<NÃO É POSSÍVEL ATENDER T TEMPO, CRIE PELOMENOS UMA CAIXA>>");
            return;
        }
        boolean result = false;
        for (int i = 0; i < this.getContarCaixa(); i++) {
            if (!(this.getCaixas()[i].getClienteEmFilas() == 0)) {
                if (tempo < this.getCaixas()[i].getTempoClienteTopo()) {
                    this.getCaixas()[i].setTempoTotalAtendimento(this.getCaixas()[i].getTempoTotalAtendimento() + tempo);
                    this.getCaixas()[i].setTempoClienteTopo(this.getCaixas()[i].getTempoClienteTopo() - tempo);
                } else if (tempo == this.getCaixas()[i].getTempoClienteTopo()) {
                    this.getCaixas()[i].removerClienteTopo(this.getTempoAtendimentoProduto());
                } else {
                    tempo = tempo - this.getCaixas()[i].getTempoClienteTopo();
                    this.getCaixas()[i].removerClienteTopo(this.getTempoAtendimentoProduto());
                    i = 0;
                }
                result = true;
            }

        }
        if (result) {
            System.out.println("<<ATENDIDO COM SUCESSO>>");
        }
    }

    //Método para retornar caixa vazia
    public Caixa retornarCaixaVazia() {

        int i;
        Caixa c = null;
        //procurar a caixa com fila de clientes vazia
        for (i = 0; i < this.getContarCaixa() && this.getCaixas()[i].getClienteEmFilas() != 0; i++) {
        }

        //se encontrar uma caixa vazia, então chama o método retirar
        if (i < this.getContarCaixa() && this.getCaixas()[i].getClienteEmFilas() == 0) {
            c = this.getCaixas()[i];
        }
        return c;
    }

    //METODO GRAVAR NO FICHEIRO
    public abstract void escreverFicheiroDeSaida();

    //METODO LER DO FICHEIRO
    public abstract void lerFicheiro();
}
