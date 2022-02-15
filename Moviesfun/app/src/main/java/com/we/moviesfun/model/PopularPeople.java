package com.we.moviesfun.model;

import com.google.gson.annotations.SerializedName;

public class PopularPeople {

    @SerializedName("id")
    private Integer id;
    @SerializedName("profile_path")
    private String proflePath;

    @SerializedName("name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProflePath() {
        return ("https://image.tmdb.org/t/p/w500"+proflePath);
    }

    public void setProflePath(String proflePath) {
        this.proflePath = proflePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PopularPeople(Integer id, String proflePath, String name) {
        this.id = id;
        this.proflePath = proflePath;
        this.name = name;
    }
}
