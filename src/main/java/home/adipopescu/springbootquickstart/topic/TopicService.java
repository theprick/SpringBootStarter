package home.adipopescu.springbootquickstart.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class TopicService  {

    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> getAllTopics() {
        ArrayList<Topic> l = new ArrayList<>();
        topicRepository.findAll().forEach(l::add);
        return l;
    }

    public Topic getSingleTopic(String id) {
        ArrayList<Topic> l = new ArrayList<>();
        topicRepository.findAll(Collections.singleton(id)).forEach(l::add);
        int size = l.size();
        if(size == 0) {
            return null;
        } else if(size > 1) {
            throw new MultipleResultException();
        } else {
            return l.get(0);
        }
    }

    public Topic createTopic(Topic topic) {
        //TODO you may consider to check if we have a topic with an existing id already
        return topicRepository.save(topic);
    }

    //this should be executed in a transaction
    @Transactional
    public Topic updateTopic(String id, Topic updatedTopic) {
        updatedTopic.setId(id);
        if(topicRepository.exists(id)) {
            return topicRepository.save(updatedTopic);
        }
        throw new ResultNotFoundException();
    }

    //TODO here we have org.springframework.dao.EmptyResultDataAccessException: No class home.adipopescu.springbootquickstart.topic.Topic entity with id exists!
    //if the topic is not found
    public void deleteTopic(String id) {
        topicRepository.delete(id);
    }
}
