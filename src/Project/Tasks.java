package Project;

public class Tasks {
    //Attributes
    private int _id;
    private String _name;
    private String _description;
    private String _completionStatus;
    private String _category;

    //Constructor 
    public Tasks(Integer id, String name, String description, String completionStatus, String category) {
        this._id = id;
        this._name = name;
        this._description = description;
        this._completionStatus = completionStatus;
        this._category = category;
    }

    // Getters
    public int getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public String getDescription() {
        return _description;
    }

    public String getCompletionStatus() {
        return _completionStatus;
    }

    public String getCategory() {
        return _category;
    }
}
