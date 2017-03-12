package home.adipopescu.springbootquickstart.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopicController {

    @Autowired
    private TopicService topicService;

    @RequestMapping(
            path = "/topics",
            method = RequestMethod.GET,
            produces = "application/json")
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }

    @RequestMapping(
            path = "/topics/{id}",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity getTopic(@PathVariable String id) {
        try {
            Topic topic = topicService.getSingleTopic(id);
            if (topic != null) {
                return new ResponseEntity<>(topic, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } catch(MultipleResultException mrEx) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            path = "/topics",
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json")
    public ResponseEntity createTopic(@RequestBody Topic topic) {
        topicService.createTopic(topic);
        return new ResponseEntity<>(topic, HttpStatus.OK);
    }

    @RequestMapping(
            path = "/topics/{id}",
            method = RequestMethod.PUT,
            produces = "application/json",
            consumes = "application/json")
    public ResponseEntity updateTopic(@PathVariable String id,
                                      @RequestBody Topic topic) {
        try {
            topicService.updateTopic(id, topic);
            return new ResponseEntity<>(topic, HttpStatus.OK);
        } catch(ResultNotFoundException nfEx) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(
            path = "/topics/{id}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    public ResponseEntity deleteTopic(@PathVariable String id) {
        try {
            topicService.deleteTopic(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(ResultNotFoundException nfEx) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        catch(MultipleResultException mrEx) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }
}
