import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.HashMap;

public class Path implements Iterable<Integer> {
    //@ public instance model non_null int[] pNodes;

    private final ArrayList<Integer> nodes;
    private final HashSet<Integer> distinct;// keep a unique set for nodes

    public Path(final int... nodeList) {
        nodes = new ArrayList<>(nodeList.length);
        distinct = new HashSet<>(nodeList.length);
        for (final int x : nodeList) {
            nodes.add(x);
            distinct.add(x);
        }
        System.out.println(this.toString() + " Generated!");
    }

    public Iterator<Integer> iterator() {
        return nodes.iterator();
    }

    @Override
    public int hashCode() {
        return nodes.hashCode();
    }


    //@ ensures \result == pNodes.length;
    public /*@pure@*/ int size() {
        return nodes.size();
    }

    /*@ public normal_behavior
      @ requires index >= 0 && index < size();
      @ assignable \nothing;
      @ ensures \result == pNodes[index];
      @
      @ also
      @ public exceptional_behavior
      @ requires index < 0 || index >= size();
      @ assignable \nothing;
      @ [1] //TODO
      @*/
    public int getNode(final int index) throws IndexOutOfBoundsException {
        if (index >= 0 && index < size()) {
            return nodes.get(index);
        } else {
            System.err.println("Index not available in getNode(int index) !");
            throw new IndexOutOfBoundsException();
        }
    }

    /*@ [2] //TODO
      @*/
    public /*@pure@*/ boolean containsNode(final int nodeId) {
        return distinct.contains(nodeId);
    }

    /*@ ensures (\exists int[] arr;
      @        (\forall int i, j; 0 <= i && i < j && j < arr.length; arr[i] != arr[j]);
      @        (\forall int i; 0 <= i && i < arr.length;this.containsNode(arr[i]))
      @        && (\forall int node; this.containsNode(node);
      @                  (\exists int j; 0 <= j && j < arr.length; arr[j] == node))
      @        && (\result == arr.length));
      @*/
    public /*@pure@*/ int getDistinctNodeCount() {
        return distinct.size();
    }

    /*@ public normal_behavior
      @ requires obj != null && obj instanceof Path;
      @ assignable \nothing;
      @ ensures \result == (((Path) obj).pNodes.length == pNodes.length) && [3] //TODO
      @ also
      @ public normal_behavior
      @ requires obj == null || !(obj instanceof Path);
      @ assignable \nothing;
      @ ensures \result == false;
      @*/
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Path)) {
            return false;
        }
        Path objPath = (Path) obj;
        if (objPath.hashCode() != this.hashCode()) {
            return false;
        }
        if (objPath.size() != this.size() ||
                objPath.getDistinctNodeCount() != this.getDistinctNodeCount()) {
            return false;
        }
        Iterator<Integer> iterator = objPath.iterator();
        Iterator<Integer> myIterator = this.iterator();
        while (iterator.hasNext()) {
            if (!(myIterator.hasNext())) {
                return false;
            }
            if ([4]){ //TODO
                return false;
            }
        }
        return true;
    }

    //@ ensures \result == (pNodes.length >= 2);
    public /*@pure@*/ boolean isValid() {
        return (size() >= 2);
    }

    /*@ public normal_behavior
      @ \assignable nothing;
      @ requires (\exists int i,j; 0 <= i && i < j &&  j < pNodes.length; pNodes[i]=pNodes[j]);
      @ ensures \result instanceof Path &&
      @           (\exists int i; 0 <= i && i < pNodes.length-1;
      @               (\forall int j; 0 <= j && j < \result.pNodes.length;
      @                   i + j < pNodes.length && \result.pNodes[j] == pNodes[j+i]))
      @                       && [5] //TODO
      @
      @ also
      @ public normal_behavior
      @ \assignable nothing;
      @ [6] (请使用一条\forall或\exists实现，量词只允许出现一次) //TODO
      @ (\forall int i; 0 <= i && i < pNodes.length; containsNode(i));
      @ ensures \result == null
      @*/
    public Path loopPath() {
        if ([7]){ // 请使用类中的方法和运算符实现判断路径中是否存在环 //TODO
            //size() == getDistinctNodeCount()
            return null;
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        int mapIndex = 0;
        int startIndex = 0;
        int endIndex = 0;
        for (final int x : nodes) {
            endIndex++;
            if (!map.containsKey(x)) {
            [8] //TODO
                //map.put(x, mapIndex++);
            } else {
                startIndex = (int) map.get(x);
                break;
            }
        }
        int[] loopList = new int[endIndex - startIndex];
        for (int i = startIndex; i < endIndex; i++) {
            loopList[i - startIndex] = (int) nodes.get(i);
        }
        Path loop = new Path(loopList);
        return loop;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("Path : ");
        Iterator<Integer> iterator = this.iterator();
        while (iterator.hasNext()) {
            res.append(iterator.next().toString());
            if (iterator.hasNext()) {
                res.append("->");
            }
        }
        return res.toString();
    }
}
