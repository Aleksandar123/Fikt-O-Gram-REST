package instagram.model.base;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by aleksandar on 24.1.17.
 */
public class ModelBase implements Serializable {
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
