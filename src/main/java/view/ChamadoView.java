/*
 * Created by JFormDesigner on Sat Jul 01 16:23:19 BRT 2023
 */

package view;

import controller.ChamadoController;
import model.dto.ChamadoEditarDto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class ChamadoView extends JDialog {

    private Long idChamado;
    private Long idUsuario;

    public ChamadoView(Window owner, Long idUsuario, Long idChamado) {
        super(owner);
        this.idUsuario = idUsuario;
        this.idChamado = idChamado;
        initComponents();
        if (idChamado != null) {
            okButton.setText("Editar");
            ChamadoController chamadoController = new ChamadoController();
            try {
                ChamadoEditarDto chamado = chamadoController.procurarChamadoPorId(idChamado);
                textCodigo.setText(chamado.getCodChamado());
                textTitulo.setText(chamado.getTitulo());
                textDescricao.setText(chamado.getDescricao());
            }catch (SQLException e) {

            }
        }
    }

    private void fecharTelaChamado(MouseEvent e) {
        IndexView mainView = new IndexView(idUsuario);
        mainView.setVisible(true);
        mainView.setLocationRelativeTo(null);
        dispose();
    }

    private void criarNovoChamado(MouseEvent e) {
        String erros = "";
        if(textTitulo.getText().isBlank()) {
            erros += "Título é obrigatório!\n";
        }
        if (textTitulo.getText().length()> 45){
            erros += "Título muito grande!\n";
        }
        if(textDescricao.getText().isBlank()) {
            erros += "Descrição é obrigatório!";
        }
        if (textDescricao.getText().length() > 100) {
            erros += "Descrição muito grande!\n";
        }
        if(!erros.isBlank()) {
            JOptionPane.showMessageDialog(this, erros);
        }else{
            if (idChamado == null) {
                try {
                    ChamadoController chamadoController = new ChamadoController();
                    boolean cadastrado = chamadoController.criarNovoChamado(idUsuario, textTitulo.getText(), textDescricao.getText());
                    if (cadastrado) {
                        JOptionPane.showMessageDialog(this, "Cadastrado com sucesso!");
                        IndexView mainView = new IndexView(idUsuario);
                        mainView.setVisible(true);
                        mainView.setLocationRelativeTo(null);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Não foi possível realizar o cadastro!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }else {
                try {
                    ChamadoController chamadoController = new ChamadoController();
                    boolean atualizado = chamadoController.atualizarChamado(idChamado, textTitulo.getText(), textDescricao.getText());
                    if (atualizado) {
                        JOptionPane.showMessageDialog(this, "Atualizado com sucesso!");
                        IndexView mainView = new IndexView(idUsuario);
                        mainView.setVisible(true);
                        mainView.setLocationRelativeTo(null);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Não foi possível realizar a atualização!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }

        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label3 = new JLabel();
        textCodigo = new JTextField();
        label1 = new JLabel();
        textTitulo = new JTextField();
        label2 = new JLabel();
        scrollPane1 = new JScrollPane();
        textDescricao = new JTextArea();
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
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 12, 0, 25, 0, 105, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0E-4};

                //---- label3 ----
                label3.setText("C\u00f3digo do Chamado");
                contentPanel.add(label3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- textCodigo ----
                textCodigo.setEditable(false);
                contentPanel.add(textCodigo, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- label1 ----
                label1.setText("Titulo");
                contentPanel.add(label1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
                contentPanel.add(textTitulo, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- label2 ----
                label2.setText("Descri\u00e7\u00e3o");
                contentPanel.add(label2, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //======== scrollPane1 ========
                {
                    scrollPane1.setViewportView(textDescricao);
                }
                contentPanel.add(scrollPane1, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.NORTH);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {202, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- okButton ----
                okButton.setText("Cadastrar");
                okButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        criarNovoChamado(e);
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
                        fecharTelaChamado(e);
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
    private JLabel label3;
    private JTextField textCodigo;
    private JLabel label1;
    private JTextField textTitulo;
    private JLabel label2;
    private JScrollPane scrollPane1;
    private JTextArea textDescricao;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
