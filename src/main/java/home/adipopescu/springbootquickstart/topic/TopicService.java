package home.adipopescu.springbootquickstart.topic;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TopicService  {

    private static List<Topic> topics = new ArrayList<>(Arrays.asList(
            new Topic("a", "aaa", "aaa"),
            new Topic("b", "bbb", "bbb"),
            new Topic("c", "ccc", "ccc"),
            new Topic("d", "ddd", "ddd")));

    public List<Topic> getAllTopics() {
        return topics;
    }

    public Topic getSingleTopic(String id) {
        List<Topic> result = topics.stream().filter(t -> t.getId().equals(id)).collect(Collectors.<Topic>toList());
        int size = result.size();
        if(size == 0) {
            return null;
        } else if(size > 1) {
            throw new MultipleResultException();
        } else {
            return result.get(0);
        }
    }

    public void createTopic(Topic topic) {
        topics.add(topic);
    }


    public void updateTopic(String id, Topic updatedTopic) {
        updatedTopic.setId(id);

        for(int i = 0; i < topics.size(); i++) {
            Topic topic = topics.get(i);
            if(topic.getId().equals(id)) {
                topics.set(i, updatedTopic);
                return;
           }
        }
        throw new ResultNotFoundException();
    }

    public void deleteTopic(String id) {
        topics.removeIf(t -> t.getId().equals(id));
    }
}
