package com.lv.greendao3;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.lv.greendao3.adapter.UserAdapter;
import com.lv.greendao3.data.DbManager;
import com.lv.greendao3.model.User;
import com.lv.greendao3.utils.MyToast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerViewUser;
    private RelativeLayout layoutDeleteBottom;
    private FloatingActionButton floatButtonAdd;
    private Button btnDelete;

    private UserAdapter userAdapter;
    private List<User> users;
    private Set<User> selectUsers;
    private boolean isEditMode;//是否是编辑模式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initUI();
        queryUsers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isEditMode) {
            menu.findItem(R.id.nav_edit).setTitle("完成");
        } else {
            menu.findItem(R.id.nav_edit).setTitle("编辑");
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_edit:
                if (isEditMode) {
                    isEditMode = false;
                    layoutDeleteBottom.setVisibility(View.GONE);
                } else {
                    isEditMode = true;
                    layoutDeleteBottom.setVisibility(View.VISIBLE);
                }
                setFloatButtonAdd();
                userAdapter.setEditMode(isEditMode);
                invalidateOptionsMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        isEditMode = false;
        selectUsers = new HashSet<>();
        users = new ArrayList<>();
    }

    private void initUI() {
        recyclerViewUser = (RecyclerView) findViewById(R.id.recycler_view_user);
        layoutDeleteBottom = (RelativeLayout) findViewById(R.id.layout_delete_bottom);
        floatButtonAdd = (FloatingActionButton) findViewById(R.id.float_button_add);
        btnDelete = (Button) findViewById(R.id.btn_delete);

        layoutDeleteBottom.setVisibility(View.GONE);
        setRecyclerViewUser();
        setFloatButtonAdd();
        floatButtonAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    private void setRecyclerViewUser() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewUser.setLayoutManager(linearLayoutManager);
        userAdapter = new UserAdapter(this, isEditMode);
        recyclerViewUser.setAdapter(userAdapter);
    }

    private void setFloatButtonAdd() {
        if (isEditMode) {
            floatButtonAdd.setVisibility(View.GONE);
        } else {
            floatButtonAdd.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.float_button_add://新增
                showAddDialog();
                break;
            case R.id.btn_delete://删除
                if (userAdapter.getSelectUsers() != null && userAdapter.getSelectUsers().size() > 0) {
                    DbManager.deleteUserList(userAdapter.getSelectUsers());
                    queryUsers();
                } else {
                    MyToast.showShortToast("请选择要删除的用户");
                }
                break;
        }
    }

    private void queryUsers() {
        List<User> userList = DbManager.queryUserListAll();
        users.clear();
        users.addAll(userList);
        userAdapter.setUsers(users);
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("新增");
        builder.setView(getView());
        builder.setCancelable(true);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (validateSignIn()) {
                    DbManager.addUser(userName, userSexy);
                    queryUsers();
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

    private TextInputEditText editSignName;
    private RadioGroup radioGroupSexy;
    private RadioButton radioButtonMan;
    private RadioButton radioButtonWoman;

    private View getView() {

        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_user_layout, null);
        editSignName = (TextInputEditText) contentView.findViewById(R.id.edit_text_name);
        radioGroupSexy = (RadioGroup) contentView.findViewById(R.id.radio_group_sexy);
        radioButtonMan = (RadioButton) contentView.findViewById(R.id.radio_button_man);
        radioButtonWoman = (RadioButton) contentView.findViewById(R.id.radio_button_woman);

        return contentView;
    }

    //报名姓名
    private String userName;
    //性别
    private int userSexy;

    private boolean validateSignIn() {
        boolean result = true;
        userName = editSignName.getText().toString().trim();
        StringBuilder builder = new StringBuilder();

        if (TextUtils.isEmpty(userName)) {
            builder.append("姓名不能为空\n");
        }
        if (!TextUtils.isEmpty(builder)) {
            result = false;
            MyToast.showShortToast(builder.substring(0, builder.length() - 1));
        }
        if (radioGroupSexy.getCheckedRadioButtonId() == R.id.radio_button_man) {
            userSexy = User.MAN;
        } else {
            userSexy = User.WOMAN;
        }
        return result;
    }
}
