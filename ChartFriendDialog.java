package edu.android.appgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChartFriendDialog extends DialogFragment {

    public interface  ChartFriendCallback{
        void getFriendId(String id);
    }

    private ChartFriendCallback callback;

    private static final String TAG = "chart_tag";

    private FirebaseDatabase mDatabase;
    private DatabaseReference  mReference;

    private ListView listFriends;
    private ArrayAdapter<String> adapter;
    List<String> arrayF = new ArrayList<>();
    private Context context;

    private TextView textFriends;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof  ChartFriendCallback){
            callback = (ChartFriendCallback) context;
            this.context = context;
        } else {
            throw new AssertionError(context + "는 ChartFriendCallback을 반드시 구현해야 합니다");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.activity_chart_friend, null);
        builder.setView(view);


        listFriends = view.findViewById(R.id.listFriends);
        textFriends = view.findViewById(R.id.textFriends);

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("Member");

        adapter = new ArrayAdapter<>((Context) callback, android.R.layout.simple_expandable_list_item_1, arrayF);
        listFriends.setAdapter(adapter);
        mReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.i(TAG,"11" );
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Log.i(TAG,"22");
                        String name = snapshot.getKey();
                        arrayF.add(name);
                        Log.i(TAG,"??" + name);
                        Log.i(TAG, " arrayF" + arrayF.toString());
                    }
                    adapter.notifyDataSetChanged();
                    mReference.removeEventListener(this);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.i(TAG, "ValueEventListener cancelled");
                }
            });

        listFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // 선택된 리스트의 친구 아이디를 보냄
                sendMainFriendId(position);
            }
        });
        return  builder.create();
    }

    private void sendMainFriendId(int position) {
        String id = arrayF.get(position);
        callback.getFriendId(id);
        dismiss();
    }

} // end class ChartFriendDialogFragment
