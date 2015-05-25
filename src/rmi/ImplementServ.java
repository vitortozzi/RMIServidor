package rmi;

import entidades.Carro;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ImplementServ extends UnicastRemoteObject implements InterfaceServ {

    private ArrayList<Carro> carros;

    public ImplementServ() throws RemoteException {
        this.carros = new ArrayList<>();
        addCarros();
    }

    @Override
    public void chamar(String nomeCli, InterfaceCli interfaceCli) throws RemoteException {
        System.out.println(nomeCli);
        interfaceCli.echo("servidor mandando oi");
    }

    @Override
    public ArrayList<Carro> getAllCarros() throws RemoteException {
        return this.carros;
    }

    @Override
    public Carro getCarro(int id) throws RemoteException {
        int i = 0;
        boolean found = false;
        for (i = 0; i < carros.size(); i++) {
            if (carros.get(i).getId() == id) {
                found = true;
                break;
            }
        }

        if (found) {
            return this.carros.get(i);
        } else {
            return null;
        }
    }

    @Override
    public boolean locarCarro(Carro c, InterfaceCli refCliente) throws RemoteException {
        synchronized (this) {
            int i = 0;
            boolean found = false;           
            for (i = 0; i < carros.size(); i++) {
                if (carros.get(i).getId() == c.getId()) {
                    found = true;
                    break;
                }
            }

            if (found) {
                if(!carros.get(i).getDisponivel()){
                    return false;
                }
                carros.get(i).setDisponivel(false);
                carros.get(i).setClienteAtual(refCliente);
                carros.get(i).setLocalRetirada(c.getLocalRetirada());
                carros.get(i).setLocalDevolucao(c.getLocalDevolucao());
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean registrarIntCarro(int id, InterfaceCli refCliente) throws RemoteException {
        int i = 0;
        boolean found = false;
        for (i = 0; i < carros.size(); i++) {
            if (carros.get(i).getId() == id) {
                found = true;
                break;
            }
        }

        if (found) {
            carros.get(i).addClienteInteressado(refCliente);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addCarros() throws RemoteException {

        carros.add(new Carro(0, "Corolla", "Toyota", "ASD-1234", 50));
        carros.add(new Carro(1, "Civic", "Honda", "QWE-5123", 40));
        carros.add(new Carro(2, "Gol", "Volkswagen", "JIT-4892", 25));
        carros.add(new Carro(3, "Palio", "Fiat", "LOD-4923", 20));
        carros.add(new Carro(4, "Fit", "Honda", "INS-4921", 20));
        carros.add(new Carro(5, "Fiesta", "Ford", "FDS-6542", 30));
        carros.add(new Carro(6, "Fox", "Volkswagen", "MLA-9491", 35));

    }

    @Override
    public ArrayList<Carro> requestCarros() throws RemoteException {
        return carros;
    }

}
