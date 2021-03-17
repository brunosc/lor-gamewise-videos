package info.gamewise.lor.videos;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import info.gamewise.lor.videos.entity.QVideoJpaEntity;
import info.gamewise.lor.videos.deckcodeextractor.DeckCodeExtractor;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.base.DescribedPredicate.not;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.belongToAnyOf;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class BackendArchitectureTest {

    private static final String PREFIX = "info.gamewise.lor.videos";
    private static final String CONTROLLER = PREFIX + ".controller";
    private static final String DECK_CODE_EXTRACTOR = PREFIX + ".deckcodeextractor";
    private static final String ENTITY = PREFIX + ".entity";
    private static final String SERVICE = PREFIX + ".service";
    private static final String USE_CASE = PREFIX + ".usecase";

    @Test
    void entityShouldBePrivatePackage() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(ENTITY);

        ArchRule rule = classes()
                .that(not(belongToAnyOf(QVideoJpaEntity.class)))
                .should()
                .bePackagePrivate();

        rule.check(importedClasses);
    }

    @Test
    void serviceNameAndPackagePrivate() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(SERVICE);

        ArchRule rule = classes()
                .that()
                .areAnnotatedWith(Service.class)
                .should()
                .haveSimpleNameEndingWith("Service")
                .andShould()
                .bePackagePrivate();

        rule.check(importedClasses);
    }

    @Test
    void controllerShouldBeAnnotatedAndPackagePrivate() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(CONTROLLER);

        ArchRule rule = classes()
                .should()
                .haveSimpleNameEndingWith("Controller")
                .andShould()
                .beAnnotatedWith(RestController.class)
                .andShould()
                .beAnnotatedWith(RequestMapping.class)
                .andShould()
                .bePackagePrivate();

        rule.check(importedClasses);
    }

    @Test
    void deckCodeDecorator() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(DECK_CODE_EXTRACTOR);

        ArchRule rule = classes()
                .that()
                .areNotInterfaces()
                .and()
                .arePublic()
                .should()
                .implement(DeckCodeExtractor.class);

        rule.check(importedClasses);
    }

    @Test
    void useCase() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(USE_CASE);

        ArchRule rule = classes()
                .that()
                .areNotNestedClasses()
                .should()
                .beInterfaces()
                .andShould()
                .haveSimpleNameEndingWith("UseCase");

        rule.check(importedClasses);
    }

}
