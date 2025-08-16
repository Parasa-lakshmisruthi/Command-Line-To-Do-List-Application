/**
 * Task.java
 * ఈ క్లాస్ ఒకే టాస్క్‌కు సంబంధించిన వివరాలను (వివరణ మరియు స్టేటస్) స్టోర్ చేస్తుంది.
 */
public class Task {
    private String description; // టాస్క్ యొక్క వివరణ
    private boolean isDone;     // టాస్క్ పూర్తయిందా లేదా?

    // కొత్త టాస్క్‌ను క్రియేట్ చేయడానికి కన్‌స్ట్రక్టర్
    public Task(String description) {
        this.description = description;
        this.isDone = false; // కొత్తగా క్రియేట్ చేసిన టాస్క్ 'కంప్లీట్' అవ్వలేదు
    }

    // ఫైల్ నుండి టాస్క్‌ను లోడ్ చేయడానికి వాడే కన్‌స్ట్రక్టర్
    // **ఈ కన్‌స్ట్రక్టర్ మీ పాత కోడ్‌లో లేదు, అందుకే ఎర్రర్ వచ్చింది.**
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    // Getters
    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    // టాస్క్‌ను 'కంప్లీట్'గా మార్చడానికి మెథడ్
    public void markAsDone() {
        this.isDone = true;
    }

    // కన్సోల్‌లో టాస్క్‌ను ఎలా చూపించాలో డిఫైన్ చేస్తుంది
    @Override
    public String toString() {
        // పూర్తయితే [X] అని, లేకపోతే [ ] అని చూపిస్తుంది
        return (isDone ? "[X] " : "[ ] ") + description;
    }

    // టాస్క్‌ను ఫైల్‌లో సేవ్ చేయడానికి వాడే ఫార్మాట్
    public String toFileString() {
        // ఉదాహరణ: "Buy milk,false"
        return description + "," + isDone;
    }
}
