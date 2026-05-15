package YouVideo;

import java.util.Comparator;

public class ComparatorByName implements Comparator<String> {
    @Override
    public int compare(String tag1, String tag2) {
        // 1. Primeiro, comparamos ignorando maiúsculas e minúsculas.
        // Assim, "Ação" e "ação" são considerados iguais nesta etapa.
        int compare = tag1.compareToIgnoreCase(tag2);

        // 2. Se as palavras forem iguais (ignorando a caixa),
        // usamos o compareTo normal como desempate para o TreeSet não excluir a tag original.
        if (compare == 0) {
            return tag1.compareTo(tag2);
        }

        return compare;
    }
}

