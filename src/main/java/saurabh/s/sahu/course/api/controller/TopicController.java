package saurabh.s.sahu.course.api.controller;

import org.springframework.web.bind.annotation.*;
import saurabh.s.sahu.course.api.model.Topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {


    List<Topic> topics = new ArrayList<>(Arrays.asList(
            new Topic("java", "Java Spring", "Java Spring Description."),
            new Topic("spring", "Spring Framework", "Spring Framework Description."),
            new Topic("node", "Node JS", "Node JS Description Description."),
            new Topic("sql", "Database SQL", "Database SQL Description.")));
    List<String> strings = new ArrayList<>(){};

    @GetMapping()
    public List<Topic> getTopics() {
        return topics;
    }

    @GetMapping("/{id}")
    public Topic getTopicById(@PathVariable(name = "id") String topicId) {
        return topics.stream()
                .filter(topic -> topic.getId().equalsIgnoreCase(topicId))
                .findFirst()
                .orElse(null);
    }

    @PostMapping()
    public void createTopic(@RequestBody Topic topic) {
        topics.add(topic);
    }

    @PutMapping()
    public void updateTopic(@RequestBody Topic topic) {
        topics.stream()
                .filter(t -> t.getId().equalsIgnoreCase(topic.getId()))
                .forEach(t -> {
                    t.setName(topic.getName());
                    t.setDescription(topic.getDescription());
                });
    }

    @DeleteMapping("/{id}")
    public void deleteTopicById(@PathVariable(name = "id") String topicId) {
        topics.removeIf(topic -> topic.getId().equalsIgnoreCase(topicId));
    }

}
