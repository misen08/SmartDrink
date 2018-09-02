package xyris.smartdrink;
import android.graphics.drawable.Drawable;

public class CategoryListBebidasProgramadas {

    private String categoryId;
    private String nombreBebidaProgramada;
    private String fechaHora;
    private String hielo;
    private String agitado;
    private String idPedido;
    private Drawable buttonEdit;
    private Drawable buttonDelete;

    public CategoryListBebidasProgramadas() {
        super();
    }

    public CategoryListBebidasProgramadas(String categoryId, String nombreBebidaProgramada, String fechaHora,
                                          String hielo, String agitado, String idPedido,
                                          Drawable buttonEdit, Drawable buttonDelete) {
        super();
        this.categoryId = categoryId;
        this.nombreBebidaProgramada = nombreBebidaProgramada;
        this.fechaHora = fechaHora;
        this.hielo = hielo;
        this.agitado = agitado;
        this.buttonEdit = buttonEdit;
        this.buttonDelete = buttonDelete;
        this.idPedido = idPedido;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getNombreBebidaProgramada() {
        return nombreBebidaProgramada;
    }

    public void setNombreBebidaProgramada(String nombreBebidaProgramada) {
        this.nombreBebidaProgramada = nombreBebidaProgramada;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getHielo() {
        return hielo;
    }

    public void setHielo(String hielo) {
        this.hielo = hielo;
    }

    public String getAgitado() {
        return agitado;
    }

    public void setAgitado(String agitado) {
        this.agitado = agitado;
    }

    public Drawable getButtonEdit() {
        return buttonEdit;
    }

    public void setButtonEdit(Drawable buttonEdit) {
        this.buttonEdit = buttonEdit;
    }

    public Drawable getButtonDelete() {
        return buttonDelete;
    }

    public void setButtonDelete(Drawable buttonDelete) {
        this.buttonDelete = buttonDelete;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }
}