package org.zalando.stups.clients.kio.spring;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

class ResourceUtil {

    static Resource resource(final String resourcename) {
        return new ClassPathResource(resourcename + ".json", ResourceUtil.class);
    }

}
