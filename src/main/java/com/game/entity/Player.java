package com.game.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "player", schema = "rpg")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = "selectAllPlayerCount", query = "select count(p) from Player p ")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name", length = 12)
    private String name;

    @Column(nullable = false, name = "title", length = 30)
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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}