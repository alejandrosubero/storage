package com.js.storage.dataModel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.widget.Toast;
//import java.util.Base64;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Date;



public class DataConverter {

    @TypeConverter
    public static Date toDate(Long datLong) {
        return datLong == null ? null : new Date(datLong);
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }


    public static byte[] convertImageToArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        return stream.toByteArray();
    }

    public  static byte[]  conertBase64ToBytes(String imageString){
        return Base64.decode(imageString, Base64.DEFAULT);
    }

    public  static Bitmap conertBase64ToBitmap(String imageString){
        byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        return decodedImage;
    }

    public  static String conertByteToBase64(byte[] arrayByte){
        return Base64.encodeToString(arrayByte, Base64.DEFAULT);
    }

    public static Bitmap convertArrayToImage(byte[] array) {
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }


    public static Bitmap reduceBitmap(Context contexto, Uri selectedImageUri, int maxAncho, int maxAlto) {
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(contexto.getContentResolver().openInputStream(selectedImageUri), null, options);
            options.inSampleSize = (int) Math.max(Math.ceil(options.outWidth / maxAncho), Math.ceil(options.outHeight / maxAlto));
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(
                    contexto.getContentResolver().openInputStream(selectedImageUri),
                    null, options);
        } catch (FileNotFoundException e) {
            Toast.makeText(contexto, "Fichero/recurso no encontrado",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        }
    }


}

//        Log.d("******** Actiones ===> ", ""+unit);