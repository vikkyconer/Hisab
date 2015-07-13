package com.example.vikky.hisab;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
    Button buttonYes, buttonNo;

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
        buttonYes = (Button) view.findViewById(R.id.yes);
        buttonNo = (Button) view.findViewById(R.id.no);
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
        buttonYes.setOnClickListener(this);
        buttonNo.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.yes) {
            if (!isValid()) {
                return;
            }
            placeData.put("friendName", String.valueOf(inputName.getText()));
            name.onNext(placeData);
            dismiss();
        } else if (v.getId() == R.id.no) {
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
