package com.sdm.mgp2021;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.text.method.Touch;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import java.util.Random;

public class Ship implements EntityBase {

    public Bitmap bmp = null;
    private Bitmap scaledbmp = null;

    private boolean isDone = false;

    int ScreenWidth, ScreenHeight;

    public float xPos = 0, yPos = 0;

    private float yLimit = 0, yStart = 0;

    private float flapAmount = 0, gravityVec = 0;
    private boolean tapGuard = false;

    private SurfaceView view = null;
    Matrix tfx = new Matrix();
    DisplayMetrics metrics;

    private Sprite spritesmurf = null;
    private boolean hasTouched = false;

    private float lifetime;

    @Override
    public boolean IsDone(){ return isDone; }

    @Override
    public void SetIsDone(boolean _isDone){ isDone = _isDone; }

    @Override
    // For us to intialize or load resource eg: images
    public void Init(SurfaceView _view){
        //bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.ship2_1);
        bmp = ResourceManager.Instance.GetBitmap( R.drawable.ship2_1);

        // New to Week 8
        // Using Sprite animation class to load our sprite sheet
        spritesmurf = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.smurf_sprite),4,4, 16 );

        metrics = _view.getResources().getDisplayMetrics();
        ScreenHeight = metrics.heightPixels / 5;
        ScreenWidth = metrics.widthPixels / 5;
        //scaledbmp = Bitmap.createScaledBitmap();

        Random ranGen = new Random();
        xPos = ranGen.nextFloat() * _view.getWidth();
        yPos = ranGen.nextFloat() * _view.getHeight();

        lifetime = 30.0f;
    }

    @Override
    public void Update(float _dt) {
        spritesmurf.Update(_dt);

        lifetime -= _dt;
        if (lifetime < 0.0f) {
            SetIsDone(true);
        }

//        if (TouchManager.Instance.IsDown()){
//            float imgRadius = bmp.getHeight() * 0.5f;
//            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius))
//            {
//                SetIsDone(true);
//            }
//        }

        if (TouchManager.Instance.HasTouch()) // Touch and drag
        {
            // Check collision with the smurf sprite
            float imgRadius = spritesmurf.GetWidth() * 0.5f;
            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius) || hasTouched)
            {
                hasTouched = true;
                xPos = TouchManager.Instance.GetPosX();
                yPos = TouchManager.Instance.GetPosY();
            }
        }
    }

    @Override
    public void Render(Canvas _canvas){
        spritesmurf .Render(_canvas, 100, 100);

        _canvas.drawBitmap(bmp, xPos, yPos, null);

        Matrix transform = new Matrix();
        transform.postScale((0.5f + Math.abs((float)Math.sin(lifetime))), (0.5f + Math.abs((float)Math.sin(lifetime))));
        _canvas.drawBitmap(bmp, transform, null);
    }

    @Override
    public boolean IsInit(){ return bmp != null; }

    @Override
    public int GetRenderLayer(){ return LayerConstants.PLAYER_LAYER; }

    @Override
    public void SetRenderLayer(int _newLayer){ return; }

    @Override
    public EntityBase.ENTITY_TYPE GetEntityType(){ return EntityBase.ENTITY_TYPE.ENT_DEFAULT; }

    public static Ship Create(){
        Ship result = new Ship();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }
}
