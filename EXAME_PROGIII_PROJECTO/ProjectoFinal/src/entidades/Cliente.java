package entidades;

/**
 *
 * @author Rui Malemba
 */
public class Cliente {

    //ATRIBUTOS
    private int id;
    private int produtosCarregados;

    static int counterIdCliente = 0;

    //CONSTRUTORES
    public Cliente(int produtosCarregados) {
        this.id = ++counterIdCliente;
        this.produtosCarregados = produtosCarregados;
    }

    public Cliente() {
        this.id = ++counterIdCliente;
    }

    //GETERS E SETERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProdutosCarregados() {
        return produtosCarregados;
    }

    public void setProdutosCarregados(int produtosCarregados) {
        this.produtosCarregados = produtosCarregados;
    }

}
