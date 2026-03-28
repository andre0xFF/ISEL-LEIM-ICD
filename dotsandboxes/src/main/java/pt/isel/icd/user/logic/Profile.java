package pt.isel.icd.user.logic;

/**
 * Represents a user's profile with stats.
 * Includes nationality, age, photo (base64 string), wins, losses.
 */
public record Profile(
    String username,
    String nationality,
    int age,
    String photo,
    int wins,
    int losses
) {}
