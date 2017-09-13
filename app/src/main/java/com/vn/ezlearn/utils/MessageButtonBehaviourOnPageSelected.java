package com.vn.ezlearn.utils;

import android.util.SparseArray;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.vn.ezlearn.R;
import com.vn.ezlearn.adapter.SlidesAdapter;
import com.vn.ezlearn.fragment.SlideFragment;

import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.listeners.IPageSelectedListener;

import static com.vn.ezlearn.fragment.SlideFragment.isNotNullOrEmpty;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 13/09/2017.
 */

public class MessageButtonBehaviourOnPageSelected implements IPageSelectedListener {
    private Button messageButton;
    private SlidesAdapter adapter;
    private SparseArray<MessageButtonBehaviour> messageButtonBehaviours;

    public MessageButtonBehaviourOnPageSelected(Button messageButton, SlidesAdapter adapter, SparseArray<MessageButtonBehaviour> messageButtonBehaviours) {
        this.messageButton = messageButton;
        this.adapter = adapter;
        this.messageButtonBehaviours = messageButtonBehaviours;
    }

    @Override
    public void pageSelected(int position) {
        final SlideFragment slideFragment = adapter.getItem(position);

        if (checkIfMessageButtonHasBehaviour(position)) {
            showMessageButton(slideFragment);
            messageButton.setText(messageButtonBehaviours.get(position).getMessageButtonText());
            messageButton.setOnClickListener(messageButtonBehaviours.get(position).getClickListener());
        } else if (messageButton.getVisibility() != View.INVISIBLE) {
            messageButton.startAnimation(AnimationUtils.loadAnimation(slideFragment.getContext(), R.anim.fade_out));
            messageButton.setVisibility(View.INVISIBLE);
        }
    }

    private boolean checkIfMessageButtonHasBehaviour(int position) {
        return messageButtonBehaviours.get(position) != null && isNotNullOrEmpty(messageButtonBehaviours.get(position).getMessageButtonText());
    }

    private void showMessageButton(final SlideFragment fragment) {
        if (messageButton.getVisibility() != View.VISIBLE) {
            messageButton.setVisibility(View.VISIBLE);
            if (fragment.getActivity() != null) {
                messageButton.startAnimation(AnimationUtils.loadAnimation(fragment.getActivity(), R.anim.fade_in));

            }
        }
    }
}