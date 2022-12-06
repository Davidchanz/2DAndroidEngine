package com.androidengine2d.engine;

import android.graphics.Canvas;

/**Interface for print Shapes on scene*/
public interface Painting {
    /**pint object on scene*/
    void paint(Canvas g, ShapeObject o);
}
