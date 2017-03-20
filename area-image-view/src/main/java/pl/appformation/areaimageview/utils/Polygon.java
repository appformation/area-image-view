package pl.appformation.areaimageview.utils;

import java.util.ArrayList;
import java.util.List;

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
public class Polygon
{
    private final BoundingBox mBoundingBox;
    private final List<Line> mSides;

    private Polygon(List<Line> sides, BoundingBox boundingBox)
    {
        mSides = sides;
        mBoundingBox = boundingBox;
    }

    /**
     * Get the builder of the polygon
     *
     * @return The builder
     */
    public static Builder Builder()
    {
        return new Builder();
    }

    /**
     * Builder of the polygon
     *
     * @author Roman Kushnarenko (sromku@gmail.com)
     */
    public static class Builder
    {
        private List<Point> mVertexes = new ArrayList<>();
        private List<Line> mSides = new ArrayList<>();
        private BoundingBox mBoundingBox = null;

        private boolean mFirstPoint = true;
        private boolean mIsClosed = false;

        /**
         * Add vertex points of the polygon.<br>
         * It is very important to add the vertexes by order, like you were drawing them one by one.
         *
         * @param point- The vertex point
         * @return The builder
         */
        public Builder addVertex(Point point)
        {
            if (mIsClosed)
            {
                // each hole we start with the new array of vertex points
                mVertexes = new ArrayList<>();
                mIsClosed = false;
            }

            updateBoundingBox(point);
            mVertexes.add(point);

            // add line (edge) to the polygon
            if (mVertexes.size() > 1)
            {
                Line Line = new Line(mVertexes.get(mVertexes.size() - 2), point);
                mSides.add(Line);
            }

            return this;
        }

        /**
         * Close the polygon shape. This will create a new side (edge) from the <b>last</b> vertex point to the <b>first</b> vertex point.
         * @return The builder
         */
        public Builder close()
        {
            validate();

            mSides.add(new Line(mVertexes.get(mVertexes.size() - 1), mVertexes.get(0)));
            mIsClosed = true;

            return this;
        }

        /**
         * Build the instance of the polygon shape.
         *
         * @return The polygon
         */
        public Polygon build()
        {
            validate();

            if (!mIsClosed)
            {
                mSides.add(new Line(mVertexes.get(mVertexes.size() - 1), mVertexes.get(0)));
            }

            return new Polygon(mSides, mBoundingBox);
        }

        /**
         * Update bounding box with a new point.<br>
         * @param point- New point
         */
        private void updateBoundingBox(Point point)
        {
            if (mFirstPoint)
            {
                mBoundingBox = new BoundingBox();
                mBoundingBox.xMax = point.x;
                mBoundingBox.xMin = point.x;
                mBoundingBox.yMax = point.y;
                mBoundingBox.yMin = point.y;

                mFirstPoint = false;
            }
            else
            {
                if (point.x > mBoundingBox.xMax)
                {
                    mBoundingBox.xMax = point.x;
                }
                else if (point.x < mBoundingBox.xMin)
                {
                    mBoundingBox.xMin = point.x;
                }
                if (point.y > mBoundingBox.yMax)
                {
                    mBoundingBox.yMax = point.y;
                }
                else if (point.y < mBoundingBox.yMin)
                {
                    mBoundingBox.yMin = point.y;
                }
            }
        }

        private void validate()
        {
            if (mVertexes.size() < 3)
            {
                throw new RuntimeException("Polygon must have at least 3 points");
            }
        }
    }

    /**
     * Check if the the given point is inside of the polygon.<br>
     *
     * @param point- The point to check
     * @return <code>True</code> if the point is inside the polygon, otherwise return <code>False</code>
     */
    public boolean contains(Point point)
    {
        if (inBoundingBox(point))
        {
            Line ray = createRay(point);
            int intersection = 0;
            for (Line side : mSides)
            {
                if (intersect(ray, side))
                {
                    intersection++;
                }
            }

            if (intersection % 2 == 1)
            {
                return true;
            }
        }
        return false;
    }

    public List<Line> getSides()
    {
        return mSides;
    }

    private boolean intersect(Line ray, Line side)
    {
        Point intersectPoint;

        if (!ray.isVertical() && !side.isVertical())
        {
            if (ray.getA() - side.getA() == 0)
            {
                return false;
            }

            float x = ((side.getB() - ray.getB()) / (ray.getA() - side.getA()));
            float y = side.getA() * x + side.getB();
            intersectPoint = new Point(x, y);
        }
        else if (ray.isVertical() && !side.isVertical())
        {
            float x = ray.getStart().x;
            float y = side.getA() * x + side.getB();
            intersectPoint = new Point(x, y);
        }
        else if (!ray.isVertical() && side.isVertical())
        {
            float x = side.getStart().x;
            float y = ray.getA() * x + ray.getB();
            intersectPoint = new Point(x, y);
        }
        else
        {
            return false;
        }

        return side.isInside(intersectPoint) && ray.isInside(intersectPoint);
    }

    /**
     * Create a ray. The ray will be created by given point and on point outside of the polygon.<br>
     * The outside point is calculated automatically.
     */
    private Line createRay(Point point)
    {
        float epsilon = (mBoundingBox.xMax - mBoundingBox.xMin) / 100f;
        Point outsidePoint = new Point(mBoundingBox.xMin - epsilon, mBoundingBox.yMin);

        return new Line(outsidePoint, point);
    }

    /**
     * Check if the given point is in bounding box
     * @return <code>True</code> if the point in bounding box, otherwise return <code>False</code>
     */
    private boolean inBoundingBox(Point point)
    {
        return !(point.x < mBoundingBox.xMin || point.x > mBoundingBox.xMax || point.y < mBoundingBox.yMin || point.y > mBoundingBox.yMax);
    }

    private static class BoundingBox
    {
        float xMax = Float.NEGATIVE_INFINITY;
        float xMin = Float.NEGATIVE_INFINITY;
        float yMax = Float.NEGATIVE_INFINITY;
        float yMin = Float.NEGATIVE_INFINITY;
    }
}