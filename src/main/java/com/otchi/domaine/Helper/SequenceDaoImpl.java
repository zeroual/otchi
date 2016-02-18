package com.otchi.domaine.Helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * Created by MJR2 on 2/18/2016.
 */

@Repository
class SequenceDaoImpl implements SequenceDao {

    @Autowired
    private MongoOperations mongoOperation;

    @Override
    public Long getNextSequenceId(String key) throws SequenceException {
       // Get Sequence Id
        Query query = new Query(Criteria.where("_id").is(key));

        // Increase sequence id by 1
        Update update = new Update();
        update.inc("seq",1);

        // Return new increased id
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        // This is the magic happened
        SequenceId seqId = mongoOperation.findAndModify(query, update, options, SequenceId.class);

        // If no Id throws SequenceException
        // Optional, just a way to tell the user when the sequence id is failed to generate.
        if(seqId == null)
        throw new SequenceException("Unable to get the sequence id for the key: " + key);

        return seqId.getSeq();
    }
}
