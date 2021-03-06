package com.sdm.mgp2021;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class RenderBackground implements EntityBase{

    private boolean isDone = false;
    private Bitmap bmp = null;
    private Bitmap ship = null;
    private float xPos, yPos, offset;
    private SurfaceView view = null;

    // For use to scale the background w respect to the screen size
    private Bitmap scaledbmp = null;
    int ScreenWidth, ScreenHeight;

    // Check if anything that we want to do with the entity is done. (use for pause)
    @Override
    public boolean IsDone(){
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone){
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view){
        bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.gamebg);
        //ship = BitmapFactory.decodeResource(_view.getResources(),R.drawable.ship2_1);
        ship = ResourceManager.Instance.GetBitmap(R.drawable.ship2_1);
        ship = ResourceManager.Instance.GetBitmap(R.drawable.ship2_1);
        // Find the surfaceview size or the screen size.
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        scaledbmp = Bitmap.createScaledBitmap(bmp, ScreenWidth, ScreenHeight, true);
    }

    @Override
    public void Update(float _dt){

        xPos -= _dt * 500; // deals w the speed of moving the screen

        if (xPos < - ScreenWidth){
            xPos = 0;
        }

    }

    @Override
    public void Render(Canvas _canvas){

        _canvas.drawBitmap(scaledbmp, xPos, yPos, null);  // 1st image
        _canvas.drawBitmap(scaledbmp, xPos + ScreenWidth, yPos, null); // 2nd image

        //Matrix transform = new Matrix();

        //transform.postTranslate (x,y)
        //transform.postScale (1,1)

        //transform.postRotate((float) Math.toDegrees(30));
        //_canvas.drawBitmap(ship, transform, null);
    }

    @Override
    public boolean IsInit(){
        return bmp != null;
    }

    @Override
    public int GetRenderLayer(){
        return LayerConstants.BACKGROUND_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer){
        return;
    }

    public static RenderBackground Create(){
        RenderBackground result = new RenderBackground();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){
        return ENTITY_TYPE.ENT_DEFAULT;
    }
}
