package ru.otus.units;

public class Unit implements IUnit {
    private IUnit uPrev=null;
    private IUnit uNext=null;

    public IUnit setPrevUnit(IUnit pu) {
        uPrev = pu;
        return pu;
    }

    public IUnit setNextUnit(IUnit nu) {
        uNext = nu;
        if (uNext!=null) {
            uNext.setPrevUnit(this);
        }
        return nu;
    }

    @Override
    public void Process(ITransaction t) {
        t.Log("Process: " + this.getClass().toString() + "\n");
        if (uNext!=null) {
            uNext.Process(t);
        } else {
            onCommit(t);
        }
    }

    @Override
    public void onCommit(ITransaction t) {
        t.Log("Commit: " + this.getClass().toString() + "\n");
        if (uPrev!=null) {
            uPrev.onCommit(t);
        }
    }

    @Override
    public void onRollback(ITransaction t) {
        t.Log("Rollback: " + this.getClass().toString() + "\n");
        if (uPrev!=null) {
            uPrev.onRollback(t);
        }
    }
}
