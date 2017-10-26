package com.vitaviva.scalesizetextview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public final class ScaleTextSizeUtil {

    private static final String KEY_SCALE = "TEXT_SIZE_SCALE_INFO";
    static final String SP_FILE_NAME = "SCALE_SP";

    private ScaleTextSizeUtil() {
        curScaleInfo = getCachedScaleInfo();
    }

    private static ScaleTextSizeUtil INSTANCE = null;

    private ScaleInfo curScaleInfo = ScaleInfo.NORMAL;

    public static ScaleTextSizeUtil getInstance(Context context) {
        SharedPreferencesMgr.init(context, ScaleTextSizeUtil.SP_FILE_NAME);
        if (INSTANCE == null) {
            synchronized (ScaleTextSizeUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ScaleTextSizeUtil();
                }
            }
        }
        return INSTANCE;
    }

    private final ScaleInfo[] scaleInfos = {
            ScaleInfo.SMALL,
            ScaleInfo.NORMAL,
            ScaleInfo.LARGE,
            ScaleInfo.XLARGE,
            ScaleInfo.XXLARGE
    };

    private ScaleInfo[] getScaleInfos() {
        return scaleInfos;
    }

    public void setScale(ScaleInfo scaleInfo) {
        if (curScaleInfo != scaleInfo) {
            curScaleInfo = scaleInfo;
            cacheCurScaleInfo(scaleInfo);
            EventBus.getDefault().post(new TextSizeScaleChangedAction(curScaleInfo));
        }
    }

    public ScaleInfo getCurScale() {
        return curScaleInfo;
    }


    private void cacheCurScaleInfo(ScaleInfo scaleInfo) {
        SharedPreferencesMgr.setFloat(KEY_SCALE, scaleInfo.getAdd());
    }

    private ScaleInfo getCachedScaleInfo() {
        float scale = SharedPreferencesMgr.getFloat(KEY_SCALE, 1.0f);
        for (ScaleInfo item : scaleInfos) {
            if (Float.compare(item.getAdd(), scale) == 0 || item.getAdd() > scale) {
                return item;
            }
        }
        return ScaleInfo.NORMAL;
    }


    public static class SelectScaleDialog extends DialogFragment
            implements DialogInterface.OnClickListener {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            List<String> strings = new ArrayList<>(
                    ScaleTextSizeUtil.getInstance(getContext()).getScaleInfos().length);
            for (ScaleInfo item : ScaleTextSizeUtil.getInstance(getContext()).getScaleInfos()) {
                strings.add(getActivity().getString(item.getDescription()));
            }
            return getListDialog(getActivity(), strings.toArray(new String[strings.size()]),
                    this);
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            ScaleTextSizeUtil.getInstance(getContext())
                    .setScale(ScaleTextSizeUtil.getInstance(getContext()).getScaleInfos()[which]);
            dismiss();
        }
    }

    private static AlertDialog getListDialog(Context context, String[] data,
                                             DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setAdapter(new ArrayAdapter<Object>(context, R.layout.textview_alert_dialog, data),
                listener);
        builder.setItems(data, listener);
        AlertDialog dialog = builder.create();

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public enum ScaleInfo {

        SMALL(R.string.scale_textsize_discription_small, 0.8f),
        NORMAL(R.string.scale_textsize_discription_normal, 1),
        LARGE(R.string.scale_textsize_discription_large, 1.1f),
        XLARGE(R.string.scale_textsize_discription_xlarge, 1.3f),
        XXLARGE(R.string.scale_textsize_discription_xxlarge, 1.5f);

        private final int description;
        private final float add;

        ScaleInfo(int description, float add) {
            this.description = description;
            this.add = add;
        }

        public int getDescription() {
            return description;
        }

        public float getAdd() {
            return add;
        }

        public float compute(float origin) {
            return origin * add;
        }

        public float compute(Context context, float origin) {
            return origin * add;
        }
    }
}
