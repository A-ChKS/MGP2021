package com.sdm.mgp2021;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;

import java.util.HashMap;

public class ResourceManager {

    public final static ResourceManager Instance = new ResourceManager();

    private Resources res = null;
    //  bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.gamescene);
    // Meant we need to retrieve the information from the _view, which is our surfaceview , and our image to be loaded on the view.

    // WE will use a HASH MAP
    private HashMap<Integer, Bitmap> resMap = new HashMap<Integer, Bitmap>();

    private ResourceManager() {}

    public void Init(SurfaceView _view) {
        res = _view.getResources();
    }

    public Bitmap GetBitMap (int _id) {
        if (resMap.containsKey(_id)) // Use by KEY
            return resMap.get(_id);

        // This allow the images to be loaded.
        // === Bitmap bmp
        // Every image used there is always an ID tied to it.
        // If image is null --> program wil clash
        // Image size to big will also clash the program.

        // Type your own if you want to check for null !!!!
        Bitmap results = BitmapFactory.decodeResource(res, _id);
        resMap.put(_id, results);
        return  results;
    }
}
