import java.util.ArrayList;

public class Word {
    private ArrayList<Integer> listLine = new ArrayList<>();
    private ArrayList<Integer> listRow = new ArrayList<>();
    
    public void addPos(int line, int row) {
        listLine.add(line);
        listRow.add(row);
    }
    
    public int getLength() {
        return listLine.size();
    }
    
    public void print() {
        for (int i = 0; i < listLine.size(); i++) {
            System.out.println("\t(" + listLine.get(i) + ", "
                    + listRow.get(i) + ")");
        }
    }
}
