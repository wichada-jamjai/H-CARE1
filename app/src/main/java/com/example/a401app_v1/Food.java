package com.example.a401app_v1;

public class Food {

    private String foodName;
    private int foodCalories;
    private float foodFat, foodProtein;

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setFoodCalories(int foodCalories) {
        this.foodCalories = foodCalories;
    }

    public void setFoodFat(float foodFat) {
        this.foodFat = foodFat;
    }

    public void setFoodProtein(float foodProtein) {
        this.foodProtein = foodProtein;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getFoodCalories() {
        return foodCalories;
    }

    public float getFoodFat() {
        return foodFat;
    }

    public float getFoodProtein() {
        return foodProtein;
    }
}
