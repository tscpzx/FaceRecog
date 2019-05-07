package com.cjw.library.view.dialog;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjw.library.R;
import com.cjw.library.utils.Util;


public class ContentDialog {
	public static final class Builder {

		private Context context;
		private int height = 450, width = 550;
		private View view;
		private TextView mTvTitle;
		private TextView mTvContent;
		private RelativeLayout mRlContent;
		private View.OnClickListener cancelListener;
		private View.OnClickListener okListener;
		private final RelativeLayout.LayoutParams mParams;
		private boolean cancelTouchOut = true;
		private boolean backCancelable = true;

		public Builder(Context context) {
			this.context = context;
			view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_common, null);
			mTvTitle = (TextView) view.findViewById(R.id.title);
			mRlContent = (RelativeLayout) view.findViewById(R.id.content);

			mTvContent = new TextView(context);
			mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			mTvContent.setTextColor(Util.getColor(context, R.color.tv_colorPrimary));
			mTvContent.setMaxLines(4);
			mTvContent.setGravity(Gravity.CENTER);
			mRlContent.addView(mTvContent);

			mParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			//			mParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		}

		public Builder contentView(int resId) {
			View contentView = LayoutInflater.from(context).inflate(resId, null);
			contentView.setLayoutParams(mParams);
			mRlContent.removeAllViews();
			mRlContent.addView(contentView);
			return this;
		}

		public Builder contentView(View contentView) {
			contentView.setLayoutParams(mParams);
			mRlContent.removeAllViews();
			mRlContent.addView(contentView);
			return this;
		}

		public Builder height(int val) {
			height = Util.dip2px(context, val);
			return this;
		}

		public Builder width(int val) {
			width = Util.dip2px(context, val);
			return this;
		}

		public Builder addViewOnclick(int viewRes, View.OnClickListener listener) {
			view.findViewById(viewRes).setOnClickListener(listener);
			return this;
		}

		public Builder setTitle(String title) {
			mTvTitle.setText(title);
			return this;
		}

		public Builder setContent(String content) {
			mTvContent.setText(content);
			return this;
		}

		public Builder setCancelListener(View.OnClickListener listener) {
			this.cancelListener = listener;
			return this;
		}

		public Builder setOkListener(View.OnClickListener listener) {
			this.okListener = listener;
			return this;
		}

		public Builder setOkListener(String btName, View.OnClickListener listener) {
			((TextView) view.findViewById(R.id.ok)).setText(btName);
			this.okListener = listener;
			return this;
		}

		public Builder setSingleButton() {
			view.findViewById(R.id.cancel).setVisibility(View.GONE);
			return this;
		}

		public Builder isTouchOutCancel(boolean val) {
			cancelTouchOut = val;
			return this;
		}

		public Builder isBackCancelable(boolean val) {
			backCancelable = val;
			return this;
		}

		public BaseCustomDialog build() {
			final BaseCustomDialog dialog = new BaseCustomDialog.Builder(context).height(height).width(width).isBackCancelable(backCancelable).isTouchOutCancel(cancelTouchOut).view(view).addViewOnclick(R.id.cancel, cancelListener).addViewOnclick(R.id.ok, okListener).build();

			if (cancelListener == null) {
				cancelListener = new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						dialog.dismiss();
					}
				};
				view.findViewById(R.id.cancel).setOnClickListener(cancelListener);
			}
			if (okListener == null) {
				okListener = new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						dialog.dismiss();
					}
				};
				view.findViewById(R.id.ok).setOnClickListener(okListener);
			}
			return dialog;
		}
	}
}
