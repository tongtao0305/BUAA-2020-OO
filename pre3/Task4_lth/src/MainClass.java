import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {
    
    public static void main(String[] args) throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        StringBuilder article = new StringBuilder();
        ArrayList<Integer> linest = new ArrayList<>();
        int linepos = 0;
        while (reader.ready()) {
            linest.add(linepos);
            String now = reader.readLine();
            linepos += now.length();
            if (now.endsWith("-")) {
                article.append(now.substring(0, now.length() - 1));
                linepos--;
            } else {
                article.append(now + " ");
                linepos++;
            }
        };
        Pattern word = Pattern.compile("[a-zA-z-]+");
        Matcher find = word.matcher(article);
        Group group = new Group();
        while (find.find()) {
            int begin = find.start();
            String now = article.substring(begin, find.end()).toLowerCase();
            int row = 0;
            int line = linest.size() - 1;
            for (; line >= 0; line--) {
                if (begin >= linest.get(line)) {
                    row = begin - linest.get(line) + 1;
                    break;
                }
            }
            group.setGroup(now, line + 1, row);
        }
        for (String index : group.getMap()) {
            group.print(index);
        }
    }
}
