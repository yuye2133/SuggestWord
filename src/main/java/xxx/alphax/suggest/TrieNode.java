package xxx.alphax.suggest;

import java.util.*;

/**
 * Created by user on 2018/4/2.
 */
public class TrieNode<T> {

    public T value;

    public boolean isEnd;

    public List<TrieNode<T>> children;

    public int score;

    protected TrieNode<T> parent;

    public TrieNode(T value) {
        this.value = value;
        this.children = new LinkedList<TrieNode<T>>();
        isEnd = false;
        score = 0;
    }

    public TrieNode findNode(T c){
        for(TrieNode node : children){
            if(node.value == c){
                return node;
            }
        }
        return null;
    }

    public T getValue() {
        return value;
    }

    public TrieNode<T> add(T value) {
        if (null == this.children) {
            this.children = new ArrayList<TrieNode<T>>();
        }
        TrieNode<T> child = new TrieNode<T>(value);
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    private void setParent(TrieNode<T> parent) {
        this.parent = parent;
    }

    public TrieNode<T> subNode(String c){
        if(this.children != null){
            if (children.size() > 0) {
                for (TrieNode<T> eachChild : children) {
                    if (eachChild.value.equals(c)) {
                        return eachChild;
                    }
                }
            }
        }
        return null;
    }

    public String toString() {
        return value.toString();
    }

}
