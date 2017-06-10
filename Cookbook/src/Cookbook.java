/**
 * Cookbook Project: Cookbook Class
 * This class contains the cookbook logic for our cookbook project.
 * @author Nathan Leung
 * @version 18 May 2017
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Cookbook {
	private List<Recipe> recipes;
	
	public Cookbook() {
		recipes = new ArrayList<Recipe>();
	}
	
	private static List<String> stringToList(String listString) {
		int length = listString.length();
		// Remove [brackets] from string
		String csv = listString.substring(1, length - 1);
		// Separate string by commas
		// Split by ", " since ArrayList::toString creates
		// string with spaces and commas
		return new ArrayList<String>(
			Arrays.asList(
				csv.split(", ")
			)
		);
	}
	
	public static Recipe createRecipeFromString(String recipeString) {
		Pattern recipePattern = Pattern.compile(
			"Recipe: (?<name>.*)" +
			"\\nIngredients: (?<ingredients>.*)" +
			"\\nProcedure: (?<procedure>.*)" + 
			"\\nTime \\(min\\): (?<time>.*)" +
			"\\nServings: (?<servings>.*)" +
			"\\nGluten Free\\? (?<isGlutenFree>.*)" +
			"\\nCategories: (?<categories>.*)" +
			"\\nAuthor: (?<author>.*)" +
			"\\nSource: (?<source>.*)"
		);
		Matcher m = recipePattern.matcher(recipeString);
		if (m.find()) {
			String name = m.group("name");
			List<String> ingredients = stringToList(m.group("ingredients"));
			List<String> procedure = stringToList(m.group("procedure"));
			int time = Integer.parseInt(m.group("time"));
			int servings = Integer.parseInt(m.group("servings"));
			boolean isGlutenFree = Boolean.parseBoolean(m.group("isGlutenFree"));
			List<String> categories = stringToList(m.group("categories"));
			String author = m.group("author");
			String source = m.group("source");
			
			return new Recipe(
				name,
				ingredients,
				procedure,
				time,
				servings,
				isGlutenFree,
				categories,
				author,
				source
			);
		} else {
			System.out.println("Recipe file could not be read.");
			return null;
		}
	}
	
	/**
	 * Loads recipe files from recipes/ directory
	 */
	public void readRecipeFiles() {
		List<String> data = new ArrayList<String>();
		// From http://stackoverflow.com/questions/1844688/read-all-files-in-a-folder
		// Reads all files in recipe directory
		try (Stream<Path> paths = Files.walk(Paths.get("recipes"))) {
		    paths
		    	.filter(filePath -> {
		    		return Files.isRegularFile(filePath);
		    	})
		    	.forEach(filePath -> {
		    		String content;
					try {
						content = new String(
							Files.readAllBytes(filePath)
						);
			    		data.add(content);
					} catch (IOException e) {
						e.printStackTrace();
					}
			    });
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		recipes = data
					.stream()
					.map(Cookbook::createRecipeFromString)
					.collect(Collectors.toList());
	}
	
	/**
	 * Gets recipe categories
	 * @return recipe categories
	 */
	public List<String> getRecipeCategories() {
		List<String> categories = new ArrayList<String>();
		for (Recipe recipe : recipes) {
			for (String category : recipe.getCategories()) {
				if (categories.indexOf(category) == -1) {
					categories.add(category);
				}
			}
		}
		return categories;
	}
	
	/**
	 * Gets recipe data
	 * @return recipe data
	 */
	public List<Recipe> getRecipes() {
		return recipes;
	}
	/**
	 * Gets the names of available recipes
	 * @return a list of recipe names
	 */
	public List<String> getRecipeNames() {
		return recipes
					.stream()
					.map(recipe -> recipe.getName())
					.collect(Collectors.toList());
	}
}
