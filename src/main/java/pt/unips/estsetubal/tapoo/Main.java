package pt.unips.estsetubal.tapoo;

import pt.unips.estsetubal.tapoo.adt.Map;
import pt.unips.estsetubal.tapoo.adt.MapBST;

public class Main {
    public static void main(String[] args) {
        Map<Integer, String> map = new MapBST<>();
        int[] keys = {8, 3, 12, 1, 6, 10, 14};
        String[] values = {"Ana", "Pedro", "Jose", "Jose", "Beatriz", "Rui", "Luis"};
        for (int i = 0; i < keys.length; i++) map.put(keys[i], values[i]);
        System.out.println("Mapa inicial: " + map);
        System.out.println("Chaves em ordem: " + map.keys());
        System.out.println("Valores por ordem das chaves: " + map.values());
        System.out.println("Valor anterior da chave 6: " + map.put(6, "Alberto"));
        System.out.println("Valor atual da chave 6: " + map.get(6));
        for (int key : new int[]{1, 12, 3, 8}) {
            System.out.println("Remoção de " + key + ": " + map.remove(key));
            System.out.println("Chaves: " + map.keys());
        }
    }
}
