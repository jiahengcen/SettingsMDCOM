package com.sty.setting.md.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.sty.setting.md.R;

import java.util.List;

/**
 * Created by Steven.T on 2017/10/10/0010.
 */

public class SettingsHeaderPrefActivity extends AppCompatPreferenceActivity {
    List<Header> mCopyHeaders;

    protected static boolean mIsSinglePane = false;

    private HeaderAdapter headerAdapter;

    private static class HeaderAdapter extends ArrayAdapter<Header>{
        private static class HeaderViewHolder{
            ImageView icon;
            TextView title;
            TextView summary;
            ImageView arrow;
        }

        private int selectItem = -1;
        private LayoutInflater mInflater;

        public HeaderAdapter(Context context, List<Header> objects) {
            super(context, 0, objects);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void setSelectItem(int selectItem){
            this.selectItem = selectItem;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HeaderViewHolder holder;
            View view;
            if(convertView == null){
                view = mInflater.inflate(R.layout.preference_header_item_layout, parent, false);
                holder = new HeaderViewHolder();
                holder.icon = (ImageView) view.findViewById(R.id.icon);
                holder.title = (TextView) view.findViewById(R.id.title);
                holder.summary = (TextView) view.findViewById(R.id.summary);
                holder.arrow = (ImageView) view.findViewById(R.id.right_arrow);
                view.setTag(holder);
            }else{
                view = convertView;
                holder = (HeaderViewHolder) view.getTag(); //returns this view's tag
            }

            //All view fields must be updated every time, because the view may be recycled
            Header header = getItem(position);
            holder.icon.setImageResource(header.iconRes);
            CharSequence tt = header.getTitle(getContext().getResources());
            holder.title.setText(tt);
            CharSequence summary = header.getSummary(getContext().getResources());
            if(!TextUtils.isEmpty(summary)){
                holder.summary.setVisibility(View.VISIBLE);
                holder.summary.setText(summary);
            }else{
                holder.summary.setVisibility(View.GONE);
            }

            holder.arrow.setVisibility(View.VISIBLE);
            view.setBackgroundColor(Color.TRANSPARENT);

//            if(position == selectItem){
//                view.setBackgroundColor(getContext().getResources().getColor(R.color.textEdit_hint));
//                holder.arrow.setVisibility(View.VISIBLE);
//            }else{
//                view.setBackgroundColor(Color.TRANSPARENT);
//                if(!mIsSinglePane){
//                    holder.arrow.setVisibility(View.INVISIBLE);
//                }
//            }
            return view;
        }
    }

    @Override
    public void onHeaderClick(Header header, int position) {
        super.onHeaderClick(header, position);
        headerAdapter.setSelectItem(position);
    }

    @Override
    public void loadHeadersFromResource(int resid, List<Header> target) {
        super.loadHeadersFromResource(resid, target);
        mCopyHeaders = target;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsSinglePane = onIsHidingHeaders() || !onIsMultiPane();
        headerAdapter = new HeaderAdapter(this, mCopyHeaders);
        if(!(mCopyHeaders == null || mCopyHeaders.size() == 0)){
            setListAdapter(headerAdapter);
            //D800默认获取焦点， A920则不设置焦点
            getListView().setFocusable(!mIsSinglePane);
            if(!mIsSinglePane){
                headerAdapter.setSelectItem(0);
            }
        }
        if(mIsSinglePane){
            getListView().setDivider(getResources().getDrawable(R.drawable.list_divider));
            getListView().setDividerHeight(1);
//            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getListView().getLayoutParams();
//            lp.setMargins(0, 25, 0, 0);
//            getListView().setLayoutParams(lp);
            ((ViewGroup) getListView().getParent()).setBackgroundColor(getResources().getColor(
                    R.color.setting_background_color2));
            getListView().setBackgroundColor(getResources().getColor(
                    R.color.setting_background_color2));
        }
    }
}
