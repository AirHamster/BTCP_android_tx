package com.btcontrol.btcp;




import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


public class Joys extends Activity {

	private static int FINGER_CIRCLE_SIZE = 30;
	private static int FINGER_CIRCLE_SIZE2 = 30;
	private static final UUID MY_UUID_SECURE =
	        UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	//private static BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
   // private OutputStream outStream = null;
	// Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;

    private static final int REQUEST_ENABLE_BT = 3;
	private static final String TAG = null; 
	// Name of the connected device
    private String mConnectedDeviceName = null;
    private boolean BT_is_connect;
    // String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    private String SendN, SendG, SendV, SendE;
    private String address;
	private String msgBuffer;
private cBluetooth bl = null;
    int ireg = 0;
    double k = 1.5;
    boolean dbg = false;
	//private String address;
    
//	private Intent data;
//	private String address = data.getExtras()
 //           .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
    
    

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
      //  setContentView(v1);
     // Get local Bluetooth adapter
        address = (String) getResources().getText(R.string.default_MAC);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        bl  = new cBluetooth(null, null);
   //     bl.checkBTState();
	}
	@Override
    protected void onPause() {
    	super.onPause();
    	bl.BT_onPause();
    }
	
	@Override
    public void onStart() {
        super.onStart();
     

        // If BT is not on, request that it be enabled.
       
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
 //       if (mBluetoothAdapter.isEnabled()){
  //      	Intent List = new Intent(this, DeviceListActivity.class);
//        	startActivityForResult(List, REQUEST_CONNECT_DEVICE_SECURE);        }
    }
	
/*@Override
protected void onActivityResult(int REQUEST_ENABLE_BT, int resultCode, Intent data){
	if (resultCode == RESULT_OK){
		Intent List = new Intent(this, DeviceListActivity.class);
		startActivityForResult(List, REQUEST_CONNECT_DEVICE_SECURE);
	}
	
}
*/
protected void onActivityResult1(int REQUEST_CONNECT_DEVICE_SECURE, int resultCode, Intent EXTRA_DEVICE_ADDRESS){
	if (resultCode == RESULT_OK){
		onResume();

	}
	
}
public void onResume() {
  super.onResume();
  BT_is_connect = bl.BT_Connect(address, false);

}
	

	class MyView extends View {

		Paint fingerPaint, finger2Paint, borderPaint, textPaint, linepaint;
		
		float x;  // координаты рисования стиков
        float y;
        
        int x01 = 188;  // начальные координаты
        int y01 = 415;
        
        float x2;
        float y2;
        
        int x02 = 666;
        int y02 = 240;
        
     
    	
        // переменные для перетаскивания
        boolean drag = false;
    
        boolean drag2 = false;

 
        boolean i1 = false;
        boolean i2 = false;
        boolean i3 = false;
        boolean i4 = false;
        int id1;
        int id2;


        int cmdV;
        int cmdE;
        int cmdN;
        int cmdG;

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
	        textPaint.setTextSize(18); 
	        
	        linepaint = new Paint();
	        linepaint.setARGB(255, 51, 51, 51);
	        linepaint.setStrokeWidth(8);
        }


        protected void onDraw(Canvas canvas) {
        
        	if(!drag){
        		x = x01;
        		y = y01;
        		fingerPaint.setColor(Color.RED);}
        	if(!drag2){
        		x2 = x02;
        		y2 = y02;
        		finger2Paint.setColor(Color.RED);
        	}
        	canvas.drawRGB(22, 22, 22);
        	canvas.drawLine(x, 65, x, 415, linepaint);
        	canvas.drawLine(x2, 65, x2, 415, linepaint);
        	canvas.drawLine(13, y, 363, y, linepaint);
        	canvas.drawLine(491, y2, 841, y2, linepaint);
        	canvas.drawCircle(x, y, FINGER_CIRCLE_SIZE, fingerPaint);              
            canvas.drawRect(13, 65, 363, 415, borderPaint);
            canvas.drawCircle(x2, y2, FINGER_CIRCLE_SIZE2, finger2Paint);
            canvas.drawRect(491, 65, 841, 415, borderPaint);
            
   if (dbg){
	//      canvas.drawText(String.valueOf("Connected:"+mConnectedDeviceName), 150, 75, textPaint);
            canvas.drawText(String.valueOf("SendV:"+SendV), 10, 15, textPaint);
            canvas.drawText(String.valueOf("SendE:"+SendE), 10, 35, textPaint);
            canvas.drawText(String.valueOf("SendG:"+SendG), 10, 55, textPaint);
            canvas.drawText(String.valueOf("SendN:"+SendN), 10, 75, textPaint);
            
            canvas.drawText(String.valueOf("x: "+x), 400, 15, textPaint);
            canvas.drawText(String.valueOf("x01: "+x01), 400, 35, textPaint);
            canvas.drawText(String.valueOf("y: "+y), 400, 55, textPaint);
            canvas.drawText(String.valueOf("y01 :"+y01), 400, 75, textPaint);
            
            canvas.drawText(String.valueOf("x2: "+x2), 550, 15, textPaint);
            canvas.drawText(String.valueOf("x02: "+x02), 550, 35, textPaint);
            canvas.drawText(String.valueOf("y2: "+y2), 550, 55, textPaint);
            canvas.drawText(String.valueOf("y02 :"+y02), 550, 75, textPaint);
        }
        }
        
		public boolean onTouchEvent(MotionEvent event) {
   
       

            // индекс касания
            int pointerIndex = event.getActionIndex();
            // число касаний
            int pointerCount = event.getPointerCount();
            int pointerId = event.getPointerId(pointerIndex);
         
        	// координаты Touch-события
            float evX = event.getX();
        	float evY = event.getY();
        	
        	float evX2 = event.getX();
        	float evY2 = event.getY();
            
        	
        	cmdV = (int) ((y2 - y02)*0.72857+128);
            cmdE = (int) ((x2 - x02)*0.72857+128);
            cmdG = (int) ((y01 - y)*0.72857);
            cmdN = (int) ((x - x01)*0.72857+128);
            CalcServo(cmdG,cmdN,cmdV,cmdE);
             
        	
            	   
        	
        	switch (event.getAction() & MotionEvent.ACTION_MASK) {

        	case MotionEvent.ACTION_DOWN:
        		
        		if (evX <= 427){
        			i1 = true;
        			id1 = pointerId;
        			x = evX;
        			y = evY;
        			if( evX < 13){
        				x = 13;
        			}
        			if ( evX > 363){
        				x = 363;
        			}
        			if (evY < 65){
        				y = 65;
        			}
        			if (evY > 415 ){
        				y = 415;
        			}
        			
        			fingerPaint.setColor(Color.GREEN);
        			FINGER_CIRCLE_SIZE = 40;
        			CalcServo(cmdG,cmdN,cmdV,cmdE);
        			invalidate();
        			drag = true;
        		}
        		
        		if(evX2 >=428){
        			i3 = true;
        			id2 = pointerId;
        			x2 = evX2;
        			y2 = evY2;
        			if( evX2 < 491){
        				x2 = 491;
        			}
        			if ( evX2 > 841){
        				x2 = 841;
        			}
        			if (evY2 < 65){
        				y2 = 65;
        			}
        			if (evY2 > 415 ){
        				y2 = 415;
        			}
        			
        			finger2Paint.setColor(Color.GREEN);
        			FINGER_CIRCLE_SIZE2 = 40;
        			CalcServo(cmdG,cmdN,cmdV,cmdE);
        			invalidate();
        			drag2 = true;
        		}
        	case MotionEvent.ACTION_POINTER_DOWN:
        		
        		if (event.getX(pointerId) <= 427){
        			i2 = true;
        			id1 = pointerId;
        			x = event.getX(pointerId);
        			y = event.getY(pointerId);
        			if( event.getX(pointerId) < 13){
        				x = 13;
        			}
        			if ( event.getX(pointerId) > 363){
        				x = 363;
        			}
        			if (event.getY(pointerId) < 65){
        				y = 65;
        			}
        			if (event.getY(pointerId) > 415 ){
        				y = 415;
        			}
        			
        			fingerPaint.setColor(Color.GREEN);
        			FINGER_CIRCLE_SIZE = 40;
        			CalcServo(cmdG,cmdN,cmdV,cmdE);
        			invalidate();
        			drag = true;
        		}
        		if(event.getX(pointerId) >=428){
        			i4 = true;
        			id2 = pointerId;
        			x2 = event.getX(pointerId);
        			y2 = event.getY(pointerId);
        			if( event.getX(pointerId) < 491){
        				x2 = 491;
        			}
        			if ( event.getX(pointerId) > 841){
        				x2 = 841;
        			}
        			if (event.getY(pointerId) < 65){
        				y2 = 65;
        			}
        			if (event.getY(pointerId) > 415 ){
        				y2 = 415;
        			}
        			
        			finger2Paint.setColor(Color.GREEN);
        			FINGER_CIRCLE_SIZE2 = 40;
        			CalcServo(cmdG,cmdN,cmdV,cmdE);
        			invalidate();
        			drag2 = true;
        		}
        	
   
        	
        		break;
        	case MotionEvent.ACTION_MOVE:
        		// если режим перетаскивания включен
        	{
        		for(int i = 0; i < pointerCount; ++i)
        			
            {
                pointerIndex = i;
                pointerId = event.getPointerId(pointerIndex);
        		
        		{
        			if ( pointerCount == 1)
        		{
        			if (i1 | i3 && event.getX() <= 426){
        				x = event.getX();
            			y = event.getY();
        				if( event.getX() < 13){
            				x = 13;
            			}
            			if ( event.getX() > 363){
            				x = 363;
            			}
            			if (event.getY() < 65){
            				y = 65;
            			}
            			if (event.getY() > 415){
            				y = 415;
            			}
            			
            			fingerPaint.setColor(Color.GREEN);  
            			FINGER_CIRCLE_SIZE = 40;
            			CalcServo(cmdG,cmdN,cmdV,cmdE);
            			invalidate(); 
        			}
        		
        		
        			else if ( i2 | i4 && event.getX() >= 428){
        				x2 = event.getX();
            			y2 = event.getY();
        				if( event.getX() < 491){
            				x2 = 491;
            			}
            			if ( event.getX() > 841){
            				x2 = 841;
            			}
            			if (event.getY() < 65){
            				y2 = 65;
            			}
            			if (event.getY() > 415 ){
            				y2 = 415;
            			}
            			
            			finger2Paint.setColor(Color.GREEN); 
            			FINGER_CIRCLE_SIZE2 = 40;
            			CalcServo(cmdG,cmdN,cmdV,cmdE);
            			invalidate(); 
        			}
        		}
        			 if (pointerCount >= 2)
        		
                //ДЖОЙ 1
        			if (event.getX(pointerId) <= 426){
        				x = event.getX(pointerId);
            			y = event.getY(pointerId);
        				if( event.getX(pointerId) < 13){
            				x = 13;
            			}
            			if ( event.getX(pointerId) > 363){
            				x = 363;
            			}
            			if (event.getY(pointerId) < 65){
            				y = 65;
            			}
            			if (event.getY(pointerId) > 415 ){
            				y = 415;
            			}
            			
            			fingerPaint.setColor(Color.GREEN);  
            			FINGER_CIRCLE_SIZE = 40;
            			CalcServo(cmdG,cmdN,cmdV,cmdE);
            			invalidate(); 
        			}
        		
        		
        			else if (event.getX(pointerId) >= 428){
        				x2 = event.getX(pointerId);
            			y2 = event.getY(pointerId);
        				if( event.getX(pointerId) < 491){
            				x2 = 491;
            			}
            			if ( event.getX(pointerId) > 841){
            				x2 = 841;
            			}
            			if (event.getY(pointerId) < 65){
            				y2 = 65;
            			}
            			if (event.getY(pointerId) > 415 ){
            				y2 = 415;
            			}
            			
            			finger2Paint.setColor(Color.GREEN); 
            			FINGER_CIRCLE_SIZE2 = 40;
            			CalcServo(cmdG,cmdN,cmdV,cmdE);
            			invalidate(); 
        			}
        		
        		}
            			
            }
            			break;
            			
        	}
        			
        	
            			 
        		
        	
        
        	// касание завершено
        	case MotionEvent.ACTION_UP:
        		// выключае режим перетаскивания
     //   		if (pointerIndex == 0){
            		if (event.getX(pointerIndex) < 426){
            			FINGER_CIRCLE_SIZE = 30;
            			ireg = 3;
            			CalcServo(0,128,cmdV,cmdE);
            		drag = false;
            		invalidate();
            		}
            		if (event.getX(pointerIndex) > 428){
            			FINGER_CIRCLE_SIZE2 = 30;
            			ireg = 3;
            			CalcServo(cmdG,cmdN,128,128);
            		drag2 = false;
            		invalidate();
            		}
//            	}
        		
        	case MotionEvent.ACTION_POINTER_UP:
       // 	if (pointerIndex == 1){
        		if (event.getX(pointerIndex) < 426){
        			FINGER_CIRCLE_SIZE = 30;
       			ireg = 3;
        			CalcServo(0,128,cmdV,cmdE);
        		drag = false;
        		invalidate();
        		}
        		if (event.getX(pointerIndex) > 428){
        			FINGER_CIRCLE_SIZE2 = 30;
        			ireg = 3;
        			CalcServo(cmdG,cmdN,128,128);
        		drag2 = false;
        		invalidate();
        		}
    //    	}
        		
        		// касание завершено
        	
        		break;
        	} // ot swich
			return true;
        	
        }
        
	
	}
public void CalcServo(int cmdG, int cmdN, int cmdV, int cmdE) {
	if (ireg == 3){
	ireg = 0;
	SendV = String.valueOf("V"+cmdV+"\r");
	SendG = String.valueOf("G"+cmdG+"\r");
	SendE = String.valueOf("E"+cmdE+"\r");
	SendN = String.valueOf("N"+cmdN+"\r");
	msgBuffer = (SendG+SendV+SendE+SendN);
//	byte[] b = msgBuffer.getBytes();
	bl.sendData(msgBuffer);}
	else {
		ireg++;
	}
	
}	
}

	

		
		