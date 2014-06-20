package uk.ac.man.cs.finderapplication.model;

import java.io.File;
import java.util.*;
import javax.swing.*;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.util.AnnotationValueShortFormProvider;
import org.semanticweb.owlapi.util.ShortFormProvider;

public class FinderOntology {

    //public static final Preferences PREFERENCES;

    public static final String TOPPING_SUFFIX = "";//"Topping";

    private OWLReasoner reasoner;

    private OWLOntology ontology;

    private OWLDataFactory df;

    private OWLOntologyManager manager;

    private ShortFormProvider sfp;

    /*static {
        PREFERENCES = Preferences.getInstance();
    }*/

     public FinderOntology(){
        loadOntology();
        setupReasoner();
        setupShortFormProvider();
        //System.out.println(getFilters());
    }

    protected void loadOntology() {
        try {
            manager = OWLManager.createOWLOntologyManager();
            df = manager.getOWLDataFactory();
            //ontology = manager.loadOntologyFromOntologyDocument(PREFERENCES.getOntologyDocumentIRI());
            // hardcoded ontology creatition
            System.out.println(new Settings().getOnologyLocation());
            ontology = manager.loadOntologyFromOntologyDocument(IRI.create(new File(new Settings().getOnologyLocation()).getAbsoluteFile()));
        } catch (final Throwable e) {
            Runnable runnable = new Runnable() {
                public void run() {
                    JOptionPane.showMessageDialog(null,
                            "Could not create the ontology.  (This probably happened\n" +
                                    "because the ontology could not be accessed due to network\n" +
                                    "problems.)\n" +
                                    "[" + e.getMessage() + "]",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
    }

    public OWLOntology getOntology() {
        return ontology;
    }

    protected void setupReasoner() {
        // run reasoner
        try {
            //replaced with hermit reasoner
            OWLReasonerFactory factory = new Reasoner.ReasonerFactory();
            reasoner = factory.createReasoner(ontology);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "A reasoner error has ocurred.\n" +
                            "[" + e.getMessage() + "]",
                    "Reasoner Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupShortFormProvider() {
        final Map<OWLAnnotationProperty, List<String>> langMap = new HashMap<OWLAnnotationProperty, List<String>>();
        //langMap.put(df.getRDFSLabel(), Arrays.asList(PREFERENCES.getLanguage()));
        langMap.put(df.getRDFSLabel(), Arrays.asList("en"));
        sfp = new AnnotationValueShortFormProvider(Arrays.asList(df.getRDFSLabel()),
                langMap, manager);
    }

    /**
     * getReasoner
     *
     * @return reasoner
     */
    public OWLReasoner getReasoner() {
        return reasoner;
    }

    /**
     * Gets the main Ingredients categories.  This actually returns
     * the direct named subclasses of the Ingredients class.
     *
     * @return A <code>Collection</code> of <code>OWLNamedClasses</code>
     */
    public Collection<OWLClass> getIngredientsCategories() {
        OWLClass ingredientsCls = getIngClass();
        return reasoner.getSubClasses(ingredientsCls, true).getFlattened();
    }
   /** This method add by Hani Al Abbas.
    *@return a OWLClass that hasRoll as IngClass
    */
    public OWLClass getIngClass(){
        OWLClass cls = null;
        for(OWLClass c:ontology.getClassesInSignature()){
            for(OWLAnnotationAssertionAxiom a:c.getAnnotationAssertionAxioms(ontology)){
                if(a.getValue().toString().replace("\"", "").equals("IngClass")){
                    cls = c;
                    break;
                }
            }
        }
        return cls;   
    }
    
    /**
    * Removing Suffix from ingredients classes
    */
    public String render(OWLEntity entity) {
        String shortForm = sfp.getShortForm(entity);
        StringBuilder sb = null;
        String suffix = getSuffix();
        if(suffix!=null) {
            if(shortForm.endsWith(suffix)){
                shortForm = shortForm.substring(0, shortForm.length() - suffix.length());
            }
            sb = new StringBuilder();
            char last = 0;
            for(int i = 0; i < shortForm.length(); i++) {
                char ch = shortForm.charAt(i);
                if(Character.isUpperCase(ch) && last != 0 && last != ' ') {
                    sb.append(" ");
                }
                sb.append(ch);
                last = ch;
            }
        }
        else{
            suffix = "";
            if(shortForm.endsWith(suffix)){
                shortForm = shortForm.substring(0, shortForm.length() - suffix.length());
            }
            sb = new StringBuilder();
            char last = 0;
            for(int i = 0; i < shortForm.length(); i++) {
                char ch = shortForm.charAt(i);
                if(Character.isUpperCase(ch) && last != 0 && last != ' ') {
                    sb.append(" ");
                }
                sb.append(ch);
                last = ch;
            }
        }
        
        return sb.toString();
    }

    /**
     * Tests to see if the specified class is a VegetarianPizza - i.e.
     * a subclass of the vegetarian pizza class.
     *
     * @param pizzaClass The class to be tested.
     * @return <code>true</code> if the specified class is a vegetarian pizza
     *         (subclass of the vegetarian pizza class), or <code>false</code> if the
     *         specified class is not a vegetarian pizza (not a subclass of the vegetarian
     *         pizza class).
     */
    public boolean isVegetarianPizza(OWLClass pizzaClass) {
        OWLClass cl = getVegetarianClass();
        if(cl != null){
            return this.filterClasses(reasoner.getSuperClasses(pizzaClass, false)).contains(getVegetarianClass());
        }
        else{
            return false;
        }
    }

    /**
     * check to see if specified class is the base class
     *
     * @param baseClass
     * @return
     */
    public boolean isNamedPizza(OWLClass baseClass) {
        return this.filterClasses(reasoner.getSuperClasses(baseClass, true)).contains(getBaseClass());
    }

    /**
     * Tests to see if the specified class is a SpicyPizza - i.e. a
     * subclass of the Spicy Pizza class.
     *
     * @param pizzaClass The class to be tested.
     * @return <code>true</code> if instances of the specified class are spciy pizzas
     *         (i.e. the specified class is a subclass of the spicy pizza class), or <code>false</code>
     *         if instances of the specified class are not spicy pizzas (i.e. the specified class cannot
     *         be determined to be a subclass of the hot pizza class).
     */
    public boolean isSpicyPizza(OWLClass pizzaClass) {
        OWLClass cl = getSpicyClass();
        if(cl != null){
            return this.filterClasses(reasoner.getSuperClasses(pizzaClass, true)).contains(getSpicyClass());
        }
        else{
            return false;
        }
    }

    /**
     * Gets the Vegetarian class.
     *
     * @return The named class that represents things that are
     *         vegetarian class.
     */
    public OWLClass getVegetarianClass() {
        OWLClass cls = null;
        for(OWLClass c:ontology.getClassesInSignature()){
            for(OWLAnnotationAssertionAxiom a:c.getAnnotationAssertionAxioms(ontology)){
                if(a.getValue().toString().contains("VegeClass")){
                    cls = c;
                }
            }
        }
        return cls;
    }

    /**
     * Gets the Spicy Class.
     *
     * @return The named class that represents things that are spicy class
     */
    public OWLClass getSpicyClass() {
        OWLClass cls = null;
        for(OWLClass c:ontology.getClassesInSignature()){
            for(OWLAnnotationAssertionAxiom a:c.getAnnotationAssertionAxioms(ontology)){
                if(a.getValue().toString().contains("SpicyClass")){
                    cls = c;
                }
            }
        }
        return cls;
    }
    
    /** This method add by Hani Al Abbas.
    *@return a OWLClass that hasRoll as BaseClass
    */
    public OWLClass getBaseClass(){
        OWLClass cls = null;
        for(OWLClass c:ontology.getClassesInSignature()){
            for(OWLAnnotationAssertionAxiom a:c.getAnnotationAssertionAxioms(ontology)){
                if(a.getValue().toString().replace("\"", "").equals("BaseClass")){
                    cls = c;
                    break;
                }
            }
        }
        return cls;   
    }

    /**
     * Gets the pizzas that match the requirement for included and excluded
     * toppings.
     *
     * @param includeToppings The toppings that the pizza should have.
     * @param excludeToppings The toppings that the pizza should NOT have.
     * @return A <code>Collection</code> of classes that represent the pizza
     *         classes that statisfy the description of the required toppings.
     */
    public Collection getPizzas(Set<OWLClass> includeToppings, Set<OWLClass> excludeToppings) {
        Collection c;
        // Temporarily create a description (class) that will have the required
        // pizzas (the pizzas with the included toppings but not the excluded toppings).
        // OWLClassExpression toppingDesc = createPizzaDescription(includeToppings, excludeToppings,"7");
        OWLClassExpression toppingDesc = createPizzaDescription(includeToppings, excludeToppings);
        // Ask the reasoner for the subclasses of the temp description
        return filterClasses(reasoner.getSubClasses(toppingDesc, false));
    }

    /**
     * Creates OWLClassExpression (query) by given inclided topping and excluded topping
     *
     * @param includeToppings
     * @param excludeToppings
     * @return
     */
    private OWLClassExpression createPizzaDescription(Set<OWLClass> includeToppings, Set<OWLClass> excludeToppings) {
        // Include means existential restrictions
        // Exclude means negated existential restrictions
        OWLObjectProperty prop = getProperty();   //has_topping
        // Create a hash set to stor the components (existential restrictions)
        // of our description
        Set<OWLClassExpression> classes = new HashSet<OWLClassExpression>();
        // Everthing must be a pizza
        classes.add(getBaseClass());        //get OWL class for Pizza
        // Create the existential restrictions that represent the toppings
        // that we want to include.
        OWLDataFactory df = manager.getOWLDataFactory();
        for (OWLClass topping : includeToppings) {
            classes.add(df.getOWLObjectSomeValuesFrom(prop, topping));         // e.g. hasTopping some top_A , hasTopping some top_B
        }
        // Create the negated existential restrictions of the toppings that we
        // want to exclude
        for (OWLClass excludeTopping : excludeToppings) {
            OWLClassExpression restriction = df.getOWLObjectSomeValuesFrom(prop, excludeTopping);  // has_topping some topping_A
            OWLObjectComplementOf neg = df.getOWLObjectComplementOf(restriction);    //not (has_topping some topping_A)
            classes.add(neg);
        }
        // Bind the whole thing up in an intersection class
        // to create a concept description of the pizza we
        // are looking for.
        return df.getOWLObjectIntersectionOf(classes);
    }
    
    /** This method add by Hani Al Abbas.
    *@return a OWLClass that hasRoll as BaseClass
    */
    public OWLObjectProperty getProperty(){
        OWLObjectProperty obj = null;
        for(OWLObjectProperty o:ontology.getObjectPropertiesInSignature()){
            for(OWLAnnotationAssertionAxiom a:o.getAnnotationAssertionAxioms(ontology)){
                if(a.getValue().toString().replace("\"", "").equals("Property")){
                    obj = o;
                    break;
                }
            }
        }
        return obj;   
    }

    /**
     * filters the result of e.g. getSubclasses which is  Set<Set<OWLClass>>  To  Set<OWLClass>
     *
     * @param original
     * @return
     */
    protected Set<OWLClass> filterClasses(NodeSet<OWLClass> original) {
        Set<OWLClass> result = new TreeSet<OWLClass>();
        for (OWLClass cls : original.getFlattened()) {
            if (reasoner.isSatisfiable(cls)) {
                result.add(cls);
            }
        }
        return result;
    }
    
    public String getSuffix(){
        String cls = null;
            for(OWLAnnotationAssertionAxiom a:getIngClass().getAnnotationAssertionAxioms(ontology)){
                String property = a.getProperty().toString();
                if(property.contains("suffix")){
                    cls = a.getValue().toString().trim().replaceAll("\"", "");
                    
                    break;
                }
            }
        return cls;   
    }
    
    public ArrayList<OWLClass> getFilters(){
        ArrayList<OWLClass> filters = new ArrayList<OWLClass>();
        for(OWLClass c:ontology.getClassesInSignature()){
            for(OWLAnnotation a:c.getAnnotations(ontology)){
                if(a.getValue().toString().contains("filter")){
                    filters.add(c);
                }
            }
        }
        return filters;
    }
    
    public boolean FilterExists(){
        if(getFilters().size()!=0){
            return true;
        }
        else{
            return false;
        }
    }
}
