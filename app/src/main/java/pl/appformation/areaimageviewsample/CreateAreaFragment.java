package pl.appformation.areaimageviewsample;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.LinkedList;

import pl.appformation.areaimageview.utils.Point;

import static android.app.Activity.RESULT_OK;

public class CreateAreaFragment extends Fragment
{
    private static final int PHOTO_SELECT = 1;

    private PathImageView mPathImageView;
    private View mInfo;
    private View mFileBrowser;

    public static CreateAreaFragment newIstance()
    {
        return new CreateAreaFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_create_area, container, false);

        mPathImageView = (PathImageView) view.findViewById(R.id.path_img);
        mInfo = view.findViewById(R.id.info);
        mFileBrowser = view.findViewById(R.id.img_browser);

        mInfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                showFileChooser();
            }
        });

        mFileBrowser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                showFileChooser();
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }

    public void clearPath()
    {
        if (mPathImageView != null)
        {
            mPathImageView.clearPath();
        }
    }

    public void backPath()
    {
        if (mPathImageView != null)
        {
            mPathImageView.back();
        }
    }

    public LinkedList<Point> getRealPath()
    {
        return mPathImageView.getPath();
    }

    private void showFileChooser()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_SELECT);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PHOTO_SELECT)
        {
            Uri imageUri = data.getData();
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                mPathImageView.setBitmap(bitmap);
                mInfo.setVisibility(View.GONE);
                mFileBrowser.setVisibility(View.VISIBLE);
                mPathImageView.clearPath();
                ((MainActivity) getActivity()).updateMenuItems(mPathImageView.getPath());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
