package pt.isel.icd.user.logic;

/**
 * Perfil de um utilizador com os respetivos dados e estatisticas.
 *
 * Campos novos do TP02:
 *  - fullName: nome completo (pesquisa de adversarios por AutoComplete);
 *  - preferredColor: cor de fundo preferida do ecra de jogo (#RRGGBB);
 *  - totalGames: jogos concluidos (para o tempo medio);
 *  - totalTimeMillis: tempo de jogo acumulado (medio = total/totalGames).
 *
 * "nationality" passa a ser um codigo ISO 3166-1 alfa-2 (ex.: PT) para se poder
 * derivar a bandeira no quadro de honra.
 */
public record Profile(
    String username,
    String fullName,
    String nationality,
    int age,
    String photo,
    String preferredColor,
    int wins,
    int losses,
    int totalGames,
    long totalTimeMillis
) {
    /** Cor de fundo por omissao quando o perfil ainda nao a define. */
    public static final String DEFAULT_COLOR = "#FFFFFF";

    /**
     * Devolve uma copia com os campos editaveis substituidos, preservando as
     * estatisticas (wins/losses/totalGames/totalTimeMillis).
     */
    public Profile withEdits(
        String fullName,
        String nationality,
        int age,
        String photo,
        String preferredColor
    ) {
        return new Profile(
            username,
            fullName,
            nationality,
            age,
            photo,
            preferredColor,
            wins,
            losses,
            totalGames,
            totalTimeMillis
        );
    }

    /**
     * Devolve uma copia com o resultado de um jogo aplicado: incrementa
     * vitorias/derrotas, soma um jogo e acumula o tempo decorrido.
     */
    public Profile withGameResult(
        boolean won,
        boolean lost,
        long durationMillis
    ) {
        return new Profile(
            username,
            fullName,
            nationality,
            age,
            photo,
            preferredColor,
            wins + (won ? 1 : 0),
            losses + (lost ? 1 : 0),
            totalGames + 1,
            totalTimeMillis + Math.max(0, durationMillis)
        );
    }

    /** Tempo medio por jogo, em milissegundos (0 se ainda nao jogou). */
    public double averageTimeMillis() {
        return totalGames == 0 ? 0.0 : (double) totalTimeMillis / totalGames;
    }
}
