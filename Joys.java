package com.btcontrol.btcp;


import java.lang.ref.WeakReference;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;
import android.widget.ToggleButton;


public class Joys extends Activity {
	
	private final static int FINGER_CIRCLE_SIZE = 30;
	private final static int BIG_CIRCLE_SIZE = 120;

	

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView v1 = new MyView(this);
        //setContentView(new MyView(this));
        setContentView(v1);
       
	}
	
	class MyView extends View {

		Paint fingerPaint, finger2Paint, borderPaint, textPaint;

        int dispWidth;
        int dispHeight;
        
        float x;
        float y;
        
        float x1;
        float y1;
        
        float xrect;
        float yrect;
        
    	String directionL = "";
    	String directionR = "";
    	//String cmdSend;
    	//String temptxtMotor;
    	
        // переменные для перетаскивания
        boolean drag = false;
        float dragX = 300;
        float dragY = 100;
        

        public MyView(Context context) {
        	super(context);
        	fingerPaint = new Paint();
        	fingerPaint.setAntiAlias(true);
        	fingerPaint.setColor(Color.RED);
        	
        	finger2Paint = new Paint();
        	finger2Paint.setAntiAlias(true);
        	finger2Paint.setColor(Color.RED);
                
        	borderPaint = new Paint();
        	borderPaint.setColor(Color.BLUE);
        	borderPaint.setAntiAlias(true);
        	borderPaint.setStyle(Style.STROKE);
        	borderPaint.setStrokeWidth(3);
        	
	        textPaint = new Paint(); 
	        textPaint.setColor(Color.WHITE); 
	        textPaint.setStyle(Style.FILL); 
	        textPaint.setColor(Color.BLACK); 
	        textPaint.setTextSize(14); 
        }


        protected void onDraw(Canvas canvas) {
        	dispWidth = (int) ((this.getRight()-this.getLeft())/3.5);
        	dispHeight = (int) Math.round((this.getBottom()-this.getTop())/1.7);
        	if(!drag){
        		x = 188;
        		y = 240;
        		x1 = 666;
        		y1 = 240;
        		fingerPaint.setColor(Color.RED);
        	}

            canvas.drawCircle(x, y, FINGER_CIRCLE_SIZE, fingerPaint);              
            canvas.drawRect(13, 65, 363, 415, borderPaint);
            canvas.drawCircle(x1, y1, FINGER_CIRCLE_SIZE, finger2Paint);
            canvas.drawRect(491, 65, 841, 415, borderPaint);
            
            
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
        	
        	// координаты Touch-события
        	float evX = event.getX();
        	float evY = event.getY();
                          
        	xrect = event.getX() - 200;
        	yrect = event.getY() - 240;
        	//Log.d("4WD", String.valueOf("X:"+this.getRight()+" Y:"+dispHeight));
            	   
        	float diagonal = (float) Math.sqrt(Math.pow(Math.abs(xrect),2)+Math.pow(Math.abs(yrect),2));

        	switch (event.getAction()) {

        	case MotionEvent.ACTION_DOWN:        
        		if(diagonal >= 0 && diagonal <= 175){
        			x = evX;
        			y = evY;
        			fingerPaint.setColor(Color.GREEN);
        			//temptxtMotor = CalcMotor(xcirc,ycirc);
        			CalcMotor(xrect,yrect);
        			invalidate();
        			drag = true;
        		}
        		break;

        	case MotionEvent.ACTION_MOVE:
        		// если режим перетаскивания включен
        		if (drag && diagonal >= 0 && diagonal <= 175) {
        			x = evX;
        			y = evY;
        			fingerPaint.setColor(Color.GREEN);
        			//temptxtMotor = CalcMotor(xcirc,ycirc);
        			CalcMotor(xrect,yrect);
        			invalidate();
        		}
        		break;

        	// касание завершено
        	case MotionEvent.ACTION_UP:
        		// выключаем режим перетаскивания
        		xrect = 0;
        		yrect = 0; 
        		drag = false;
        		//temptxtMotor = CalcMotor(xcirc,ycirc);
        		CalcMotor(xrect,yrect);
        		invalidate();
        		break;
        	}
        	return true;
        }

	
	private void CalcMotor(float calc_x, float calc_y){
    	String directionL = "";
    	String directionR = "";
    	//String cmdSend;
    	
    	calc_x = -calc_x;
	}
	}
}
		
		