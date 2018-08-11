package xyris.smartdrink;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class CategoryList {

    private String title;
    private String categoryId;
    private String description;
    private Drawable imagen;
    private Drawable buttonInfo;
    private Drawable buttonDelete;

    public CategoryList() {
        super();
    }

    public CategoryList(String categoryId, String title,
                        Drawable buttonInfo, Drawable buttonDelete) {
        super();
        this.title = title;
        this.description = description;
        //this.imagen = imagen;
        //this.categoryId = categoryId;
        this.buttonInfo = buttonInfo;
        this.buttonDelete = buttonDelete;
    }


    public CategoryList(String categoryId, String title, String description, Drawable imagen,
                        Drawable buttonInfo, Drawable buttonDelete) {
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Drawable getImagen() {
        return imagen;
    }

    public void setImagen(Drawable imagen) {
        this.imagen = imagen;
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

    //    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public Drawable getImage() {
//        return imagen;
//    }
//
//    public void setImagen(Drawable imagen) {
//        this.imagen = imagen;
//    }
//
//    public String getCategoryId(){return categoryId;}
//
//    public void setCategoryId(String categoryId) {
//        this.categoryId = categoryId;
//    }
//
//    public Drawable getImagen() {
//        return imagen;
//    }
//
//    public Drawable getButtonInfo() {
//        return buttonInfo;
//    }
//
//    public void setButtonInfo(Drawable buttonInfo) {
//        this.buttonInfo = buttonInfo;
//    }
//
//    public ImageButton getButtonDelete() {
//        return buttonDelete;
//    }
//
//    public void setButtonDelete(ImageButton buttonDelete) {
//        this.buttonDelete = buttonDelete;
//    }
}