import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

class Loops implements GeneralLoops {
    private List<Integer> upperLimits;
    private List<Integer> lowerLimits;
    private ArrayList<Integer> state;
    private ArrayList<ArrayList<Integer>> result;

    @Override
    public void setUpperLimits(List<Integer> limits) {
        upperLimits = new ArrayList<>(limits);
    }

    @Override
    public void setLowerLimits(List<Integer> limits) {
        lowerLimits = new ArrayList<>(limits);
    }

    @Override
    public List<List<Integer>> getResult() {
        if (lowerLimits == null) {
            if (upperLimits == null) {
                lowerLimits = Arrays.asList(0);
                upperLimits = Arrays.asList(0);
            }
            else {
                lowerLimits = new ArrayList<>();
                for (int i = 0; i < upperLimits.size(); i++) {
                    lowerLimits.add(0);
                }
            }
        }
        else if (upperLimits == null) {
            upperLimits = new ArrayList<>();
            for (int i = 0; i < lowerLimits.size(); i++) {
                upperLimits.add(0);
            }
        }
        else {
            while (lowerLimits.size() < upperLimits.size()) {
                lowerLimits.add(0);
            }
            while (lowerLimits.size() > upperLimits.size()) {
                upperLimits.add(0);
            }
        }

        state = new ArrayList<>();
        result = new ArrayList<>();

        getResultRecursion(state, result);

        return List.copyOf(result);
    }

    private void getResultRecursion(ArrayList<Integer> state, ArrayList<ArrayList<Integer>> result) {
        int depth = state.size();
        for (int j = lowerLimits.get(depth); j <= upperLimits.get(depth); j++) {
            ArrayList<Integer> next = new ArrayList<>(state);
            next.add(j);
            
            if (depth == lowerLimits.size() - 1) {
                result.add(next);
            }
            else {
                getResultRecursion(next, result);
            }
        }
    }
}