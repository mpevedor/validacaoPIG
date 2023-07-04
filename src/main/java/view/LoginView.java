/*
 * Created by JFormDesigner on Sun Jun 25 00:44:54 BRT 2023
 */

package view;

import controller.UsuarioController;
import util.HashUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class LoginView extends JFrame {
    public LoginView() {
        initComponents();
    }

    public static void initialize() {
        LoginView mainView = new LoginView();
        mainView.setVisible(true);
        mainView.setLocationRelativeTo(null);
    }
    private void cancelButtonMouseClicked(MouseEvent e) {
        this.dispose();
    }

    private void okButtonMouseClicked(MouseEvent e) {
        boolean erro = false;
        if (textLogin.getText().isBlank()){
            message_usuario.setText("Usuário inválido!");
            erro = true;
        }
        if(textSenha.getPassword().length == 0) {
            message_senha.setText("Password inválido!");
            erro=true;
        }


        if(!erro){
            try {
                String senha = Arrays.toString(textSenha.getPassword());
                String hash = HashUtils.CriptografaSenha(senha);
                String login = textLogin.getText();
                UsuarioController usuarioController = new UsuarioController();
                Long idUsuario = usuarioController.logar(login, hash);
                if(idUsuario != null){
                    IndexView mainView = new IndexView(idUsuario);
                    mainView.setVisible(true);
                    mainView.setLocationRelativeTo(null);
                    dispose();
                } else {
                    message_usuario.setText("Login/Senha incorretos!");
                    message_senha.setText("");
                }
            } catch (Exception ex){
                System.out.println("Erro ao fazer login.");
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        buttonBar = new JPanel();
        label1 = new JLabel();
        textLogin = new JTextField();
        label2 = new JLabel();
        textSenha = new JPasswordField();
        message_usuario = new JLabel();
        message_senha = new JLabel();
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
                contentPanel.setLayout(new GridLayout(1, 1));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- label1 ----
                label1.setText("Login");
                label1.setHorizontalAlignment(SwingConstants.CENTER);
                buttonBar.add(label1, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));
                buttonBar.add(textLogin, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label2 ----
                label2.setText("Senha");
                label2.setHorizontalAlignment(SwingConstants.CENTER);
                buttonBar.add(label2, new GridBagConstraints(0, 3, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));
                buttonBar.add(textSenha, new GridBagConstraints(0, 4, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- message_usuario ----
                message_usuario.setHorizontalAlignment(SwingConstants.CENTER);
                buttonBar.add(message_usuario, new GridBagConstraints(0, 6, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- message_senha ----
                message_senha.setHorizontalAlignment(SwingConstants.CENTER);
                buttonBar.add(message_senha, new GridBagConstraints(0, 7, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- okButton ----
                okButton.setText("Autenticar");
                okButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        okButtonMouseClicked(e);
                    }
                });
                buttonBar.add(okButton, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("Fechar");
                cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cancelButtonMouseClicked(e);
                    }
                });
                buttonBar.add(cancelButton, new GridBagConstraints(2, 8, 1, 1, 0.0, 0.0,
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
    private JPanel buttonBar;
    private JLabel label1;
    private JTextField textLogin;
    private JLabel label2;
    private JPasswordField textSenha;
    private JLabel message_usuario;
    private JLabel message_senha;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
