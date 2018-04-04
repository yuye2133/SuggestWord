package xxx.alphax.suggest;

import java.util.*;

/**
 * Created by user on 2018/4/2.
 */
public class TrieTree {
    protected List<TrieNode> children;

    protected TrieNode root;

    protected String value;

    public TrieTree(String value) {
        root = new TrieNode<String>(value);
    }

    private void recurseChildren(List<TrieNode<String>> list, TrieNode<String> parent) {
        if (null == parent.children) {
            list.add(parent);
        } else {
            for (TrieNode<String> node : parent.children) {
                recurseChildren(list, node);
            }
        }
    }

    public void add(String word, int count) {
        TrieNode currentNode = root;
        for (int i=0; i<word.length(); i++) {
            String tmp_word = word.substring(i, i+1);
            TrieNode child = currentNode.subNode(tmp_word);
            if (child != null) {
                currentNode = child;
            } else {
                currentNode.children.add(new TrieNode<String>(tmp_word));
                currentNode = currentNode.subNode(tmp_word);
            }
        }
        currentNode.isEnd = true;
        currentNode.score += count;
    }

    public void DFSearch(TrieNode<String> parent, String prefix, Map<String, Integer> suggestList) {
        if (parent.children != null) {
            if (parent.isEnd) {
                int score = parent.score;
                suggestList.put(prefix, score);
            }
            for (TrieNode<String> node : parent.children) {
                String value = node.value;
                String word = prefix + value;
                int score = node.score;
                if (node.isEnd) {
                    suggestList.put(word, score);
                }
                DFSearch(node, word, suggestList);
            }
        }
    }

    public List<String> next(String word) {
        TrieNode currentNode = root;
        List<String> suggestList = new ArrayList<String>();
        for (int i=0; i<word.length(); i++) {
            String tmp_word = word.substring(i, i+1);
            currentNode = currentNode.subNode(tmp_word);
            if (currentNode == null) {
                return suggestList;
            }
        }
        Map<String, Integer> result = new HashMap<String, Integer>();
        if (currentNode.children != null) {
            DFSearch(currentNode, word, result);
        }
        List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(
                result.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });

        for (int i=0; i < entryList.size(); i++) {
            Map.Entry<String, Integer> entry = entryList.get(i);
            String suggest_word = entry.getKey();
            suggestList.add(suggest_word);
            if (i >= 9) {
                break;
            }
        }
        return suggestList;
    }
}
