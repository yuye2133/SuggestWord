package xxx.alphax.suggest;

import java.io.*;
import java.util.*;

/**
 * Created by user on 2018/4/2.
 */
public class SuggestWord {

    private String sugest_word_file;

    private TrieTree sugestWordTree;

    private Map<String, Integer> load_sugest_word() {
        Map<String, Integer> word_dict = new HashMap<String, Integer>();
        try {
            FileInputStream inputStream = new FileInputStream(sugest_word_file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.replace("\n", "");
                String[] data = line.split("\t");
                if (data.length != 2) {
                    continue;
                }
                String word = data[0];
                int count = Integer.parseInt(data[1]);
                word_dict.put(word, count);
            }
            inputStream.close();
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return word_dict;
    }

    public SuggestWord() {
        Properties prop = new Properties();
        try {
            InputStream inputStream = SuggestWord.class.getResourceAsStream("/application.properties");
            prop.load(inputStream);
            sugestWordTree = new TrieTree("ROOT");
            sugest_word_file = prop.getProperty("sugestfile");
            Map<String, Integer> word_dict = load_sugest_word();
            for (String word : word_dict.keySet()) {
                int count = word_dict.get(word);
                sugestWordTree.add(word, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getNextSuggestWord(String word) {
        return sugestWordTree.next(word);
    }

    public static void main(String[] args) {

        SuggestWord suggestWord = new SuggestWord();

        System.out.println("please input: ");

        Scanner sc = new Scanner(System.in);

        while (true) {
            String line = sc.nextLine();
            long start = System.currentTimeMillis();
            System.out.println(suggestWord.getNextSuggestWord(line));
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        }

    }
}
