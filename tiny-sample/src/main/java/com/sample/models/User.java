package com.sample.models;

import com.tinyweb.persistent.ebean.Model;

import javax.persistence.Entity;

/**
 * User
 * Created by luliru on 6/30/21.
 */
@Entity
public class User extends Model {

    public String username;

    public String avatar;
}
