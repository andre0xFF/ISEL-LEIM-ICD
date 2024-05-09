package pt.isel.icd.user.management;

import pt.isel.icd.patterns.verticals.Entity;

public record User(String username, String password) implements Entity {

}
