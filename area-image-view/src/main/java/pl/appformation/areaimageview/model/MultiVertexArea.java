package pl.appformation.areaimageview.model;

import java.util.ArrayList;

import pl.appformation.areaimageview.utils.Point;
import pl.appformation.areaimageview.utils.Polygon;

public class MultiVertexArea extends ClickableArea
{
    private Polygon mPolygon;

    public MultiVertexArea(ArrayList<Point> vertices, Object tag)
    {
        super(tag);
        Polygon.Builder polyBuilder = new Polygon.Builder();
        generatePolygon(polyBuilder, vertices);

        mPolygon = polyBuilder.build();
    }

    @SafeVarargs
    public MultiVertexArea(ArrayList<Point> vertices, Object tag, ArrayList<Point>... gaps)
    {
        super(tag);
        Polygon.Builder polyBuilder = new Polygon.Builder();

        generatePolygon(polyBuilder, vertices);

        for (ArrayList<Point> gap : gaps)
        {
            polyBuilder.close();

            for (Point vert : gap)
            {
                polyBuilder.addVertex(vert);
            }
        }

        mPolygon = polyBuilder.build();
    }

    private void generatePolygon(Polygon.Builder polyBuilder, ArrayList<Point> vertices)
    {
        for (Point point : vertices)
        {
            polyBuilder.addVertex(point);
        }
    }

    public boolean insideArea(Point point)
    {
        return mPolygon.contains(point);
    }
}
