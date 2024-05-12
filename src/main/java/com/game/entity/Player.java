package com.game.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "player", schema = "rpg")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = "selectAllPlayerCount", query = "select count(*) from Player ")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "title")
    private String title;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, name = "race")
    private Race race;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, name = "profession")
    private Profession profession;

    @Column(nullable = false, name = "birthday")
    private Date birthday;

    @Column(nullable = false, name = "banned")
    private Boolean banned;

    @Column(nullable = false, name = "level")
    private Integer level;

}