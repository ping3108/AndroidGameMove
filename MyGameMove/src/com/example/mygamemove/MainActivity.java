package com.example.mygamemove;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private TextView tv;
	private MyView mv;
	private LinearLayout l;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);
		
		l = (LinearLayout) findViewById(R.id.mainlayout);
		// get textScale;
		DisplayMetrics m = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(m);
		mv = new MyView(this, m.scaledDensity);
		l.addView(mv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		tv = (TextView)this.findViewById(R.id.mytv);
		
		Button btn = (Button)this.findViewById(R.id.mybtn);
		btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				getIt();
			}
		});			
	}
	
	private void getIt(){
		tv.setText(mv.getWH() + "px; w: " + l.getWidth() + "px, h: " 
				+ l.getHeight() + "px, margin: " + l.getPaddingLeft() + "px");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class MyView extends View{
		private int x;
		private int y;
		private String word;
		private Paint paint;
		private float delta;
		
		public MyView(Context context, float textScale){
			super(context);			
			this.setFocusable(true);
			this.setFocusableInTouchMode(true);
			
			x = 0;
			y = 20;
			word = "ping3108";
			
			paint = new Paint();
			paint.setColor(Color.RED);
			paint.setAntiAlias(true);
			paint.setTextSize(textScale * 16);
			
			FontMetrics fm = paint.getFontMetrics(); 
			delta = fm.bottom - fm.top;
		}
		
		@SuppressLint("ClickableViewAccessibility")
		@Override
		public boolean onTouchEvent(MotionEvent event){
			this.x = (int)event.getX();
			this.y = (int)(event.getY() + delta - delta);
			switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:
				word = "down";
				break;
			case MotionEvent.ACTION_MOVE:
				word = "move";
				break;
			case MotionEvent.ACTION_UP:
				word = "up";
				break;
			}
			this.invalidate();
			//return super.onTouchEvent(event);
			//note: if you don't return true, action_move & action_up won't be fired
			return true;
		}
		
		@Override
		protected void onDraw(Canvas canvas){
			super.onDraw(canvas);
			canvas.drawRGB(230, 230, 230);
			canvas.drawText("ÖÐÎÄ(" + word + ", x:"+ x +", y:" + y + ")", x, y, paint);
		}
		
		public String getWH(){
			return "w: " + this.getWidth() + "px, h: " + this.getHeight();
		}
	}
}
