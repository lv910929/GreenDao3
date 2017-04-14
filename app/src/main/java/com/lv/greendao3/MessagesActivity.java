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

import com.lv.greendao3.adapter.MessageAdapter;
import com.lv.greendao3.base.SwipeActivity;
import com.lv.greendao3.model.Message;
import com.lv.greendao3.utils.DialogUtils;
import com.lv.greendao3.utils.IntentUtil;

import java.util.ArrayList;
import java.util.List;

public class MessagesActivity extends SwipeActivity implements View.OnClickListener {

    private Toolbar toolbarComm;
    private RelativeLayout layoutDeleteBottom;
    private RecyclerView recyclerViewMessage;
    private Button btnDelete;

    private List<Message> messages;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        initData();
        initUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clean:
                IntentUtil.notifyUpdateMenu(0);
                break;
            case R.id.menu_read:
                IntentUtil.notifyUpdateMenu(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        messages = new ArrayList<>();
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
        messageAdapter = new MessageAdapter(this);
        recyclerViewMessage.setAdapter(messageAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.float_button_add://新增
                DialogUtils.showContactDialog(MessagesActivity.this, null);
                break;
            case R.id.btn_delete://删除

                break;
        }
    }
}
