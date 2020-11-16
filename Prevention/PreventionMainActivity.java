package edu.android.appgame.Prevention;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import edu.android.appgame.R;

public class PreventionMainActivity extends AppCompatActivity {
    public static final String KEY_PREVENTION_INDEX = "prevention_index";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private PreventionDao dao = PreventionDao.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevention);

        recyclerView = findViewById(R.id.pRecyclerview);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        PreventionAdapter adpater = new PreventionAdapter();

        recyclerView.setAdapter(adpater);
    }
    public class PreventionAdapter extends RecyclerView.Adapter<PreventionAdapter.ViewHolder>{

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= getLayoutInflater().inflate(R.layout.prevention_item,parent,false);
            ViewHolder vh= new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
            Prevention PreDetail = dao.getpreventionList().get(position);

            viewHolder.imageGame.setImageResource(PreDetail.getPhotoId());
            viewHolder.itemName.setText(PreDetail.getpName());
            viewHolder.itemDesc.setText(PreDetail.getpDesc());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPreventionDetail(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return dao.getpreventionList().size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageGame;
            private TextView itemName, itemDesc;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imageGame = itemView.findViewById(R.id.imageGame);
                itemName = itemView.findViewById(R.id.itemName);
                itemDesc = itemView.findViewById(R.id.itemDesc);
            }
        }
    }

    private void showPreventionDetail(int position) {
        Intent intent = new Intent(this, PreventionDetail.class);
        intent.putExtra(KEY_PREVENTION_INDEX, position);
        startActivity(intent);
    } // end showGameDetail()

} // end class GameActivity

