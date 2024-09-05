package Project;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private List<Tasks<Integer>> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public void addTask(Tasks<Integer> task) {
        tasks.add(task);
    }

    public void removeTask(Tasks<Integer> task) {
        tasks.remove(task);
    }

    public List<Tasks<Integer>> getTasks() {
        return tasks;
    }
}
