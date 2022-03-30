package com.apps.dbrah_Provider.uis.activity_reviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.adapter.OfferAdapter;
import com.apps.dbrah_Provider.adapter.ReviewAdapter;
import com.apps.dbrah_Provider.databinding.ActivityReviewsBinding;
import com.apps.dbrah_Provider.uis.activity_base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ReviewsActivity extends BaseActivity {
    private ActivityReviewsBinding binding;
    private ReviewAdapter reviewAdapter;
    private List<Object> reviewList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_reviews);
        initView();
    }

    private void initView() {
        setUpToolbar(binding.toolbar, getString(R.string.reviews), R.color.white, R.color.black);

        reviewList = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(reviewList, this);
        binding.recViewReviews.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewReviews.setAdapter(reviewAdapter);
    }
}