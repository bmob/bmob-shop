package bmob.store.demo.utlis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import bmob.store.demo.R;
import bmob.store.demo.base.BaseApplication;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class ImageViewUtil {
	/**
	 * 根据ID创建�?个图�?
	 * @param context
	 * @param bitAdress
	 * @return
	 */
	public static final Bitmap CreatImage(Context context, int drawableID) {  
		Bitmap bitmaptemp = null;  
		bitmaptemp = BitmapFactory.decodeResource(context.getResources(),  
				drawableID);  
		return bitmaptemp;  
		}  
	/**
	 * 
	 * 设置透明�?
	 * @param sourceImg 图片文件
	 * @param number  透明�?
	 * @return
	 */
	public static Bitmap setAlpha(Bitmap sourceImg, int number) {  
		int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];  
		sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0,sourceImg.getWidth(), sourceImg.getHeight());// ���ͼƬ��ARGBֵ  
		number = number * 255 / 100;  
		for (int i = 0; i < argb.length; i++) {  
		argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);// �޸����?2λ��ֵ  
		}  
		sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg.getHeight(), Config.ARGB_8888);  
		return sourceImg;  
		}
	 public static Bitmap createBitmap(String path,int w,int h){
	    	try{
				BitmapFactory.Options opts = new BitmapFactory.Options();
				opts.inJustDecodeBounds = true;
				// 这里是整个方法的关键，inJustDecodeBounds设为true时将不为图片分配内存
				BitmapFactory.decodeFile(path, opts);
				int srcWidth = opts.outWidth;// 获取图片的原始宽
				int srcHeight = opts.outHeight;// 获取图片原始高度
				int destWidth = 0;
				int destHeight = 0;
				// 缩放的比例
				double ratio = 0.0;
				if (srcWidth < w || srcHeight < h) {
					ratio = 0.0;
					destWidth = srcWidth;
					destHeight = srcHeight;
				} else if (srcWidth > srcHeight) {// 按比例计算缩放后的图片大小，maxLength是长或宽允许的最大长�?
					ratio = (double) srcWidth / w;
					destWidth = w;
					destHeight = (int) (srcHeight / ratio);
				} else {
					ratio = (double) srcHeight / h;
					destHeight = h;
					destWidth = (int) (srcWidth / ratio);
				}
				BitmapFactory.Options newOpts = new BitmapFactory.Options();
				// 缩放的比例，缩放是很难按准备的比例进行缩放的，目前我只发现只能�?�过inSampleSize来进行缩放，其�?�表明缩放的倍数，SDK中建议其值是2的指数�??
				newOpts.inSampleSize = (int) ratio + 1;
				// inJustDecodeBounds设为false表示把图片读进内存中
				newOpts.inJustDecodeBounds = false;
				// 设置大小，这个一般是不准确的，是以inSampleSize的为准，但是如果不设置却不能缩放
				newOpts.outHeight = destHeight;
				newOpts.outWidth = destWidth;
				// 获取缩放后的图
				return BitmapFactory.decodeFile(path, newOpts);
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}
	 }
	/**
	 * saveBitmap
	 * 
	 * @param @param filename---完整的路径格�?-包含目录以及文件�?
	 * @param @param bitmap
	 * @param @param isDelete --是否只留�?�?
	 * @return void
	 * @throws
	 */
	public static void saveBitmap(String dirpath,String filename,Bitmap bitmap, int display,boolean isDelete) {
		File dir = new File(dirpath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dirpath, filename);
		// 若存在即删除-默认只保留一张
		if (isDelete) {
			if (file.exists()) {
				file.delete();
			}
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("create"+e);
				e.printStackTrace();
			}
		}

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, display, out)) {
				out.flush();
			}
		} catch (FileNotFoundException e) {
			System.out.println(""+e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(""+e);
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					System.out.println("tiw"+e);
					e.printStackTrace();
				}
			}
		}
	}
	public static void setUrlImageView(String url,ImageView imageView) {
		ImageLoader.getInstance().displayImage(
				url,
				imageView,
				BaseApplication.getInstance().getOptions(
						R.drawable.ic_launcher),
				new SimpleImageLoadingListener() {
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						super.onLoadingComplete(imageUri, view, loadedImage);
					}
				});
	}
}
