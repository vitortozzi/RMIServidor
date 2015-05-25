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

    private InterfaceServ refServidor;

    private HomeServidor home;
    private JanelaConsulta consulta;
    ArrayList<Carro> carros;

    public ViewListener(InterfaceServ refServidor) throws RemoteException {
        this.refServidor = refServidor;

        this.home = new HomeServidor();
        this.home.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.home.setVisible(true);

        this.home.getjButton1().addActionListener(this);
        populaTabela();

        consulta = new JanelaConsulta();

    }

    public void mapearAcoesBttnsJanelaConsultar() {
        this.consulta.getBtnAlterarPreco().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.home.getjButton1()) {
            int rowIndex = home.getjTable1().getSelectedRow();

            if (rowIndex != -1) {
                String placaEscolhido = (String) home.getjTable1().getModel().getValueAt(rowIndex, 1);
                Carro temp = null;
                for (Carro c : carros) {
                    if (c.getPlaca().equals(placaEscolhido)) {
                        temp = c;
                    }
                }
                if (temp != null) {
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
        } else if (e.getSource() == this.consulta.getBtnAlterarPreco()) {
            if (!this.consulta.gettValor().getText().equals("") && this.consulta.gettValor().getText() != null) {
                String placa = this.consulta.gettPlaca().getText();
                try {
                    double novoPrecoDiaria = Double.parseDouble(this.consulta.gettValor().getText());
                    boolean diminuiuValor = false;
                    for (Carro c : carros) {
                        if (c.getPlaca().equals(placa)) {
                            if (novoPrecoDiaria < c.getPrecoDiaria()) {
                                diminuiuValor = true;
                            }
                            c.setPrecoDiaria(novoPrecoDiaria);
                            System.out.println("Existem " + c.getClientesInteressados().size() + " clientes interessados no carro " + c.getModelo() + " da " + c.getMarca());
                            for (InterfaceCli ic : c.getClientesInteressados()) {
                                try {
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
                    atualizarTabela(placa, novoPrecoDiaria);
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(consulta, "Insira um valor válido para o preço da diária!");
                }
            } else {
                JOptionPane.showMessageDialog(consulta, "Insira um valor para o preço da diária!");
            }
        }

    }

    private void populaTabela() throws RemoteException {
        DefaultTableModel model = (DefaultTableModel) home.getjTable1().getModel();
        carros = refServidor.requestCarros();
        for (Carro c : carros) {
            model.addRow(new Object[]{c.getModelo(), c.getPlaca(), c.getPrecoDiaria()});
        }
    }

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
