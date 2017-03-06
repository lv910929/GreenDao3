package com.lv.greendao3;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.lv.greendao3.adapter.ItemLongClickListener;
import com.lv.greendao3.adapter.PhoneAdapter;
import com.lv.greendao3.base.SwipeActivity;
import com.lv.greendao3.data.DbManager;
import com.lv.greendao3.model.Phone;
import com.lv.greendao3.model.User;
import com.lv.greendao3.utils.ActivityUtil;
import com.lv.greendao3.utils.MyToast;
import com.lv.greendao3.utils.ValidateUtil;
import com.lv.greendao3.widget.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.carbs.android.avatarimageview.library.AvatarImageView;

public class ContactActivity extends SwipeActivity implements View.OnClickListener, ItemLongClickListener, BottomSheetListener {

    private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar toolbar;
    private AvatarImageView avatarImageUser;
    private FloatingActionButton floatButtonAdd;
    private MyRecyclerView recyclerPhone;
    private PhoneAdapter phoneAdapter;

    private User user;
    private int colorId;
    private List<Phone> phones;
    private Phone selectPhone;

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
        phones = new ArrayList<>();
        phones.addAll(DbManager.queryPhonesByUserId(user.getId()));
        selectPhone = null;
    }

    private void initUI() {
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        avatarImageUser = (AvatarImageView) findViewById(R.id.avatar_image_user);
        floatButtonAdd = (FloatingActionButton) findViewById(R.id.float_button_add);
        recyclerPhone = (MyRecyclerView) findViewById(R.id.recycler_phone);

        setCollapsingToolbar();
        setRecyclerPhone();
        floatButtonAdd.setOnClickListener(this);
    }

    private void setCollapsingToolbar() {
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

    private void setRecyclerPhone() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerPhone.setLayoutManager(linearLayoutManager);
        phoneAdapter = new PhoneAdapter(this, this);
        phoneAdapter.setPhones(phones);
        recyclerPhone.setAdapter(phoneAdapter);
    }

    private void queryPhones() {
        List<Phone> phoneList = DbManager.queryPhonesByUserId(user.getId());
        phones.clear();
        phones.addAll(phoneList);
        phoneAdapter.setPhones(phones);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.float_button_add:
                showDialog(null);
                break;
        }
    }

    private void showDialog(final Phone phone) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (user == null) {
            builder.setTitle("新增");
        } else {
            builder.setTitle("修改");
        }
        builder.setView(getView(phone));
        builder.setCancelable(true);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (validateSignIn()) {
                    if (phone == null) {//说明是新增
                        Phone newPhone = new Phone(null, phoneNumber, phoneType, user.getId());
                        DbManager.addPhone(newPhone);
                    } else {
                        phone.setPhoneNumber(phoneNumber);
                        phone.setPhoneType(phoneType);
                        DbManager.updatePhone(phone);
                    }
                    queryPhones();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private TextInputEditText editTextPhone;
    private RadioGroup radioGroupType;
    private RadioButton radioButtonCmcc;
    private RadioButton radioButtonCucc;
    private RadioButton radioButtonCtcc;

    private View getView(Phone phone) {

        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_phone_layout, null);
        editTextPhone = (TextInputEditText) contentView.findViewById(R.id.edit_text_phone);
        radioGroupType = (RadioGroup) contentView.findViewById(R.id.radio_group_type);
        radioButtonCmcc = (RadioButton) contentView.findViewById(R.id.radio_button_cmcc);
        radioButtonCucc = (RadioButton) contentView.findViewById(R.id.radio_button_cucc);
        radioButtonCtcc = (RadioButton) contentView.findViewById(R.id.radio_button_ctcc);

        if (phone != null) {
            editTextPhone.setText(phone.getPhoneNumber());
            switch (phone.getPhoneType()) {
                case Phone.CMCC:
                    radioButtonCmcc.setChecked(true);
                    break;
                case Phone.CUCC:
                    radioButtonCucc.setChecked(true);
                    break;
                case Phone.CTCC:
                    radioButtonCtcc.setChecked(true);
                    break;
            }
        }
        return contentView;
    }

    private String phoneNumber;//号码
    private int phoneType; //类型

    private boolean validateSignIn() {
        boolean result = true;
        phoneNumber = editTextPhone.getText().toString().trim();
        StringBuilder builder = new StringBuilder();

        if (TextUtils.isEmpty(phoneNumber)) {
            builder.append("手机号码不能为空\n");
        } else if (!ValidateUtil.validatePhone(phoneNumber)) {
            builder.append("请填写正确的手机号码\n");
        }
        if (!TextUtils.isEmpty(builder)) {
            result = false;
            MyToast.showShortToast(builder.substring(0, builder.length() - 1));
        }
        if (radioButtonCmcc.isChecked()) {
            phoneType = Phone.CMCC;
        } else if (radioButtonCucc.isChecked()) {
            phoneType = Phone.CUCC;
        } else {
            phoneType = Phone.CTCC;
        }
        return result;
    }

    private void editPhone(Phone phone) {
        showDialog(phone);
    }

    private void deletePhone(final Phone phone) {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("您确定要删除手机号码 " + phone.getPhoneNumber() + "?")
                .setCancelable(true)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        List<Phone> phoneList = new ArrayList<>();
                        phoneList.add(phone);
                        DbManager.deletePhoneList(phoneList);
                        queryPhones();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    @Override
    public void onItemLongClick(Phone phone) {
        selectPhone = phone;
        new BottomSheet.Builder(this)
                .setSheet(R.menu.menu_edit)
                .setTitle(selectPhone.getPhoneNumber())
                .grid()
                .setListener(this)
                .show();
    }

    @Override
    public void onSheetShown(@NonNull BottomSheet bottomSheet) {

    }

    @Override
    public void onSheetItemSelected(@NonNull BottomSheet bottomSheet, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_delete:
                deletePhone(selectPhone);
                break;
            case R.id.menu_modify:
                editPhone(selectPhone);
                break;
            case R.id.menu_copy:
                ActivityUtil.copyTextToClip(ContactActivity.this, selectPhone.getPhoneNumber());
                MyToast.showShortToast("已复制到剪切板");
                break;
        }
    }

    @Override
    public void onSheetDismissed(@NonNull BottomSheet bottomSheet, @DismissEvent int i) {

    }
}
