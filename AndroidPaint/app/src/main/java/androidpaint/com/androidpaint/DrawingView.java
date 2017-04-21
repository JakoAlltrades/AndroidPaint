package androidpaint.com.androidpaint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Matthew Balderas on 4/19/2017.
 */

public class DrawingView extends View {

    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFF660000;
    //canvas
    private Canvas drawCanvas;
    private Canvas backgroundCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;
    private Bitmap mergedCanvasBitmap;
    private int backgroundColor = 0x00000000;
    private boolean isLine = false;
    private boolean isOval = false;

    public DrawingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setupDrawing();
    }

    private void setupDrawing() {
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(10);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    public void changeBrushSize(View view) {
        if (drawPaint.getStrokeWidth() == 10) {
            drawPaint.setStrokeWidth(20);
        } else if (drawPaint.getStrokeWidth() == 20) {
            drawPaint.setStrokeWidth(30);
        } else if (drawPaint.getStrokeWidth() == 30) {
            drawPaint.setStrokeWidth(50);
        } else if (drawPaint.getStrokeWidth() == 50) {
            drawPaint.setStrokeWidth(60);
        } else if (drawPaint.getStrokeWidth() == 60) {
            drawPaint.setStrokeWidth(10);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //view given size
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mergedCanvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
        backgroundCanvas = new Canvas(mergedCanvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //draw view
        mergeLayers();
        canvas.drawBitmap(mergedCanvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //detect user touch
        float touchX = event.getX();
        float touchY = event.getY();
        RectF oval;
        if (isLine) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    drawPath.moveTo(touchX, touchY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    //drawPath.lineTo(touchX, touchY);
                    break;
                case MotionEvent.ACTION_UP:
                    drawPath.lineTo(touchX, touchY);
                    drawCanvas.drawPath(drawPath, drawPaint);
                    drawPath.reset();
                    break;
                default:
                    return false;
            }
        } else if (isOval) {
            oval = new RectF(touchX,touchY,touchX+10,touchY-10);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    drawPath.moveTo(touchX, touchY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    //drawPath.lineTo(touchX, touchY);
                    //make oval here
                    drawCanvas.drawOval(oval,drawPaint);
                    break;
                case MotionEvent.ACTION_UP:

                    drawCanvas.drawPath(drawPath, drawPaint);
                    drawPath.reset();
                    break;
                default:
                    return false;
            }
        } else {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    drawPath.moveTo(touchX, touchY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    drawPath.lineTo(touchX, touchY);
                    break;
                case MotionEvent.ACTION_UP:
                    drawCanvas.drawPath(drawPath, drawPaint);
                    drawPath.reset();
                    break;
                default:
                    return false;
            }
        }
        invalidate();
        return true;
    }

    public void ChangeBackground(String newBackground) {
        backgroundColor = Color.parseColor(newBackground);
        Log.i("TESt", "TEEEESSSSSTTTT");
        invalidate();
    }

    public void setColor(String newColor) {
        //set color
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }

    private void mergeLayers() {
        backgroundCanvas.drawColor(backgroundColor);
        backgroundCanvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
    }

    public void clearCanvas() {
        int width = drawCanvas.getWidth();
        int height = drawCanvas.getHeight();
        canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mergedCanvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
        backgroundCanvas = new Canvas(mergedCanvasBitmap);
//        drawCanvas.drawColor(backgroundColor);
//        drawCanvas = new Canvas(canvasBitmap);
        invalidate();
    }

    public void makeLine(View view) {
        if (isLine) {
            isLine = false;
        } else {
            isLine = true;
        }
    }

    public void makeOval(View view) {
        if (isOval) {
            isOval = false;
        } else {
            isOval = true;
        }
    }

}
