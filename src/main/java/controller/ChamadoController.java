package controller;

import model.dao.ChamadoDao;
import model.dto.ChamadoDto;
import model.dto.ChamadoEditarDto;
import model.dto.ChamadoTabelaDto;

import java.sql.SQLException;
import java.util.List;

public class ChamadoController {



    public boolean criarNovoChamado (Long idUsuario, String titulo, String descricao) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.criarNovoChamado(idUsuario, titulo, descricao);
    }

    public List<ChamadoTabelaDto> carregarChamadosUsuarios() throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.carregarChamadosUsuarios();
    }

    public List<ChamadoTabelaDto> carregarChamados() throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.carregarChamados();
    }

    public boolean excluirChamadoPorId(Long idChamado) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.excluirChamadoPorId(idChamado);
    }

    public ChamadoEditarDto procurarChamadoPorId(Long idChamado) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.procurarChamadoPorId(idChamado);
    }

    public boolean atualizarChamado(Long idUsuario, String titulo, String descricao) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.atualizarChamado(idUsuario, titulo, descricao);
    }

    public ChamadoDto carregarChamadoPorId(Long idChamado) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.carregarChamadoPorId(idChamado);
    }

    public boolean receberChamado(Long idChamado, Long idUsuario)  throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.receberChamado(idChamado, idUsuario);
    }

    public boolean resolverChamado(Long idChamado, String solucao) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.resolverChamado(idChamado, solucao);
    }

    public List<ChamadoTabelaDto> carregarMeusChamadosAbertos(Long idUsuario) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.carregarMeusChamadosAbertos(idUsuario);
    }

    public List<ChamadoTabelaDto> carregarMeusChamadosAtendidos(Long idUsuario) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.carregarMeusChamadosAtendidos(idUsuario);
    }

    public int buscarTotalChamadosFechados () throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.numeroChamadosFechados();
    }
    public int buscarTotalChamadosEmAtendimento () throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.numeroChamadosAtendimento();
    }
    public int buscarTotalChamadosAbertos () throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.numeroChamados();
    }

    public int buscarTotalChamadosFechadosHoje () throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.numeroChamadosFechadosHoje();
    }

    public int buscarTotalMeusChamadosAbertos (Long idUsuario) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.numeroMeusChamadosAbertos(idUsuario);
    }

    public int buscarTotalMeusChamadosEmAtendimento(Long idUsuario) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.numeroMeusChamadosEmAtendimento(idUsuario);
    }

    public int buscarTotalChamadosFechadosPorMim(Long idUsuario) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.numeroChamadosFechadosPorMim(idUsuario);
    }
}
