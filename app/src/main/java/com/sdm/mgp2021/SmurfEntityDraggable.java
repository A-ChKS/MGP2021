package com.sdm.mgp2021;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceView;

import java.util.Random;

public class SmurfEntityDraggable implements EntityBase {
    private boolean isDone = false;
    private float xPos, yPos, offset;
    private Sprite spritesmurf = null;   // New on Week 8

    Random ranGen = new Random(); //wk 8=>Random Generator

    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {
        //week 8 => create new sprite instance
        spritesmurf = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.smurf_sprite),4,4, 8 );
        //week 8=>randomise position
        xPos = ranGen.nextFloat() * _view.getWidth()/2;
        yPos = ranGen.nextFloat() * _view.getHeight()/2;
    }

    @Override
    public void Update(float _dt) {
        // wk8=> update sprite animation frame based on timing
        spritesmurf.Update(_dt);

        //wk8=>Dragging code --
        if (TouchManager.Instance.HasTouch())  // Touch and drag
        {
            // Check collision with the smurf sprite
            float imgRadius1 = spritesmurf.GetWidth() * 0.5f;
            //Log.v("imgrad","s"+imgRadius1);
            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius1) )
            {
                xPos = TouchManager.Instance.GetPosX();
                yPos = TouchManager.Instance.GetPosY();
            }
        }
        //Wk8=>End Dragging Code
    }

    @Override
    public void Render(Canvas _canvas) {
        //wk 8=>draw sprite using xpos,ypos, must cast in int
        spritesmurf.Render(_canvas, (int)xPos, (int)yPos);
    }

    @Override
    public boolean IsInit() {
        return spritesmurf != null;
    } //wk 8=>update to ret sprite variable

    @Override
    public int GetRenderLayer() {
        return LayerConstants.SMURF_LAYER;
    } //wk 8=>update smurf layer

    @Override
    public void SetRenderLayer(int _newLayer) { }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_SMURF;
    } //Week 8=>Update ent type

    public static SmurfEntityDraggable Create() {
        SmurfEntityDraggable result = new SmurfEntityDraggable(); //wek 8
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_SMURF); //wk8=>update ent tyep
        return result;
    }
}
