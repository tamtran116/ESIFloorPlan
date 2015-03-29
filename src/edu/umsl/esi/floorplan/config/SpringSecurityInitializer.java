package edu.umsl.esi.floorplan.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Created by Tam Tran on 2/22/2015.
 */
public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {
    public SpringSecurityInitializer() {
        super(SecurityConfig.class);
    }
}
