package com.lv.greendao3;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.lv.greendao3.adapter.EditUserListener;
import com.lv.greendao3.adapter.UserAdapter;
import com.lv.greendao3.data.DbManager;
import com.lv.greendao3.data.MainEvent;
import com.lv.greendao3.model.User;
import com.lv.greendao3.utils.DialogUtils;
import com.lv.greendao3.utils.MyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, EditUserListener {

    private Toolbar toolbarComm;
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
        EventBus.getDefault().register(this);
        initData();
        initUI();
        queryUsers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action, menu);
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
                    userAdapter.unSelectAll();
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
        toolbarComm = (Toolbar) findViewById(R.id.toolbar_comm);
        recyclerViewUser = (RecyclerView) findViewById(R.id.recycler_view_user);
        layoutDeleteBottom = (RelativeLayout) findViewById(R.id.layout_delete_bottom);
        floatButtonAdd = (FloatingActionButton) findViewById(R.id.float_button_add);
        btnDelete = (Button) findViewById(R.id.btn_delete);

        toolbarComm.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbarComm);
        layoutDeleteBottom.setVisibility(View.GONE);
        setRecyclerViewUser();
        setFloatButtonAdd();
        floatButtonAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    private void setRecyclerViewUser() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewUser.setLayoutManager(linearLayoutManager);
        userAdapter = new UserAdapter(this, isEditMode, this);
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
                DialogUtils.showContactDialog(MainActivity.this, null);
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

    @Override
    public void editUser(User user) {
        DialogUtils.showContactDialog(MainActivity.this, user);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(MainEvent event) {
        switch (event.getWhat()) {
            case 0://更新通讯录列表
                queryUsers();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
