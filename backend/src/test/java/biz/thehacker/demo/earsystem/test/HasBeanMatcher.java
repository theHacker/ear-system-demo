package biz.thehacker.demo.earsystem.test;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

/**
 * Hamcrest matcher to check if a Spring {@link ApplicationContext} contains a certain bean.
 */
public class HasBeanMatcher extends TypeSafeMatcher<ApplicationContext> {

    private final Class<?> expectedBeanClass;

    public HasBeanMatcher(Class<?> expectedBeanClass) {
        this.expectedBeanClass = expectedBeanClass;
    }

    @Override
    protected boolean matchesSafely(ApplicationContext applicationContext) {
        try {
            applicationContext.getBean(expectedBeanClass);
            return true;
        } catch (NoSuchBeanDefinitionException e) {
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("contains a bean of type " + expectedBeanClass.getName());
    }

    @Override
    protected void describeMismatchSafely(ApplicationContext item, Description mismatchDescription) {
        mismatchDescription.appendText("does not contain a bean of type " + expectedBeanClass.getName());
    }

    public static <T> Matcher<ApplicationContext> hasBean(Class<T> beanClass) {
        return new HasBeanMatcher(beanClass);
    }
}
