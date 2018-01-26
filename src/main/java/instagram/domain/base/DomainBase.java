package instagram.domain.base;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by aleksandar on 24.1.17.
 */
public class DomainBase implements Serializable {
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
