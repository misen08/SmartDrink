package xyris.smartdrink;

import android.graphics.drawable.Drawable;

public class CategoryList {

    private String title;
    private String categoryId;
    private Drawable buttonInfo;
    private Drawable buttonDelete;
    private Drawable disableImage;

    public Drawable getDisableImage() {
        return disableImage;
    }

    public void setDisableImage(Drawable disableImage) {
        this.disableImage = disableImage;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    private String disponible;

    public CategoryList() {
        super();
    }

    public CategoryList(String categoryId, String title, Drawable disableImage,
                        Drawable buttonInfo, Drawable buttonDelete, String disponible) {
        super();
        this.title = title;
        this.buttonInfo = buttonInfo;
        this.buttonDelete = buttonDelete;
        this.categoryId = categoryId;
        this.disableImage = disableImage;
        this.buttonInfo = buttonInfo;
        this.buttonDelete = buttonDelete;
        this.disponible = disponible;
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