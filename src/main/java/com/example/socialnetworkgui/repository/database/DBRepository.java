package com.example.socialnetworkgui.repository.database;

import com.example.socialnetworkgui.domain.Entity;
import com.example.socialnetworkgui.repository.Repository;

public interface DBRepository<ID, E extends Entity<ID>> extends Repository<ID, E> {

    public Iterable<E> getCustomList(String sqlStatement);

}
