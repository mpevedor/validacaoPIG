/*
 * Created by JFormDesigner on Sun Jun 25 01:13:58 BRT 2023
 */

package view;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;
import controller.ChamadoController;
import model.dto.ChamadoTabelaDto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class      IndexView extends JFrame {

    private Long idUsuario;

    private List<ChamadoTabelaDto> listarChamados = new ArrayList<>();
    private List<ChamadoTabelaDto> listarChamadosAbertos = new ArrayList<>();
    private List<ChamadoTabelaDto> listarChamadosAtendidos = new ArrayList<>();


    public IndexView(Long idUsuario) {
        this.idUsuario = idUsuario;
        initComponents();
        carregarChamados();
        carregarMeusChamadosAbertos();
        carregarMeusChamadosAtendidos();

    }


    private void carregarMeusChamadosAtendidos() {
        ChamadoController chamadoController = new ChamadoController();
        try {
            listarChamadosAtendidos = chamadoController.carregarMeusChamadosAtendidos(idUsuario);
            DefaultTableModel modelo = (DefaultTableModel) tableChamadosAtendidos.getModel();
            for(ChamadoTabelaDto chamado : listarChamadosAtendidos) {
                modelo.addRow(new Object[]{
                        chamado.getCodigo(), chamado.getDataHora(), chamado.getTitulo()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,"Não foi possível carregar seus chamados abertos!");
        }
    }

    private void carregarMeusChamadosAbertos() {
        ChamadoController chamadoController = new ChamadoController();
        try {
            listarChamadosAbertos = chamadoController.carregarMeusChamadosAbertos(idUsuario);
            DefaultTableModel modelo = (DefaultTableModel) tableChamadosAbertos.getModel();
            for(ChamadoTabelaDto chamado : listarChamadosAbertos) {
                modelo.addRow(new Object[]{
                        chamado.getCodigo(), chamado.getDataHora(), chamado.getTitulo()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,"Não foi possível carregar seus chamados abertos!");
        }

    }

    private void carregarChamados() {
        ChamadoController chamadoController = new ChamadoController();
        try {
            listarChamados = chamadoController.carregarChamadosUsuarios();
            DefaultTableModel modelo = (DefaultTableModel) tableChamados.getModel();
            for(ChamadoTabelaDto chamado : listarChamados) {
                modelo.addRow(new Object[]{
                        chamado.getCodigo(), chamado.getDataHora(), chamado.getTitulo()
                });
            }
            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelo);
            tableChamados.setRowSorter(sorter);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,"Não foi possível carregar os chamados!");
        }
    }

    private void novoChamadoButtonMouseClicked(MouseEvent e) {
        ChamadoView chamadoView = new ChamadoView(null, idUsuario, null);
        chamadoView.setVisible(true);
        chamadoView.setLocationRelativeTo(null);
        dispose();
    }

    private void tabelaTodosChamados(MouseEvent e) {
        boolean marcado = checkBoxTodosChamados.isSelected();
        ChamadoController chamadoController = new ChamadoController();
        if(marcado) {
            try {
                listarChamados = chamadoController.carregarChamados();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Não foi possível carregar os chamados!");
            }
        }else {
            try {
                listarChamados = chamadoController.carregarChamadosUsuarios();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Não foi possível carregar os chamados!");
            }
        }
        atualizarTabelaChamadosAtendidos(tableChamados, listarChamados);
    }

    private void editarDeletarChamado(MouseEvent e) {
        int botaoClicado = e.getButton();

        if (botaoClicado == 1) {
            int i = tableChamados.rowAtPoint(e.getPoint());
            Long idChamado = listarChamados.get(i).getIdChamado();
            ChamadoView chamadoView = new ChamadoView(null, idUsuario, idChamado);
            chamadoView.setVisible(true);
            chamadoView.setLocationRelativeTo(null);
            dispose();
        }

        if (botaoClicado == 2) {
            int i = tableChamados.rowAtPoint(e.getPoint());
            Long idChamado = listarChamados.get(i).getIdChamado();
            AtendimentoChamadoView atendimentoChamadoView = new AtendimentoChamadoView(idChamado, idUsuario);
            atendimentoChamadoView.setVisible(true);
            atendimentoChamadoView.setLocationRelativeTo(null);
            dispose();
        }

        if (botaoClicado == 3) {
            int i = tableChamados.rowAtPoint(e.getPoint());
            int confirma = JOptionPane.showConfirmDialog(this, "Você tem certeza que deseja apagar o chamado: " +
                    listarChamados.get(i).getTitulo() + "?", "Excluir chamado", JOptionPane.YES_NO_OPTION);
            if (confirma == 0) {
                ChamadoController chamadoController = new ChamadoController();
                try {
                    boolean excluido = chamadoController.excluirChamadoPorId(listarChamados.get(i).getIdChamado());
                    if (excluido) {
                        JOptionPane.showMessageDialog(this, "Chamado excluído com sucesso!");
                        ChamadoTabelaDto chamadoTabelaDto = listarChamados.get(i);
                        listarChamados.remove(chamadoTabelaDto);
                        listarChamadosAbertos.remove(chamadoTabelaDto);
                        listarChamadosAtendidos.remove(chamadoTabelaDto);
                        atualizarTabelaChamadosAtendidos(tableChamados,listarChamados);
                        atualizarTabelaChamadosAtendidos(tableChamadosAbertos, listarChamadosAbertos);
                        atualizarTabelaChamadosAtendidos(tableChamadosAtendidos, listarChamadosAtendidos);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private void editarDeletarMeusChamadosAbertos(MouseEvent e) {
        int botaoClicado = e.getButton();

        if (botaoClicado == 1) {
            int i = tableChamadosAbertos.rowAtPoint(e.getPoint());
            Long idChamado = listarChamadosAbertos.get(i).getIdChamado();
            ChamadoView chamadoView = new ChamadoView(null, idUsuario, idChamado);
            chamadoView.setVisible(true);
            chamadoView.setLocationRelativeTo(null);
            dispose();
        }

        if (botaoClicado == 2) {
            int i = tableChamadosAbertos.rowAtPoint(e.getPoint());
            Long idChamado = listarChamadosAbertos.get(i).getIdChamado();
            AtendimentoChamadoView atendimentoChamadoView = new AtendimentoChamadoView(idChamado, idUsuario);
            atendimentoChamadoView.setVisible(true);
            atendimentoChamadoView.setLocationRelativeTo(null);
            dispose();
        }

        if (botaoClicado == 3) {
            int i = tableChamadosAbertos.rowAtPoint(e.getPoint());
            int confirma = JOptionPane.showConfirmDialog(this, "Você tem certeza que deseja apagar o chamado: " +
                    listarChamadosAbertos.get(i).getTitulo() + "?", "Excluir chamado", JOptionPane.YES_NO_OPTION);
            if (confirma == 0) {
                ChamadoController chamadoController = new ChamadoController();
                try {
                    boolean excluido = chamadoController.excluirChamadoPorId(listarChamadosAbertos.get(i).getIdChamado());
                    if (excluido) {
                        JOptionPane.showMessageDialog(this, "Chamado excluído com sucesso!");
                        ChamadoTabelaDto chamadoTabelaDto = listarChamadosAbertos.get(i);
                        listarChamados.remove(chamadoTabelaDto);
                        listarChamadosAbertos.remove(chamadoTabelaDto);
                        listarChamadosAtendidos.remove(chamadoTabelaDto);
                        atualizarTabelaChamadosAtendidos(tableChamados,listarChamados);
                        atualizarTabelaChamadosAtendidos(tableChamadosAbertos, listarChamadosAbertos);
                        atualizarTabelaChamadosAtendidos(tableChamadosAtendidos, listarChamadosAtendidos);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private void editarDeletarChamadosAtendidos(MouseEvent e) {
        int botaoClicado = e.getButton();

        if (botaoClicado == 1) {
            int i = tableChamadosAtendidos.rowAtPoint(e.getPoint());
            Long idChamado = listarChamadosAtendidos.get(i).getIdChamado();
            ChamadoView chamadoView = new ChamadoView(null, idUsuario, idChamado);
            chamadoView.setVisible(true);
            chamadoView.setLocationRelativeTo(null);
            dispose();
        }

        if (botaoClicado == 2) {
            int i = tableChamadosAtendidos.rowAtPoint(e.getPoint());
            Long idChamado = listarChamadosAtendidos.get(i).getIdChamado();
            AtendimentoChamadoView atendimentoChamadoView = new AtendimentoChamadoView(idChamado, idUsuario);
            atendimentoChamadoView.setVisible(true);
            atendimentoChamadoView.setLocationRelativeTo(null);
            dispose();
        }

        if (botaoClicado == 3) {
            int i = tableChamadosAtendidos.rowAtPoint(e.getPoint());
            int confirma = JOptionPane.showConfirmDialog(this, "Você tem certeza que deseja apagar o chamado: " +
                    listarChamadosAtendidos.get(i).getTitulo() + "?", "Excluir chamado", JOptionPane.YES_NO_OPTION);
            if (confirma == 0) {
                ChamadoController chamadoController = new ChamadoController();
                try {
                    boolean excluido = chamadoController.excluirChamadoPorId(listarChamadosAtendidos.get(i).getIdChamado());
                    if (excluido) {
                        Long idChamado = listarChamados.get(i).getIdChamado();
                        JOptionPane.showMessageDialog(this, "Chamado excluído com sucesso!");
                        ChamadoTabelaDto chamadoTabelaDto = listarChamadosAtendidos.get(i);
                        listarChamados.remove(chamadoTabelaDto);
                        listarChamadosAbertos.remove(chamadoTabelaDto);
                        listarChamadosAtendidos.remove(chamadoTabelaDto);
                        atualizarTabelaChamadosAtendidos(tableChamados,listarChamados);
                        atualizarTabelaChamadosAtendidos(tableChamadosAbertos, listarChamadosAbertos);
                        atualizarTabelaChamadosAtendidos(tableChamadosAtendidos, listarChamadosAtendidos);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private void atualizarTabelaChamadosAtendidos(JTable tableChamadosAtendidos, List<ChamadoTabelaDto> listarChamadosAtendidos) {
        DefaultTableModel modelo = (DefaultTableModel) tableChamadosAtendidos.getModel();
        modelo.getDataVector().removeAllElements();
        for (ChamadoTabelaDto chamado : listarChamadosAtendidos) {
            modelo.addRow(new Object[]{
                    chamado.getCodigo(), chamado.getDataHora(), chamado.getTitulo()
            });
        }
        modelo.fireTableDataChanged();
    }

    private void gerarRelatorio(MouseEvent e) {
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(f.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String path = f.getSelectedFile().getPath();
            ChamadoController chamadoController = new ChamadoController();
            try {
                int numeroChamadosAbertos = chamadoController.buscarTotalChamadosAbertos ();
                int numeroChamadosEmAtendimento = chamadoController.buscarTotalChamadosEmAtendimento ();
                int numeroChamadosFechados = chamadoController.buscarTotalChamadosFechados ();
                int numeroChamadosFechadosHoje = chamadoController.buscarTotalChamadosFechadosHoje ();
                int numeroMeusChamadosAbertos = chamadoController.buscarTotalMeusChamadosAbertos (idUsuario);
                int numeroMeusChamadosEmAtendimento = chamadoController.buscarTotalMeusChamadosEmAtendimento (idUsuario);
                int numeroChamadosFechadosPorMim = chamadoController.buscarTotalChamadosFechadosPorMim (idUsuario);

                Document relatorio = new Document();
                PdfWriter.getInstance(relatorio, new FileOutputStream(path+"\\Relatorio.pdf"));

                relatorio.open();
                Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dataHoje = simpleDateFormat.format(new Date());
                Chunk titulo = new Chunk("Relatório - "+dataHoje, font);
                Chunk infos = new Chunk("\nNúmero de chamados abertos: "+numeroChamadosAbertos+"\n");
                infos.append("Número de chamados em atendimento: "+numeroChamadosEmAtendimento+"\n");
                infos.append("Número de chamados resolvidos: "+numeroChamadosFechados+"\n");
                infos.append("Número de chamados resolvidos hoje: "+numeroChamadosFechadosHoje+"\n");
                infos.append("Número de chamados abertos por mim: "+numeroMeusChamadosAbertos+"\n");
                infos.append("Número de chamados atendidos por mim: "+numeroMeusChamadosEmAtendimento+"\n");
                infos.append("Número de chamados resolvidos por mim: "+numeroChamadosFechadosPorMim+"\n");
                Paragraph p1 = new Paragraph(titulo);
                Paragraph p2 = new Paragraph(infos);
                relatorio.add(p1);
                relatorio.add(p2);
                relatorio.close();
                JOptionPane.showMessageDialog(null, "Relatório gerado com sucesso!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!");
            }
        }

    }

    private void cadastroUsuario(MouseEvent e) {
        CadastroUsuarioView cadastroUsuarioView = new CadastroUsuarioView(idUsuario, null);
        cadastroUsuarioView.setVisible(true);
        cadastroUsuarioView.setLocationRelativeTo(null);
        dispose();
    }

    private void alterarSenha(MouseEvent e) {
        CadastroUsuarioView cadastroUsuarioView = new CadastroUsuarioView(idUsuario, idUsuario);
        cadastroUsuarioView.setVisible(true);
        cadastroUsuarioView.setLocationRelativeTo(null);
        dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        content = new JPanel();
        panel2 = new JPanel();
        button2 = new JButton();
        scrollPane1 = new JScrollPane();
        tableChamados = new JTable();
        desktopPane1 = new JDesktopPane();
        internalFrame1 = new JInternalFrame();
        scrollPane2 = new JScrollPane();
        tableChamadosAbertos = new JTable();
        desktopPane2 = new JDesktopPane();
        internalFrame2 = new JInternalFrame();
        scrollPane3 = new JScrollPane();
        tableChamadosAtendidos = new JTable();
        buttonAlterarSenha = new JButton();
        checkBoxTodosChamados = new JCheckBox();
        novoChamadoButton = new JButton();
        button1 = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(1, 1));

        //======== content ========
        {
            content.setLayout(new BorderLayout());

            //======== panel2 ========
            {
                panel2.setLayout(new GridBagLayout());
                ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 245, 0, 0, 0, 0};
                ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 189, 177, 27, 0};
                ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0, 1.0, 1.0, 1.0E-4};
                ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0, 0.0, 1.0E-4};

                //---- button2 ----
                button2.setText("Cadastrar Usu\u00e1rio");
                button2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cadastroUsuario(e);
                    }
                });
                panel2.add(button2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //======== scrollPane1 ========
                {

                    //---- tableChamados ----
                    tableChamados.setModel(new DefaultTableModel(
                        new Object[][] {
                        },
                        new String[] {
                            "C\u00f3digo", "Data de Abertura", "T\u00edtulo"
                        }
                    ) {
                        boolean[] columnEditable = new boolean[] {
                            false, false, false
                        };
                        @Override
                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                            return columnEditable[columnIndex];
                        }
                    });
                    tableChamados.setEnabled(false);
                    tableChamados.setCellSelectionEnabled(true);
                    tableChamados.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            editarDeletarChamado(e);
                        }
                    });
                    scrollPane1.setViewportView(tableChamados);
                }
                panel2.add(scrollPane1, new GridBagConstraints(0, 1, 3, 2, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //======== desktopPane1 ========
                {

                    //======== internalFrame1 ========
                    {
                        internalFrame1.setVisible(true);
                        internalFrame1.setIconifiable(true);
                        internalFrame1.setMaximizable(true);
                        internalFrame1.setClosable(true);
                        internalFrame1.setResizable(true);
                        internalFrame1.setTitle("Chamados abertos por mim");
                        var internalFrame1ContentPane = internalFrame1.getContentPane();
                        internalFrame1ContentPane.setLayout(new GridBagLayout());
                        ((GridBagLayout)internalFrame1ContentPane.getLayout()).columnWidths = new int[] {0, 0, 0};
                        ((GridBagLayout)internalFrame1ContentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                        ((GridBagLayout)internalFrame1ContentPane.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
                        ((GridBagLayout)internalFrame1ContentPane.getLayout()).rowWeights = new double[] {1.0, 1.0, 1.0, 1.0E-4};

                        //======== scrollPane2 ========
                        {

                            //---- tableChamadosAbertos ----
                            tableChamadosAbertos.setModel(new DefaultTableModel(
                                new Object[][] {
                                },
                                new String[] {
                                    "C\u00f3digo", "Data de Abertura", "T\u00edtulo"
                                }
                            ) {
                                boolean[] columnEditable = new boolean[] {
                                    false, true, true
                                };
                                @Override
                                public boolean isCellEditable(int rowIndex, int columnIndex) {
                                    return columnEditable[columnIndex];
                                }
                            });
                            tableChamadosAbertos.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    editarDeletarMeusChamadosAbertos(e);
                                }
                            });
                            scrollPane2.setViewportView(tableChamadosAbertos);
                        }
                        internalFrame1ContentPane.add(scrollPane2, new GridBagConstraints(0, 0, 2, 3, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));
                    }
                    desktopPane1.add(internalFrame1, JLayeredPane.DEFAULT_LAYER);
                    internalFrame1.setBounds(0, 0, 250, 160);
                }
                panel2.add(desktopPane1, new GridBagConstraints(3, 1, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //======== desktopPane2 ========
                {

                    //======== internalFrame2 ========
                    {
                        internalFrame2.setVisible(true);
                        internalFrame2.setIconifiable(true);
                        internalFrame2.setMaximizable(true);
                        internalFrame2.setResizable(true);
                        internalFrame2.setClosable(true);
                        internalFrame2.setTitle("Meus Atendimentos");
                        var internalFrame2ContentPane = internalFrame2.getContentPane();
                        internalFrame2ContentPane.setLayout(new GridBagLayout());
                        ((GridBagLayout)internalFrame2ContentPane.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
                        ((GridBagLayout)internalFrame2ContentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
                        ((GridBagLayout)internalFrame2ContentPane.getLayout()).columnWeights = new double[] {1.0, 1.0, 0.0, 1.0E-4};
                        ((GridBagLayout)internalFrame2ContentPane.getLayout()).rowWeights = new double[] {1.0, 1.0, 1.0, 0.0, 1.0E-4};

                        //======== scrollPane3 ========
                        {

                            //---- tableChamadosAtendidos ----
                            tableChamadosAtendidos.setModel(new DefaultTableModel(
                                new Object[][] {
                                },
                                new String[] {
                                    "C\u00f3digo", "Data de Abertura", "T\u00edtulo"
                                }
                            ) {
                                boolean[] columnEditable = new boolean[] {
                                    false, true, true
                                };
                                @Override
                                public boolean isCellEditable(int rowIndex, int columnIndex) {
                                    return columnEditable[columnIndex];
                                }
                            });
                            tableChamadosAtendidos.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    editarDeletarChamadosAtendidos(e);
                                }
                            });
                            scrollPane3.setViewportView(tableChamadosAtendidos);
                        }
                        internalFrame2ContentPane.add(scrollPane3, new GridBagConstraints(0, 0, 3, 4, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));
                    }
                    desktopPane2.add(internalFrame2, JLayeredPane.DEFAULT_LAYER);
                    internalFrame2.setBounds(0, 0, 250, 145);
                }
                panel2.add(desktopPane2, new GridBagConstraints(3, 2, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- buttonAlterarSenha ----
                buttonAlterarSenha.setText("Alterar Senha");
                buttonAlterarSenha.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        alterarSenha(e);
                    }
                });
                panel2.add(buttonAlterarSenha, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- checkBoxTodosChamados ----
                checkBoxTodosChamados.setText("Todos os Chamados");
                checkBoxTodosChamados.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        tabelaTodosChamados(e);
                    }
                });
                panel2.add(checkBoxTodosChamados, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- novoChamadoButton ----
                novoChamadoButton.setText("Novo Chamado");
                novoChamadoButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        novoChamadoButtonMouseClicked(e);
                    }
                });
                panel2.add(novoChamadoButton, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- button1 ----
                button1.setText("Gerar Relat\u00f3rio");
                button1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        gerarRelatorio(e);
                    }
                });
                panel2.add(button1, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            content.add(panel2, BorderLayout.CENTER);
        }
        contentPane.add(content);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel content;
    private JPanel panel2;
    private JButton button2;
    private JScrollPane scrollPane1;
    private JTable tableChamados;
    private JDesktopPane desktopPane1;
    private JInternalFrame internalFrame1;
    private JScrollPane scrollPane2;
    private JTable tableChamadosAbertos;
    private JDesktopPane desktopPane2;
    private JInternalFrame internalFrame2;
    private JScrollPane scrollPane3;
    private JTable tableChamadosAtendidos;
    private JButton buttonAlterarSenha;
    private JCheckBox checkBoxTodosChamados;
    private JButton novoChamadoButton;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
