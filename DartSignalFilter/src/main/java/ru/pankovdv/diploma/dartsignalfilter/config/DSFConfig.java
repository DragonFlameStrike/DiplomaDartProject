package ru.pankovdv.diploma.dartsignalfilter.config;

import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public class DSFConfig {

    Long currentStation;

    Long currentEvent;
}
