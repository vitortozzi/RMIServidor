
package rmi;

import entidades.Carro;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/* metodos no servidor:
    - get all carros
    - get carro
    - locar carro
    - registrar interesse em carro 
*/
public interface InterfaceServ extends Remote {
    
    public ArrayList<Carro> getAllCarros() throws RemoteException;
    public Carro getCarro(int id) throws RemoteException;
    public boolean locarCarro(Carro c, InterfaceCli refCliente) throws RemoteException;
    public boolean registrarIntCarro(int id, InterfaceCli refCliente) throws RemoteException;
    public void addCarros() throws RemoteException;
    public ArrayList<Carro> requestCarros() throws RemoteException;
    
    public void chamar(String nomeCli, InterfaceCli interfaceCli) throws RemoteException;
}
