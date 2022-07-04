package ru.prooftechit.smh.notification.channels.email;

import java.nio.charset.StandardCharsets;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.prooftechit.smh.api.email.Email;
import ru.prooftechit.smh.configuration.properties.MailProperties;

/**
 * @author Roman Zdoronok
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class EmailMessageSender {

    private final JavaMailSender javaMailSender;
    private final MailProperties mailProperties;

    public void send(Email email, String to) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setTo(to);
            helper.setSubject(email.getSubject());
            helper.setText(email.getHtmlBody(), true);
            helper.setFrom(mailProperties.getFrom(), mailProperties.getFromAlias());
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("Ошибка отправки письма", e);
        }
    }
}
