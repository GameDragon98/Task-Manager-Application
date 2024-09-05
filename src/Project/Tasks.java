package Project;

public class Tasks<T> {

    private T _id;
    private String _name;
    private String _description;
    private String _completionStatus;
    private String _category;

    public Tasks(T id, String name, String description, String completionStatus, String category) {
        this._id = id;
        this._name = name;
        this._description = description;
        this._completionStatus = completionStatus;
        this._category = category;
    }

    // Getters and Setters
    public T getId() {
        return _id;
    }

    public void setId(T id) {
        this._id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        this._description = description;
    }

    public String getCompletionStatus() {
        return _completionStatus;
    }

    public void setCompletionStatus(String completionStatus) {
        this._completionStatus = completionStatus;
    }

    public String getCategory() {
        return _category;
    }

    public void setCategory(String category) {
        this._category = category;
    }
}
