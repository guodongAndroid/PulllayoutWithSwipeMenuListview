package demo.sun.com.pulllayoutwithswipemenulistview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.devspark.appmsg.AppMsg;
import com.xiaosu.pulllayout.PullLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PullLayout.OnPullCallBackListener
{

    @Bind(R.id.listview)
    SwipeMenuListView mListView;

    @Bind(R.id.pulllayout)
    PullLayout mPullLayout;

    private List<String> mLists = new ArrayList<>();

    private SwipeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init()
    {
        mPullLayout.setOnPullListener(this);
        for (int i = 0; i < 20; i++)
        {
            mLists.add("This is the " + i + " Item");
        }
        mAdapter = new SwipeAdapter();

        mListView.setAdapter(mAdapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        mListView.setMenuCreator(creator);

        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        break;
                    case 1:
                        AppMsg.makeText(MainActivity.this, "您关闭了:" + mAdapter.getItem(position), AppMsg.STYLE_CONFIRM).show();
                        mLists.remove(position);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    @Override
    public void onRefresh()
    {
        postDelay(new Runnable()
        {
            @Override
            public void run()
            {
                mPullLayout.finishPull();
            }
        });
    }

    @Override
    public void onLoad()
    {
        postDelay(new Runnable()
        {
            @Override
            public void run()
            {
                mPullLayout.finishPull();
            }
        });
    }

    private void postDelay(Runnable action)
    {
        getWindow().getDecorView().postDelayed(action, 3000);
    }

    private int dp2px(int dp)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    class SwipeAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return mLists.size();
        }

        @Override
        public String getItem(int position)
        {
            return mLists.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
            {
                convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            }
            TextView text = (TextView) convertView;
            text.setText(mLists.get(position));
            return convertView;
        }
    }
}
