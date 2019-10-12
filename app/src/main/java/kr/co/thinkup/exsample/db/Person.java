package kr.co.thinkup.exsample.db;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 2019-10-04 by yh.Choi
 */
public class Person  {

    private long id;
    private String name;
    private RealmList<Dog> dogs;

    public Person() {
        this.id = 0;
        this.name = "";
        this.dogs = new RealmList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(RealmList<Dog> dogs) {
        this.dogs = dogs;
    }
}
