package com.lv.greendao3;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lv.greendao3.base.SwipeActivity;
import com.lv.greendao3.model.User;

import cn.carbs.android.avatarimageview.library.AvatarImageView;

public class ContactActivity extends SwipeActivity {

    private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar toolbar;
    private AvatarImageView avatarImageUser;

    private User user;
    private int colorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initData();
        initUI();
    }

    private void initData() {
        Bundle bundle = this.getIntent().getExtras();
        user = (User) bundle.getSerializable("user");
        colorId = bundle.getInt("colorId", 0);
    }

    private void initUI() {
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        avatarImageUser = (AvatarImageView) findViewById(R.id.avatar_image_user);

        String firstChar = String.valueOf(user.getName().charAt(0));
        avatarImageUser.setTextAndColor(firstChar, AvatarImageView.COLORS[colorId]);
        collapsingToolbar.setTitle(user.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
