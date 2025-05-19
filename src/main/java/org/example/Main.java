package org.example;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        transaction(Main::transactionBody, "pekara");
    }

    public static void transaction(Consumer<EntityManager> method, String entityManagerName) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(entityManagerName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            method.accept(em);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        tx.commit();
        em.close();
        emf.close();
    }

    public static void transactionBody(EntityManager em) {
        Ingredient sir = addIngredient(em, "sir");
        Ingredient tijesto = addIngredient(em, "tijesto");
        Ingredient cokolada = addIngredient(em, "cokolada");
        Ingredient salama = addIngredient(em, "salama");

        addMeal(em,"Burek", Arrays.asList(sir, tijesto));
        addMeal(em, "Pizza", Arrays.asList(sir,tijesto, salama));
        addMeal(em, "Krafna", Arrays.asList(tijesto, cokolada));

        printMeals(em);
    }

    public static Meal addMeal(EntityManager em, String mealName, List<Ingredient> ingredients) {
        Meal meal = new Meal(mealName, ingredients);
        em.persist(meal);
        return meal;
    }

    public static Ingredient addIngredient(EntityManager em, String ingredientName) {
        Ingredient ingredient = new Ingredient(ingredientName);
        em.persist(ingredient);
        return ingredient;
    }

    public static List<Meal> getMeals(EntityManager em) {
        return  em.createQuery("select m from Meal m", Meal.class).getResultList();
    }

    public static void printMeals(EntityManager entityManager) {
        List<Meal> meals = getMeals(entityManager);
        System.out.println();

        for (Meal meal : meals) {
            System.out.println("Name: " + meal.getName());
            printIngredients(meal.getIngredients());
            System.out.println();
        }

        printIfEmptyMeals(meals);
    }

    public static void printIfEmptyMeals(List<Meal> meals) {
        if (meals.isEmpty()) {
            System.out.println("No meals");
        }
    }

    public static void printIngredients(Set<Ingredient> ingredients) {
        for (Ingredient a : ingredients) {
            System.out.println("Ingredient: " + a.getName());
        }
    }
}