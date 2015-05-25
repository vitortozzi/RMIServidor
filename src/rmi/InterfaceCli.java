
package rmi;

import entidades.Carro;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceCli extends Remote {
    
    public void echo(String msg) throws RemoteException;
    
    public int getIdCliente() throws RemoteException;
    public String getNomeCliente() throws RemoteException;
    public void receberNotificacao(Carro c, boolean desceValor) throws RemoteException;
}
