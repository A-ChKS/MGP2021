package com.sdm.mgp2021;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceView;

public class Ship implements EntityBase {

    public Bitmap bmp = null;
    private Bitmap scaledbmp = null;

    private boolean isDone = false;

    int ScreenWidth, ScreenHeight;

    public float xPos = 0, yPos = 0;

    private float yLimit = 0, yStart = 0;

    private float flapAmount = 0, gravityVec = 0;
    private boolean tapGuard = false;

    @Override
    public boolean IsDone(){ return isDone; }

    @Override
    public void SetIsDone(boolean _isDone){ isDone = _isDone; }

    @Override
    // For us to intialize or load resource eg: images
    public void Init(SurfaceView _view){}

    @Override
    public void Update(float _dt) {}

    @Override
    public void Render(Canvas _canvas){}

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
