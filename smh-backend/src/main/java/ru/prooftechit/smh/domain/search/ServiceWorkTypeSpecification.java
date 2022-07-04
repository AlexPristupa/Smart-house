package ru.prooftechit.smh.domain.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.prooftechit.smh.domain.model.ServiceWorkType;
import ru.prooftechit.smh.domain.model.ServiceWorkType_;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ServiceWorkTypeSpecification extends AbstractSearchSpecification<ServiceWorkType> {

    @Override
    public String[] getSearchFields() {
        return new String[]{
            ServiceWorkType_.NAME,
            ServiceWorkType_.DESCRIPTION
        };
    }
}
