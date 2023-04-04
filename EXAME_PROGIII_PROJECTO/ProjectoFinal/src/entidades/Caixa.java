package entidades;

import java.util.ArrayList;

/**
 *
 * @author Rui Malemba
 */
public class Caixa {

    //Atributos
    private int id;
    private int clienteEmFilas;
    private int clientesAtendidos;
    private int tempoTotalAtendimento;
    private int tempoMedioAtendimentoPorCliente;
    private int tempoClienteTopo;

    private ArrayList<Cliente> filaDeClientes = new ArrayList<>();

    static int counterID = 0;

    //CONSTRUTOR
    public Caixa() {
        this.id = ++counterID;
    }

    //GETERS E SETERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteEmFilas() {
        return clienteEmFilas;
    }

    public void setClienteEmFilas(int clienteEmFilas) {
        this.clienteEmFilas = clienteEmFilas;
    }

    public int getClientesAtendidos() {
        return clientesAtendidos;
    }

    public void setClientesAtendidos(int clientesAtendidos) {
        this.clientesAtendidos = clientesAtendidos;
    }

    public int getTempoTotalAtendimento() {
        return tempoTotalAtendimento;
    }

    public void setTempoTotalAtendimento(int tempoTotalAtendimento) {
        this.tempoTotalAtendimento = tempoTotalAtendimento;
    }

    public int getTempoMedioAtendimentoPorCliente() {
        return tempoMedioAtendimentoPorCliente;
    }

    public void setTempoMedioAtendimentoPorCliente(int tempoMedioAtendimentoPorCliente) {
        this.tempoMedioAtendimentoPorCliente = tempoMedioAtendimentoPorCliente;
    }

    public ArrayList<Cliente> getFilaDeClientes() {
        return filaDeClientes;
    }

    public void setFilaDeClientes(ArrayList<Cliente> filaDeClientes) {
        this.filaDeClientes = filaDeClientes;
    }

    public int getTempoClienteTopo() {
        return tempoClienteTopo;
    }

    public void setTempoClienteTopo(int tempoClienteTopo) {
        this.tempoClienteTopo = tempoClienteTopo;
    }

    //Obter o tempo do cliente de topo
    public void calcularTempoClienteDeTopo(int tempoAtendimentoProduto) {
        if (this.getClienteEmFilas() > 0) {
            this.setTempoClienteTopo(tempoAtendimentoProduto * this.filaDeClientes.get(0).getProdutosCarregados());
        }
    }

    //REMOVER CLIENTE DO TOPO
    public void removerClienteTopo(int tempoTotalAtendimento) {
        //this.calcularTempoClienteDeTopo(tempoTotalAtendimento);
        //Atualiza tempo total
        this.setTempoTotalAtendimento(this.getTempoTotalAtendimento() + this.getTempoClienteTopo());
        //Atualiza nº de clientes atendidos
        this.setClientesAtendidos(this.getClientesAtendidos() + 1);
        //Atualiza tempo médio 
        this.setTempoMedioAtendimentoPorCliente(this.getTempoTotalAtendimento() / this.getClientesAtendidos());

        //Por fim, elimina o cliente de topo
        this.getFilaDeClientes().remove(0);

        //reduzir clientes em fila
        this.setClienteEmFilas(this.getClienteEmFilas() - 1);
        //Atualiza para novo cliente de topo
        if (this.getClienteEmFilas() == 0) {
            this.setTempoClienteTopo(0);
        } else {
            this.calcularTempoClienteDeTopo(tempoTotalAtendimento);
        }

        //Atualiza clientes em fila
        this.getSizeFila();

    }

    //obter o tamanho do arraylist filaDeClientes
    private void getSizeFila() {
        this.clienteEmFilas = this.filaDeClientes.size();
    }

    public void addCliente(Cliente cliente) {
        this.filaDeClientes.add(cliente);
        //Atualiza clientes em fila
        this.getSizeFila();
    }

}
