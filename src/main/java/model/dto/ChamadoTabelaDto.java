package model.dto;

import java.util.Objects;

public class ChamadoTabelaDto {

    private Long idChamado;

    private String codigo;

    private String dataHora;

    private String titulo;

    public ChamadoTabelaDto(Long idChamado, String codigo, String dataHora, String titulo) {
        this.idChamado = idChamado;
        this.codigo = codigo;
        this.dataHora = dataHora;
        this.titulo = titulo;
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

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChamadoTabelaDto that = (ChamadoTabelaDto) o;
        return Objects.equals(idChamado, that.idChamado) && Objects.equals(codigo, that.codigo) && Objects.equals(dataHora, that.dataHora) && Objects.equals(titulo, that.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idChamado, codigo, dataHora, titulo);
    }
}

