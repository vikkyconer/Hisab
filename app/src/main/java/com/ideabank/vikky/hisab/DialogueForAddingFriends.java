package com.ideabank.vikky.hisab;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by vikky on 7/7/15.
 */
public class DialogueForAddingFriends extends DialogFragment implements View.OnClickListener {
    View view;
    EditText inputName;
    Map<String, String> placeData;
    BehaviorSubject<Map<String, String>> name = BehaviorSubject.create();
    RelativeLayout cancel, ok;

    static DialogueForAddingFriends newInstance() {
        Log.i("DialogueForAddFriendss", "newInstance called");
        DialogueForAddingFriends dialogue = new DialogueForAddingFriends();
        return dialogue;
    }

    public Observable<Map<String, String>> inputPlaceName() {
        Log.i("DialogueForAddFriendss", "Observable called");
        return name.asObservable();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialogue_for_adding_friends, container, false);
        ok = (RelativeLayout) view.findViewById(R.id.ok);
        cancel = (RelativeLayout) view.findViewById(R.id.cancel);
        placeData = new HashMap<>();
        inputName = (EditText) view.findViewById(R.id.enter_name);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Log.i("DialogueForAddFriendss", "onCreateView");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("DialogueForAddFriendss", "onActivityCreated called");
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ok) {
            if (!isValid()) {
                return;
            }
            placeData.put("friendName", String.valueOf(inputName.getText()));
            name.onNext(placeData);
            dismiss();
        } else if (v.getId() == R.id.cancel) {
            Log.i("DialogueForAddFriendss", "in else");
            dismiss();
        }
    }

    private boolean isValid() {
        String msg = "";
        if (inputName.getText().length() == 0) {
            Log.i("DialogueForAddFriendss", "in isValid");
            msg = "please specify place name";
        }
        if (msg.length() != 0) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
        }
        return msg.length() == 0;
    }
}
