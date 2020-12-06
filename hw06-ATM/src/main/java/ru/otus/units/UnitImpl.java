package ru.otus.units;

public class UnitImpl implements Unit {
    private Unit uPrev=null;
    private Unit uNext=null;

    public Unit setPrevUnit(Unit pu) {
        uPrev = pu;
        return pu;
    }

    public Unit setNextUnit(Unit nu) {
        uNext = nu;
        if (uNext!=null) {
            uNext.setPrevUnit(this);
        }
        return nu;
    }

    @Override
    public void Process(Context t) {
        t.Log("Process: " + this.getClass().toString() + "\n");
        if (uNext!=null) {
            uNext.Process(t);
        } else {
            onCommit(t);
        }
    }

    @Override
    public void onCommit(Context t) {
        t.Log("Commit: " + this.getClass().toString() + "\n");
        if (uPrev!=null) {
            uPrev.onCommit(t);
        }
    }

    @Override
    public void onRollback(Context t) {
        t.Log("Rollback: " + this.getClass().toString() + "\n");
        if (uPrev!=null) {
            uPrev.onRollback(t);
        }
    }
}
