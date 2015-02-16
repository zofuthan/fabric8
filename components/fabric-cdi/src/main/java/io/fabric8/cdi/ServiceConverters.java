/*
 * Copyright 2005-2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package io.fabric8.cdi;


import io.fabric8.cdi.annotations.Service;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import java.net.MalformedURLException;
import java.net.URL;

import static io.fabric8.cdi.KubernetesHolder.KUBERNETES;

public class ServiceConverters {

    @Produces
    @Service
    public String serviceToString(InjectionPoint injectionPoint) {
        Annotated annotated = injectionPoint.getAnnotated();
        Service service = annotated.getAnnotation(Service.class);
        io.fabric8.kubernetes.api.model.Service srv = KUBERNETES.getService(service.value());
        return (srv.getProtocol() + "://" + srv.getPortalIP() + ":" + srv.getPort()).toLowerCase();
    }


    @Produces
    @Service
    public URL serviceToUrl(InjectionPoint injectionPoint) {
        try {
            return new URL(serviceToString(injectionPoint));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}