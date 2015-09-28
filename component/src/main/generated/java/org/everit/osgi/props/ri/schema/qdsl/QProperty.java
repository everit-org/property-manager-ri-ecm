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
/**
 * This file is part of Everit - Property Manager RI.
 *
 * Everit - Property Manager RI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Everit - Property Manager RI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Everit - Property Manager RI.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.everit.osgi.props.ri.schema.qdsl;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.mysema.query.sql.ColumnMetadata;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.StringPath;

/**
 * QProperty is a Querydsl query type for QProperty
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QProperty extends com.mysema.query.sql.RelationalPathBase<QProperty> {

    public class PrimaryKeys {

        public final com.mysema.query.sql.PrimaryKey<QProperty> propertyPk = createPrimaryKey(key);

    }

    public static final QProperty property = new QProperty("prop_property");

    private static final long serialVersionUID = 634441093;

    public final StringPath key = createString("key");

    public final PrimaryKeys pk = new PrimaryKeys();

    public final StringPath value = createString("value");

    public QProperty(Path<? extends QProperty> path) {
        super(path.getType(), path.getMetadata(), "org.everit.osgi.props.ri", "prop_property");
        addMetadata();
    }

    public QProperty(PathMetadata<?> metadata) {
        super(QProperty.class, metadata, "org.everit.osgi.props.ri", "prop_property");
        addMetadata();
    }

    public QProperty(String variable) {
        super(QProperty.class, forVariable(variable), "org.everit.osgi.props.ri", "prop_property");
        addMetadata();
    }

    public QProperty(String variable, String schema, String table) {
        super(QProperty.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(key, ColumnMetadata.named("key").ofType(12).withSize(255).notNull());
        addMetadata(value, ColumnMetadata.named("value").ofType(12).withSize(2000).notNull());
    }

}
