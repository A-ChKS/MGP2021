package com.sdm.mgp2021;

import android.graphics.Canvas;
import android.view.SurfaceView;
import java.util.Random;

public class SmurfEntity implements EntityBase {
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
        spritesmurf = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.smurf_sprite),4,4, 16 );
        //week 8=>randomise position
        xPos = ranGen.nextFloat() * _view.getWidth();
        yPos = ranGen.nextFloat() * _view.getHeight();
    }

    @Override
    public void Update(float _dt) {
        // wk8=> update sprite animation frame based on timing
        spritesmurf.Update(_dt);
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

    public static SmurfEntity Create() {
        SmurfEntity result = new SmurfEntity(); //wek 8
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_SMURF); //wk8=>update ent tyep
        return result;
    }
}
