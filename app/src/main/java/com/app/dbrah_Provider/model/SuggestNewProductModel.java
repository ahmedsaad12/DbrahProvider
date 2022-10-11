package com.app.dbrah_Provider.model;

import android.content.Context;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.app.dbrah_Provider.BR;
import com.app.dbrah_Provider.R;

public class SuggestNewProductModel extends BaseObservable {
    private String image;
    private String product_title;
    private int main_category_id;
    private String specifications;
    private String selected_category;

    public ObservableField<String> error_title = new ObservableField<>();
    public ObservableField<String> error_specifications = new ObservableField<>();

    public boolean isDataValid(Context context) {
        if (!image.isEmpty()
                && !product_title.isEmpty()
                && !specifications.isEmpty()
                && main_category_id != 0) {
            error_title.set(null);
            error_specifications.set(null);
            return true;
        } else {
            if (main_category_id == 0) {
                Toast.makeText(context, R.string.choose_category, Toast.LENGTH_SHORT).show();
            }
            if (image.isEmpty()) {
                Toast.makeText(context, R.string.add_product_image, Toast.LENGTH_SHORT).show();
            }
            if (product_title.isEmpty()) {
                error_title.set(context.getString(R.string.field_required));
            } else {
                error_title.set(null);
            }
            if (specifications.isEmpty()) {
                error_specifications.set(context.getString(R.string.field_required));
            } else {
                error_specifications.set(null);
            }
            return false;
        }
    }

    public SuggestNewProductModel() {
        this.main_category_id = 0;

        this.image = "";
        notifyPropertyChanged(BR.image);
        this.product_title = "";
        notifyPropertyChanged(BR.product_title);
        this.specifications = "";
        notifyPropertyChanged(BR.specifications);
        this.selected_category="";
        notifyPropertyChanged(BR.selected_category);
    }

    @Bindable
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
    }

    @Bindable
    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
        notifyPropertyChanged(BR.product_title);
    }

    public int getMain_category_id() {
        return main_category_id;
    }

    public void setMain_category_id(int main_category_id) {
        this.main_category_id = main_category_id;
    }

    @Bindable
    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
        notifyPropertyChanged(BR.specifications);
    }

    @Bindable
    public String getSelected_category() {
        return selected_category;
    }

    public void setSelected_category(String selected_category) {
        this.selected_category = selected_category;
        notifyPropertyChanged(BR.selected_category);
    }
}
