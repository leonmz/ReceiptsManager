package dao;

import api.ReceiptResponse;
import generated.tables.records.ReceiptsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.RECEIPTS;

public class ReceiptDao {
    DSLContext dsl;
    HashMap<String,List<Integer>> tags;
    List<Integer> Receipts;

    public ReceiptDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
        tags  = new HashMap<>();
        Receipts = new ArrayList<>();
    }

    public int insert(String merchantName, BigDecimal amount) {
        ReceiptsRecord receiptsRecord = dsl
                .insertInto(RECEIPTS, RECEIPTS.MERCHANT, RECEIPTS.AMOUNT)
                .values(merchantName, amount)
                .returning(RECEIPTS.ID)
                .fetchOne();

        checkState(receiptsRecord != null && receiptsRecord.getId() != null, "Insert failed");
        Receipts.add(receiptsRecord.getId());
        return receiptsRecord.getId();
    }
    public void toggleTag(int id,String tag){
        if(!tags.containsKey(tag))
            tags.put(tag,new ArrayList<>());

        List<Integer> Rlist = tags.get(tag);
        if(Rlist.contains(id)) {
            int index = Rlist.indexOf(id);
            Rlist.remove(index);
        }
        else
            Rlist.add(id);

    }
    public List<ReceiptResponse> getAllReceipts() {
        List<ReceiptsRecord> records = dsl.selectFrom(RECEIPTS).fetch();
        ArrayList<ReceiptResponse> responses = new ArrayList<>();
        for (ReceiptsRecord record : records) {
            ArrayList<String> itstag = new ArrayList<>();
            for (String tag : tags.keySet()){
                if(tags.get(tag).contains(record.getId()))
                    itstag.add(tag);
            }
            ReceiptResponse response = new ReceiptResponse(record);
            response.setTags(itstag);
            responses.add(response);
        }
        return responses;
    }

    public List<ReceiptsRecord> getTag(String tag){
        List<ReceiptsRecord> result = null;
        if(tags.containsKey(tag)){
            List<Integer> Rlist = tags.get(tag);
            result = new ArrayList<ReceiptsRecord>();
            List<ReceiptsRecord> temp = dsl.selectFrom(RECEIPTS).fetch();
            for(int id:Rlist)
                for(ReceiptsRecord r:temp)
                    if(id == r.getId())
                        result.add(r);
        }
        return result;

    }
//    public List<ReceiptsRecord> getAllReceipts() {
//        return dsl.selectFrom(RECEIPTS).fetch();
//    }

}
