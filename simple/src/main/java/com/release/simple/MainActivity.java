package com.release.simple;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.release.alert.Alert;
import com.release.alert.ItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.release
 * @create 2019-12-27
 * @Describe
 */
public class MainActivity extends AppCompatActivity {

    private String[] bottomData = {"发送给好友", "转载到空间相册", "上传到群相册", "保存到手机", "收藏", "查看聊天图片"};
    private List<ItemBean> listData = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 100; i++) {
            listData.add(new ItemBean("条目 " + i, 0));
        }
    }

    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_alert:
                new Alert(MainActivity.this)
                        .builder()
                        .setMsg("仿ios无标题弹窗")
                        .setPositiveButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .show();
                break;
            case R.id.btn_alert2:
                new Alert(MainActivity.this)
                        .builder()
                        .setTitle("标题")
                        .setMsg("仿ios弹窗")
                        .setPositiveButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                break;
            case R.id.btn_alert_bottom:
                new Alert(MainActivity.this)
                        .builder(Alert.Type.BOTTOM)
                        .addItem("清空列表")
                        .setOnItemClickListener(new Alert.OnAlertItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
            case R.id.btn_alert_bottom2:
                new Alert(MainActivity.this)
                        .builder(Alert.Type.BOTTOM)
                        .addItem("相机")
                        .addItem("相册")
                        .setOnItemClickListener(new Alert.OnAlertItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
            case R.id.btn_alert_bottom3:
                new Alert(MainActivity.this)
                        .builder(Alert.Type.BOTTOM)
                        .addItem(bottomData)
                        .setOnItemClickListener(new Alert.OnAlertItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
            case R.id.btn_alert_bottom4:
                new Alert(MainActivity.this)
                        .builder(Alert.Type.BOTTOM)
                        .addItem(listData)
                        .setOnItemClickListener(new Alert.OnAlertItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
            case R.id.btn_alert_loading: {
                new Alert(MainActivity.this)
                        .builder(Alert.Type.PROGRESS)
                        .setProgressTextGone()
                        .show();

            }
            break;
            case R.id.btn_alert_loading2: {
                new Alert(MainActivity.this)
                        .builder(Alert.Type.PROGRESS)
                        .show();
            }
            break;
            case R.id.btn_alert_loading3: {
                new Alert(MainActivity.this)
                        .builder(Alert.Type.PROGRESS)
                        .setProgressText("自定义文字自定义文字自定义文字自定义文字自定义文字")
                        .show();
            }
            break;
            case R.id.btn_alert_custom: {
                new Alert(MainActivity.this)
                        .builder(Alert.Type.CUSTOM_DIALOG, R.layout.custom_dialog_layout)
                        .initDialogLayout(new Alert.OnInitDialogLayoutListener() {
                            @Override
                            public void initView(View view, final Dialog dialog) {
                                view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(MainActivity.this, "消失", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                                view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(MainActivity.this, "领取", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                            }
                        })
                        .show();
            }
            break;
        }
    }
}
