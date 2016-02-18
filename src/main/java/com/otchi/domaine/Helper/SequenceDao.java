package com.otchi.domaine.Helper;

/**
 * Created by MJR2 on 2/18/2016.
 */
public interface SequenceDao {
    Long getNextSequenceId(String key) throws SequenceException;
}
