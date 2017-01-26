package pl.appformation.areaimageview.model;


public class ClickableArea
{
    private Object tag;

    ClickableArea(Object tag)
    {
        this.tag = tag;
    }

    public Object getTag()
    {
        return this.tag;
    }
}
