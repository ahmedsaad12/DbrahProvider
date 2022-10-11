package com.app.dbrah_Provider.uis.activity_reviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.adapter.OfferAdapter;
import com.app.dbrah_Provider.adapter.ReviewAdapter;
import com.app.dbrah_Provider.databinding.ActivityReviewsBinding;
import com.app.dbrah_Provider.model.ReviewModel;
import com.app.dbrah_Provider.mvvm.ActivityReviewsMvvm;
import com.app.dbrah_Provider.uis.activity_base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ReviewsActivity extends BaseActivity {
    private ActivityReviewsBinding binding;
    private ReviewAdapter reviewAdapter;
    private ActivityReviewsMvvm activityReviewsMvvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reviews);
        initView();
    }

    private void initView() {
        activityReviewsMvvm = ViewModelProviders.of(this).get(ActivityReviewsMvvm.class);
        setUpToolbar(binding.toolbar, getString(R.string.reviews), R.color.white, R.color.black);
        reviewAdapter = new ReviewAdapter(this);
        binding.recViewReviews.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewReviews.setAdapter(reviewAdapter);
        activityReviewsMvvm.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.nested.setVisibility(View.GONE);
                    binding.progBar.setVisibility(View.VISIBLE);
                } else {
                    binding.progBar.setVisibility(View.GONE);
                    binding.nested.setVisibility(View.VISIBLE);
                }
            }
        });
        activityReviewsMvvm.getListMutableLiveData().observe(this, new Observer<List<ReviewModel>>() {
            @Override
            public void onChanged(List<ReviewModel> reviewModels) {
                if (reviewModels != null && reviewModels.size() > 0) {
                    reviewAdapter.updateList(reviewModels);
                    binding.tvNoData.setVisibility(View.GONE);
                    updateData(reviewModels);
                } else {
                    reviewAdapter.updateList(new ArrayList<>());
                    binding.tvNoData.setVisibility(View.VISIBLE);
                    binding.setRate(0);
                    binding.setSize(0);
                }
            }
        });


        activityReviewsMvvm.getReviews(getUserModel());
    }

    private void updateData(List<ReviewModel> reviewModels) {
        float rate = 0;
        for (int i = 0; i < reviewModels.size(); i++) {
            rate += Float.parseFloat(reviewModels.get(i).getRate());
        }
        binding.setRate(rate/reviewModels.size());
        binding.setSize(reviewModels.size());
    }
}