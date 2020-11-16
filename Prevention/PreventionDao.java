package edu.android.appgame.Prevention;

import java.util.ArrayList;
import java.util.List;

import edu.android.appgame.R;

public class PreventionDao {
    private List<Prevention> preventionList = new ArrayList<>();
    private static PreventionDao instance = null;

    public static PreventionDao getInstance() {
        if (instance == null ){
            instance = new PreventionDao();
        }
        return instance;
    }

    public List<Prevention> getpreventionList() {
        return preventionList;
    }

    private PreventionDao(){
        makeDummyData();
    }

    private void makeDummyData() {
        preventionList.add(new Prevention("치매관련 주요구조", "뇌는 우리의 생각, 판단, 운동, 감각 등을 담당하는 매우 중요한 기관입니다.", R.drawable.brain2));
        preventionList.add(new Prevention("치매관련 현황 ", "범세계적인 고령화로 인해 치매환자 수도 급격히 증가하고 있습니다.", R.drawable.brain3));
        preventionList.add(new Prevention("치매의 정의", "과거에는 치매를 망령, 노망이라고 부르면서 노인이면 당연히 겪게 되는 노화 현상이라고 생각했으나, 최근의 많은 연구를 통해 분명한 뇌 질환으로 인식되고 있습니다.", R.drawable.brain4));
        preventionList.add(new Prevention("치매의 종류", "치매의 원인 중 가장 대표적인 알츠하이머병은 뇌세포의 퇴화로 기억력을 비롯한 여러 인지기능이 점진적으로 저하되며 일상생활의 장애가 초래되는 만성뇌질환입니다. ", R.drawable.brain5));
        preventionList.add(new Prevention("이거 치매 아닌가요?", "나이가 들면서 단어가 예전과 달리 빨리 생각이 나지 않는다거나 약속 등을 깜박하는 경우, 치매가 아닌가 걱정하게 되는 경우가 있습니다. ", R.drawable.brain6));
    } // end makeDummyData()

}
