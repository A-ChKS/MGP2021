package com.sdm.mgp2021;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceView;

public class RenderTextEntity implements EntityBase{

    private boolean isDone = false;

    // Paint
    Paint paint = new Paint();
    private int red = 0, green = 0, blue = 0;

    Typeface myfont; // Use for loading font

    // Use for loading FPS so need the more parmeters!
    int frameCount;
    long lastTime = 0;
    long lastFPSTime = 0;
    float fps;

    @Override
    public boolean IsDone(){ return isDone; }

    public void SetIsDone(boolean _isDone){ isDone = _isDone; }

    @Override
    // For us to intialize or load resource eg: images
    public void Init(SurfaceView _view){
        myfont = Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/Gemcut.otf");
    }
    @Override
    public void Update(float _dt) {
        // get actual fps
        frameCount++;

        long currentTime = System.currentTimeMillis();

        lastTime = currentTime;

        if (currentTime - lastFPSTime > 1000) {
            fps = (frameCount * 1000.f)/ (currentTime - lastFPSTime);
            lastFPSTime = currentTime;
            frameCount = 0;
        }
    }

    @Override
    public void Render(Canvas _canvas){
        paint. setARGB(255, 0, 0, 0);
        paint.setStrokeWidth(200);
        paint.setTypeface(myfont);
        paint.setTextSize(75);
        _canvas.drawText("FPS: " + fps, 30, 80, paint); // For now, default number but u can use _view.getWidth
    }


    @Override
    public boolean IsInit(){ return false; }

    @Override
    public int GetRenderLayer(){
        return LayerConstants.RENDERTEXT_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer){ return; }

    @Override
    public ENTITY_TYPE GetEntityType(){ return ENTITY_TYPE.ENT_DEFAULT; }

    public static RenderTextEntity Create(){
        RenderTextEntity result = new RenderTextEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }
}
