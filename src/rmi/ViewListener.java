package rmi;

import entidades.Carro;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import views.HomeServidor;
import views.JanelaConsulta;

public class ViewListener implements ActionListener {

//    Referencia do servidor
    private InterfaceServ refServidor;

//    Janelas
    private HomeServidor home;
    private JanelaConsulta consulta;
    
//    Lista de carros
    ArrayList<Carro> carros;

    public ViewListener(InterfaceServ refServidor) throws RemoteException {
        this.refServidor = refServidor;

        this.home = new HomeServidor();
        this.home.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.home.setVisible(true);

        this.home.getjButton1().addActionListener(this);
//        Popula tabela com os carros do servidor
        populaTabela();

        consulta = new JanelaConsulta();

    }

//    Mapeamento dos listenerts da janela de consulta
    public void mapearAcoesBttnsJanelaConsultar() {
        this.consulta.getBtnAlterarPreco().addActionListener(this);
    }

//    Eventos listeners
    @Override
    public void actionPerformed(ActionEvent e) {

//        Botão consultar veículo
        if (e.getSource() == this.home.getjButton1()) {
            int rowIndex = home.getjTable1().getSelectedRow();

            if (rowIndex != -1) {
//                Procura o carro pela placa do carro selecionado
                String placaEscolhido = (String) home.getjTable1().getModel().getValueAt(rowIndex, 1);
                Carro temp = null;
//                Procura do carro
                for (Carro c : carros) {
//                    Encontrou o carro
                    if (c.getPlaca().equals(placaEscolhido)) {
                        temp = c;
                    }
                }
                if (temp != null) {
//                    Preenche os dados da janela com os dados do carro selecionado anteriormente
                    consulta = new JanelaConsulta();
                    consulta.getlModelo().setText(temp.getModelo());
                    consulta.gettMarca().setText(temp.getMarca());
                    consulta.gettValor().setText(String.valueOf(temp.getPrecoDiaria()));
                    consulta.gettPlaca().setText(temp.getPlaca());
                    this.mapearAcoesBttnsJanelaConsultar();
                    consulta.setVisible(true);
                    if (!temp.getDisponivel()) {
                        consulta.gettStatus().setText("Alugado");
                    } else {
                        consulta.gettStatus().setText("Disponível");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(consulta, "Selecione um carro da tabela para alterar o preço.");
            }
        } 
//        Botão alterar preço
        else if (e.getSource() == this.consulta.getBtnAlterarPreco()) {
//            Verificação se campo do valor não está vazio
            if (!this.consulta.gettValor().getText().equals("") && this.consulta.gettValor().getText() != null) {
//                Recupera a placa do veículo para realizar a procura
                String placa = this.consulta.gettPlaca().getText();
                try {
//                    Novo preço e diária é extraido do campo na janela
                    double novoPrecoDiaria = Double.parseDouble(this.consulta.gettValor().getText());
//                    Flag que indica se o preço sofreu 
                    boolean diminuiuValor = false;
                    for (Carro c : carros) {
//                        Procura pelo veículo
                        if (c.getPlaca().equals(placa)) {
                            if (novoPrecoDiaria < c.getPrecoDiaria()) {
//                                Veículo sofreu redução no valor da diária
                                diminuiuValor = true;
                            }
//                            Atualização do valor da diária do veículo
                            c.setPrecoDiaria(novoPrecoDiaria);
                            System.out.println("Existem " + c.getClientesInteressados().size() + " clientes interessados no carro " + c.getModelo() + " da " + c.getMarca());
//                            Atualiza todos os clientes interssados sobre a mudança no valor da diária
                            for (InterfaceCli ic : c.getClientesInteressados()) {
                                try {
//                                    Referencia ao cliente que possui um método que realizará a atualização
                                    ic.receberNotificacao(c, diminuiuValor);
                                } catch (RemoteException ex) {
                                    Logger.getLogger(ViewListener.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            break;

                        }

                    }                    
                    JOptionPane.showMessageDialog(consulta, "Preço alterado com sucesso.");
                    this.consulta.dispose();
//                    Atualiza a tabela do servidor
                    atualizarTabela(placa, novoPrecoDiaria);
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(consulta, "Insira um valor válido para o preço da diária!");
                }
            } else {
                JOptionPane.showMessageDialog(consulta, "Insira um valor para o preço da diária!");
            }
        }

    }

//    Popula a tabela do servidor com os veículos
    private void populaTabela() throws RemoteException {
        DefaultTableModel model = (DefaultTableModel) home.getjTable1().getModel();
        carros = refServidor.requestCarros();
        for (Carro c : carros) {
            model.addRow(new Object[]{c.getModelo(), c.getPlaca(), c.getPrecoDiaria()});
        }
    }

//    Atualiza a tabela de veiculos do servidor, alterando o valor da diária
    public void atualizarTabela(String placa, double novoPreco) {
        for (int i = 0; i < this.home.getjTable1().getRowCount(); i++) {
            //System.out.println(jTable1.getModel().getValueAt(i, 4).toString());
            String placaTable = (String) this.home.getjTable1().getValueAt(i, 1);
            if (placaTable.equals(placa)) {
                System.out.println("Achou carro na tabela!");
                this.home.getjTable1().setValueAt(novoPreco, i, 2);
            }
        }
    }

}
