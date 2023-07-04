package controller;

import model.dao.UsuarioDao;
import model.dto.UsuarioEditarDto;

import java.sql.SQLException;

public class UsuarioController {

    public Long logar(String login, String senha) throws SQLException {
        UsuarioDao usuarioDao = new UsuarioDao();
        return usuarioDao.logar(login,senha);
    }


    public boolean cadastrarUsuario(String codigo, String nome, String email, String login, String hashSenha) throws SQLException {
        UsuarioDao usuarioDao = new UsuarioDao();
        return usuarioDao.cadastrarUsuario(codigo, nome, email, login, hashSenha);
    }

    public UsuarioEditarDto carregaUsuarioPorId(Long idUsuarioEditar) throws SQLException {
        UsuarioDao usuarioDao = new UsuarioDao();
        return usuarioDao.carregaUsuarioPorId(idUsuarioEditar);
    }

    public boolean alterarSenha(Long idUsuarioEditar, String hashSenha) throws SQLException {
        UsuarioDao usuarioDao = new UsuarioDao();
        return usuarioDao.alterarSenha(idUsuarioEditar, hashSenha);
    }
}
