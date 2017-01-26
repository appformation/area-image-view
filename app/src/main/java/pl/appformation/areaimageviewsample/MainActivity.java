package pl.appformation.areaimageviewsample;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.LinkedList;

import pl.appformation.areaimageview.utils.Point;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = MainActivity.class.getSimpleName();

    private SampleFragment mSampleFragment;
    private CreateAreaFragment mCreateAreaFragment;

    private DrawerLayout drawerLayout;

    private MenuItem mOk;
    private MenuItem mBack;
    private MenuItem mClear;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSampleFragment = SampleFragment.newInstance();
        mCreateAreaFragment = CreateAreaFragment.newIstance();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        startToolbar();
        showFragment();
        handleDrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawing_menu, menu);

        mOk = menu.findItem(R.id.ok);
        mClear = menu.findItem(R.id.clear);
        mBack = menu.findItem(R.id.back);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (mCreateAreaFragment == null || mCreateAreaFragment.isHidden())
        {
            return super.onOptionsItemSelected(item);
        }

        switch (item.getItemId())
        {
            case R.id.ok:
            {
                showResultDialog();
                break;
            }
            case R.id.clear:
            {
                mCreateAreaFragment.clearPath();
                updateMenuItems(mCreateAreaFragment.getRealPath());
                break;
            }
            case R.id.back:
            {
                mCreateAreaFragment.backPath();
                updateMenuItems(mCreateAreaFragment.getRealPath());
            }
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFragment()
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, mCreateAreaFragment);
        transaction.hide(mCreateAreaFragment);
        transaction.add(R.id.fragment, mSampleFragment);
        transaction.commit();
    }

    private void startToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(ContextCompat.getColor(this, android.R.color.transparent));
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed);

        toogle.getDrawerArrowDrawable().setColor(Color.WHITE);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
    }

    private void handleDrawer()
    {
        findViewById(R.id.sample).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mSampleFragment.isHidden())
                {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.show(mSampleFragment);
                    transaction.hide(mCreateAreaFragment);
                    transaction.commit();

                    showMenuItems(false);
                }

                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

        findViewById(R.id.area_creator).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mCreateAreaFragment.isHidden())
                {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.show(mCreateAreaFragment);
                    transaction.hide(mSampleFragment);
                    transaction.commit();

                    updateMenuItems(mCreateAreaFragment.getRealPath());
                }

                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
    }

    public void updateMenuItems(LinkedList<Point> path)
    {
        if (path.isEmpty())
        {
            showMenuItems(false);
        }
        else
        {
            showMenuItems(true);
        }
    }

    private void showMenuItems(boolean b)
    {
        mBack.setVisible(b);
        mClear.setVisible(b);
        mOk.setVisible(b);
    }

    private void showResultDialog()
    {
        StringBuffer buffer = new StringBuffer();

        for (Point point : mCreateAreaFragment.getRealPath())
        {
            buffer.append("Point(")
                    .append(Math.round(point.x))
                    .append(", ")
                    .append(Math.round(point.y))
                    .append(")\n");
        }

        Log.d(TAG, buffer.toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Your vertices are:");
        builder.setMessage(buffer);
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", null);
        builder.create().show();
    }
}
