/**
 * Cookbook Project: Driver Class
 * This class is the driver for our object-oriented cookbook; it shows the GUI.
 * GUI bootstrapped with example code here: https://docs.oracle.com/javase/tutorial/uiswing/examples/start/HelloWorldSwingProject/src/start/HelloWorldSwing.java
 * @author Nathan Leung
 * @version 18 May 2017
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CookbookDriver {
	// Global panels
	private static JPanel recipeListPanel;
	private static JPanel mainPanel;
	private static JPanel rightPanel;
	
	private static Cookbook globalCookbook;
	
	// State data
	private static String currentCategory;
	private static boolean glutenFreeFilter;
	private static boolean lessThanHourFilter;
	
	/**
	 * Creates and shows the GUI
	 */
	private static void createAndShowGUI() {
		setUIFont(new javax.swing.plaf.FontUIResource("Segoe UI", Font.PLAIN, 16));
		JFrame frame = new JFrame("Nathan's Cookbook");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Maximize frame on launch
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// Set frame background to white
		frame.getContentPane().setBackground(Color.WHITE);
		
		// Put everything into JPanels so we can add it all in one
		// go to the JFrame and in the BorderLayout
		/* Remove this header, it's too big
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(Color.WHITE);
		JLabel header = new JLabel("<html><h1>Nathan's Cookbook</h1></html>");
		header.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		headerPanel.add(header);
		*/
		
		// Recipe list
		recipeListPanel = new JPanel();
		recipeListPanel.setLayout(new BoxLayout(recipeListPanel, BoxLayout.Y_AXIS));
		recipeListPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		recipeListPanel.setBackground(Color.WHITE);
		refreshRecipeList();
		
		// Create new recipe form
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
		showRecipeForm();
		
		// Buttons
		rightPanel = new JPanel();
		rightPanel.setBackground(Color.WHITE);
		rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		Container pane = frame.getContentPane();
		// pane.add(headerPanel, BorderLayout.NORTH);
		
		JScrollPane recipeListScroll = new JScrollPane(recipeListPanel);
		recipeListScroll.setBorder(BorderFactory.createEmptyBorder());
		pane.add(recipeListScroll, BorderLayout.WEST);
		pane.add(new JScrollPane(mainPanel), BorderLayout.CENTER);
		

		pane.add(rightPanel, BorderLayout.EAST);
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Shows the recipe form for a new recipe
	 */
	private static void showRecipeForm() {
		// If we're creating a new recipe, call with null
		// If editing a recipe, pass as argument
		showRecipeForm(null);
	}
	
	/**
	 * Shows the recipe form to edit a recipe
	 * @param recipe the recipe to edit, or null for a new recipe
	 */
	private static void showRecipeForm(Recipe recipe) {
		mainPanel.removeAll();
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 10;
		
		String headerString = "Create New Recipe";
		// If recipe isn't null, we're actually editing a recipe
		if (recipe != null) {
			headerString = "Editing Recipe for \"" + recipe.getName() + "\"";
		}
		JLabel header = new JLabel("<html><h2>" + headerString + "</h2></html>");		
		mainPanel.add(header, c);
		
		// gridy sets the order of objects in the y axis
		c.gridy = 1;
		JTextField nameField = addTextFieldAndLabel("Name", mainPanel, c);
		c.gridy = 2;
		JTextField ingredientsField = addTextFieldAndLabel("Ingredients (comma-separated)", mainPanel, c);
		c.gridy = 3;
		JTextField procedureField = addTextFieldAndLabel("Procedure (comma-separated)", mainPanel, c);
		c.gridy = 4;
		JTextField timeField = addTextFieldAndLabel("Time (minutes)", mainPanel, c);
		c.gridy = 5;
		JTextField servingsField = addTextFieldAndLabel("Servings", mainPanel, c);
		
		JPanel glutenFreePanel = new JPanel();
		glutenFreePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel glutenFreeLabel = new JLabel("Gluten Free?");
		glutenFreePanel.add(glutenFreeLabel);
		String[] glutenFreeChoices = { "Yes", "No" };
		JComboBox<String> glutenFreeComboBox = new JComboBox<String>(glutenFreeChoices);
		glutenFreePanel.add(glutenFreeComboBox);
		
		c.gridy = 6;
		mainPanel.add(glutenFreePanel, c);
		
		c.gridy = 7;
		JTextField categoriesField = addTextFieldAndLabel("Categories (comma-separated)", mainPanel, c);
		c.gridy = 8;
		JTextField authorField = addTextFieldAndLabel("Author", mainPanel, c);
		c.gridy = 9;
		JTextField sourceField = addTextFieldAndLabel("Source", mainPanel, c);
		
		c.gridy = 10;
		JLabel padding = new JLabel("");
		padding.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPanel.add(padding, c);
		
		JButton button = new JButton("Save Recipe");
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(45, 160, 0));
		button.setBorder(BorderFactory.createEmptyBorder());
		c.gridy = 11;
		c.ipady = 30;
		mainPanel.add(button, c);
		
		c.gridy = 12;
		JLabel errorLabel = new JLabel("");
		mainPanel.add(errorLabel, c);
		
		// Set text fields to recipe data if recipe is not null
		if (recipe != null) {
			nameField.setText(recipe.getName());
			ingredientsField.setText(
				String.join(",", recipe.getIngredients())
			);
			procedureField.setText(
				String.join(",", recipe.getProcedure())
			);
			timeField.setText(
				Integer.toString(recipe.getTime())
			);
			servingsField.setText(
				Integer.toString(recipe.getServings())
			);
			String glutenFreeString = recipe.getIsGlutenFree() ? "Yes" : "No";
			glutenFreeComboBox.setSelectedItem(glutenFreeString);
			categoriesField.setText(
				String.join(",", recipe.getCategories())
			);
			authorField.setText(recipe.getAuthor());
			sourceField.setText(recipe.getSource());
		}
		
		// Save recipe on button click
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean error = false;
				String name = nameField.getText();
				String ingredients = ingredientsField.getText();
				String procedure = procedureField.getText();
				String categories = categoriesField.getText();
				String author = authorField.getText();
				String source = sourceField.getText();
				
				// Validation
				if (name.equals("")) {
					errorLabel.setText("Please give your recipe a name.");
					error = true;
				} else if (
					ingredients.equals("") ||
					procedure.equals("") ||
					categories.equals("") ||
					author.equals("") ||
					source.equals("")
				) {
					errorLabel.setText("Please ensure you've filled in all fields.");
					error = true;
				} else {
					int time = 0;
					int servings = 0;
					// Make sure time and servings are numbers
					try {
						time = Integer.parseInt(timeField.getText());
						servings = Integer.parseInt(servingsField.getText());
					} catch (NumberFormatException exception) {
						errorLabel.setText(
							"There's an error with your time or servings number. " +
							"Please take a look."
						);
						error = true;
					}
					
					// If there have been no errors, make the recipe
					if (!error) {
						Recipe newRecipe = new Recipe(
							name,
							// Turn comma-separated string into List
							Arrays.asList(ingredients.split(",")),
							Arrays.asList(procedure.split(",")),
							time,
							servings,
							// Check whether the selected value for gluten free is "Yes"
							glutenFreeComboBox.getSelectedItem().toString().equals("Yes"),
							Arrays.asList(categoriesField.getText().split(",")),
							author,
							source
						);
						// If this is a brand new recipe (not editing) and the
						// recipe file already exists, that means there's a name
						// collision
						if (recipe == null && newRecipe.fileExists()) {
							errorLabel.setText("A recipe is already saved with that name. Please choose a different name.");
							return;
						}
						// Returns true is saved successfully
						boolean recipeSaved = newRecipe.writeToFile();
						if (recipeSaved) {
							errorLabel.setText("Recipe successfully saved.");
							refreshRecipeList();
							openRecipe(newRecipe.getName());
						} else {
							errorLabel.setText(
								"There was an error saving that recipe."
							);		
						}
					}
				}
			}
		});
		
		// Must be called after every panel update
		mainPanel.revalidate();
		mainPanel.repaint();
	}
	
	/**
	 * Refresh the list of recipes (in case new files are saved)
	 */
	private static void refreshRecipeList() {
		recipeListPanel.removeAll();
		
		// Put the Create Recipe button on top
		JButton createRecipeBtn = new JButton("+ Create Recipe");
		Color createRecipeColor = new Color(0, 116, 217);
		createRecipeBtn.setBackground(createRecipeColor);
		createRecipeBtn.setForeground(Color.WHITE);
		createRecipeBtn.setBorder(BorderFactory.createLineBorder(createRecipeColor, 5));
		createRecipeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		createRecipeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshRecipeList();
				showRecipeForm();
				// When the create recipe is showing,
				// don't show recipe buttons (like delete)
				hideRecipeButtons();
			}
		});
		recipeListPanel.add(createRecipeBtn);
		
		// Load recipes from file so we can get recipe names and categories
		globalCookbook.readRecipeFiles();
		
		JLabel categoriesLabel = new JLabel("<html><h3>Categories</h3></html>");
		categoriesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		recipeListPanel.add(categoriesLabel);
		// Iterate over each category and create a button for it
		globalCookbook.getRecipeCategories().forEach(category -> {
			JButton categoryBtn = new JButton(category);
			categoryBtn.setForeground(Color.WHITE);
			Color categoryBtnColor = new Color(61, 153, 112);
			categoryBtn.setBackground(categoryBtnColor);
			categoryBtn.setBorder(BorderFactory.createLineBorder(categoryBtnColor, 5));
			categoryBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
			categoryBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					openCategory(category);
				}
			});
			recipeListPanel.add(categoryBtn);
			// Add 5px margin under button
			recipeListPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		});
		
		JLabel recipesLabel = new JLabel("<html><h3>All Recipes</h3></html>");
		recipesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		recipeListPanel.add(recipesLabel);
		// Iterate over each recipe and create a button for it
		globalCookbook.getRecipeNames().forEach(name -> {
			JButton recipeBtn = new JButton(name);
			recipeBtn.setForeground(Color.WHITE);
			Color recipeBtnColor = new Color(240, 18, 190);
			recipeBtn.setBackground(recipeBtnColor);
			recipeBtn.setBorder(BorderFactory.createLineBorder(recipeBtnColor, 5));
			recipeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
			recipeBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					openRecipe(name);
				}
			});
			recipeListPanel.add(recipeBtn);
			recipeListPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		});
		recipeListPanel.revalidate();
		recipeListPanel.repaint();
	}
	
	/**
	 * Open a recipe file and display on screen
	 * @param name the name of the recipe to open
	 */
	private static void openRecipe(String name) {
		mainPanel.removeAll();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		String recipeFileContent;
		try {
			// Convert recipe name to name used in file
			String normalizedName = name.replace(" ", "-").toLowerCase();
			recipeFileContent = new String(
				Files.readAllBytes(Paths.get("recipes/recipe-" + normalizedName + ".txt"))
			);
			Recipe recipe = Cookbook.createRecipeFromString(recipeFileContent);
			
			// Show Edit/Delete buttons
			showRecipeButtons(recipe);
			
			JLabel nameLabel = new JLabel("<html><h2>" + recipe.getName() + "</h2></html>");
			mainPanel.add(nameLabel);
			
			String ingredientsString = "<html><h3>Ingredients</h3><ul>";
			for (String ingredient : recipe.getIngredients()) {
				ingredientsString += ("<li>" + ingredient + "</li>");
			}
			ingredientsString += "</ul></html>";
			JLabel ingredientsLabel = new JLabel(ingredientsString);
			mainPanel.add(ingredientsLabel);
			
			String procedureString = "<html><h3>Steps</h3><ol>";
			for (String step : recipe.getProcedure()) {
				procedureString += ("<li>" + step + "</li>");
			}
			procedureString += "</ol></html>";
			JLabel procedureLabel = new JLabel(procedureString);
			mainPanel.add(procedureLabel);
			
			JLabel timeLabel = new JLabel(
					"<html><h3>Time</h3>" +
					recipe.getTime() + " minutes" +
					"</html>"
			);
			mainPanel.add(timeLabel);
			
			JLabel servingsLabel = new JLabel(
				"<html><h3>Servings</h3>" +
				recipe.getServings() +
				"</html>"
			);
			mainPanel.add(servingsLabel);
			
			boolean isGlutenFree = recipe.getIsGlutenFree();
			JLabel glutenFreeLabel = new JLabel(
				"<html><h3>Gluten Free?</h3>" +
				(isGlutenFree ? "Yes" : "No") + "</html>"
			);
			mainPanel.add(glutenFreeLabel);
			
			String categoriesString = "<html><h3>Categories</h3><ul>";
			for (String step : recipe.getCategories()) {
				categoriesString += ("<li>" + step + "</li>");
			}
			categoriesString += "</ul></html>";
			JLabel categoriesLabel = new JLabel(categoriesString);
			mainPanel.add(categoriesLabel);
			
			JLabel authorLabel = new JLabel(
				"<html><h3>Author</h3>" + recipe.getAuthor() + "</html>"
			);
			mainPanel.add(authorLabel);
			
			JLabel sourceLabel = new JLabel(
				"<html><h3>Source</h3>" + recipe.getSource()
			);
			mainPanel.add(sourceLabel);
			
			mainPanel.revalidate();
			mainPanel.repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * List recipes in a category on screen
	 * @param name the category from which to pull recipes
	 */
	private static void openCategory(String name) {
		// Reset filters when different category is opened
		if (name != currentCategory) {
			glutenFreeFilter = false;
			lessThanHourFilter = false;
		}
		
		// Set current category globally so we can filter recipes
		// in a different method
		currentCategory = name;
		
		mainPanel.removeAll();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		// Hide Edit/Delete buttons and show filter buttons
		hideRecipeButtons();
		showFilterButtons();
		
		JLabel categoryLabel = new JLabel(
			"<html><h2>" + name + " Recipes</h2></html>"
		);
		mainPanel.add(categoryLabel);
		
		// Get all recipes, filter based on currently active filters
		// and then make buttons
		globalCookbook
			.getRecipes()
			.stream()
			.filter(recipe -> {
				if (glutenFreeFilter) {
					return recipe.getIsGlutenFree();
				}
				return true;
			})
			.filter(recipe -> {
				if (lessThanHourFilter) {
					return recipe.getTime() < 60;
				}
				return true;
			})
			.forEach(recipe -> {
				if (recipe.getCategories().indexOf(name) != -1) {
					String recipeName = recipe.getName();
					JButton recipeBtn = new JButton(recipeName);
					recipeBtn.setForeground(Color.WHITE);
					Color recipeColor = new Color(0, 31, 63);
					recipeBtn.setBackground(recipeColor);
					recipeBtn.setBorder(BorderFactory.createLineBorder(recipeColor, 5));
					
					recipeBtn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							openRecipe(recipeName);
						}
					});
					mainPanel.add(recipeBtn);
					mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
				}
			});
		
		mainPanel.revalidate();
		mainPanel.repaint();
	}
	
	/**
	 * Show the edit/delete buttons for a recipe
	 * @param recipe the recipe to show the buttons for
	 */
	private static void showRecipeButtons(Recipe recipe) {
		rightPanel.removeAll();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		JButton deleteRecipeBtn = new JButton("Delete Recipe");
		deleteRecipeBtn.setForeground(Color.WHITE);
		Color deleteRecipeColor = new Color(255, 65, 54);
		deleteRecipeBtn.setBackground(deleteRecipeColor);
		deleteRecipeBtn.setBorder(BorderFactory.createLineBorder(deleteRecipeColor, 5));
		deleteRecipeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		deleteRecipeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recipe.deleteRecipeFile();
				refreshRecipeList();
				showRecipeForm();
				// When the recipe form is showing,
				// don't show recipe buttons (like delete)
				hideRecipeButtons();
			}
		});

		rightPanel.add(deleteRecipeBtn);
		
		rightPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		JButton editRecipeBtn = new JButton("Edit Recipe");
		editRecipeBtn.setForeground(Color.WHITE);
		Color editRecipeColor = new Color(255, 133, 27);
		editRecipeBtn.setBackground(editRecipeColor);
		editRecipeBtn.setBorder(BorderFactory.createLineBorder(editRecipeColor, 5));
		editRecipeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		editRecipeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showRecipeForm(recipe);
			}
		});
		rightPanel.add(editRecipeBtn);
		
		rightPanel.revalidate();
		rightPanel.repaint();
	}
	
	/**
	 * Hide edit/delete buttons
	 */
	private static void hideRecipeButtons() {
		rightPanel.removeAll();
		rightPanel.revalidate();
		rightPanel.repaint();
	}
	
	/**
	 * Show filter buttons (for use on category pages)
	 */
	private static void showFilterButtons() {
		rightPanel.removeAll();
		
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
		// Toggle between "Show [filter name]" and "Hide [filter name]" on buttons
		String showHideGlutenFreeWord = glutenFreeFilter ? "Show" : "Hide";
		JButton glutenFreeFilterBtn = new JButton(
			showHideGlutenFreeWord + " Non-Gluten Free"
		);
		Color glutenFreeColor = new Color(177, 13, 201);
		glutenFreeFilterBtn.setBackground(glutenFreeColor);
		glutenFreeFilterBtn.setForeground(Color.WHITE);
		glutenFreeFilterBtn.setBorder(BorderFactory.createLineBorder(glutenFreeColor, 5));
		glutenFreeFilterBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		glutenFreeFilterBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				glutenFreeFilter = !glutenFreeFilter;
				openCategory(currentCategory);
			}
		});
		rightPanel.add(glutenFreeFilterBtn);
		rightPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		String showHideLessThanHourWord = lessThanHourFilter ? "Show" : "Hide";
		JButton lessThanHourFilterBtn = new JButton(
			showHideLessThanHourWord + " Long Recipes (>1 hr)"
		);
		lessThanHourFilterBtn.setForeground(Color.WHITE);
		Color lessThanHourColor = new Color(46, 204, 64);
		lessThanHourFilterBtn.setBackground(lessThanHourColor);
		lessThanHourFilterBtn.setBorder(BorderFactory.createLineBorder(lessThanHourColor, 5));
		lessThanHourFilterBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		lessThanHourFilterBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lessThanHourFilter = !lessThanHourFilter;
				openCategory(currentCategory);
			}
		});
		rightPanel.add(lessThanHourFilterBtn);
		
		rightPanel.revalidate();
		rightPanel.repaint();
	}
	
	/**
	 * Helper method to add a text field with a label to the recipe form panel
	 * @param labelString the string to use on the label
	 * @param panel the panel to add the text field and label to
	 * @param c some layout data (configured in the parent function)
	 * @return the text field that was added (so we can get the text data later)
	 */
	public static JTextField addTextFieldAndLabel(
		String labelString,
		JPanel panel,
		GridBagConstraints c
	) {
		JPanel textFieldPanel = new JPanel();
		textFieldPanel.setLayout(new GridBagLayout());
		// c1 is the constraints object specifically
		// for a single textbox/label set
		GridBagConstraints c1 = new GridBagConstraints();
		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.gridx = 0;
		c1.gridy = 0;
		// Essentially sets width of text field
		c1.ipadx = 400;
		c1.ipady = 10;
		JLabel label = new JLabel(labelString);
		JTextField input = new JTextField(15);
		input.setBorder(new EmptyBorder(5, 5, 5, 5));
		textFieldPanel.add(label, c1);
		c1.gridy = 1;
		textFieldPanel.add(input, c1);
		// Add textFieldPanel to original panel
		panel.add(textFieldPanel, c);
		return input;
	}

	/**
	 * Main method starts up GUI
	 * @param args args for the program
	 */
	public static void main(String[] args) {
		Cookbook cookbook = new Cookbook();
		globalCookbook = cookbook;
		
		// Creating GUI in this wrapper is recommended according
		// to the example file (see link in top-level comment)
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
	
	/**
	 * Makes a rather complex demo recipe. To test with the GUI, add:
	 *     makeDemoRecipe().writeToFile();
	 * to the main function
	 * @return a demo recipe
	 */
	public static Recipe makeDemoRecipe() {
		String name = "Chicken Fricassee";
		
		List<String> ingredients = new ArrayList<String>();
		ingredients.add("1 whole chicken");
		ingredients.add("coarse salt");
		ingredients.add("freshly ground pepper");
		ingredients.add("3 tbsp unsalted butter");
		ingredients.add("1 tbsp extra-virgin olive oil");
		ingredients.add("1 small yellow onion");
		ingredients.add("1 carrot");
		ingredients.add("1 celery stalk");
		ingredients.add("8 oz cremini mushrooms");
		ingredients.add("2 tbsp flour");
		ingredients.add("2/3 cup dry white wine");
		ingredients.add("4 cups chicken broth");
		ingredients.add("2 sprigs fresh flat-leaf parsley");
		ingredients.add("2 sprigs fresh thyme");
		ingredients.add("1 bay leaf");
		ingredients.add("2 large egg yolks");
		ingredients.add("1/4 cup heavy cream");
		ingredients.add("2 to 3 tbsp fresh tarragon leaves");
		ingredients.add("2 tbsp fresh lemon juice");
		
		List<String> procedure = new ArrayList<String>();
		procedure.add("Brown chicken");
		procedure.add("Saute mirepoix");
		procedure.add("Cook mushrooms and flour");
		procedure.add("Pour in wine and broth");
		procedure.add("Simmer chicken");
		procedure.add("Make and add liaison");
		procedure.add("Finish with tarragon lemon and butter");
		
		int time = 75;
		int servings = 4;
		boolean isGlutenFree = false;
		
		List<String> categories = new ArrayList<String>();
		categories.add("chicken");
		
		String author = "Martha Stewart Living";
		String source = "http://www.marthastewart.com/925889/chicken-fricassee-fricassee-de-poulet-lancienne";
		
		Recipe recipe = new Recipe(
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
		
		return recipe;
	}

	/**
	 * Sets the default font of the GUI
	 * From https://stackoverflow.com/questions/7434845/setting-the-default-font-of-swing-program
	 * @param f font to use
	 */
	public static void setUIFont (javax.swing.plaf.FontUIResource f){
	    java.util.Enumeration keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
        Object key = keys.nextElement();
        Object value = UIManager.get (key);
        if (value != null && value instanceof javax.swing.plaf.FontUIResource)
         	UIManager.put (key, f);
        }
    } 
}
