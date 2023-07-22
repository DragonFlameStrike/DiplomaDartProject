package ru.pankovdv.diploma.dartsignalfilter.config;

import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Data
public class DataConfig {
    private boolean isDataExist;
    private String data;
}
