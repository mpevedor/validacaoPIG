package model.dao;

import model.dto.UsuarioEditarDto;

import java.sql.*;

public class UsuarioDao {

    public Long  logar(String login, String senha) throws SQLException {
        Long idUsuario = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_usuario FROM usuario WHERE login LIKE ? AND senha LIKE ?");
            pstmt.setString(1, login);
            pstmt.setString(2, senha);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                idUsuario = rs.getLong("id_usuario");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return idUsuario;
    }

    public boolean cadastrarUsuario(String codigo, String nome, String email, String login, String hashSenha) throws SQLException {
        boolean cadastrou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("INSERT INTO usuario (codigo, nome, email, login, senha)" + "VALUES (?, ?, ?, ?, ? )");
            pstmt.setString(1,codigo);
            pstmt.setString(2, nome);
            pstmt.setString(3, email);
            pstmt.setString(4, login);
            pstmt.setString(5, hashSenha);
            pstmt.executeUpdate();
            cadastrou = true;

        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return cadastrou;
    }

    public UsuarioEditarDto carregaUsuarioPorId(Long idUsuarioEditar) throws SQLException {
        UsuarioEditarDto usuarioEditarDto = new UsuarioEditarDto();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_usuario, codigo, nome, email, login FROM usuario WHERE id_usuario = ?");
            pstmt.setLong(1, idUsuarioEditar);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long idUsuario = rs.getLong("id_usuario");
                String codigo = rs.getString("codigo");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String login = rs.getString("login");
                usuarioEditarDto.setIdUsuario(idUsuario);
                usuarioEditarDto.setCodUsuario(codigo);
                usuarioEditarDto.setNome(nome);
                usuarioEditarDto.setEmail(email);
                usuarioEditarDto.setLogin(login);
                usuarioEditarDto.setSenha("");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return usuarioEditarDto;
    }

    public boolean alterarSenha(Long idUsuarioEditar, String hashSenha) throws SQLException {
        boolean atualizou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/validacaoDesktop", "root", "admin");
            pstmt = conn.prepareStatement("UPDATE usuario SET senha = ? WHERE id_usuario = ?");
            pstmt.setString(1,hashSenha);
            pstmt.setLong(2, idUsuarioEditar);
            pstmt.executeUpdate();
            atualizou = true;

        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return atualizou;
    }
}
