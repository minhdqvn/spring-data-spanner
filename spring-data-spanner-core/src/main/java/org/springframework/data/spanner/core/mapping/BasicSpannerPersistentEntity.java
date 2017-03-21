/*
 * Copyright 2017 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.data.spanner.core.mapping;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mapping.model.BasicPersistentEntity;
import org.springframework.data.util.TypeInformation;
import org.springframework.util.StringUtils;

/**
 * Created by rayt on 3/14/17.
 */
public class BasicSpannerPersistentEntity<T> extends BasicPersistentEntity<T, SpannerPersistentProperty> implements
    SpannerPersistentEntity<T>, ApplicationContextAware {

  private final String tableName;

  public BasicSpannerPersistentEntity(TypeInformation<T> information) {
    super(information);

    Class<?> rawType = information.getType();
    String fallback = extractTableNameFromClass(rawType);

    Table table = this.findAnnotation(Table.class);
    if (table != null) {
      this.tableName = StringUtils.hasText(table.value()) ? table.value() : fallback;
    } else {
      this.tableName = fallback;
    }
  }

  protected String extractTableNameFromClass(Class<?> entityClass) {
    return StringUtils.uncapitalize(entityClass.getSimpleName());
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
  }

  @Override
  public String tableName() {
    return this.tableName;
  }

  @Override
  public void verify() {
    super.verify();
  }
}
