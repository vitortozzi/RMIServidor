package rmi;

import entidades.Carro;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ImplementServ extends UnicastRemoteObject implements InterfaceServ {

//    Lista de carros armazenada pelo servidor
    private ArrayList<Carro> carros;

    public ImplementServ() throws RemoteException {
//        Inicializa a lista de carros
        this.carros = new ArrayList<>();
//        Popula a lista com os carros - estático
        addCarros();
    }

//    Método de teste
    @Override
    public void chamar(String nomeCli, InterfaceCli interfaceCli) throws RemoteException {
        System.out.println(nomeCli);
        interfaceCli.echo("servidor mandando oi");
    }
// Método que retorna a lista de carros do servidor
    @Override
    public ArrayList<Carro> getAllCarros() throws RemoteException {
        return this.carros;
    }
// Método que retorna um carro específico baseado no ID fornecido
    @Override
    public Carro getCarro(int id) throws RemoteException {
        int i = 0;
        boolean found = false;
        for (i = 0; i < carros.size(); i++) {
//            Encontrou o carro desejado
            if (carros.get(i).getId() == id) {
                found = true;
                break;
            }
        }

        if (found) {
//            Retorna o carro encontrado
            return this.carros.get(i);
        } else {
            return null;
        }
    }
// Método que processa a locação de um carro
    @Override
    public boolean locarCarro(Carro c, InterfaceCli refCliente) throws RemoteException {
//        Controle de concorrência
        synchronized (this) {
            int i = 0;
            boolean found = false;           
//            Procura pelo carro na lista de carros do servidor
            for (i = 0; i < carros.size(); i++) {
//                Encontrou o carro
                if (carros.get(i).getId() == c.getId()) {
                    found = true;
                    break;
                }
            }

            if (found) {
//                Carro já está alugado
                if(!carros.get(i).getDisponivel()){
                    return false;
                }
//                Carro é posto como indisponível (em locação)
                carros.get(i).setDisponivel(false);
                carros.get(i).setClienteAtual(refCliente);
                carros.get(i).setLocalRetirada(c.getLocalRetirada());
                carros.get(i).setLocalDevolucao(c.getLocalDevolucao());
//                Sucesso da transação
                return true;
            } else {
                return false;
            }
        }
    }

//    Método que registra o interesse de um cliente num determinado carro
    @Override
    public boolean registrarIntCarro(int id, InterfaceCli refCliente) throws RemoteException {
        int i = 0;
        boolean found = false;
//        Procura pelo carro na lista de carros do servidor
        for (i = 0; i < carros.size(); i++) {
//            Encontrou o carro
            if (carros.get(i).getId() == id) {
                found = true;
                break;
            }
        }

        if (found) {
//            Adiciona a referencia do cliente numa lista de referencias de clientes
            carros.get(i).addClienteInteressado(refCliente);
            return true;
        } else {
            return false;
        }
    }

//    Método estático que realiza a adição de carros
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
