package pl.appformation.areaimageview.utils;

/**
 * Copyright 2013-present Roman Kushnarenko

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
class Line
{
    private final Point mStart;
    private final Point mEnd;
    private float mA = Float.NaN;
    private float mB = Float.NaN;
    private boolean mVertical = false;

    Line(Point start, Point end)
    {
        mStart = start;
        mEnd = end;

        if (mEnd.x - mStart.x != 0)
        {
            mA = ((mEnd.y - mStart.y) / (mEnd.x - mStart.x));
            mB = mStart.y - mA * mStart.x;
        }

        else
        {
            mVertical = true;
        }
    }

    /**
     * Indicate whereas the point lays on the line.
     *
     * @param point
     *            - The point to check
     * @return <code>True</code> if the point lays on the line, otherwise return <code>False</code>
     */
    boolean isInside(Point point)
    {
        float maxX = mStart.x > mEnd.x ? mStart.x : mEnd.x;
        float minX = mStart.x < mEnd.x ? mStart.x : mEnd.x;
        float maxY = mStart.y > mEnd.y ? mStart.y : mEnd.y;
        float minY = mStart.y < mEnd.y ? mStart.y : mEnd.y;

        return (point.x >= minX && point.x <= maxX) && (point.y >= minY && point.y <= maxY);
    }

    /**
     * Indicate whereas the line is vertical. <br>
     * For example, line like x=1 is vertical, in other words parallel to axis Y. <br>
     * In this case the A is (+/-)infinite.
     *
     * @return <code>True</code> if the line is vertical, otherwise return <code>False</code>
     */
    public boolean isVertical()
    {
        return mVertical;
    }

    /**
     * y = <b>A</b>x + B
     *
     * @return The <b>A</b>
     */
    public float getA()
    {
        return mA;
    }


    float getB()
    {
        return mB;
    }

    Point getStart()
    {
        return mStart;
    }

    public Point getEnd()
    {
        return mEnd;
    }

    @Override
    public String toString()
    {
        return String.format("%s-%s", mStart.toString(), mEnd.toString());
    }
}
