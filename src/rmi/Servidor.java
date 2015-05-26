
package rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
    
    public static void main(String[] args) throws RemoteException {
        try {
//            Cria um registro de nomes
            Registry referenciaServicoNomes = LocateRegistry.createRegistry(9898);
//            Instancia a classe que implementa os métodos da interface do servidor
            ImplementServ serventeServidor = new ImplementServ();
//            Ligação da referência de nomes com a classe implementadora dos métodos do servidor
            referenciaServicoNomes.bind("ServenteServ", serventeServidor);
//            Instancia a janela do servidor, passando sua referencia
            ViewListener viewListener = new ViewListener(serventeServidor);
        } catch (Exception e) {
            
        }
    }
}
