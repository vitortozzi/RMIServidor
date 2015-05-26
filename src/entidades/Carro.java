
package entidades;

import java.io.Serializable;
import java.util.ArrayList;
import rmi.InterfaceCli;

public class Carro implements Serializable {

    private ArrayList<InterfaceCli> clientesInteressados;

    private InterfaceCli clienteAtual;
    private boolean disponivel;
    private String localRetirada;
    private String localDevolucao;
    
    private int id;
    private String modelo;
    private String marca;
    private String placa;
    private double precoDiaria;
    
    private static final long serialVersionUID = 1L;
    
    public Carro(int id, String modelo, String marca, String placa, double precoDiaria) {
        clientesInteressados = new ArrayList<>();
        this.disponivel = true;
        
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
        this.placa = placa;
        this.precoDiaria = precoDiaria;
    }
    
//    Clienter interessados num determinado carro
    public ArrayList<InterfaceCli> getClientesInteressados() {
        return clientesInteressados;
    }   
    
//    Atribui um lista de interessados à um carro
    public void setClientesInteressados(ArrayList<InterfaceCli> clientesInteressados) {
        this.clientesInteressados = clientesInteressados;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecoDiaria() {
        return precoDiaria;
    }

    public void setPrecoDiaria(double precoDiaria) {
        this.precoDiaria = precoDiaria;
    }

    public InterfaceCli getClienteAtual() {
        return clienteAtual;
    }

    public void setClienteAtual(InterfaceCli clienteAtual) {
        this.clienteAtual = clienteAtual;
    }
    
    public void addClienteInteressado(InterfaceCli refCliente) {
        this.clientesInteressados.add(refCliente);
    }

    public String getLocalRetirada() {
        return localRetirada;
    }

    public void setLocalRetirada(String localRetirada) {
        this.localRetirada = localRetirada;
    }

    public String getLocalDevolucao() {
        return localDevolucao;
    }
    
    public void setLocalDevolucao(String localDevolucao) {
        this.localDevolucao = localDevolucao;
    }
    
}
