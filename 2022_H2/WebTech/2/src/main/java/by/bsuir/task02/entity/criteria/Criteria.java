package by.bsuir.task02.entity.criteria;

import java.util.HashMap;
import java.util.Map;

public class Criteria {
    private final Map<String, Object> criteriaMap = new HashMap<>();

    private final String groupSearchName;

    public Criteria(String groupSearchName) {
        this.groupSearchName = groupSearchName;
    }

    public String getGroupSearchName() {
        return groupSearchName;
    }

    public Map<String, Object> getCriteriaMap() {
        return criteriaMap;
    }

    public void add(String searchCriteria, Object value) {
        criteriaMap.put(searchCriteria, value);
    }
}
