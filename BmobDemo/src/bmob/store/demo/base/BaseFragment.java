package bmob.store.demo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment{
	
	public Context context;
	public View contentView;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		contentView = inflater.inflate(getLayoutID(), null);
		findViews();
		return contentView;
	}
	public void onStart() {
		super.onStart();
		setupViews();
		setListener();
	}
	protected abstract int getLayoutID();
	protected abstract void findViews();
	protected abstract void setupViews();
	protected abstract void setListener();
}
