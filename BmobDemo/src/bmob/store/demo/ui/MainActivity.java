package bmob.store.demo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import bmob.store.demo.R;
import bmob.store.demo.base.BaseFragmentActivity;
import bmob.store.demo.fragment.BBSFragment;
import bmob.store.demo.fragment.ShopFragment;
import bmob.store.demo.fragment.TalkFragment;

public class MainActivity extends BaseFragmentActivity {
	
	//底部按钮及布局
	private LinearLayout layout_Bottom;
	private Button[] btns= new Button[3];
	//3个fragments
	private Fragment[] fragments = new Fragment[3];
	//actionBar布局及textView
	private LinearLayout layout_actionBar;
	private TextView text_actionBar;
	private String[] actionBarStr = {"商城","会话","论坛"};
	
	private int index = 0;//当前的索引值
	private int lastindex = 0;//上一个索引值
	
	
	protected int getLayoutViewID() {
		return R.layout.activity_main;
	}
	
	protected void findViews() {
		mfragmentManager = getSupportFragmentManager();
		mfragmentTransaction = mfragmentManager.beginTransaction();
		//actionbar设置
		layout_actionBar = (LinearLayout)findViewById(R.id.main_layoutActionBar);
		text_actionBar = (TextView)layout_actionBar.findViewById(R.id.actionBar_showText);
		//底部按钮获取
		layout_Bottom = (LinearLayout)findViewById(R.id.main_layoutBottom);
		btns[0] = (Button)layout_Bottom.findViewById(R.id.main_btnShop);
		btns[1] = (Button)layout_Bottom.findViewById(R.id.main_btnTalk);
		btns[2] = (Button)layout_Bottom.findViewById(R.id.main_btnBBS);
	}
	
	protected void setupViews() {
		//初始化第一个fragment和按钮以及actionBar
		 if (null == fragments[0]){
			 fragments[0] = new ShopFragment();
			 mfragmentTransaction.add(R.id.main_fragmentContainer, fragments[0]);
         } else {
        	 mfragmentTransaction.show(fragments[0]);
         }
		 mfragmentTransaction.commit();
		 btns[0].setSelected(true);
		 text_actionBar.setText(actionBarStr[index]);
	}
	
	public void OnTabBtnClick(View v){
		mfragmentTransaction = mfragmentManager.beginTransaction();
		switch (v.getId()) {
			case R.id.main_btnShop:
				index = 0;
				hideFragments(mfragmentTransaction);
				if(fragments[index] == null)
					fragments[index] = new ShopFragment();
				break;
			case R.id.main_btnTalk:
				index = 1;
				hideFragments(mfragmentTransaction);
				if(fragments[index] == null)
					fragments[index] = new TalkFragment();
				break;
			case R.id.main_btnBBS:
				index = 2;
				hideFragments(mfragmentTransaction);
				if(fragments[index] == null)
					fragments[index] = new BBSFragment();
				break;
			default:
				break;
		}
		
		if (lastindex != index) {
			if (!fragments[index].isAdded()) {
				mfragmentTransaction.add(R.id.main_fragmentContainer,fragments[index]);
			}
			mfragmentTransaction.show(fragments[index]).commit();
		}
		btns[lastindex].setSelected(false);
		btns[index].setSelected(true);
		text_actionBar.setText(actionBarStr[index]);
		lastindex = index;
	}
	/**
	 * 隐藏所有的fragment
	 * @param transaction
	 */
    private void hideFragments(FragmentTransaction transaction) {
    	for (int i = 0; i < fragments.length; i++) {
			if(fragments[i] != null)
				transaction.hide(fragments[i]);
		}
    }
	protected void setLinstener() {
		
	}
}
