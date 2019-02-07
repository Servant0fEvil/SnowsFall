package com.QuangVinh.Snow;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.*;
import android.view.*;
import android.content.res.*;
import android.graphics.drawable.*;
import java.util.*;
import android.media.*;

/**
 *
 *@author ServantOfEvil
 */


public class MainActivity extends Activity 
{
	MediaPlayer pl;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(new RenderedScreen(this));
		pl = MediaPlayer.create(this, R.raw.letitsnow);
		pl.setLooping(true);
		pl.start();
	}

	@Override
	public void onBackPressed()
	{
		try
		{
			if (pl.isPlaying())
			{	
				pl.stop();
			}
		}
		catch (Exception e)
		{}
		super.onBackPressed();
	}


}

class RenderedScreen extends android.view.View
{
	int w,h;
	boolean first_init =true;
	Paint p = new Paint();
	final int NUM_SNOW = 100;
	Drawable db;
	Vector snows = new Vector();
	Random rd = new Random();
	Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.tuyet);

	public RenderedScreen(Context con)
	{
		super(con);
		Resources res = getResources();
		db = res.getDrawable(R.drawable.bg);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		w = getWidth();
		h = getHeight();
		while (snows.size() < NUM_SNOW)Add(first_init);
		first_init = false;
		canvas.drawColor(Color.BLACK);
		db.setBounds(0, h - 221, w, h);
		db.draw(canvas);
		for (int i=0;i < snows.size();i++)
		{
			Snow sn = (Snow)snows.elementAt(i);
			sn.Animate(canvas, p);
			if (sn.x > w || sn.x < 0 || sn.y > h)snows.removeElementAt(i);	
		}
		//p.setTextSize(20);
		//p.setTextAlign(Paint.Align.CENTER);
		//p.setColor(0xffffffff);
		//canvas.drawText("Tường Vy Lê",getWidth()/2,getHeight()/2,p);
		//canvas.drawBitmap(bm, 50, 50, p);
		super.onDraw(canvas);
		invalidate();
	}

	public void Add(boolean init)
	{
		snows.addElement(new Snow(Math.abs(rd.nextInt() % w), init ?Math.abs(rd.nextInt() % h): 0, rd.nextInt() % 2, Math.abs(rd.nextInt() % 4) + 1, Bitmap.createBitmap(bm, 0, Math.abs(rd.nextInt() % 3) * 5, 5, 5)));
	}
}

class Snow
{
	int x,y,vx,vy;
	Bitmap snow;

	public Snow(int x, int y, int vx, int vy, Bitmap frame)
	{
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		snow = frame;
	}

	public void Animate(Canvas canvas, Paint p)
	{
		x += vx;
		y += vy;
		canvas.drawBitmap(snow, x, y, p);
	}
}
