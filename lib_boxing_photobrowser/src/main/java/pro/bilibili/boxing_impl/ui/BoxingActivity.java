/*
 *  Copyright (C) 2017 Bilibili
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package pro.bilibili.boxing_impl.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import pro.bilibili.boxing.AbsBoxingActivity;
import pro.bilibili.boxing.AbsBoxingViewFragment;
import pro.bilibili.boxing.model.config.BoxingConfig;
import pro.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing_impl.R;
import pro.bilibili.boxing_impl.crop.ui.ImagePicker;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

/**
 * Default UI Activity for simplest usage.
 * A simple subclass of {@link AbsBoxingActivity}. Holding a {@link AbsBoxingViewFragment} to display medias.
 */
public class BoxingActivity extends AbsBoxingActivity {
    private BoxingViewFragment mPickerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boxing);
        createToolbar();
        setTitleTxt(getBoxingConfig());
    }

    @NonNull
    @Override
    public AbsBoxingViewFragment onCreateBoxingView(ArrayList<BaseMedia> medias) {
        mPickerFragment = (BoxingViewFragment) getSupportFragmentManager().findFragmentByTag(BoxingViewFragment.TAG);
        if (mPickerFragment == null) {
            mPickerFragment = (BoxingViewFragment) BoxingViewFragment.newInstance().setSelectedBundle(medias);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, mPickerFragment, BoxingViewFragment.TAG).commit();
        }
        return mPickerFragment;
    }

    private void createToolbar() {
        Toolbar bar = findViewById(R.id.nav_top_bar);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setTitleTxt(BoxingConfig config) {
        TextView titleTxt = findViewById(R.id.pick_album_txt);
        if (config.getMode() == BoxingConfig.Mode.VIDEO) {
            titleTxt.setText(R.string.boxing_video_title);
            titleTxt.setCompoundDrawables(null, null, null, null);
            return;
        }
        mPickerFragment.setTitleTxt(titleTxt);
    }

    @Override
    public void onBoxingFinish(Intent intent, @Nullable List<BaseMedia> medias) {
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

//    @Override
//    public void onBoxingCropFinish(Intent intent) {
//        setResult(Activity.RESULT_OK, intent);
//        finish();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == ImagePicker.RESULT_CODE_ITEMS) {
            setResult(Activity.RESULT_OK, data);
            finish();
        }
    }
}
