package bmob.store.demo.ui;

import java.io.File;
import java.util.Date;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import bmob.store.demo.R;
import bmob.store.demo.base.BaseFragmentActivity;
import bmob.store.demo.bean.MyUser;
import bmob.store.demo.bean.Shop;
import bmob.store.demo.config.MyConfig;
import bmob.store.demo.utlis.ImageViewUtil;
import bmob.store.demo.utlis.ToastFactory;
import bmob.store.demo.view.CircleImageView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class RealeseShopActivity extends BaseFragmentActivity implements OnClickListener{
	CircleImageView shop_avator;
	Button realeseOK;
	protected int getLayoutViewID() {
		return R.layout.realese_shop_activity;
	}
	protected void findViews() {
		shop_avator = (CircleImageView)findViewById(R.id.realese_addavatorBtn);
		realeseOK = (Button)findViewById(R.id.release_ok);
		
	}
	
	protected void setupViews() {
		
	}
	
	protected void setLinstener() {
		shop_avator.setOnClickListener(this);
		realeseOK.setOnClickListener(this);
	}
	
	public void go_changeFromAlbum()
	{
		Intent intent = new Intent(Intent.ACTION_PICK, null);
    	intent.setDataAndType(
			MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		startActivityForResult(intent, MyConfig.REQUESTCODE_TAKE_ALBUM);
	}
	
	public String getSDPath(){
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState()
		.equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
		if (sdCardExist)
		{
			sdDir = Environment.getExternalStorageDirectory();//获取跟目录
		}
		return sdDir.toString();
	}
	//绝对路径
    private String filePath = "";
    //照片存储的目录
    private String avator_cacheDir = getSDPath()+MyConfig.AVATOR_CACHE_DIR;
    
    private String camera_Name = "";
    /**
     * 跳转到相机界面
     * @param dirpath 需要一个sd卡目录来存放照片
     */
    public void go_CameraActivity()
    {
    	Log.i("sd dir",avator_cacheDir);
		File avator_cache_file = new File(avator_cacheDir);
		if (!avator_cache_file.exists()) {
			avator_cache_file.mkdirs();
		}
		camera_Name = String.valueOf((new Date()).getTime());
		File avator2File = new File(avator_cache_file, camera_Name+".png");//目录加名字
		// 原图
	    filePath = avator2File.getAbsolutePath();// 获取相片的保存路径
		Uri imageUri = Uri.fromFile(avator2File);
		Intent intentFromCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intentFromCamera.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
		intentFromCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//这个意思是存在将输出的照片的Uri存到指定位置
		//如果不设置输出的话,裁剪无法获得该照片
		startActivityForResult(intentFromCamera, MyConfig.REQUESTCODE_TAKE_CAMERA);
    }
    /**
     * 回调
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
	    	if(resultCode == RESULT_OK){
	    	switch (requestCode) {
			case MyConfig.REQUESTCODE_TAKE_CAMERA:
				Log.i("filepath----------",filePath);
				go_crop_pic(Uri.fromFile(new File(filePath)));
				break;
			case MyConfig.REQUESTCODE_TAKE_ALBUM:
				if(data == null)
					return;
				go_crop_pic(data.getData());
				break;
			case MyConfig.REQUESTCODE_TAKE_CROP:
				if(data !=null)
					cropBack(data);
				break;
			default:
				break;
			}
	    }
    }
    /**
     * 处理裁剪后的照片
     * @param data
     */
    Bitmap bb = null;
    public void cropBack(Intent data){
    	bb = data.getExtras().getParcelable("data");//获取裁剪后的Bitmap对象
		//在照片目录创建正常大小的bitmap,这个是裁剪后的bitmap
    	//压缩50%,采用JPG模式压缩
    	//可保持在9~15kb之间(4M~5M)
		ImageViewUtil.saveBitmap(avator_cacheDir,camera_Name+".png", bb, 50, true);
		//设置,上传,更新,over
		shop_avator.setImageBitmap(bb);
    }
    /**
     * 更新,根据File上传
     */
    public void createShop(){
    	final BmobFile bf = new BmobFile(new File(avator_cacheDir+camera_Name+".png"));
    	final ProgressDialog pd = ProgressDialog.show(this, "","正在创建....");
    	bf.upload(this,new UploadFileListener() {
			public void onSuccess() {
				//设置shop的图标等信息并save
				Shop s = new Shop();
				final MyUser u = BmobUser.getCurrentUser(RealeseShopActivity.this,MyUser.class);
				Log.i("id",u.getObjectId());
				s.setMyuser(u);
				s.setAdress("重庆市");
				s.setShopAvator(bf);
				s.setShopName("Bmob store");
				s.setShopType("小吃");
				s.save(RealeseShopActivity.this, new SaveListener() {
					public void onSuccess() {
						//创建成功
						//如果bmobobject子类中包含了objectid属性的值,那么更新的时候不要填入
						u.setIs_openShop(true);
						u.update(RealeseShopActivity.this,new UpdateListener() {
							public void onSuccess() {
								//更新成功
								pd.dismiss();
								ToastFactory.show(RealeseShopActivity.this, "创建成功!");
								finish();
							}
							public void onFailure(int arg0, String arg1) {
								pd.dismiss();
								ToastFactory.show(RealeseShopActivity.this, "创建失败!");
							}
						});
					}
					public void onFailure(int arg0, String arg1) {
						pd.dismiss();
					}
				});
			}
			public void onFailure(int arg0, String arg1) {
				//提醒开店失败
				pd.dismiss();
			}
		});
    }
   /**
    * 裁剪
    */
	public void go_crop_pic(Uri fileUri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
         intent.setDataAndType(fileUri, "image/*");  
		intent.putExtra("aspectX", 4);//长宽比
		intent.putExtra("aspectY", 4);
		intent.putExtra("outputX", 50);
		intent.putExtra("outputY", 50);
		intent.putExtra("crop", "true");
		intent.putExtra("scale", true);
//		 intent.putExtra("noFaceDetection", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("return-data", true);
		startActivityForResult(intent, MyConfig.REQUESTCODE_TAKE_CROP);
	}
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.realese_addavatorBtn:
			//显示dialog，分拍照和相册两种模式
			new AlertDialog.Builder(RealeseShopActivity.this)
			.setItems(new String[]{"相机","相册"},new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					if(which == 0){
						//跳转到相机界面
						go_CameraActivity();
					}else{
						//跳转到相册界面
						go_changeFromAlbum();
					}
				}
			}).show();
			break;
		case R.id.release_ok:
			//这里应该判断EditText是否为空以及判断身份证号是否合格,由于时间关系就直接写死了
			if(bb != null)
				createShop();
			else 
				ToastFactory.show(RealeseShopActivity.this, "还没有小店图标!");
			break;
		}
	}
}
