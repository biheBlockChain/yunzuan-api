package one.bihe.yunzuan.entry;

import java.util.ArrayList;
import java.util.List;

public class ApiTransactionVo {
    private boolean hasNext;
    private List<TxVo> transactionRecords = new ArrayList<>();

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<TxVo> getTransactionRecords() {
        return transactionRecords;
    }

    public void setTransactionRecords(List<TxVo> transactionRecords) {
        this.transactionRecords = transactionRecords;
    }
}
