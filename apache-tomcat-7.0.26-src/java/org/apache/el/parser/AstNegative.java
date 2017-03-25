/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/* Generated By:JJTree: Do not edit this line. AstNegative.java */

package org.apache.el.parser;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.el.ELException;

import org.apache.el.lang.EvaluationContext;


/**
 * @author Jacob Hookom [jacob@hookom.net]
 * @version $Id: AstNegative.java 1049572 2010-12-15 14:54:23Z markt $
 */
public final class AstNegative extends SimpleNode {
    public AstNegative(int id) {
        super(id);
    }

    @Override
    public Class<?> getType(EvaluationContext ctx)
            throws ELException {
        return Number.class;
    }

    @Override
    public Object getValue(EvaluationContext ctx)
            throws ELException {
        Object obj = this.children[0].getValue(ctx);

        if (obj == null) {
            return Long.valueOf(0);
        }
        if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).negate();
        }
        if (obj instanceof BigInteger) {
            return ((BigInteger) obj).negate();
        }
        if (obj instanceof String) {
            if (isStringFloat((String) obj)) {
                return new Double(-Double.parseDouble((String) obj));
            }
            return Long.valueOf(-Long.parseLong((String) obj));
        }
        if (obj instanceof Long) {
            return Long.valueOf(-((Long) obj).longValue());
        }
        if (obj instanceof Double) {
            return new Double(-((Double) obj).doubleValue());
        }
        if (obj instanceof Integer) {
            return Integer.valueOf(-((Integer) obj).intValue());
        }
        if (obj instanceof Float) {
            return new Float(-((Float) obj).floatValue());
        }
        if (obj instanceof Short) {
            return Short.valueOf((short) -((Short) obj).shortValue());
        }
        if (obj instanceof Byte) {
            return Byte.valueOf((byte) -((Byte) obj).byteValue());
        }
        Long num = (Long) coerceToNumber(obj, Long.class);
        return Long.valueOf(-num.longValue());
    }
}
