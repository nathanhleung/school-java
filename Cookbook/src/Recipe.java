/**
 * Cookbook Project: Recipe Class
 * This class contains the recipe logic for our cookbook project.
 * @author Nathan Leung
 * @version 18 May 2017
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

public class Recipe {
	private String name;
	private List<String> ingredients;
	private List<String> procedure;
	private int time;
	private int servings;
	private boolean isGlutenFree;
	private List<String> categories;
	private String author;
	private String source;
	
	/**
	 * Constructs a new instance of the Recipe class
	 * @param name the name of the recipe
	 * @param ingredients the ingredients for the recipe
	 * @param procedure the procedure to make the recipe
	 * @param time the time, in minutes, to make the recipe
	 * @param servings the number of servings the recipe produces
	 * @param isGlutenFree whether the recipe is gluten free or not
	 * @param categories the categories of the recipe
	 * @param author the author of the recipe
	 * @param source the source of the recipe
	 */
	public Recipe(
		String name,
		List<String> ingredients,
		List<String> procedure,
		int time,
		int servings,
		boolean isGlutenFree,
		List<String> categories,
		String author,
		String source
	) {
		this.name = name;
		this.ingredients = ingredients;
		this.procedure = procedure;
		this.time = time;
		this.servings = servings;
		this.isGlutenFree = isGlutenFree;
		this.categories = categories;
		this.author = author;
		this.source = source;
	}
	
	/**
	 * Gets the name of the recipe
	 * @return the name of the recipe
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the recipe
	 * @param newName the new name for the recipe
	 * @return the newly set name
	 */
	public String setName(String newName) {
		name = newName;
		return name;
	}
	
	/**
	 * Gets the ingredients of the recipe
	 * @return the ingredients of the recipe
	 */
	public List<String> getIngredients() {
		return ingredients;
	}
	
	/**
	 * Sets the ingredients of the recipe
	 * @param newIngredients the new ingredients for the recipe
	 * @return the newly set ingredients
	 */
	public List<String> setIngredients(
			List<String> newIngredients
	) {
		ingredients = newIngredients;
		return ingredients;
	}
	
	/**
	 * Gets the procedure for the recipe
	 * @return the procedure for the recipe
	 */
	public List<String> getProcedure() {
		return procedure;
	}
	
	/**
	 * Sets the procedure for the recipe
	 * @param newProcedure the new procedure for the recipe
	 * @return the newly set procedure
	 */
	public List<String> setProcedure(List<String> newProcedure) {
		procedure = newProcedure;
		return procedure;
	}
	
	/**
	 * Gets the time needed to complete the recipe, in minutes
	 * @return the time needed for the recipe in minutes
	 */
	public int getTime() {
		return time;
	}
	
	/**
	 * Sets the time needed to complete the recipe
	 * @param newTime the new time for the recipe
	 * @return the newly set time for the recipe
	 */
	public int setTime(int newTime) {
		time = newTime;
		return time;
	}
	
	/**
	 * Gets the servings produced by the recipe
	 * @return the servings produced by the recipe
	 */
	public int getServings() {
		return servings;
	}
	
	/**
	 * Sets the servings produced by the recipe
	 * @param newServings the new number of servings produced by the recipe
	 * @return the newly set number of servings
	 */
	public int setServings(int newServings) {
		servings = newServings;
		return servings;
	}
	
	/**
	 * Gets whether the recipe is gluten free
	 * @return whether the recipe is gluten free
	 */
	public boolean getIsGlutenFree() {
		return isGlutenFree;
	}
	
	/**
	 * Sets whether the recipe is gluten free
	 * @param newIsGlutenFree the new value for whether the recipe is gluten free
	 * @return the newly set gluten free value
	 */
	public boolean setIsGlutenFree(boolean newIsGlutenFree) {
		isGlutenFree = newIsGlutenFree;
		return isGlutenFree;
	}
	
	/**
	 * Gets the categories of the recipe
	 * @return the categories of the recipe
	 */
	public List<String> getCategories() {
		return categories;
	}
	
	/**
	 * Sets the categories of the recipe
	 * @param newCategories the new categories of the recipe
	 * @return the newly set categories of the recipe
	 */
	public List<String> setCategories(List<String> newCategories) {
		categories = newCategories;
		return categories;
	}
	
	/**
	 * Gets the author of the recipe
	 * @return the author of the recipe
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * Sets the author of the recipe
	 * @param newAuthor the new author of the recipe
	 * @return the newly set author of the recipe
	 */
	public String setAuthor(String newAuthor) {
		author = newAuthor;
		return author;
	}
	
	/**
	 * Gets the source of the recipe
	 * @return the source of the recipe
	 */
	public String getSource() {
		return source;
	}
	
	/**
	 * Sets the source of the recipe
	 * @param newSource the new source of the recipe
	 * @return the newly set source of the recipe
	 */
	public String setSource(String newSource) {
		source = newSource;
		return source;
	}
	
	/**
	 * Checks whether a file already exists for the current recipe
	 * @return whether the file for this recipe already exists
	 */
	public boolean fileExists() {
		String normalizedName = name.replace(" ", "-").toLowerCase();
		File recipeFile =
			new File("recipes/recipe-" + normalizedName + ".txt");
		return recipeFile.exists();
	}
	
	/**
	 * Saves the current recipe to a file in the recipes/ directory
	 * The Cookbook class reads from the recipes/ directory to
	 * get the list of recipes
	 * @return whether the operation was successful
	 */
	public boolean writeToFile() {
		try {
			String normalizedName = name.replace(" ", "-").toLowerCase();
			File recipeFile =
				new File("recipes/recipe-" + normalizedName + ".txt");
			// Create recipes directory if nonexistent
			recipeFile.getParentFile().mkdirs();
			Writer writer = new BufferedWriter(
				new OutputStreamWriter(
					new FileOutputStream(recipeFile),
					"utf-8"
				)
			);
			writer.write(toString());
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void deleteRecipeFile() {
		String normalizedName = name.replace(" ", "-").toLowerCase();
		File recipeFile =
			new File("recipes/recipe-" + normalizedName + ".txt");
		recipeFile.delete();
	}
	
	/**
	 * Creates a string representation of the recipe
	 * @return a string representation of the recipe
	 */
	public String toString() {
		return "Recipe: " + name +
			"\nIngredients: " + ingredients +
			"\nProcedure: " + procedure +
			"\nTime (min): " + time +
			"\nServings: " + servings +
			"\nGluten Free? " + isGlutenFree +
			"\nCategories: " + categories +
			"\nAuthor: " + author +
			"\nSource: " + source;
	}
}
