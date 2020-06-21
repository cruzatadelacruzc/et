package com.example.demo;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArchTest {
    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.example.demo");

        noClasses()
                .that()
                .resideInAnyPackage("..service..")
                .or()
                .resideInAnyPackage("..repository..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..com.example.demo.web..")
                .because("Services and repository should not depend on web layer")
                .check(importedClasses);
    }
}