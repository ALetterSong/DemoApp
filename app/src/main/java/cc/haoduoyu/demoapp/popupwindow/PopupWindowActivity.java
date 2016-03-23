package cc.haoduoyu.demoapp.popupwindow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import cc.haoduoyu.demoapp.R;

/**
 * PopupWindow
 * Created by XP on 2016/3/23.
 */
public class PopupWindowActivity extends AppCompatActivity {

    PopupWindow popupWindow;
    Button button, button1;
    TextView shareTv, commentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);
        View view = getLayoutInflater().inflate(R.layout.popup_window, null);

        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        shareTv = (TextView) view.findViewById(R.id.share);
        commentTv = (TextView) view.findViewById(R.id.comment);

        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAsDropDown(button);
            }
        });

        shareTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "分享", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();

            }
        });

        commentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "评论", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(PopupWindowActivity.this, button1);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu2:
                                Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu3:
                                Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
}
