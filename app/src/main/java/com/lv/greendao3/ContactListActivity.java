package com.lv.greendao3;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.lv.greendao3.adapter.ContactAdapter;
import com.lv.greendao3.data.DbManager;
import com.lv.greendao3.data.MainEvent;
import com.lv.greendao3.model.User;
import com.lv.greendao3.utils.ActivityUtil;
import com.lv.greendao3.utils.DialogUtils;
import com.lv.greendao3.utils.MyToast;
import com.lv.greendao3.widget.badge.MenuItemBadge;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;

public class ContactListActivity extends AppCompatActivity implements BottomSheetListener {

    private Toolbar toolbarComm;
    private IndexableLayout indexLayoutContact;

    private ContactAdapter contactAdapter;
    private List<User> users;
    private User selectUser;

    private MenuItem menuItemNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        EventBus.getDefault().register(this);
        initUI();
        queryUsers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menuItemNotification = menu.findItem(R.id.menu_notification);
        MenuItemBadge.update(this, menuItemNotification, new MenuItemBadge.Builder()
                .iconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_action_notify))
                .iconTintColor(Color.WHITE)
                .textBackgroundColor(Color.RED)
                .textColor(Color.WHITE));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_notification:

                break;
            case R.id.menu_add:
                DialogUtils.showContactDialog(ContactListActivity.this, null);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        toolbarComm = (Toolbar) findViewById(R.id.toolbar_comm);
        indexLayoutContact = (IndexableLayout) findViewById(R.id.index_layout_contact);

        setToolbarComm();
        setIndexLayoutContact();
    }

    private void setToolbarComm() {
        toolbarComm.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbarComm);
    }

    private void setIndexLayoutContact() {
        indexLayoutContact.setLayoutManager(new LinearLayoutManager(this));
        contactAdapter = new ContactAdapter(this);
        indexLayoutContact.setAdapter(contactAdapter);
        // set Material Design OverlayView
        indexLayoutContact.setOverlayStyle_MaterialDesign(Color.RED);
        // 全字母排序。  排序规则设置为：每个字母都会进行比较排序；速度较慢
        indexLayoutContact.setCompareMode(IndexableLayout.MODE_ALL_LETTERS);
        contactAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<User>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, User entity) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", entity);
                bundle.putInt("colorId", (int) (entity.getId() % 6));
                ActivityUtil.startActivity(ContactListActivity.this, ContactDetailActivity.class, bundle);
            }
        });
        contactAdapter.setOnItemContentLongClickListener(new IndexableAdapter.OnItemContentLongClickListener<User>() {
            @Override
            public boolean onItemLongClick(View v, int originalPosition, int currentPosition, User entity) {
                selectUser = entity;
                new BottomSheet.Builder(ContactListActivity.this)
                        .setSheet(R.menu.menu_edit)
                        .setTitle(selectUser.getName())
                        .grid()
                        .setListener(ContactListActivity.this)
                        .show();
                return false;
            }
        });
    }

    private void queryUsers() {
        List<User> userList = DbManager.queryUserListAll();
        users = new ArrayList<>();
        users.addAll(userList);
        contactAdapter.setDatas(users);
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
    public void onSheetShown(@NonNull BottomSheet bottomSheet) {

    }

    @Override
    public void onSheetItemSelected(@NonNull BottomSheet bottomSheet, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_delete:
                String title = "提示";
                String message = "您确定要删除联系人 " + selectUser.getName() + "?";
                DialogUtils.showActionDialog(ContactListActivity.this, title, message, onClickListener);
                break;
            case R.id.menu_modify:
                DialogUtils.showContactDialog(ContactListActivity.this, selectUser);
                break;
            case R.id.menu_copy:
                ActivityUtil.copyTextToClip(ContactListActivity.this, selectUser.getName());
                MyToast.showShortToast("已复制到剪切板");
                break;
        }
    }

    @Override
    public void onSheetDismissed(@NonNull BottomSheet bottomSheet, @DismissEvent int i) {

    }

    private DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            DbManager.deleteUser(selectUser);
            queryUsers();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
