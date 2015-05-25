
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

    /**
     * @return the clientesInteressados
     */
    public ArrayList<InterfaceCli> getClientesInteressados() {
        return clientesInteressados;
    }

    /**
     * @param clientesInteressados the clientesInteressados to set
     */
    public void setClientesInteressados(ArrayList<InterfaceCli> clientesInteressados) {
        this.clientesInteressados = clientesInteressados;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the disponivel
     */
    public boolean getDisponivel() {
        return disponivel;
    }

    /**
     * @param disponivel the disponivel to set
     */
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    /**
     * @return the placa
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * @param placa the placa to set
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * @return the modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the marca
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * @return the precoDiaria
     */
    public double getPrecoDiaria() {
        return precoDiaria;
    }

    /**
     * @param precoDiaria the precoDiaria to set
     */
    public void setPrecoDiaria(double precoDiaria) {
        this.precoDiaria = precoDiaria;
    }

    /**
     * @return the clienteAtual
     */
    public InterfaceCli getClienteAtual() {
        return clienteAtual;
    }

    /**
     * @param clienteAtual the clienteAtual to set
     */
    public void setClienteAtual(InterfaceCli clienteAtual) {
        this.clienteAtual = clienteAtual;
    }
    
    public void addClienteInteressado(InterfaceCli refCliente) {
        this.clientesInteressados.add(refCliente);
    }

    /**
     * @return the localRetirada
     */
    public String getLocalRetirada() {
        return localRetirada;
    }

    /**
     * @param localRetirada the localRetirada to set
     */
    public void setLocalRetirada(String localRetirada) {
        this.localRetirada = localRetirada;
    }

    /**
     * @return the localDevolucao
     */
    public String getLocalDevolucao() {
        return localDevolucao;
    }

    /**
     * @param localDevolucao the localDevolucao to set
     */
    public void setLocalDevolucao(String localDevolucao) {
        this.localDevolucao = localDevolucao;
    }
    
}
