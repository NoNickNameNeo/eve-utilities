/*
 * Copyright (C) 2016 Khadajico
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
package com.arrggh.eve.blueprint.model;

import lombok.Data;

@Data
public class EveSkill {
    private int typeId;
    private String name;
    private int level;
}
