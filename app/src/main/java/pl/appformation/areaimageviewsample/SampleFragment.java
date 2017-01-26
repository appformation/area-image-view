package pl.appformation.areaimageviewsample;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pl.appformation.areaimageview.listeners.AreaClickListener;
import pl.appformation.areaimageview.utils.Point;
import pl.appformation.areaimageview.AreaImageView;

public class SampleFragment extends Fragment
{
    private AreaImageView mAreaImage;


    public static SampleFragment newInstance()
    {
        return new SampleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_sample, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        assignClickableArea();
    }


    private void assignClickableArea()
    {
        mAreaImage = (AreaImageView) getView().findViewById(R.id.area_image);

        ArrayList<Point> pOne = new ArrayList<>();
        pOne.add(new Point(95, 492));
        pOne.add(new Point(97, 571));
        pOne.add(new Point(70, 575));
        pOne.add(new Point(70, 495));

        ArrayList<Point> pTwo = new ArrayList<>();
        pTwo.add(new Point(127, 394));
        pTwo.add(new Point(158, 394));
        pTwo.add(new Point(160, 575));
        pTwo.add(new Point(125, 573));

        ArrayList<Point> pThree = new ArrayList<>();
        pThree.add(new Point(193, 294));
        pThree.add(new Point(225, 294));
        pThree.add(new Point(225, 577));
        pThree.add(new Point(188, 573));

        ArrayList<Point> pFour = new ArrayList<>();
        pFour.add(new Point(256, 196));
        pFour.add(new Point(286, 196));
        pFour.add(new Point(288, 464));
        pFour.add(new Point(256, 464));

        ArrayList<Point> pFive = new ArrayList<>();
        pFive.add(new Point(319, 99));
        pFive.add(new Point(350, 96));
        pFive.add(new Point(353, 366));
        pFive.add(new Point(317, 366));

        ArrayList<Point> pSix = new ArrayList<>();
        pSix.add(new Point(382, 0));
        pSix.add(new Point(413, 0));
        pSix.add(new Point(415, 264));
        pSix.add(new Point(378, 262));

        ArrayList<Point> pSeven = new ArrayList<>();
        pSeven.add(new Point(443, 99));
        pSeven.add(new Point(482, 96));
        pSeven.add(new Point(480, 371));
        pSeven.add(new Point(445, 366));

        ArrayList<Point> pEig = new ArrayList<>();
        pEig.add(new Point(506, 194));
        pEig.add(new Point(543, 196));
        pEig.add(new Point(541, 459));
        pEig.add(new Point(504, 459));

        ArrayList<Point> pNin = new ArrayList<>();
        pNin.add(new Point(574, 310));
        pNin.add(new Point(604, 307));
        pNin.add(new Point(604, 575));
        pNin.add(new Point(571, 577));

        ArrayList<Point> pTen = new ArrayList<>();
        pTen.add(new Point(634, 401));
        pTen.add(new Point(669, 399));
        pTen.add(new Point(672, 575));
        pTen.add(new Point(632, 575));

        ArrayList<Point> pEl = new ArrayList<>();
        pEl.add(new Point(697, 497));
        pEl.add(new Point(735, 494));
        pEl.add(new Point(732, 579));
        pEl.add(new Point(706, 579));

        ArrayList<Point> pTw = new ArrayList<>();
        pTw.add(new Point(511, 592));
        pTw.add(new Point(539, 590));
        pTw.add(new Point(543, 736));
        pTw.add(new Point(515, 740));

        ArrayList<Point> pThi = new ArrayList<>();
        pThi.add(new Point(441, 585));
        pThi.add(new Point(478, 579));
        pThi.add(new Point(480, 797));
        pThi.add(new Point(450, 794));

        ArrayList<Point> pFourt = new ArrayList<>();
        pFourt.add(new Point(319, 583));
        pFourt.add(new Point(351, 583));
        pFourt.add(new Point(358, 799));
        pFourt.add(new Point(319, 794));

        ArrayList<Point> pFif = new ArrayList<>();
        pFif.add(new Point(253, 583));
        pFif.add(new Point(286, 585));
        pFif.add(new Point(290, 736));
        pFif.add(new Point(258, 736));

        mAreaImage.addMultiVertexClickableArea(pOne, R.drawable.s1);
        mAreaImage.addMultiVertexClickableArea(pTwo, R.drawable.s2);
        mAreaImage.addMultiVertexClickableArea(pThree, R.drawable.s3);
        mAreaImage.addMultiVertexClickableArea(pFour, R.drawable.s4);
        mAreaImage.addMultiVertexClickableArea(pFive, R.drawable.s5);
        mAreaImage.addMultiVertexClickableArea(pSix, R.drawable.s6);
        mAreaImage.addMultiVertexClickableArea(pSeven, R.drawable.s7);
        mAreaImage.addMultiVertexClickableArea(pEig, R.drawable.s8);
        mAreaImage.addMultiVertexClickableArea(pNin, R.drawable.s9);
        mAreaImage.addMultiVertexClickableArea(pTen, R.drawable.s10);
        mAreaImage.addMultiVertexClickableArea(pEl, R.drawable.s11);
        mAreaImage.addMultiVertexClickableArea(pTw, R.drawable.s12);
        mAreaImage.addMultiVertexClickableArea(pThi, R.drawable.s13);
        mAreaImage.addMultiVertexClickableArea(pFourt, R.drawable.s14);
        mAreaImage.addMultiVertexClickableArea(pFif, R.drawable.s15);

        mAreaImage.addAreaListener(new AreaClickListener()
        {
            @Override
            public void OnAreaClick(Object drawableId)
            {
                mAreaImage.setImageDrawable(ContextCompat.getDrawable(getActivity(), (int) drawableId));
            }
        });
    }
}
