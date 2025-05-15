package org.example;

import jakarta.persistence.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("library");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();


        Meal burek = addMeal(em,"Burek");
        Meal pizza = addMeal(em, "Pizza");
        Meal krafna = addMeal(em, "Krafna");

        Ingredient sir = addIngredient(em, "sir");
        Ingredient tijesto = addIngredient(em, "tijesto");
        Ingredient cokolada = addIngredient(em, "cokolada");
        Ingredient salama = addIngredient(em, "salama");

        burek.getIngredients().add(sir);
        burek.getIngredients().add(tijesto);

        pizza.getIngredients().add(sir);
        pizza.getIngredients().add(tijesto);
        pizza.getIngredients().add(salama);

        krafna.getIngredients().add(tijesto);
        krafna.getIngredients().add(cokolada);

        printMeals(em);



    }

    public static Meal addMeal(EntityManager em, String mealName) {
        Meal meal = new Meal();
        meal.setName(mealName);
        em.persist(meal);
        return meal;
    }

    public static Ingredient addIngredient(EntityManager em, String ingredientName) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientName);
        em.persist(ingredient);
        return ingredient;
    }

    public static List<Meal> getMeals(EntityManager em) {
        return  em.createQuery("select m from Meal m", Meal.class).getResultList();
    }


    public static void printMeals(EntityManager entityManager) {
        List<Meal> meals = getMeals(entityManager);
        System.out.println();
        if (meals.isEmpty()) {
            System.out.println("No meals");
        }
        for (Meal b : meals) {
            System.out.println("Name: " + b.getName());
            for (Ingredient a : b.getIngredients()) {
                System.out.println("Ingredient: " + a.getName());
            }
            System.out.println();
        }
    }
}