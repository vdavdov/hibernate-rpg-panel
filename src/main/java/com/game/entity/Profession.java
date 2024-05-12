package com.game.entity;

import jakarta.persistence.Table;

@Table(name = "profession")
public enum Profession {
    WARRIOR,
    ROGUE,
    SORCERER,
    CLERIC,
    PALADIN,
    NAZGUL,
    WARLOCK,
    DRUID
}