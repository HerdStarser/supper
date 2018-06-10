package com.restful.model.common;

import java.util.List;

public class TestModel {

    private String id;

    private Integer age;

    private List<TestModel> modelList;

    public TestModel(String id, Integer age, List<TestModel> modelList) {
        this.id = id;
        this.age = age;
        this.modelList = modelList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<TestModel> getModelList() {
        return modelList;
    }

    public void setModelList(List<TestModel> modelList) {
        this.modelList = modelList;
    }

    @Override
    public String toString() {
        return "TestModel{" +
                "id='" + id + '\'' +
                ", age=" + age +
                ", modelList=" + modelList +
                '}';
    }
}
