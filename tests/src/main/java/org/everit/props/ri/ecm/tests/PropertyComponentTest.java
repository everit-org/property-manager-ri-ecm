/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.org)
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
package org.everit.props.ri.ecm.tests;

import java.util.Random;

import org.everit.osgi.dev.testrunner.TestRunnerConstants;
import org.everit.osgi.ecm.annotation.Component;
import org.everit.osgi.ecm.annotation.ConfigurationPolicy;
import org.everit.osgi.ecm.annotation.Service;
import org.everit.osgi.ecm.annotation.ServiceRef;
import org.everit.osgi.ecm.annotation.attribute.StringAttribute;
import org.everit.osgi.ecm.annotation.attribute.StringAttributes;
import org.everit.osgi.ecm.extender.ECMExtenderConstants;
import org.everit.props.PropertyManager;
import org.junit.Assert;
import org.junit.Test;

import aQute.bnd.annotation.headers.ProvideCapability;

/**
 * Test for Property Component.
 */
@Component(componentId = "org.everit.props.ri.ecm.tests.PropertyManagerTest",
    configurationPolicy = ConfigurationPolicy.FACTORY)
@ProvideCapability(ns = ECMExtenderConstants.CAPABILITY_NS_COMPONENT,
    value = ECMExtenderConstants.CAPABILITY_ATTR_CLASS + "=${@class}")
@StringAttributes({
    @StringAttribute(attributeId = TestRunnerConstants.SERVICE_PROPERTY_TESTRUNNER_ENGINE_TYPE,
        defaultValue = "junit4"),
    @StringAttribute(attributeId = TestRunnerConstants.SERVICE_PROPERTY_TEST_ID,
        defaultValue = "propertyComponentTest") })
@Service(value = PropertyComponentTest.class)
public class PropertyComponentTest {

  /**
   * The maximum length of the key and value.
   */
  private static final int MAX_LENGTH = 100;

  private PropertyManager propertyManager;

  /**
   * Generating String to the first and second name.
   *
   * @return the random length string.
   */
  private String generateString() {
    Random random = new Random();
    String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    int length = random.nextInt(MAX_LENGTH);
    char[] text = new char[length];
    for (int i = 0; i < length; i++) {
      text[i] = characters.charAt(random.nextInt(characters.length()));
    }
    return new String(text);
  }

  @ServiceRef(defaultValue = "")
  public void setPropertyManager(final PropertyManager propertyManager) {
    this.propertyManager = propertyManager;
  }

  @Test
  public void testNullParameter() {
    try {
      propertyManager.addProperty("test", null);
      Assert.fail("NPE should have been thrown");
    } catch (NullPointerException e) {
      Assert.assertEquals("Null values are not supported!", e.getMessage());
    }

    try {
      propertyManager.updateProperty("test", null);
      Assert.fail("NPE should have been thrown");
    } catch (NullPointerException e) {
      Assert.assertEquals("Null values are not supported!", e.getMessage());
    }

    try {
      propertyManager.addProperty(null, "test");
      Assert.fail("NPE should have been thrown");
    } catch (NullPointerException e) {
      Assert.assertEquals("Null key is not supported!", e.getMessage());
    }

    try {
      propertyManager.updateProperty(null, "test");
      Assert.fail("NPE should have been thrown");
    } catch (NullPointerException e) {
      Assert.assertEquals("Null key is not supported!", e.getMessage());
    }

    try {
      propertyManager.removeProperty(null);
      Assert.fail("NPE should have been thrown");
    } catch (NullPointerException e) {
      Assert.assertEquals("Null key is not supported!", e.getMessage());
    }

    try {
      propertyManager.getProperty(null);
      Assert.fail("NPE should have been thrown");
    } catch (NullPointerException e) {
      Assert.assertEquals("Null key is not supported!", e.getMessage());
    }
  }

  @Test
  public void testPropertyService() {

    String key1 = generateString();
    String value1 = generateString();

    Assert.assertNull(propertyManager.getProperty(key1));

    propertyManager.addProperty(key1, value1);
    Assert.assertNotNull(propertyManager.getProperty(key1));
    Assert.assertEquals(value1, propertyManager.getProperty(key1));

    String value2 = generateString();
    String retString = propertyManager.updateProperty(key1, value2);
    Assert.assertTrue(value1.equals(retString));
    Assert.assertEquals(value2, propertyManager.getProperty(key1));

    String key2 = generateString();
    propertyManager.addProperty(key2, value2);
    Assert.assertEquals(value2, propertyManager.getProperty(key2));

    retString = propertyManager.removeProperty(key2);
    Assert.assertEquals(value2, retString);
    Assert.assertNull(propertyManager.getProperty(key2));

    retString = propertyManager.removeProperty("notexist");
    Assert.assertNull(retString);

    retString = propertyManager.updateProperty("notexist", "dummy");
    Assert.assertNull(retString);
    Assert.assertNull(propertyManager.getProperty("notexist"));
  }
}
