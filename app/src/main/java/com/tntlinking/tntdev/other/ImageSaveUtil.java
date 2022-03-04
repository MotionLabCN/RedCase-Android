package com.tntlinking.tntdev.other;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Dan on 2021/12/17.
 */

public class ImageSaveUtil {

    public static void toSaveQrImg(Context context, View view, String fileName, CallBack listener) {

        try {
            if (view == null) {
                return;
            }

            Bitmap bitmap;
            if (view instanceof ImageView) {
                BitmapDrawable drawable = (BitmapDrawable) ((ImageView) view).getDrawable();
                if (drawable == null) {
//                    MyToast.showToast(context.getString(R.string.picture_cannot_empty))
                    return;
                }
                bitmap = drawable.getBitmap();
            } else {
                bitmap = createBitmap(view);
            }

            if (bitmap == null) {
//                MyToast.showToast(context.getString(R.string.picture_cannot_empty))
                return;
            }

            Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            deletePicture(contentUri, "$fileName.jpeg", context, new ImgQueryFinishListener() {
                @Override
                public void onImgQueryFinishListener() {
                    //设置保存参数到ContentValues中
                    ContentValues contentValues =new  ContentValues();
                    //设置文件名
                    contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "$fileName.jpeg");
                    //兼容Android Q和以下版本
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        //android Q中不再使用DATA字段，而用RELATIVE_PATH代替
                        //RELATIVE_PATH是相对路径不是绝对路径
                        //DCIM是系统文件夹，关于系统文件夹可以到系统自带的文件管理器中查看，不可以写没存在的名字
                        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/Camera");
                    }
                    //设置文件类型
                    contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                    Uri uri = context.getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            contentValues
                    );

                    if (uri != null) { //若生成了uri，则表示该文件添加成功
                        //使用流将内容写入该uri中即可
                        try {
                            OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
                            if (outputStream != null) {
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                                outputStream.flush();
                                outputStream.close();

                                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                                listener.onSuccess(uri);
                            } else {
//                            MyToast.showToast(R.string.save_fail)
                            }

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    } else {
//                        MyToast.showToast(R.string.save_fail)
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void deletePicture(Uri contentUri, String fileName, Context context, ImgQueryFinishListener imgQueryFinishListener) {
        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, "_id desc");

        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String path =
                            cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                    int ringtoneID =
                            cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                    if (path.contains(fileName)) {
                        Uri mImageUri = Uri.withAppendedPath(contentUri, "" + ringtoneID);
                        context.getContentResolver().delete(mImageUri, null, null);
                        return;
                    }
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            imgQueryFinishListener.onImgQueryFinishListener();
        }
    }

    private static Bitmap createBitmap(View view) {
        int height = view.getHeight();
        if (view instanceof ScrollView) {
            height = ((ScrollView) view).getChildAt(0).getHeight();
        }
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public interface ImgQueryFinishListener {
        void onImgQueryFinishListener();
    }

    public interface CallBack {
        void onSuccess(Uri uri);
    }
}
