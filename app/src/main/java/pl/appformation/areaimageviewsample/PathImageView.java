package pl.appformation.areaimageviewsample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.LinkedList;

import pl.appformation.areaimageview.utils.Point;


public class PathImageView extends ImageView
{
    private LinkedList<Point> mSelectedRealPoints = new LinkedList<>();
    private LinkedList<Point> mSelectedPoints = new LinkedList<>();

    Paint currentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public PathImageView(Context context)
    {
        super(context);
        setOnTouchListener(touchListener);
    }

    public PathImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        currentPaint = new Paint();
        currentPaint.setColor(Color.CYAN);
        currentPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        currentPaint.setStrokeWidth(10);

        setOnTouchListener(touchListener);
    }

    private OnTouchListener touchListener = new OnTouchListener()
    {
        @Override
        public boolean onTouch(View view, MotionEvent event)
        {
            //http://android-er.blogspot.ru/2012/10/get-touched-pixel-color-of-scaled.html

            float imgX, imgY;

            float eventX = event.getX();
            float eventY = event.getY();
            float[] eventXY = new float[] {eventX, eventY};

            Matrix invertMatrix = new Matrix();
            ((ImageView)view).getImageMatrix().invert(invertMatrix);

            invertMatrix.mapPoints(eventXY);
            int x = (int) eventXY[0];
            int y = (int) eventXY[1];

            BitmapDrawable bitmapDrawable = (BitmapDrawable) getDrawable();
            if (bitmapDrawable == null)
            {
                return false;
            }

            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap == null)
            {
                return false;
            }

            mSelectedPoints.add(new Point(eventX, eventY));

            if(x < 0)
            {
                x = 0;
            }
            else if(x > bitmap.getWidth()-1)
            {
                x = bitmap.getWidth()-1;
            }

            if(y < 0)
            {
                y = 0;
            }
            else if(y > bitmap.getHeight()-1)
            {
                y = bitmap.getHeight()-1;
            }

            imgX = (x) / getResources().getDisplayMetrics().density;
            imgY = (y) / getResources().getDisplayMetrics().density;

            Point point = new Point(imgX, imgY);

            Log.d(PathImageView.class.getSimpleName(), "Clicked image position: " + imgX + "  " + imgY);

            mSelectedRealPoints.add(point);

            ((MainActivity) getContext()).updateMenuItems(mSelectedRealPoints);

            invalidate();

            return false;
        }
    };

    public PathImageView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        //here you set the rectangles in which you want to draw the bitmap and pass the bitmap

        if (mSelectedPoints.size() > 0)
        {
            Point startingPoint = mSelectedPoints.get(0);
            canvas.drawCircle(startingPoint.x, startingPoint.y, 15, currentPaint);
        }

        for (int i = 1; i < mSelectedPoints.size(); i++)
        {
            Point prevPoint = mSelectedPoints.get(i - 1);
            Point currPoint = mSelectedPoints.get(i);
            canvas.drawLine(prevPoint.x, prevPoint.y, currPoint.x, currPoint.y, currentPaint);
            canvas.drawCircle(currPoint.x, currPoint.y, 15, currentPaint);
        }
    }

    public void clearPath()
    {
        mSelectedPoints.clear();
        mSelectedRealPoints.clear();
        invalidate();
    }

    public void back()
    {
        if (mSelectedPoints.isEmpty() || mSelectedRealPoints.isEmpty())
        {
            return;
        }

        mSelectedPoints.removeLast();
        mSelectedRealPoints.removeLast();
        invalidate();
    }

    public LinkedList<Point> getPath()
    {
        return this.mSelectedRealPoints;
    }

    public void setBitmap(Bitmap bitmap)
    {
        if (bitmap == null)
        {
            return;
        }

        float width = bitmap.getWidth() * getResources().getDisplayMetrics().density;
        float height = bitmap.getHeight() * getResources().getDisplayMetrics().density;

        try
        {
            setImageBitmap(Bitmap.createScaledBitmap(bitmap, (int) width, (int) height, true));
        }
        catch (OutOfMemoryError er)
        {
            er.printStackTrace();
        }
    }
}
