package androidpaint.com.androidpaint;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private DrawingView drawView;
    private ImageButton currPaint;
    private boolean backgroundToggleOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawView = (DrawingView) findViewById(R.id.drawing);
        LinearLayout paintLayout = (LinearLayout) findViewById(R.id.paint_colors);
        currPaint = (ImageButton) paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
    }

    public void paintClicked(View view) {
        //use chosen color
        //Log.i("broke", "ASfuck");
        drawView = (DrawingView) findViewById(R.id.drawing);
        if (view.getId() != currPaint.getId()) {
            //update color
            ImageButton imgView = (ImageButton) view;
            String color = view.getTag().toString();
            if(drawView != null) {
                if (backgroundToggleOn) {
                    drawView.ChangeBackground(color);
                }
                else {
                    drawView.setColor(color);
                }
            }
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
        }
        currPaint = (ImageButton) view;
    }

    public void onClearClick(View view)
    {
        drawView.clearCanvas();
    }

    public void changeBackgroundClick(View view)
    {
        backgroundToggleOn = !backgroundToggleOn;
        ImageButton changeButton = (ImageButton) findViewById(R.id.ChangeBackground);
        if (backgroundToggleOn) {
            changeButton.setImageResource(R.drawable.penicon);
        }
        else {
            changeButton.setImageResource(R.drawable.paintrollericon);
        }


    }
}