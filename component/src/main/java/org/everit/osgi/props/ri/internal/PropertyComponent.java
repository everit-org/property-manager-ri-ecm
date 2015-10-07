/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.biz)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.everit.osgi.props.ri.internal;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentMap;

import org.everit.osgi.ecm.annotation.Activate;
import org.everit.osgi.ecm.annotation.Component;
import org.everit.osgi.ecm.annotation.ConfigurationPolicy;
import org.everit.osgi.ecm.annotation.Deactivate;
import org.everit.osgi.ecm.annotation.ManualService;
import org.everit.osgi.ecm.annotation.ServiceRef;
import org.everit.osgi.ecm.annotation.attribute.StringAttribute;
import org.everit.osgi.ecm.annotation.attribute.StringAttributes;
import org.everit.osgi.ecm.component.ComponentContext;
import org.everit.osgi.ecm.extender.ECMExtenderConstants;
import org.everit.osgi.props.ri.PropertyConstants;
import org.everit.persistence.querydsl.support.QuerydslSupport;
import org.everit.props.PropertyManager;
import org.everit.props.ri.PropertyManagerImpl;
import org.everit.transaction.propagator.TransactionPropagator;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;

import aQute.bnd.annotation.headers.ProvideCapability;

/**
 * ECM component for {@link PropertyManager} interface based on {@link PropertyManagerImpl}.
 */
@Component(componentId = PropertyConstants.SERVICE_FACTORY_PID_PROPERTY_MANAGER,
    configurationPolicy = ConfigurationPolicy.FACTORY, label = "Everit Property Manager",
    description = "By configuring this component, the user will get a persistent PropertyManager "
        + "as an OSGi service.")
@ProvideCapability(ns = ECMExtenderConstants.CAPABILITY_NS_COMPONENT,
    value = ECMExtenderConstants.CAPABILITY_ATTR_CLASS + "=${@class}")
@StringAttributes({
    @StringAttribute(attributeId = Constants.SERVICE_DESCRIPTION,
        defaultValue = PropertyConstants.DEFAULT_SERVICE_DESCRIPTION,
        priority = PropertyComponent.P01_SERVICE_DESCRITION,
        label = "Service Description",
        description = "The description of this component configuration. It is used to easily "
            + "identify the service registered by this component.") })
@ManualService(PropertyManager.class)
public class PropertyComponent {

  public static final int P01_SERVICE_DESCRITION = 1;

  public static final int P02_QUERYDSL_SUPPORT = 2;

  public static final int P03_TRANSACTION_PROPAGATOR = 3;

  public static final int P04_CACHE = 4;

  private ConcurrentMap<String, String> cache;

  private QuerydslSupport querydslSupport;

  private ServiceRegistration<PropertyManager> serviceRegistration;

  private TransactionPropagator transactionPropagator;

  /**
   * Component activator method.
   */
  @Activate
  public void activate(final ComponentContext<PropertyComponent> componentContext) {
    PropertyManager propertyManager =
        new PropertyManagerImpl(cache, querydslSupport, transactionPropagator);

    Dictionary<String, Object> serviceProperties =
        new Hashtable<>(componentContext.getProperties());
    serviceRegistration =
        componentContext.registerService(PropertyManager.class, propertyManager, serviceProperties);
  }

  /**
   * Component deactivate method.
   */
  @Deactivate
  public void deactivate() {
    if (serviceRegistration != null) {
      serviceRegistration.unregister();
    }
  }

  @ServiceRef(attributeId = PropertyConstants.PROP_CACHE_TARGET,
      defaultValue = PropertyConstants.DEFAULT_CACHE_TARGET, attributePriority = P04_CACHE,
      label = "Cache filter",
      description = "The OSGi filter expression to select the right Cache "
          + "(java.util.concurrent.ConcurrentMap).")
  public void setCache(final ConcurrentMap<String, String> cache) {
    this.cache = cache;
  }

  @ServiceRef(attributeId = PropertyConstants.PROP_QUERYDSL_SUPPORT_TARGET, defaultValue = "",
      attributePriority = P02_QUERYDSL_SUPPORT, label = "Querydsl Support OSGi filter",
      description = "OSGi Service filter expression for QueryDSLSupport instance.")
  public void setQuerydslSupport(final QuerydslSupport querydslSupport) {
    this.querydslSupport = querydslSupport;
  }

  @ServiceRef(attributeId = PropertyConstants.PROP_TRANSACTION_HELPER_TARGET, defaultValue = "",
      attributePriority = P03_TRANSACTION_PROPAGATOR,
      label = "Transaction Propagator OSGi filter",
      description = "OSGi Service filter expression for TransactionPropagator instance.")
  public void setTransactionPropagator(final TransactionPropagator transactionPropagator) {
    this.transactionPropagator = transactionPropagator;
  }

}
