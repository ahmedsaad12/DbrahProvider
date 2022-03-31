package com.apps.dbrah_Provider.uis.activity_chat;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.adapter.ChatAdapter;
import com.apps.dbrah_Provider.databinding.ActivityChatBinding;
import com.apps.dbrah_Provider.share.Common;
import com.apps.dbrah_Provider.uis.activity_base.BaseActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class ChatActivity extends BaseActivity {
    private ActivityChatBinding binding;
//    private ActivityChatMvvm mvvm;
    private String imagePath = "";
    private ChatAdapter adapter;
    private ActivityResultLauncher<Intent> launcher;
    private final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int READ_REQ = 1, CAMERA_REQ = 2;
    private String type;
    private int selectedReq = 0;
    private Uri uri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        initView();
    }

    private void initView() {
        setUpToolbar(binding.toolbar, getString(R.string.chat), R.color.white, R.color.black);
//        mvvm = ViewModelProviders.of(this).get(ActivityChatMvvm.class);
//        mvvm.getIsLoading().observe(this, isLoading -> {
//            binding.swipeRefresh.setRefreshing(isLoading);
//        });



        binding.flGallery.setOnClickListener(view -> {
            closeSheet();
            checkReadPermission();
        });

        binding.flCamera.setOnClickListener(view -> {
            closeSheet();
            checkCameraPermission();
        });

        binding.btnCancel.setOnClickListener(view -> closeSheet());
        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.setLang(getLang());
        binding.edtMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    binding.send.setVisibility(View.GONE);
                } else {
                    binding.send.setVisibility(View.VISIBLE);

                }
            }
        });
//        adapter = new ChatAdapter(this, getUserModel().getData().getUser().getId(), binding.recView);
        binding.recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recView.setAdapter(adapter);

//        mvvm.onDataSuccess().observe(this, list -> {
//            if (adapter != null) {
//                adapter.updateList(list);
//            }
//        });


        binding.imageCamera.setOnClickListener(v -> {
            openSheet();
        });

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (selectedReq == READ_REQ) {
                uri = result.getData().getData();
                File file = new File(Common.getImagePath(this, uri));
                sendMessage("image", "", file.getAbsolutePath());
                Log.e("lll",uri.toString());

            } else if (selectedReq == CAMERA_REQ) {
                Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                uri = getUriFromBitmap(bitmap);
                if (uri != null) {
                    String path = Common.getImagePath(this, uri);
                    sendMessage("image", "", path);
                }
            }

        });



//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }

//        mvvm.getChatMessages(getUserModel());

        binding.send.setOnClickListener(v -> {
            sendMessage("text", binding.edtMessage.getText().toString(), null);
            binding.edtMessage.setText(null);

        });

        binding.cardLastMsg.setOnClickListener(v -> {
            binding.setMsg("");
            binding.cardLastMsg.setVisibility(View.GONE);
//            binding.recView.scrollToPosition(mvvm.onDataSuccess().getValue().size() - 1);
            adapter.notifyDataSetChanged();
            //binding.recView.post(() -> );
        });

//        binding.swipeRefresh.setOnRefreshListener(() -> mvvm.getChatMessages(getUserModel()));


    }
    private Uri getUriFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "", ""));
    }

    private void sendMessage(String type, String msg, String image_url) {
//        AddChatMessageModel addChatMessageModel = new AddChatMessageModel(type, msg, image_url,getUserModel().getData().getAccess_token());
//        Intent intent = new Intent(this, ChatService.class);
//        intent.putExtra("data", addChatMessageModel);
//        startService(intent);

    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onNewMessage(MessageModel messageModel) {
//        imagePath = "";
//        mvvm.addNewMessage(messageModel);
//        adapter.notifyItemInserted(mvvm.onDataSuccess().getValue().size() - 1);
//
//    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_REQ) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SelectImage(requestCode);
            } else {
                Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == CAMERA_REQ) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                SelectImage(requestCode);
            } else {
                Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
//    }

//    public void displayLastMessage(MessageModel messageModel) {
//        if (messageModel.getType().equals("image")) {
//            // binding.setMsg(getString(R.string.attach_sent));
//        } else {
//            binding.setMsg(messageModel.getText());
//        }
//        binding.cardLastMsg.setVisibility(View.VISIBLE);
//
//
//    }

    public void hideLastMessageView() {
        binding.setMsg("");
        binding.cardLastMsg.setVisibility(View.GONE);
    }

    public void openSheet() {
        binding.expandLayout.setExpanded(true, true);
    }

    public void closeSheet() {
        binding.expandLayout.collapse(true);

    }

    public void checkReadPermission() {
        closeSheet();
        if (ActivityCompat.checkSelfPermission(this, READ_PERM) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{READ_PERM}, READ_REQ);
        } else {
            SelectImage(READ_REQ);
        }
    }

    public void checkCameraPermission() {

        closeSheet();

        if (ContextCompat.checkSelfPermission(this, write_permission) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, camera_permission) == PackageManager.PERMISSION_GRANTED
        ) {
            SelectImage(CAMERA_REQ);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{camera_permission, write_permission}, CAMERA_REQ);
        }
    }

    private void SelectImage(int req) {
        selectedReq = req;
        Intent intent = new Intent();

        if (req == READ_REQ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            } else {
                intent.setAction(Intent.ACTION_GET_CONTENT);

            }

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/*");

            launcher.launch(intent);

        } else if (req == CAMERA_REQ) {
            try {
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                launcher.launch(intent);
            } catch (SecurityException e) {
                Toast.makeText(this, R.string.perm_image_denied, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, R.string.perm_image_denied, Toast.LENGTH_SHORT).show();

            }


        }
    }


}