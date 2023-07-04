package model.dto;

public class ChamadoEditarDto {
    private Long idChamado;
    private String codChamado;
    private String titulo;
    private String descricao;

    public ChamadoEditarDto(Long idChamado, String codChamado, String titulo, String descricao) {
        this.idChamado = idChamado;
        this.codChamado = codChamado;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Long getIdChamado() {
        return idChamado;
    }

    public void setIdChamado(Long idChamado) {
        this.idChamado = idChamado;
    }

    public String getCodChamado() {
        return codChamado;
    }

    public void setCodChamado(String codChamado) {
        this.codChamado = codChamado;
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
}
