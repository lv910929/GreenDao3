package com.lv.greendao3;

import android.os.Bundle;
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
import com.lv.greendao3.base.SwipeActivity;
import com.lv.greendao3.data.DbManager;
import com.lv.greendao3.data.MainEvent;
import com.lv.greendao3.model.User;
import com.lv.greendao3.utils.ActivityUtil;
import com.lv.greendao3.utils.DialogUtils;
import com.lv.greendao3.utils.MyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessagesActivity extends SwipeActivity implements View.OnClickListener, EditUserListener {

    private Toolbar toolbarComm;
    private RelativeLayout layoutDeleteBottom;
    private RecyclerView recyclerViewMessage;
    private Button btnDelete;

    private UserAdapter userAdapter;
    private List<User> users;
    private Set<User> selectUsers;
    private boolean isEditMode;//是否是编辑模式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
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
                userAdapter.setEditMode(isEditMode);
                invalidateOptionsMenu();
                break;
            case R.id.menu_clean:
                ActivityUtil.notifyUpdateMenu(0);
                break;
            case R.id.menu_read:
                ActivityUtil.notifyUpdateMenu(0);
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
        recyclerViewMessage = (RecyclerView) findViewById(R.id.recycler_view_message);
        layoutDeleteBottom = (RelativeLayout) findViewById(R.id.layout_delete_bottom);
        btnDelete = (Button) findViewById(R.id.btn_delete);

        setToolbarComm();
        setRecyclerView();
        layoutDeleteBottom.setVisibility(View.GONE);
        btnDelete.setOnClickListener(this);
    }

    private void setToolbarComm() {
        toolbarComm.setTitle("通知中心");
        setSupportActionBar(toolbarComm);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarComm.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewMessage.setLayoutManager(linearLayoutManager);
        userAdapter = new UserAdapter(this, isEditMode, this);
        recyclerViewMessage.setAdapter(userAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.float_button_add://新增
                DialogUtils.showContactDialog(MessagesActivity.this, null);
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
        DialogUtils.showContactDialog(MessagesActivity.this, user);
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
