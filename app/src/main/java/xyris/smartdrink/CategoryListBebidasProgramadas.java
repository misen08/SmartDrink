package xyris.smartdrink;
import android.graphics.drawable.Drawable;

public class CategoryListBebidasProgramadas {

    private String title;
    private String fechaHora;
    private String categoryId;
    private Drawable buttonInfo;
    private Drawable buttonDelete;

    public CategoryListBebidasProgramadas() {
        super();
    }

    public CategoryListBebidasProgramadas(String categoryId, String title, String fechaHora,
                                          Drawable buttonInfo, Drawable buttonDelete) {
        super();
        this.title = title;
        this.fechaHora = fechaHora;
        this.buttonInfo = buttonInfo;
        this.buttonDelete = buttonDelete;
        this.categoryId = categoryId;
        this.buttonInfo = buttonInfo;
        this.buttonDelete = buttonDelete;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Drawable getButtonInfo() {
        return buttonInfo;
    }

    public void setButtonInfo(Drawable buttonInfo) {
        this.buttonInfo = buttonInfo;
    }

    public Drawable getButtonDelete() {
        return buttonDelete;
    }

    public void setButtonDelete(Drawable buttonDelete) {
        this.buttonDelete = buttonDelete;
    }

}