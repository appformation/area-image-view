package pl.appformation.areaimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

import pl.appformation.areaimageview.listeners.AreaClickListener;
import pl.appformation.areaimageview.model.CircleArea;
import pl.appformation.areaimageview.model.MultiVertexArea;
import pl.appformation.areaimageview.utils.Point;

@SuppressWarnings("unused")
public class AreaImageView extends ImageView
{
    private static final String TAG = AreaImageView.class.getSimpleName();

    private ArrayList<AreaClickListener> mAreaClickListeners = new ArrayList<>();

    private ArrayList<MultiVertexArea> mMultiVertexAreas = new ArrayList<>();
    private ArrayList<CircleArea> mCircleAreas = new ArrayList<>();

    private OnTouchListener touchListener = new OnTouchListener()
    {
        @Override
        public boolean onTouch(View view, MotionEvent event)
        {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_UP :
                {
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

                    Log.d(TAG, "Clicked position: " + imgX + "  " + imgY);

                    for (AreaClickListener listener : mAreaClickListeners)
                    {
                        for (MultiVertexArea multiArea: mMultiVertexAreas)
                        {
                            if (multiArea.insideArea(point))
                            {
                                listener.OnAreaClick(multiArea.getTag());
                            }
                        }

                        for (CircleArea circleArea : mCircleAreas)
                        {
                            if (circleArea.insideArea(point))
                            {
                                listener.OnAreaClick(circleArea.getTag());
                            }
                        }
                    }

                    return false;
                }
            }

            return true;
        }
    };

    public AreaImageView(Context context)
    {
        super(context);
        generateView();
    }


    public AreaImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        generateView();
    }

    public AreaImageView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        generateView();
    }

    private void generateView()
    {
        setOnTouchListener(touchListener);
    }

    public void addAreaListener(AreaClickListener listener)
    {
        this.mAreaClickListeners.add(listener);
    }

    public void addCircleClickableArea(Point circleCenter, int radius, Object tag)
    {
        mCircleAreas.add(new CircleArea(circleCenter, radius, tag));
    }

    public void addPolygon(Object tag, Point... points)
    {
        addMultiVertexClickableArea(new ArrayList<>(Arrays.asList(points)), tag);
    }

    public void addMultiVertexClickableArea(ArrayList<Point> vertices, Object tag)
    {
        mMultiVertexAreas.add(new MultiVertexArea(vertices, tag));
    }

    @SafeVarargs
    public final void addMultiVertexClickableArea(ArrayList<Point> vertices, Object tag, ArrayList<Point>... gaps)
    {
        mMultiVertexAreas.add(new MultiVertexArea(vertices, tag, gaps));
    }

    public void setBitmap(Bitmap bitmap)
    {
        float width = bitmap.getWidth() * getResources().getDisplayMetrics().density;
        float height = bitmap.getHeight() * getResources().getDisplayMetrics().density;
        setImageBitmap(Bitmap.createScaledBitmap(bitmap, (int) width, (int) height, true));
    }

}
