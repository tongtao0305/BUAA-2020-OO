import java.util.TreeMap;
import java.util.TreeSet;

public class Group {
    private int total = 0;
    private TreeMap<String, Word> list = new TreeMap<>();
    
    public void setGroup(String name, int line, int row) {
        if (list.containsKey(name)) {
            list.get(name).addPos(line, row);
        } else {
            Word word = new Word();
            word.addPos(line, row);
            list.put(name, word);
        }
        total++;
    }
    
    public TreeSet<String> getMap() {
        return new TreeSet<>(list.keySet());
    }
    
    public void print(String str) {
        int num = list.get(str).getLength();
        String result = String.format("%.2f",(double)num * 100 / (double)total);
        System.out.println(str + " " + num + " " + result + "%");
        list.get(str).print();
    }
}
