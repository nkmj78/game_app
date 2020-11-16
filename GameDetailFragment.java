package edu.android.appgame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import edu.android.appgame.game.GameDao;
import edu.android.appgame.game.Game;


public class GameDetailFragment extends Fragment {
    private static final String ARG_GAME_INDEX = "arg_game_index";
    private GameDao dao = GameDao.getInstance(this.getContext());
   private ImageView gameDetailImage;
   private TextView gameDetailName, gameDetailDesc;

   public GameDetailFragment() {

   }

   public static GameDetailFragment newFragment(int index) {
       GameDetailFragment fragment = new GameDetailFragment();

       Bundle args = new Bundle();
       args.putInt(ARG_GAME_INDEX, index);
       fragment.setArguments(args);

       return fragment;
   }// end newFragment()


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_detail, container, false);
        gameDetailImage = view.findViewById(R.id.gameDetailImage);
        gameDetailName = view.findViewById(R.id.gameDetailName);
        gameDetailDesc = view.findViewById(R.id.gameDetailDesc);

        Bundle args = getArguments();
        int position = args.getInt(ARG_GAME_INDEX);

        Game gameDetail = dao.getGameList().get(position);

        gameDetailImage.setImageResource(gameDetail.getPhotoId());
        gameDetailName.setText(gameDetail.getgName());
        gameDetailDesc.setText(gameDetail.getgDesc());

        return  view;
    }// end onCreateView()
} // end class GameDetailFragment





























