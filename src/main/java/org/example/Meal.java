package org.example;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="MEAL")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ingredient> ingredients = new HashSet<>();

    public Meal(String name, List<Ingredient> ingredients) {
        this.name = name;
        this.addIngredients(ingredients);
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

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        this.getIngredients().add(ingredient);
    }

    public void addIngredients(List<Ingredient> ingredients) {
        ingredients.forEach(this::addIngredient);
    }

    public void printMeal() {
        System.out.println("Name: " + getName());
        printMealIngredients();
        System.out.println();
    }

    public void printMealIngredients() {
        for (Ingredient a : ingredients) {
            System.out.println("Ingredient: " + a.getName());
        }
    }
}
