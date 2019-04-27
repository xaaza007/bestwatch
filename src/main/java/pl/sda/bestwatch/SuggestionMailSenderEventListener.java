package pl.sda.bestwatch;

import org.springframework.context.event.EventListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SuggestionMailSenderEventListener {
    private static final boolean SEND_MAIL = true;
    private SubscribersRepository subscribersRepository;
    private MailSender mailSender;

    public SuggestionMailSenderEventListener(SubscribersRepository subscribersRepository, MailSender mailSender) {
        this.subscribersRepository = subscribersRepository;
        this.mailSender = mailSender;
    }

    @EventListener
    @Async
    public void sendEmail(SuggestionAdded event) throws InterruptedException {
        if (SEND_MAIL)
            subscribersRepository.findAll().forEach(subscriber -> sendMail(subscriber, event.getSuggestion()));
    }

    private void sendMail(Subscriber subscriber, Suggestion suggestion) {
        Movie movie = suggestion.getMovie();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Suggestion");
        message.setTo(subscriber.getEmailAddress());
        message.setText(movie.getTitle() + ", " + movie.getLinkToMovie() + ", "
                + suggestion.getSuggestionAuthor());
        mailSender.send(message);
    }
}
