package model.dao;

import model.dto.ChamadoDto;
import model.dto.ChamadoEditarDto;
import model.dto.ChamadoTabelaDto;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChamadoDao {



    public boolean criarNovoChamado (Long idUsuario, String titulo, String descricao) throws SQLException {
        boolean cadastrado = false;
        Integer chamados = numeroChamados();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("insert into chamados (codigo, titulo, descricao, data_abertura, usuario_requisitante)" + "values(?, ?, ?, ?, ?)");
            String codigo = "c"+idUsuario.toString()+""+chamados;
            pstmt.setString(1,codigo);
            pstmt.setString(2,titulo);
            pstmt.setString(3,descricao);
            LocalDateTime now = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(now);
            pstmt.setTimestamp(4,timestamp);
            pstmt.setLong(5, idUsuario);
            pstmt.executeUpdate();
            cadastrado = true;

        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return cadastrado;
    }

    public int numeroChamados() throws SQLException {
        int chamados = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado FROM chamados");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                chamados = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return chamados;
        }

    public List<ChamadoTabelaDto> carregarChamadosUsuarios() throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        List<ChamadoTabelaDto> chamadosAbertos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado, codigo, data_abertura, titulo FROM chamados WHERE data_fechamento IS NULL");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long idChamado = rs.getLong(1);
                String codigo = rs.getString(2);
                Timestamp dataAbertura = rs.getTimestamp(3);
                String dataFortmatada = simpleDateFormat.format(dataAbertura);
                String titulo = rs.getString(4);
                ChamadoTabelaDto chamado = new ChamadoTabelaDto(idChamado, codigo, dataFortmatada, titulo);
                chamadosAbertos.add(chamado);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return chamadosAbertos;
    }

    public List<ChamadoTabelaDto> carregarChamados() throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        List<ChamadoTabelaDto> chamados = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado, codigo, data_abertura, titulo FROM chamados ORDER BY data_abertura");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long idChamado = rs.getLong(1);
                String codigo = rs.getString(2);
                Timestamp dataAbertura = rs.getTimestamp(3);
                String dataFortmatada = simpleDateFormat.format(dataAbertura);
                String titulo = rs.getString(4);
                ChamadoTabelaDto chamado = new ChamadoTabelaDto(idChamado, codigo, dataFortmatada, titulo);
                chamados.add(chamado);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return chamados;
    }

    public boolean excluirChamadoPorId(Long idChamado) throws SQLException {
        boolean excluido = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("DELETE FROM chamados WHERE id_chamado = ?");
            pstmt.setLong(1, idChamado);
            pstmt.executeUpdate();
            excluido = true;

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return excluido;

    }

    public ChamadoEditarDto procurarChamadoPorId(Long idChamado) throws SQLException {
        ChamadoEditarDto chamado = null;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado, codigo, titulo, descricao FROM chamados WHERE id_chamado = ?");
            pstmt.setLong(1, idChamado);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong(1);
                String codChamado = rs.getString(2);
                String titulo = rs.getString(3);
                String descricao = rs.getString(4);
                chamado = new ChamadoEditarDto(id, codChamado, titulo, descricao);
            }
            rs.close();
        }catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }finally {
            conn.close();
            pstmt.close();
        }

        return chamado;
    }

    public boolean atualizarChamado(Long idUsuario, String titulo, String descricao) throws SQLException {
        boolean atualizado = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("UPDATE chamados SET titulo = ?, descricao = ? WHERE id_chamado = ?");
            pstmt.setString(1,titulo);
            pstmt.setString(2,descricao);
            pstmt.setLong(3, idUsuario);
            pstmt.executeUpdate();
            atualizado = true;
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return atualizado;
    }

    public ChamadoDto carregarChamadoPorId(Long idChamado) throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        ChamadoDto chamado = new ChamadoDto();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado, chamados.codigo, titulo, descricao, data_abertura, nome, solucao, data_fechamento, usuario_tecnico FROM chamados INNER JOIN usuario ON chamados.usuario_requisitante = usuario.id_usuario WHERE id_chamado = ?");
            pstmt.setLong(1, idChamado);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong(1);
                String codigo = rs.getString(2);
                String titulo = rs.getString(3);
                String descricao = rs.getString(4);
                Timestamp dataAbertura = rs.getTimestamp(5);
                String dataAberturaFormatada = simpleDateFormat.format(dataAbertura);
                String nome = rs.getString(6);
                String solucao = rs.getString(7);
                Timestamp dataFechamento = rs.getTimestamp(8);
                String dataFechamentoFormatada = "";
                if (dataFechamento != null) {
                    dataFechamentoFormatada = simpleDateFormat.format(dataFechamento);
                }
                Long usuarioTecnico = rs.getLong(9);
                chamado.setIdChamado(id);
                chamado.setCodigo(codigo);
                chamado.setTitulo(titulo);
                chamado.setDescricao(descricao);
                chamado.setDataHoraAbertura(dataAberturaFormatada);
                chamado.setUsuarioRequisitante(nome);
                chamado.setSolucao(solucao);
                chamado.setDataHoraFechamento(dataFechamentoFormatada);
                chamado.setIdUsuarioTecnico(usuarioTecnico);

            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return chamado;
    }

    public boolean receberChamado(Long idChamado, Long idUsuario) throws SQLException {
        boolean recebido = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("UPDATE chamados SET usuario_tecnico = ? WHERE id_chamado = ?");
            pstmt.setLong(1, idUsuario);
            pstmt.setLong(2, idChamado);
            pstmt.executeUpdate();
            recebido = true;
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }
        return recebido;

    }

    public boolean resolverChamado(Long idChamado, String solucao) throws SQLException {
        boolean resolvido = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("UPDATE chamados SET solucao = ? , data_fechamento = ? WHERE id_chamado = ?");
            pstmt.setString(1, solucao);
            LocalDateTime now = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(now);
            pstmt.setTimestamp(2,timestamp);
            pstmt.setLong(3, idChamado);
            pstmt.executeUpdate();
            resolvido = true;
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }
        return resolvido;
    }

    public List<ChamadoTabelaDto> carregarMeusChamadosAbertos(Long idUsuario) throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        List<ChamadoTabelaDto> chamados = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado, codigo, data_abertura, titulo FROM chamados WHERE usuario_requisitante = ? ORDER BY data_abertura");
            pstmt.setLong(1,idUsuario);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long idChamado = rs.getLong(1);
                String codigo = rs.getString(2);
                Timestamp dataAbertura = rs.getTimestamp(3);
                String dataFortmatada = simpleDateFormat.format(dataAbertura);
                String titulo = rs.getString(4);
                ChamadoTabelaDto chamado = new ChamadoTabelaDto(idChamado, codigo, dataFortmatada, titulo);
                chamados.add(chamado);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return chamados;
    }

    public List<ChamadoTabelaDto> carregarMeusChamadosAtendidos(Long idUsuario) throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        List<ChamadoTabelaDto> chamados = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado, codigo, data_abertura, titulo FROM chamados WHERE usuario_tecnico = ? ORDER BY data_abertura");
            pstmt.setLong(1,idUsuario);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long idChamado = rs.getLong(1);
                String codigo = rs.getString(2);
                Timestamp dataAbertura = rs.getTimestamp(3);
                String dataFortmatada = simpleDateFormat.format(dataAbertura);
                String titulo = rs.getString(4);
                ChamadoTabelaDto chamado = new ChamadoTabelaDto(idChamado, codigo, dataFortmatada, titulo);
                chamados.add(chamado);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return chamados;
    }

    public int numeroChamadosAtendimento() throws SQLException {
        int chamadosAtendimento = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado FROM chamados WHERE usuario_tecnico IS NOT NULL AND data_fechamento IS NULL ");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                chamadosAtendimento = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return chamadosAtendimento;
    }

    public int numeroChamadosFechados() throws SQLException {
        int chamadosFechados = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado FROM chamados WHERE data_fechamento IS NOT NULL ");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                chamadosFechados = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return chamadosFechados;
    }

    public int numeroChamadosFechadosHoje() throws SQLException {
        int chamadosFechadosHoje = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado FROM chamados WHERE data_fechamento IS NOT NULL AND DATE(data_fechamento) = CURDATE() ");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                chamadosFechadosHoje = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return chamadosFechadosHoje;
    }

    public int numeroMeusChamadosAbertos(Long idUsuario) throws SQLException {
        int meusChamadosAberto = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT COUNT(id_chamado) FROM chamados WHERE usuario_requisitante = ?");
            pstmt.setLong(1,idUsuario);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                meusChamadosAberto = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return meusChamadosAberto;
    }

    public int numeroMeusChamadosEmAtendimento(Long idUsuario) throws SQLException {
        int meusChamadosAtendimento = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT COUNT(id_chamado) FROM chamados WHERE usuario_requisitante = ? AND data_fechamento IS NULL");
            pstmt.setLong(1,idUsuario);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                meusChamadosAtendimento = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return meusChamadosAtendimento;
    }

    public int numeroChamadosFechadosPorMim(Long idUsuario) throws SQLException {
        int meusChamadosFechados = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT COUNT(id_chamado) FROM chamados WHERE usuario_requisitante = ? AND data_fechamento IS NOT NULL");
            pstmt.setLong(1,idUsuario);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                meusChamadosFechados = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return meusChamadosFechados;
    }
}
