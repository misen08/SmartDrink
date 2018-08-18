package xyris.smartdrink;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class CategoryList {

    private String title;
    private String categoryId;
    private Drawable buttonInfo;
    private Drawable buttonDelete;

    public CategoryList() {
        super();
    }

    public CategoryList(String categoryId, String title,
                        Drawable buttonInfo, Drawable buttonDelete) {
        super();
        this.title = title;
        this.buttonInfo = buttonInfo;
        this.buttonDelete = buttonDelete;
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