/*
 * Created by JFormDesigner on Tue Jul 04 00:13:50 BRT 2023
 */

package view;

import controller.UsuarioController;
import model.dto.UsuarioEditarDto;
import util.HashUtils;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.*;


public class CadastroUsuarioView extends JFrame {

    private Long idUsuario;

    private Long idUsuarioEditar;
    public CadastroUsuarioView(Long idUsuario, Long idUsuarioEditar) {
        this.idUsuario = idUsuario;
        this.idUsuarioEditar = idUsuarioEditar;
        initComponents();
        if (idUsuarioEditar != null){
            UsuarioController usuarioController = new UsuarioController();
            UsuarioEditarDto usuario = null;
            try {
                usuario = usuarioController.carregaUsuarioPorId(idUsuarioEditar);
                textCodigo.setText(usuario.getCodUsuario());
                textCodigo.setEditable(false);
                textLogin.setText(usuario.getLogin());
                textLogin.setEditable(false);
                textNome.setText(usuario.getNome());
                textNome.setEditable(false);
                textEmail.setText(usuario.getEmail());
                textEmail.setEditable(false);
                botaoCadastrar.setText("Atualizar senha");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao carregar usuario");
            }

        }
    }

    private void cancelarCadastroUsuario(MouseEvent e) {
        IndexView mainView = new IndexView(idUsuario);
        mainView.setVisible(true);
        mainView.setLocationRelativeTo(null);
        dispose();
    }

    private void botaoCadastrarUsuario(MouseEvent e) {
        if(idUsuarioEditar != null){
            String erro ="";
            if (String.valueOf(passwordSenha.getPassword()).isBlank()) {
                erro += "Senha é obrigatório!\n";
            }
            if (String.valueOf(passwordSenha.getPassword()).length() > 128) {
                erro += "Senha muito grande!\n";
            }
            if(!erro.isBlank()){
                JOptionPane.showMessageDialog(null, erro);
            } else {
                UsuarioController usuarioController = new UsuarioController();
                String hashSenha = HashUtils.CriptografaSenha(Arrays.toString(passwordSenha.getPassword()));
                try {
                    boolean atualizou = usuarioController.alterarSenha(idUsuarioEditar, hashSenha);
                    if (atualizou) {
                        JOptionPane.showMessageDialog(null, "Senha alterada com sucesso!");
                        IndexView mainView = new IndexView(idUsuario);
                        mainView.setVisible(true);
                        mainView.setLocationRelativeTo(null);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao alterar senha");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao alterar a senha!");
                }
            }

        } else {
            String erro ="";
            if (textCodigo.getText().isBlank()) {
                erro += "Código é obrigatório!\n";
            }
            if(textCodigo.getText().length() > 6) {
                erro += "Código muito grande!\n";
            }
            if(textNome.getText().isBlank()) {
                erro += "Nome é obrigatório!\n";
            }
            if (textNome.getText().length() > 70) {
                erro += "Nome é muito grande!\n";
            }
            if (textEmail.getText().isBlank()) {
                erro += "Email é obrigatório!\n";
            }
            if (textEmail.getText().length() > 50) {
                erro += "Email muito grande!\n";
            }
            if (!validaEmail(textEmail.getText())){
                erro += "Informe um email válido!\n";
            }
            if (textLogin.getText().isBlank()) {
                erro += "Login é obrigatório!\n";
            }
            if (textLogin.getText().length() > 50) {
                erro += "Login muito grande!\n";
            }
            if (String.valueOf(passwordSenha.getPassword()).isBlank()) {
                erro += "Senha é obrigatório!\n";
            }
            if (String.valueOf(passwordSenha.getPassword()).length() > 128) {
                erro += "Senha muito grande!\n";
            }
            if(!erro.isBlank()){
                JOptionPane.showMessageDialog(null, erro);
            } else {
                try {
                    UsuarioController usuarioController = new UsuarioController();
                    String hashSenha = HashUtils.CriptografaSenha(Arrays.toString(passwordSenha.getPassword()));
                    String codigo = textCodigo.getText();
                    String nome = textNome.getText();
                    String email = textEmail.getText();
                    String login = textLogin.getText();
                    boolean cadastroOk = usuarioController.cadastrarUsuario(codigo, nome, email, login, hashSenha);
                    if (cadastroOk) {
                        JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                        IndexView mainView = new IndexView(idUsuario);
                        mainView.setVisible(true);
                        mainView.setLocationRelativeTo(null);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario não cadastrado, verifique os dados!");
                    }
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuário!");
                }
            }
        }



    }

    public static boolean validaEmail(String email) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label5 = new JLabel();
        textCodigo = new JTextField();
        label1 = new JLabel();
        textNome = new JTextField();
        label2 = new JLabel();
        textEmail = new JTextField();
        label3 = new JLabel();
        label4 = new JLabel();
        textLogin = new JTextField();
        passwordSenha = new JPasswordField();
        buttonBar = new JPanel();
        botaoCadastrar = new JButton();
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
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {20, 25, 20, 31, 20, 29, 20, 25, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- label5 ----
                label5.setText("C\u00f3digo");
                contentPanel.add(label5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                contentPanel.add(textCodigo, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- label1 ----
                label1.setText("Nome");
                contentPanel.add(label1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                contentPanel.add(textNome, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label2 ----
                label2.setText("Email");
                contentPanel.add(label2, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                contentPanel.add(textEmail, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label3 ----
                label3.setText("Login");
                contentPanel.add(label3, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- label4 ----
                label4.setText("Senha");
                contentPanel.add(label4, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));
                contentPanel.add(textLogin, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));
                contentPanel.add(passwordSenha, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
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

                //---- botaoCadastrar ----
                botaoCadastrar.setText("Cadastrar");
                botaoCadastrar.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        botaoCadastrarUsuario(e);
                    }
                });
                buttonBar.add(botaoCadastrar, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("Cancelar");
                cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cancelarCadastroUsuario(e);
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
    private JLabel label5;
    private JTextField textCodigo;
    private JLabel label1;
    private JTextField textNome;
    private JLabel label2;
    private JTextField textEmail;
    private JLabel label3;
    private JLabel label4;
    private JTextField textLogin;
    private JPasswordField passwordSenha;
    private JPanel buttonBar;
    private JButton botaoCadastrar;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
