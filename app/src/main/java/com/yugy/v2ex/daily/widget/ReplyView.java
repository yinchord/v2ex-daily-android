package com.yugy.v2ex.daily.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yugy.v2ex.daily.R;
import com.yugy.v2ex.daily.activity.UserActivity;
import com.yugy.v2ex.daily.model.MemberModel;
import com.yugy.v2ex.daily.model.ReplyModel;
import com.yugy.v2ex.daily.network.AsyncImageGetter;

/**
 * Created by yugy on 14-2-25.
 */
public class ReplyView extends RelativeLayout implements View.OnClickListener{
    public ReplyView(Context context) {
        super(context);
        init();
    }

    public ReplyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ReplyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private SelectorImageView mHead;
    private TextView mName;
    private RelativeTimeTextView mTime;
    private TextView mContent;

    private MemberModel mMember;

    private void init(){
        inflate(getContext(), R.layout.view_reply, this);
        mHead = (SelectorImageView) findViewById(R.id.img_view_reply_head);
        mName = (TextView) findViewById(R.id.txt_view_reply_name);
        mTime = (RelativeTimeTextView) findViewById(R.id.txt_view_reply_time);
        mContent = (TextView) findViewById(R.id.txt_view_reply_content);

        mHead.setOnClickListener(this);
    }

    public void parse(ReplyModel model){
        mName.setText(model.member.username);
        mTime.setReferenceTime(model.created * 1000);
        mContent.setText(Html.fromHtml(model.contentRendered, new AsyncImageGetter(getContext(), mContent), null));
        mContent.setMovementMethod(LinkMovementMethod.getInstance());

        mMember = model.member;

        ImageLoader.getInstance().displayImage(model.member.avatarLarge, mHead);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), UserActivity.class);
        Bundle argument = new Bundle();
        argument.putParcelable("model", mMember);
        intent.putExtra("argument", argument);
        getContext().startActivity(intent);
    }
}
