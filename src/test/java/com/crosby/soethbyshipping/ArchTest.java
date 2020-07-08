package com.crosby.soethbyshipping;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.crosby.soethbyshipping");

        noClasses()
            .that()
            .resideInAnyPackage("com.crosby.soethbyshipping.service..")
            .or()
            .resideInAnyPackage("com.crosby.soethbyshipping.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.crosby.soethbyshipping.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
