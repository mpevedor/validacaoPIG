/*
 * Created by JFormDesigner on Sun Jul 02 01:33:36 BRT 2023
 */

package view;

import controller.ChamadoController;
import model.dto.ChamadoDto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;


public class AtendimentoChamadoView extends JFrame {

    private Long idChamado;
    private Long idUsuario;
    public AtendimentoChamadoView(Long idChamado, Long idUsuario) {
        this.idChamado = idChamado;
        this.idUsuario = idUsuario;
        initComponents();
        carregarChamado();
    }

    private void carregarChamado() {
        ChamadoController chamadoController = new ChamadoController();
        try {
            ChamadoDto chamado = chamadoController.carregarChamadoPorId(idChamado);
            textCodigo.setText(chamado.getCodigo());
            textTitulo.setText(chamado.getTitulo());
            textRequisitante.setText(chamado.getUsuarioRequisitante());
            textDataAbertura.setText(chamado.getDataHoraAbertura());
            textDescricao.setText(chamado.getDescricao());
            if (chamado.getIdUsuarioTecnico()!=0) {
                textDataFechamento.setVisible(true);
                scrollSolucao.setVisible(true);
                labelDataFechamento.setVisible(true);
                okButton.setText("Solucionar");
                if (!chamado.getDataHoraFechamento().isBlank()) {
                    textSolucao.setEditable(false);
                    okButton.setEnabled(false);
                    textSolucao.setText(chamado.getSolucao());
                    labelDataFechamento.setVisible(true);
                    textDataFechamento.setVisible(true);
                    textDataFechamento.setText(chamado.getDataHoraFechamento());
                }
            }

        } catch (SQLException e){

        }

    }

    private void voltarIndex(MouseEvent e) {
        IndexView mainView = new IndexView(idUsuario);
        mainView.setVisible(true);
        mainView.setLocationRelativeTo(null);
        dispose();
    }

    private void atenderChamado(MouseEvent e) {
        if (okButton.getText().equals("Solucionar")) {
            if (textSolucao.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Entre com uma solução!");
            } else {
                if(textSolucao.getText().length() <= 256) {
                    ChamadoController chamadoController = new ChamadoController();
                    try {
                        boolean resolvido = chamadoController.resolverChamado(idChamado, textSolucao.getText());
                        if (resolvido) {
                            JOptionPane.showMessageDialog(this, "Chamado resolvido!");
                            AtendimentoChamadoView atendimentoChamadoView = new AtendimentoChamadoView(idChamado, idUsuario);
                            atendimentoChamadoView.setVisible(true);
                            atendimentoChamadoView.setLocationRelativeTo(null);
                            dispose();
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Problema ao resolver chamado!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Solução excedeu o numero máximo de caracteres!");
                }
            }
        } else {
            ChamadoController chamadoController = new ChamadoController();
            try {
                boolean recebido = chamadoController.receberChamado(idChamado, idUsuario);
                if (recebido) {
                    textDataFechamento.setVisible(true);
                    labelSolucao.setVisible(true);
                    scrollSolucao.setVisible(true);
                    labelDataFechamento.setVisible(true);
                    okButton.setText("Resolver");
                }

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        textCodigo = new JTextField();
        textRequisitante = new JTextField();
        label3 = new JLabel();
        textTitulo = new JTextField();
        label4 = new JLabel();
        scrollDescricao = new JScrollPane();
        textDescricao = new JTextArea();
        label5 = new JLabel();
        textDataAbertura = new JTextField();
        labelSolucao = new JLabel();
        scrollSolucao = new JScrollPane();
        textSolucao = new JTextArea();
        labelDataFechamento = new JLabel();
        textDataFechamento = new JTextField();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {145, 74, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {15, 29, 15, 29, 15, 25, 0, 27, 25, 0, 0, 20, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};

                //---- label1 ----
                label1.setText("C\u00f3digo do Chamado");
                contentPanel.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- label2 ----
                label2.setText("Requisitante");
                contentPanel.add(label2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- textCodigo ----
                textCodigo.setEditable(false);
                contentPanel.add(textCodigo, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textRequisitante ----
                textRequisitante.setEditable(false);
                contentPanel.add(textRequisitante, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label3 ----
                label3.setText("T\u00edtulo");
                contentPanel.add(label3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textTitulo ----
                textTitulo.setEditable(false);
                contentPanel.add(textTitulo, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label4 ----
                label4.setText("Descri\u00e7\u00e3o");
                contentPanel.add(label4, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //======== scrollDescricao ========
                {

                    //---- textDescricao ----
                    textDescricao.setEditable(false);
                    scrollDescricao.setViewportView(textDescricao);
                }
                contentPanel.add(scrollDescricao, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label5 ----
                label5.setText("Data abertura");
                contentPanel.add(label5, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textDataAbertura ----
                textDataAbertura.setEditable(false);
                contentPanel.add(textDataAbertura, new GridBagConstraints(0, 7, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- labelSolucao ----
                labelSolucao.setText("Solu\u00e7\u00e3o");
                contentPanel.add(labelSolucao, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //======== scrollSolucao ========
                {
                    scrollSolucao.setVisible(false);
                    scrollSolucao.setViewportView(textSolucao);
                }
                contentPanel.add(scrollSolucao, new GridBagConstraints(0, 9, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- labelDataFechamento ----
                labelDataFechamento.setText("Data Fechamento");
                labelDataFechamento.setVisible(false);
                contentPanel.add(labelDataFechamento, new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textDataFechamento ----
                textDataFechamento.setVisible(false);
                textDataFechamento.setEditable(false);
                contentPanel.add(textDataFechamento, new GridBagConstraints(0, 11, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- okButton ----
                okButton.setText("Confirmar");
                okButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        atenderChamado(e);
                    }
                });
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("Cancelar");
                cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        voltarIndex(e);
                    }
                });
                buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JLabel label2;
    private JTextField textCodigo;
    private JTextField textRequisitante;
    private JLabel label3;
    private JTextField textTitulo;
    private JLabel label4;
    private JScrollPane scrollDescricao;
    private JTextArea textDescricao;
    private JLabel label5;
    private JTextField textDataAbertura;
    private JLabel labelSolucao;
    private JScrollPane scrollSolucao;
    private JTextArea textSolucao;
    private JLabel labelDataFechamento;
    private JTextField textDataFechamento;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
