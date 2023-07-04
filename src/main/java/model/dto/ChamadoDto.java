package model.dto;

public class ChamadoDto {

    private Long idChamado;

    private String codigo;

    private String titulo;

    private String descricao;

    private String usuarioRequisitante;

    private String dataHoraAbertura;

    private String solucao;

    private String usuarioTecnico;

    private String dataHoraFechamento;

    private Long idUsuarioTecnico;

    public String getDataHoraFechamento() {
        return dataHoraFechamento;
    }

    public void setDataHoraFechamento(String dataHoraFechamento) {
        this.dataHoraFechamento = dataHoraFechamento;
    }

    public Long getIdUsuarioTecnico() {
        return idUsuarioTecnico;
    }

    public void setIdUsuarioTecnico(Long idUsuarioTecnico) {
        this.idUsuarioTecnico = idUsuarioTecnico;
    }

    public Long getIdChamado() {
        return idChamado;
    }

    public void setIdChamado(Long idChamado) {
        this.idChamado = idChamado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUsuarioRequisitante() {
        return usuarioRequisitante;
    }

    public void setUsuarioRequisitante(String usuarioRequisitante) {
        this.usuarioRequisitante = usuarioRequisitante;
    }

    public String getDataHoraAbertura() {
        return dataHoraAbertura;
    }

    public void setDataHoraAbertura(String dataHoraAbertura) {
        this.dataHoraAbertura = dataHoraAbertura;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public String getUsuarioTecnico() {
        return usuarioTecnico;
    }

    public void setUsuarioTecnico(String usuarioTecnico) {
        this.usuarioTecnico = usuarioTecnico;
    }
}
