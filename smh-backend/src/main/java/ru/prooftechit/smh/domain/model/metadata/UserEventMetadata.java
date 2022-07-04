package ru.prooftechit.smh.domain.model.metadata;

import java.io.Serial;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.prooftechit.smh.domain.model.UserEvent;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "user_event_meta", schema = "smarthouse")
public class UserEventMetadata extends EntityMetadata<UserEvent> {

    @Serial
    private static final long serialVersionUID = 2988455203462535288L;

}
