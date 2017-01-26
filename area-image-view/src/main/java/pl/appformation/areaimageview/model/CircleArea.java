package pl.appformation.areaimageview.model;


import pl.appformation.areaimageview.utils.Point;

public class CircleArea extends ClickableArea
{
    private Point mCenter;
    private int mRadius;

    public CircleArea(Point center, int radius, Object tag)
    {
        super(tag);
        this.mCenter = center;
        this.mRadius = radius;
    }

    public Point getCenter()
    {
        return mCenter;
    }

    public void setCenter(Point mCenter)
    {
        this.mCenter = mCenter;
    }

    public int getRadius()
    {
        return mRadius;
    }

    public void setRadius(int radius)
    {
        this.mRadius = radius;
    }

    public boolean insideArea(Point point)
    {
        return Math.pow(point.x - mCenter.x, 2) + Math.pow(point.y - mCenter.y, 2) < Math.pow(mRadius, 2);
    }
}
