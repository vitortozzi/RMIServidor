
package rmi;

import entidades.Carro;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import views.HomeServidor;
import views.JanelaConsulta;

public class ViewListener implements ActionListener {

    private InterfaceServ refServidor;
    
    //private JanelaPrincipal principal;
    private HomeServidor home;
    private JanelaConsulta consulta;
    ArrayList<Carro> carros;
    
    public ViewListener(InterfaceServ refServidor) throws RemoteException {
        this.refServidor = refServidor;
        
//        this.principal = new JanelaPrincipal();
        this.home = new HomeServidor();
//        this.principal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.home.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        this.principal.setVisible(true);
        this.home.setVisible(true);
        
//        this.principal.getjButton1().addActionListener(this);
        this.home.getjButton1().addActionListener(this);
//        this.principal.getjButton2().addActionListener(this);
        populaTabela();
        
        consulta = new JanelaConsulta();
        
    }

    public void mapearAcoesBttnsJanelaConsultar() {
        this.consulta.getBtnAlterarPreco().addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().equals(this.home.getjButton1())){
            int rowIndex = home.getjTable1().getSelectedRow();
            
            if(rowIndex != -1){
                String placaEscolhido = (String) home.getjTable1().getModel().getValueAt(rowIndex, 1);
                Carro temp = null;
                for(Carro c: carros){
                    if(c.getPlaca().equals(placaEscolhido)){
                        temp = c;
                    }
                }
                if(temp!=null){
                    consulta.getlModelo().setText(temp.getModelo());
                    consulta.gettMarca().setText(temp.getMarca());
                    consulta.gettValor().setText(String.valueOf(temp.getPrecoDiaria()));
                    consulta.gettPlaca().setText(temp.getPlaca());
                    this.mapearAcoesBttnsJanelaConsultar();
                    consulta.setVisible(true);
                    if(!temp.getDisponivel()){
                        consulta.gettStatus().setText("Alugado");
                    }else{
                        consulta.gettStatus().setText("Disponível");
                    }
                }
            }
        } else if (e.getSource().equals(this.consulta.getBtnAlterarPreco())) {
            String placa = this.consulta.gettPlaca().getText();
            double novoPrecoDiaria = Double.parseDouble(this.consulta.gettValor().getText());
            for (Carro c: carros) {
                if (c.getPlaca().equals(placa)) {
                    c.setPrecoDiaria(novoPrecoDiaria);
                    for (InterfaceCli ic: c.getClientesInteressados()) {
                        try {
                            ic.atualizarLista(c);
                        } catch (RemoteException ex) {
                            Logger.getLogger(ViewListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                }
                
            }
            this.consulta.setVisible(false);
            atualizarTabela(placa, novoPrecoDiaria);
        }
        
    }

    private void populaTabela() throws RemoteException {
        DefaultTableModel model = (DefaultTableModel) home.getjTable1().getModel();
        carros = refServidor.requestCarros();
        for(Carro c: carros){
            model.addRow(new Object[]{c.getModelo(), c.getPlaca(), c.getPrecoDiaria()});
        }
    }
    
    public void atualizarTabela(String placa, double novoPreco) {
        for (int i = 0; i < this.home.getjTable1().getRowCount(); i++) {
            //System.out.println(jTable1.getModel().getValueAt(i, 4).toString());
            String placaTable = (String) this.home.getjTable1().getValueAt(i, 1);
            if( placaTable.equals(placa) ){
                System.out.println("Achou carro na tabela!");
                this.home.getjTable1().setValueAt(novoPreco, i, 2);
            }
        }
    }
    
}
