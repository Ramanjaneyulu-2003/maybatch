package com.adobe.aem.may.batch.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "EmpConfiguration")
public @interface EmpConfiguration {
    @AttributeDefinition(
        name = "raju",
        description ="Raju isa good boy",
        required = true
    )
    public String empName();
    @AttributeDefinition(
        name = "300",
        description ="this is lucky number",
        required = true
    )
    public int empId();
    @AttributeDefinition(
        name = "200000",
        description ="good sal",
        required = true
    )
    public double empSal();

}
