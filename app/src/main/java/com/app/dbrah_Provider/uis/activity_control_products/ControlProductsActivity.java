package com.app.dbrah_Provider.uis.activity_control_products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.adapter.FilterProductAdapter;
import com.app.dbrah_Provider.adapter.MainProductCategoryAdapter;
import com.app.dbrah_Provider.adapter.SubProductCategoryAdapter;
import com.app.dbrah_Provider.databinding.ActivityControlProductsBinding;
import com.app.dbrah_Provider.model.CategoryModel;
import com.app.dbrah_Provider.model.EditProductModel;
import com.app.dbrah_Provider.model.ProductModel;
import com.app.dbrah_Provider.mvvm.ActivityControlProductsMvvm;
import com.app.dbrah_Provider.uis.activity_base.BaseActivity;
import com.app.dbrah_Provider.uis.activity_contact_us.ContactUsActivity;

import java.util.ArrayList;
import java.util.List;

public class ControlProductsActivity extends BaseActivity {
    private ActivityControlProductsBinding binding;
    private MainProductCategoryAdapter mainProductCategoryAdapter;
    private SubProductCategoryAdapter subProductCategoryAdapter;
    private FilterProductAdapter filterProductAdapter;
    private ActivityControlProductsMvvm mvvm;
    private List<String> products;
    private EditProductModel editProductModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_control_products);
        initView();
    }

    private void initView() {
        products=new ArrayList<>();
        editProductModel=new EditProductModel();
        setUpToolbar(binding.toolbar, getString(R.string.control_products), R.color.white, R.color.black);
        binding.toolbar.llBack.setOnClickListener(view -> finish());
        mvvm = ViewModelProviders.of(this).get(ActivityControlProductsMvvm.class);
        mvvm.getIsLoading().observe(this, isLoading -> {
                binding.swipeRefresh.setRefreshing(isLoading);
        });
        mvvm.getOnCategoryDataSuccess().observe(this, modelList -> {
            if (mainProductCategoryAdapter!=null){
                mainProductCategoryAdapter.updateList(modelList);
            }
        });
        mvvm.getCategoryPos().observe(this, pos -> {
            if (mainProductCategoryAdapter!=null && mvvm.getOnCategoryDataSuccess().getValue()!=null){
                List<CategoryModel> list=new ArrayList<>();
                for (CategoryModel model:mvvm.getOnCategoryDataSuccess().getValue()){
                    model.setSelected(false);
                    list.add(model);
                }
                CategoryModel model=list.get(pos);
                model.setSelected(true);
                list.set(0,model);
                mainProductCategoryAdapter=new MainProductCategoryAdapter(this,getLang());
                binding.recViewMain.setAdapter(mainProductCategoryAdapter);
                mainProductCategoryAdapter.setSelectedPos(pos);
                mainProductCategoryAdapter.updateList(list);
                mvvm.setCategoryId(mvvm.getOnCategoryDataSuccess().getValue().get(pos).getId(),getUserModel());

            }
        });
        mvvm.getCategory();
        mvvm.getOnSubCategoryDataSuccess().observe(this, modelList -> {
            if (subProductCategoryAdapter!=null){
                List<CategoryModel> subCategoryList=new ArrayList<>();
                for (CategoryModel model:modelList){
                    model.setSelected(false);
                    subCategoryList.add(model);
                }
                if (subCategoryList.size()>0){
                    CategoryModel model=subCategoryList.get(0);
                    model.setSelected(true);
                    subCategoryList.set(0,model);
                }
                subProductCategoryAdapter = new SubProductCategoryAdapter(ControlProductsActivity.this, getLang());
                binding.recViewSub.setAdapter(subProductCategoryAdapter);
                binding.recViewSub.setLayoutManager(new LinearLayoutManager(ControlProductsActivity.this, RecyclerView.HORIZONTAL, false));
                subProductCategoryAdapter.setSelectedPos(0);
                subProductCategoryAdapter.updateList(subCategoryList);
            }
        });

        mvvm.getOnProductsDataSuccess().observe(this, productModels -> {
            if (productModels.size()>0){
                binding.tvNoData.setVisibility(View.GONE);
            }else {
                binding.tvNoData.setVisibility(View.VISIBLE);
            }
            if (filterProductAdapter!=null){
                filterProductAdapter.updateList(productModels);
            }

        });
        binding.swipeRefresh.setOnRefreshListener(() -> {
            if (mvvm.getSubCategoryId().getValue()!=null){
                mvvm.controlProducts(getUserModel());
            }else {
                binding.swipeRefresh.setRefreshing(false);
            }


        });
        mainProductCategoryAdapter = new MainProductCategoryAdapter( this, getLang());
        binding.recViewMain.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.recViewMain.setAdapter(mainProductCategoryAdapter);

        subProductCategoryAdapter = new SubProductCategoryAdapter(this, getLang());
        binding.recViewSub.setAdapter(subProductCategoryAdapter);
        binding.recViewSub.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        filterProductAdapter = new FilterProductAdapter( this);
        binding.recViewProducts.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewProducts.setAdapter(filterProductAdapter);

        binding.btnSave.setOnClickListener(view -> {
            if (filterProductAdapter!=null){
                if (!products.isEmpty()){
                    editProductModel.setProducts_id(products);
                    editProductModel.setProvider_id(getUserModel().getData().getId());
                    mvvm.editProducts(this,editProductModel);
                }else {
                    Toast.makeText(this, R.string.select_product, Toast.LENGTH_SHORT).show();
//                    Toast toast=Toast.makeText(this, R.string.select_product, Toast.LENGTH_SHORT);
//                    View view1=toast.getView();
//                    TextView text = (TextView)view1.findViewById(android.R.id.message);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        text.setTextAppearance(R.style.tab_text);
//                    }
                }

            }
        });
        mvvm.save.observe(this, aBoolean -> {
            if (aBoolean) {
                Toast.makeText(ControlProductsActivity.this, getResources().getString(R.string.updated_suc), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

//    public void setItemCategory(CategoryModel categoryModel, int currentPos) {
//        mvvm.setCategoryPos(currentPos);
//    }
    public void setItemCategory(CategoryModel categoryModel) {
        mvvm.setCategoryId(categoryModel.getId(),getUserModel());
    }

    public void showProducts(CategoryModel categoryModel) {
        mvvm.getSubCategoryId().setValue(categoryModel.getId());
        mvvm.controlProducts(getUserModel());
    }

    public void addProductId(ProductModel productModel) {
        if (!products.contains(productModel.getId())){
            products.add(productModel.getId());
        }
    }
}