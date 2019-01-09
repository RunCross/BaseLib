package top.crossrun.base.recycle;

import java.util.ArrayList;
import java.util.List;

public class RecycleList implements RecycleAble ,RecycleRegister{

    List<RecycleAble> recycleAbles;

    public void add(RecycleAble recycleAble) {
        if (recycleAbles == null) {
            recycleAbles = new ArrayList<>();
        }
        recycleAbles.add(recycleAble);
    }

    @Override
    public void recyle() {
        if (recycleAbles != null) {
            for (RecycleAble recycleAble : recycleAbles) {
                if (recycleAble != null) {
                    recycleAble.recyle();
                }
            }
            recycleAbles.clear();
        }
    }

    @Override
    public void recycleRegister(RecycleObserver recycleObserver) {
        recycleObserver.add(this);
    }
}
