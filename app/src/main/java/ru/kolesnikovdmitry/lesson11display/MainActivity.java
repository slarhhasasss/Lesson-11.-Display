package ru.kolesnikovdmitry.lesson11display;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

//Для работы настройки яркости экрана используется это разрешение в манифесте:   <uses-permission android:name="android.permission.WRITE_SETTINGS"/>






    Button   mBtnGetGabaritsActMain;
    TextView mTextViewActMain;
    CardView mCardView;
    final int POPUP_MENU_GROUP_TEXT_VIEW       = 1001;
    final int POPUP_MENU_ITEM_SEE_DISPLAY_INFO = 1002;
    final int POPUP_MENU_ITEM_SEE_BTIGTNESS    = 1003;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewActMain = findViewById(R.id.textViewActMain);
        mTextViewActMain.setText("Узнать");
        mTextViewActMain.setOnLongClickListener(onLongClickTextViewActMain);

        mCardView = findViewById(R.id.cardViewActMain);
        mCardView.setBackgroundColor(getColor(R.color.colorAccent));
        mCardView.setContentPadding(15, 15, 15, 15);
        mCardView.setCardElevation(10);

        //Создаем ползунок, который программно меняет яркость в нашем приложении:
        SeekBar seekBar = findViewById(R.id.seekBarActMain);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float curVal = (float) progress / 100;
                mTextViewActMain.setText(String.valueOf(curVal));

                WindowManager.LayoutParams layPar = getWindow().getAttributes();
                layPar.screenBrightness = curVal;
                getWindow().setAttributes(layPar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    View.OnLongClickListener onLongClickTextViewActMain = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            PopupMenu popupMenu = new PopupMenu(getApplicationContext(), v);
            popupMenu.getMenu().add(POPUP_MENU_GROUP_TEXT_VIEW, POPUP_MENU_ITEM_SEE_DISPLAY_INFO, Menu.NONE, "Посмотреть инфо");
            popupMenu.setOnMenuItemClickListener(onMenuItemClickTextViewActMain);
            popupMenu.getMenu().add(POPUP_MENU_GROUP_TEXT_VIEW, POPUP_MENU_ITEM_SEE_BTIGTNESS, 2, "Яркость");
            popupMenu.show();
            return true;
        }
    };



    private PopupMenu.OnMenuItemClickListener onMenuItemClickTextViewActMain = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case POPUP_MENU_ITEM_SEE_DISPLAY_INFO:
                    //Получаем размеры экрана:
                    DisplayMetrics metrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    String strScreen = "";
                    strScreen += "Width: " + metrics.widthPixels + " pixels"
                            + "\n";
                    strScreen += "Height: " + metrics.heightPixels + " pixels"
                            + "\n";
                    strScreen += "The Logical Density: " + metrics.density
                            + "\n";
                    strScreen += "X Dimension: " + metrics.xdpi + " dot/inch"
                            + "\n";
                    strScreen += "Y Dimension: " + metrics.ydpi + " dot/inch"
                            + "\n";
                    strScreen += "The screen density expressed as dots-per-inch: "
                            + metrics.densityDpi + "\n";
                    strScreen += "A scaling factor for fonts displayed on the display: "
                            + metrics.scaledDensity + "\n";
                    mTextViewActMain.setText(strScreen);
                    break;
                case POPUP_MENU_ITEM_SEE_BTIGTNESS:
                    try {
                        int curBrigtness = android.provider.Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
                        mTextViewActMain.setText("Яркость: " + curBrigtness);
                    }catch (Throwable th) {
                        Toast.makeText(getApplicationContext(), th.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    };


    public void onClickActMain(View view) {
        switch (view.getId()) {
            case R.id.btnGetGabaritsActMain:
                Display disp = getWindowManager().getDefaultDisplay();
                Point point = new Point();
                disp.getSize(point);
                int screenWidth  = point.x;
                int screenHeight = point.y;

                String info = "Размеры экрана: ширина: " + screenWidth + ", высота: " + screenHeight;

                Snackbar snackbar = Snackbar.make(view, info, Snackbar.LENGTH_LONG);
                snackbar.show();
                break;
            default:
                break;
        }
    }
}
