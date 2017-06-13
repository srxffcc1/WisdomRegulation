package com.frame.photo;

import java.io.IOException;
import java.util.List;


import com.wisdomregulation.staticlib.Static_InfoApp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.view.SurfaceHolder;
import android.widget.Toast;

public class CameraInterface {
	private static final String TAG = "yanzi";
	private Camera mCamera;
	private Camera.Parameters mParams;
	private boolean isPreviewing = false;
	private float mPreviwRate = -1f;
	private static CameraInterface mCameraInterface;
	private String savehead;
	public static Size per;
	public interface CamOpenOverCallback{
		public void cameraHasOpened();
	}

	private CameraInterface(){

	}
	public  CameraInterface setSavehead(String savehead) {
		this.savehead = savehead;
		return this;
	}
	public static synchronized CameraInterface getInstance(){
		if(mCameraInterface == null){
			mCameraInterface = new CameraInterface();
		}
		return mCameraInterface;
	}
	/**打开Camera
	 * @param callback
	 */
	public void doOpenCamera(CamOpenOverCallback callback){
		com.wisdomregulation.utils.Log.i(TAG, "Camera open....");
		if(mCamera == null){
			mCamera = Camera.open();
			com.wisdomregulation.utils.Log.i(TAG, "Camera open over....");
			if(callback != null){
				callback.cameraHasOpened();
			}
		}else{
			com.wisdomregulation.utils.Log.i(TAG, "Camera open 异常!!!");
			doStopCamera();
			mCamera = Camera.open();
			com.wisdomregulation.utils.Log.i(TAG, "Camera open over....");
			if(callback != null){
				callback.cameraHasOpened();
			}
		}
			
	
	}
	/**使用Surfaceview开启预览
	 * @param holder
	 * @param previewRate
	 */
	public void doStartPreview(SurfaceHolder holder, float previewRate,int minwidth){
		com.wisdomregulation.utils.Log.i(TAG, "doStartPreview...");
		if(isPreviewing){
			mCamera.stopPreview();
			return;
		}
		if(mCamera != null){
			try {
				mCamera.setPreviewDisplay(holder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			initCamera(previewRate,minwidth);
		}


	}
	/**使用TextureView预览Camera
	 * @param surface
	 * @param previewRate
	 */
	public void doStartPreview(SurfaceTexture surface, float previewRate,int minwidth){
		com.wisdomregulation.utils.Log.i(TAG, "doStartPreview...");
		if(isPreviewing){
			mCamera.stopPreview();
			return;
		}
		if(mCamera != null){
			try {
				mCamera.setPreviewTexture(surface);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			initCamera(previewRate,minwidth);
		}
		
	}

	/**
	 * 停止预览，释放Camera
	 */
	public void doStopCamera(){
		if(null != mCamera)
		{
			mCamera.setPreviewCallback(null);
			mCamera.stopPreview(); 
			isPreviewing = false; 
			mPreviwRate = -1f;
			mCamera.release();
			mCamera = null;     
		}
	}
	/**
	 * 拍照
	 */
	public void doTakePicture(){
		if(isPreviewing && (mCamera != null)){
			mCamera.takePicture(mShutterCallback, null, mJpegPictureCallback);
			Static_InfoApp.create().getAllhandler().post(new Runnable() {
				
				@Override
				public void run() {
					Toast.makeText(Static_InfoApp.create().getContext(), "保存照片中...", Toast.LENGTH_SHORT).show();
					
				}
			});
		}
	}
	public boolean isPreviewing(){
		return isPreviewing;
	}

	public Size getPerfectSize(){
		Size perfect = null;
		mCamera=Camera.open();
		Camera.Parameters nParams=mCamera.getParameters();
		
		List<Size> sizes=nParams.getSupportedPreviewSizes();
		perfect=sizes.get(0);
		for (int i = sizes.size()-1; i > 0; i--) {
			if(sizes.get(i).height<=Static_InfoApp.create().getViewScreenWidth()){
				if(perfect.height<=sizes.get(i).height){
					if(sizes.get(i).width<=(Static_InfoApp.create().getViewScreenHigh()*340/369)){
						if(perfect.width<=sizes.get(i).width){
							perfect=sizes.get(i);
						}
//						break;
					}
				}

			}
		}
		
		return perfect;
	}

	private void initCamera(float previewRate,int minwidth){
		if(mCamera != null){

			mParams = mCamera.getParameters();
			mParams.setPictureFormat(PixelFormat.JPEG);//设置拍照后存储的图片格式
//			CamParaUtil.getInstance().printSupportPictureSize(mParams);
//			CamParaUtil.getInstance().printSupportPreviewSize(mParams);
			//设置PreviewSize和PictureSize
			Size pictureSize = CamParaUtil.getInstance().getPropPictureSize(
					mParams.getSupportedPictureSizes(),previewRate, minwidth);
			mParams.setPictureSize(pictureSize.width, pictureSize.height);
			Size previewSize = CamParaUtil.getInstance().getPropPreviewSize(
					mParams.getSupportedPreviewSizes(), previewRate, minwidth);
			mParams.setPreviewSize(CameraInterface.per.width, CameraInterface.per.height);

			mCamera.setDisplayOrientation(90);

//			CamParaUtil.getInstance().printSupportFocusMode(mParams);
			List<String> focusModes = mParams.getSupportedFocusModes();
			if(focusModes.contains("continuous-video")){
				mParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
			}
			mParams.setPictureFormat(ImageFormat.JPEG);  
			mParams.setJpegQuality(100);
			mParams.setJpegThumbnailQuality(100);
//			//自动聚焦模式
//			mParams.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
			mCamera.setParameters(mParams);	
//			mCamera.autoFocus(cb);
			mCamera.startPreview();//开启预览



			isPreviewing = true;
			mPreviwRate = previewRate;

			Parameters nParams = mCamera.getParameters(); //重新get一次
			com.wisdomregulation.utils.Log.i("SRXFF", "最终设置:PreviewSize--With = " + nParams.getPreviewSize().width
					+ "Height = " + nParams.getPreviewSize().height);
			com.wisdomregulation.utils.Log.i("SRXFF", "最终设置:PictureSize--With = " + nParams.getPictureSize().width
					+ "Height = " + nParams.getPictureSize().height);
		}
	}



	/*为了实现拍照的快门声音及拍照保存照片需要下面三个回调变量*/
	ShutterCallback mShutterCallback = new ShutterCallback() 
	//快门按下的回调，在这里我们可以设置类似播放“咔嚓”声之类的操作。默认的就是咔嚓。
	{
		public void onShutter() {
			// TODO Auto-generated method stub
			com.wisdomregulation.utils.Log.i(TAG, "myShutterCallback:onShutter...");
		}
	};
	PictureCallback mRawCallback = new PictureCallback() 
	// 拍摄的未压缩原数据的回调,可以为null
	{

		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			com.wisdomregulation.utils.Log.i(TAG, "myRawCallback:onPictureTaken...");

		}
	};
	PictureCallback mJpegPictureCallback = new PictureCallback() 
	//对jpeg图像数据的回调,最重要的一个回调
	{
		public void onPictureTaken(final byte[] data, Camera camera) {


			com.wisdomregulation.utils.Log.i(TAG, "myJpegCallback:onPictureTaken...");
			final Bitmap[] b = new Bitmap[1];

			
			if(null != data){
				b[0] = BitmapFactory.decodeByteArray(data, 0, data.length);//data是字节数据，将其解析成位图
				mCamera.stopPreview();
				isPreviewing = false;
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						
						if(null != b[0])
						{
							Bitmap rotaBitmap = ImageUtil.getRotateBitmap(b[0], 90.0f);
							FileUtil.saveBitmap(savehead,rotaBitmap);
							//设置FOCUS_MODE_CONTINUOUS_VIDEO)之后，myParam.set("rotation", 90)失效。
							//图片竟然不能旋转了，故这里要旋转下
							

							
						}
					}
				}).start();

			}
			//保存图片到sdcard

			//再次进入预览
			mCamera.startPreview();
			isPreviewing = true;
			Static_InfoApp.create().getContext().sendBroadcast(new Intent("Activity_PictureCollect.finishsave"));

		}
	};


}
