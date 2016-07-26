package sigxcpuyaru.library.config;

import java.nio.channels.OverlappingFileLockException;

import org.springframework.web.servlet.support.
    AbstractAnnotationConfigDispatcherServletInitializer;

public class WebApplicationInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { WebSecurityConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebAppConfig.class };
    }
}
