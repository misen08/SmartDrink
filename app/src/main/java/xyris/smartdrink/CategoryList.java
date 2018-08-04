package xyris.smartdrink;
import android.graphics.drawable.Drawable;
import android.widget.Button;

public class CategoryList {

    private String title;
    private String categoryId;
    private String description;
    private Drawable imagen;
    private Button buttonInfo;
    private Button buttonDelete;


    public CategoryList() {
        super();
    }

    public CategoryList(String categoryId, String title,
                        Button buttonInfo, Button buttonDelete) {
        super();
        this.title = title;
        this.description = description;
        //this.imagen = imagen;
        //this.categoryId = categoryId;
        this.buttonInfo = buttonInfo;
        this.buttonDelete = buttonDelete;
    }


    public CategoryList(String categoryId, String title, String description, Drawable imagen,
                        Button buttonInfo, Button buttonDelete) {
        super();
        this.title = title;
        this.description = description;
        this.imagen = imagen;
        this.categoryId = categoryId;
        this.buttonInfo = buttonInfo;
        this.buttonDelete = buttonDelete;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Drawable getImage() {
        return imagen;
    }

    public void setImagen(Drawable imagen) {
        this.imagen = imagen;
    }

    public String getCategoryId(){return categoryId;}

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Drawable getImagen() {
        return imagen;
    }

    public Button getButtonInfo() {
        return buttonInfo;
    }

    public void setButtonInfo(Button buttonInfo) {
        this.buttonInfo = buttonInfo;
    }

    public Button getButtonDelete() {
        return buttonDelete;
    }

    public void setButtonDelete(Button buttonDelete) {
        this.buttonDelete = buttonDelete;
    }
}