package com.tmt.pos.mypos.entities;

import java.io.Serializable;

public interface BaseEntity<ID extends Serializable> {

    ID getId();
}

