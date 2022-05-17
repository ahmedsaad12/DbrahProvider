package com.apps.dbrah_Provider.general_ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;


import com.apps.dbrah_Provider.R;

import com.apps.dbrah_Provider.model.NotificationModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class GeneralMethod {

    @BindingAdapter("error")
    public static void errorValidation(View view, String error) {
        if (view instanceof EditText) {
            EditText ed = (EditText) view;
            ed.setError(error);
        } else if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setError(error);


        }
    }

    @BindingAdapter("image")
    public static void image(View view, String imageUrl) {

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);


                if (view instanceof CircleImageView) {
                    CircleImageView imageView = (CircleImageView) view;
                    if (imageUrl != null) {
                        RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                        Glide.with(view.getContext()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(imageUrl)
                                .centerCrop()
                                .apply(options)
                                .into(imageView);
                    }
                } else if (view instanceof RoundedImageView) {
                    RoundedImageView imageView = (RoundedImageView) view;

                    if (imageUrl != null) {

                        RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                        Glide.with(view.getContext()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(imageUrl)
                                .centerCrop()
                                .apply(options)
                                .into(imageView);

                    }
                } else if (view instanceof ImageView) {
                    ImageView imageView = (ImageView) view;

                    if (imageUrl != null) {

                        RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                        Glide.with(view.getContext()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(imageUrl)
                                .centerCrop()
                                .apply(options)
                                .into(imageView);
                    }
                }

            }
        });


    }

    @BindingAdapter("user_image")
    public static void user_image(View view, String imageUrl) {


        if (view instanceof CircleImageView) {
            CircleImageView imageView = (CircleImageView) view;
            if (imageUrl != null) {

                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.circle_avatar)
                        .load(imageUrl)
                        .centerCrop()
                        .into(imageView);

            }
        } else if (view instanceof RoundedImageView) {
            RoundedImageView imageView = (RoundedImageView) view;

            if (imageUrl != null) {

                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.circle_avatar)
                        .load(imageUrl)
                        .centerCrop()
                        .into(imageView);

            }
        } else if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;

            if (imageUrl != null) {

                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.circle_avatar)
                        .load(imageUrl)
                        .centerCrop()
                        .into(imageView);
            }
        }

    }

    @BindingAdapter("type")
    public static void OfferType(TextView btnStatus, String type) {

        if (type.equals("other")) {
            btnStatus.setText(R.string.alternative_product);
        } else if (type.equals("less")) {
            btnStatus.setText(R.string.less_quantitiy);
        } else if (type.equals("not_found")) {
            btnStatus.setText(R.string.not_available);
        }

    }

    @BindingAdapter({"offerDate"})
    public static void displayOfferDate(TextView textView, String offerDate) {
        if (offerDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            String m_date = dateFormat.format(new Date(Long.parseLong(offerDate)));
            textView.setText(m_date);
        }
    }

    @BindingAdapter({"offertime"})
    public static void displayOffertime(TextView textView, String offerDate) {
        if (offerDate != null) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
            String m_date = dateFormat.format(new Date(Long.parseLong(offerDate)));
            textView.setText(m_date);
        }
    }

    @BindingAdapter("type")
    public static void OfferType(ImageView imDot, String type) {

        if (type.equals("other")) {
            imDot.setColorFilter(ContextCompat.getColor(imDot.getContext(), R.color.color5), PorterDuff.Mode.SRC_IN);
        } else if (type.equals("less")) {
            imDot.setColorFilter(ContextCompat.getColor(imDot.getContext(), R.color.color6), PorterDuff.Mode.SRC_IN);
        } else if (type.equals("not_found")) {
            imDot.setColorFilter(ContextCompat.getColor(imDot.getContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        }

    }

    @BindingAdapter("orderStatus")
    public static void orderStatus(TextView btnStatus, String offer_code) {
        if (offer_code != null) {
            if (offer_code.equals("202")) {
                btnStatus.setText(R.string.start_preparing);
            } else if (offer_code.equals("203")) {
                btnStatus.setText(R.string.start_delivring);
            } else if (offer_code.equals("204")) {
                btnStatus.setText(R.string.finish_delivery);
            } else if (offer_code.equals("206")) {
                btnStatus.setText(R.string.done);
            }
        }
    }

    @BindingAdapter("orderStatus")
    public static void orderStatus(ProgressBar progressBar, String offer_code) {
        if (offer_code != null) {
            if (offer_code.equals("202")) {
                progressBar.setProgress(10);
            } else if (offer_code.equals("203")) {
                progressBar.setProgress(30);
            } else if (offer_code.equals("204")) {
                progressBar.setProgress(70);
            } else if (offer_code.equals("206")) {
                progressBar.setProgress(100);
            }
        }
    }

    @BindingAdapter("createTime")
    public static void createAtTime(TextView textView, String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        if (date != null) {

            try {
                Date parse = dateFormat.parse(date);

                SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                format.setTimeZone(TimeZone.getDefault());
                String time = format.format(parse);
                textView.setText(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {

        }

    }

    @BindingAdapter("createAt")
    public static void createAt(TextView textView, String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        if (date != null) {

            try {
                Date parse = dateFormat.parse(date);

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.ENGLISH);
                format.setTimeZone(TimeZone.getDefault());
                String time = format.format(parse);
                textView.setText(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {

        }

    }

    @BindingAdapter("notification")
    public static void notification(TextView textView, NotificationModel model) {
        if (model != null) {
            Context context = textView.getContext();
            String text = "";
            if (model.getOrder_id() != null && !model.getOrder_id().isEmpty()) {
                if (model.getStatus().equals("new")) {
                    text = context.getString(R.string.new_order) + "-" + model.getUser().getName() + "\n" + context.getString(R.string.order_num) + " #" + model.getOrder_id();
                } else if (model.getStatus().equals("accepted")) {

                    text = context.getString(R.string.your_offer_has_been_accepted) + " " + model.getUser().getName() + "\n" + context.getString(R.string.order_num) + " #" + model.getOrder_id();

                } else if (model.getStatus().equals("rejected")) {
                    text = context.getString(R.string.your_offer_has_been_rejected) + " " + model.getUser().getName() + "\n" + context.getString(R.string.order_num) + " #" + model.getOrder_id();

                } else {
                    if (model.getStatus().equals("preparing")) {

                        if (model.getBody().equals("order is accepted by a representative")) {
                            text = context.getString(R.string.your_order_accepted) + " " + model.getRepresentative().getName() + "\n" + context.getString(R.string.order_num) + " #" + model.getOrder_id();
                        } else if (model.getBody().equals("order is picked up by the delivery")) {
                            text = context.getString(R.string.picked_up) + " " + model.getRepresentative().getName() + "\n" + context.getString(R.string.order_num) + " #" + model.getOrder_id();
                        }

                    } else if (model.getStatus().equals("on_way")) {
                        text = context.getString(R.string.on_the_way) + " " + model.getRepresentative().getName() + "\n" + context.getString(R.string.order_num) + " #" + model.getOrder_id();

                    } else if (model.getStatus().equals("delivered")) {
                        text = context.getString(R.string.finish_delivery) + " " + model.getRepresentative().getName() + "\n" + context.getString(R.string.order_num) + " #" + model.getOrder_id();

                    } else {
                        text = model.getBody();
                    }
                }

                textView.setText(text);

            } else {
                textView.setText(model.getBody());

            }
        }

    }
    @BindingAdapter("rate")
    public static void rate(SimpleRatingBar bar, String rate) {
        if (rate != null) {
            bar.setRating(Float.parseFloat(rate));
        }

    }

}













