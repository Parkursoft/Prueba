package com.laaficionmanda.android.fb;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.provider.MediaStore;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;

public class Utility {
	
	private static Facebook mFacebook;
    private static AsyncFacebookRunner mAsyncRunner;
    private static String APP_ID = "346416342099377";
    private static String userUID = null;
    
    private static int MAX_IMAGE_DIMENSION = 720;
    private static AndroidHttpClient httpclient = null;
    
    private static String[] permissions = { "offline_access", "publish_stream", "user_photos", "publish_checkins", "photo_upload", "email" };
    
    
    /**
     * Obtiene el id del usuario de facebook.
     * @return
     */
    public static String getUserId()
    {
    	return userUID;
    }
    
    /**
     * Setea el id del usuario de facebook.
     * @param id
     */
    public static void setUserId(String id)
    {
    	userUID = id;
    }
    
    /**
     * Obtiene los permisos que serán necesarios para
     * la aplicación.
     * @return
     */
    public static String[] getPermissions(){
    	return permissions;	
    }
    
    /**
     * Obtiene el ID del APP de Facebook.
     * @return
     */
    public String getAppID()
    {
    	return APP_ID;
    }
    
    /**
     * Setea el id del APP de facebook.
     * @param appID
     */
    public void setAppID(String appID)
    {
    	APP_ID = appID;
    }
    
    /***
     * Devuelve la instancia de la clase de facebook
     * en caso de que se encuentre nula
     * la crea con el id del APP que debe ser seteado con anteriodad.
     * @return
     */
    public static Facebook getFacebook()
    {
    	if(mFacebook == null)
    	{
    		mFacebook = new Facebook(APP_ID);
    		mAsyncRunner = new AsyncFacebookRunner(mFacebook);
    	}
    		
    	return mFacebook;
    }
    
    /***
     * Obtiene la instancia de la clase de AsyncFacebookRunner
     * la misma se crea cuando se crea la instancia de la clase
     * de Facebook, por lo que esta debe ser creada antes de
     * poder utilizar este método.
     * @return
     */
    public static AsyncFacebookRunner getAsyncFacebookRunner()
    {
    	return mAsyncRunner;
    }
    
    /**
     * 
     * @param url
     * @return
     */
    public static Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(new FlushedInputStream(is));
            bis.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpclient != null) {
                httpclient.close();
            }
        }
        return bm;
    }

    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break; // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }

    public static byte[] scaleImage(Context context, Uri photoUri) throws IOException {
        InputStream is = context.getContentResolver().openInputStream(photoUri);
        BitmapFactory.Options dbo = new BitmapFactory.Options();
        dbo.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, dbo);
        is.close();

        int rotatedWidth, rotatedHeight;
        int orientation = getOrientation(context, photoUri);

        if (orientation == 90 || orientation == 270) {
            rotatedWidth = dbo.outHeight;
            rotatedHeight = dbo.outWidth;
        } else {
            rotatedWidth = dbo.outWidth;
            rotatedHeight = dbo.outHeight;
        }

        Bitmap srcBitmap;
        is = context.getContentResolver().openInputStream(photoUri);
        if (rotatedWidth > MAX_IMAGE_DIMENSION || rotatedHeight > MAX_IMAGE_DIMENSION) {
            float widthRatio = ((float) rotatedWidth) / ((float) MAX_IMAGE_DIMENSION);
            float heightRatio = ((float) rotatedHeight) / ((float) MAX_IMAGE_DIMENSION);
            float maxRatio = Math.max(widthRatio, heightRatio);

            // Create the bitmap from file
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = (int) maxRatio;
            srcBitmap = BitmapFactory.decodeStream(is, null, options);
        } else {
            srcBitmap = BitmapFactory.decodeStream(is);
        }
        is.close();

        /*
         * if the orientation is not 0 (or -1, which means we don't know), we
         * have to do a rotation.
         */
        if (orientation > 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);

            srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(),
                    srcBitmap.getHeight(), matrix, true);
        }

        String type = context.getContentResolver().getType(photoUri);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (type.equals("image/png")) {
            srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        } else if (type.equals("image/jpg") || type.equals("image/jpeg")) {
            srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        }
        byte[] bMapArray = baos.toByteArray();
        baos.close();
        return bMapArray;
    }

    public static int getOrientation(Context context, Uri photoUri) {
        /* it's on the external media. */
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }

        cursor.moveToFirst();
        return cursor.getInt(0);
    }
    
}

