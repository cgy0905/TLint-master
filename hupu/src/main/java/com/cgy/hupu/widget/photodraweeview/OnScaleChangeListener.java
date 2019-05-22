package com.cgy.hupu.widget.photodraweeview;

/**
 * @author cgy
 * @desctiption Interface definition for callback to be invoked when attached ImageView scale changes
 * @date 2019/5/22 15:07
 */
public interface OnScaleChangeListener {
    /**
     * Callback for when the scale changes
     *
     * @param scaleFactor the scale factor (<1 for zoom out, >1 for zoom in)
     * @param focusX      focal point X position
     * @param focusY      focal point Y position
     */
    void onScaleChange(float scaleFactor, float focusX, float focusY);
}
