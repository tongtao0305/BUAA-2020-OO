package GCSimulation;

import java.util.List;

public class JVMHeap<T extends Comparable<T>>
        extends MyHeap<T> {

    JVMHeap(int size) {
        super(size);
    }

    JVMHeap(int size, double factor) {
        super(size, factor);
    }

    JVMHeap(List<T> list) {
        super(list);
    }

    /*@ public normal_behavior
      @ requires objectId != null
      @ ensures (\forall int i; 0 <= i && i < objectId.length;
                (\exists int j; 0 <= j && j < elementData.length; elementData[j].id == objectId[i] ==> elementData[j].getReferenced() == false)
                )
      @ assignable elementData;·
      @*/
    public void setUnreferenceId(List<Integer> objectId) {
        for (int id : objectId) {
            for (int index = 0; index < this.getSize(); index++) {
                MyObject mo = (MyObject) this.getElementData()[index];
                if (mo.getId() == id) {
                    mo.setReferenced(false);
                    this.setElementData(index, mo);
                }
            }
        }
    }

    /*@ public normal_behavior
      @ ensures (\forall int i; 0 <= i && i < elementData.length; elementData[i].getReferenced() == true)
      @ assignable elementData;
      @*/
    public void removeUnreference() {
        Object[] oldObject = getElementData().clone();
        // 此处新增oldSize保存原来的大小，否则在this.clear之后this.size变为0循环就不起作用了
        int oldSize = this.getSize();
        this.clear();
        int newSize = 0;
        // 此处将循环条件中的getSize改为oldSize，理由见上一条注释
        for (int i = 0; i < oldSize; i++) {
            MyObject mo = (MyObject) oldObject[i];
            if (mo.getReferenced() == true) {
                this.add(mo);
                newSize++;
            }
        }
        this.setSize(newSize);
    }

}
