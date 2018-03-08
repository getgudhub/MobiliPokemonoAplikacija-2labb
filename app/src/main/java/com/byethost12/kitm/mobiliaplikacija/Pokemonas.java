package com.byethost12.kitm.mobiliaplikacija;



public class Pokemonas {
    private int userId;
    private int id;
    private String name;
    private double weight;
    private double height;
    private String cp;
    private String abilities;
    private String type;

    public Pokemonas(int id, String name, String type, String abilities, String cp, double weight, double height, int userId) {
        this.userId = userId;
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.cp = cp;
        this.abilities = abilities;
        this.type = type;
    }

    public Pokemonas() {
    }

    public int getUserId(){return userId;}

    public void setUserId(int userId){this.userId = userId;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getAbilities() {
        return abilities;
    }

    public void setAbilities(String abilities) {
        this.abilities = abilities;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
               return ("Id: " + getId() + "\n" +
                "Name: " + getName() + "\n" +
                "Weight: " + getWeight() + " kg\n" +
                "Height: " + getHeight() + " m\n" +
                "CP: " + getCp() + "\n" +
                "Abilities: " + getAbilities() + "\n" +
                "Type: " + getType()) + "\n" +
                "userId: "+ getUserId();
    }

}
