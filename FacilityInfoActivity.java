package edu.android.appgame;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class FacilityInfoActivity  extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String[]> facilityList = new ArrayList<>();
    private static final String TAG = "tag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_info);

        fileDatasize();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FacilityAdapter adapter = new FacilityAdapter();
        recyclerView.setAdapter(adapter);


    } // end onCreate(){

    public void fileDatasize() {
        InputStream in = null;
        InputStreamReader reader = null;
        BufferedReader br = null;
        try {
            in = getAssets().open("facility_list.txt");
            reader = new InputStreamReader(in, "UTF-8");
            br = new BufferedReader(reader);

            String line = br.readLine();
            while (line != null) {
                String[] list = line.split(",");
                facilityList.add(list);
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.ViewHolder> {

        @NonNull
        @Override
        public FacilityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
            View view = getLayoutInflater().inflate(R.layout.activity_facility_item, viewGroup, false);
            ViewHolder itemView = new ViewHolder(view);
            return itemView;
        }

        @Override
        public void onBindViewHolder(@NonNull FacilityAdapter.ViewHolder viewHolder, final int i) {
            viewHolder.textCenterName.setText(facilityList.get(i)[0]);
            viewHolder.textCenterTel.setText(facilityList.get(i)[3]);
            viewHolder.textCenterAddr.setText(facilityList.get(i)[2]);

            if (facilityList.get(i)[0].equals("경기도")) {
                viewHolder.imageFacility.setImageResource(R.drawable.valley);
            }
            if (facilityList.get(i)[0].equals("서울특별시")) {
                viewHolder.imageFacility.setImageResource(R.drawable.cityscape);
            }
            if (facilityList.get(i)[0].equals("충청도")) {
                viewHolder.imageFacility.setImageResource(R.drawable.indonesia);
            }
            if (facilityList.get(i)[0].equals("강원도")) {
                viewHolder.imageFacility.setImageResource(R.drawable.river);
            }
            if (facilityList.get(i)[0].equals("경상도")) {
                viewHolder.imageFacility.setImageResource(R.drawable.bridge);
            }
            if (facilityList.get(i)[0].equals("제주특별자치도")) {
                viewHolder.imageFacility.setImageResource(R.drawable.lemonade);
            }
            if (facilityList.get(i)[0].equals("전라도")) {
                viewHolder.imageFacility.setImageResource(R.drawable.sashimi);
            }
            if (facilityList.get(i)[0].equals("중앙치매센터")) {
                viewHolder.imageFacility.setImageResource(R.drawable.enterprise);
            }
        }


        @Override
        public int getItemCount() {
            return facilityList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView textCenterName, textCenterAddr, textCenterTel;
            private ImageView imageFacility;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textCenterAddr = itemView.findViewById(R.id.textCenterAddr);
                textCenterName = itemView.findViewById(R.id.textCenterName);
                textCenterTel = itemView.findViewById(R.id.textCenterTel);
                imageFacility = itemView.findViewById(R.id.imageFacility);
            }
        } // end class ViewHolder
    }

} // end class FacilityInfoActivity
