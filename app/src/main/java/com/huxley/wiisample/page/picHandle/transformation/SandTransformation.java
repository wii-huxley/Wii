package com.huxley.wiisample.page.picHandle.transformation;

import android.content.Context;
import android.graphics.Bitmap;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.huxley.wiisample.common.BitmapUtils;
import jomeslu.com.sandpic.SandPic;

//////////////////////////////////////////////////////////
//
//      我们的征途是星辰大海
//
//		...．．∵ ∴★．∴∵∴ ╭ ╯╭ ╯╭ ╯╭ ╯∴∵∴∵∴ 
//		．☆．∵∴∵．∴∵∴▍▍ ▍▍ ▍▍ ▍▍☆ ★∵∴ 
//		▍．∴∵∴∵．∴▅███████████☆ ★∵ 
//		◥█▅▅▅▅███▅█▅█▅█▅█▅█▅███◤ 
//		． ◥███████████████████◤
//		.．.．◥████████████████■◤
//      
//      Created by huxley on 2017/10/21.
//
//////////////////////////////////////////////////////////
public class SandTransformation extends BitmapTransformation {


    public SandTransformation(Context context/*, int threshold, int ponitNum*/) {
        super(context);
    }


    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {

        // int w=toTransform.getWidth();
        // int h=toTransform.getHeight();
        // int maxPoint = 1*w*h/2;
        // maxPoint = maxPoint>100000?100000:maxPoint;
        // Bitmap blurredBitmap = toTransform.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap sobel = SandPic.getInstance().tramform(toTransform, 60, 100000);
        toTransform.recycle();
        return sobel;
    }


    @Override
    public String getId() {
        return "sand"; // todo
    }
}