package ru.prooftechit.smh.api.email;

import lombok.Data;

/**
 * @author Roman Zdoronok
 */
@Data
public class Email {
    private final String subject;
    private final String htmlBody;
    //TODO: attachments
}
