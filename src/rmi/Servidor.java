
package rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
    
    public static void main(String[] args) throws RemoteException {
        try {
            Registry referenciaServicoNomes = LocateRegistry.createRegistry(9898);
            ImplementServ serventeServidor = new ImplementServ();
            referenciaServicoNomes.bind("ServenteServ", serventeServidor);
        } catch (Exception e) {
            
        }
    }
}
