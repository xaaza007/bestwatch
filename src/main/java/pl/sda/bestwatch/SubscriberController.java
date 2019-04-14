package pl.sda.bestwatch;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscribers")
public class SubscriberController {

    private SubscribersRepository subscribersRepository;

    public SubscriberController(SubscribersRepository subscribersRepository) {
        this.subscribersRepository = subscribersRepository;
    }

    @PostMapping
    public void subscribe(@RequestBody Subscriber subscriber){
        subscribersRepository.save(subscriber);
    }

}
