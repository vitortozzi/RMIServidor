
package rmi;

import entidades.Carro;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface InterfaceCli extends Remote {
    public void echo(String msg) throws RemoteException;
    public void atualizarLista(Carro c) throws RemoteException;
    
}
