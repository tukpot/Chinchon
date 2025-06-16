package proyectos.poo2022.chinchon.utilidades;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import proyectos.poo2022.chinchon.enumerados.Palo;
import proyectos.poo2022.chinchon.modelo.Carta;

public class Tests {

    public static void runAllTests() {
        testListasCerrables();
        System.out.println("");
        testCartasNoUsadas();
    }

    public static void testListasCerrables() {
        class ListaCerrableTest {
            String nombre;
            List<Carta> cartas;

            ListaCerrableTest(String nombre, List<Carta> cartas) {
                this.nombre = nombre;
                this.cartas = cartas;
            }
        }

        List<ListaCerrableTest> listasCerrables = List.of(
                new ListaCerrableTest("Escala de 7 cartas", List.of(
                        new Carta(Palo.ORO, 1), new Carta(Palo.ORO, 2), new Carta(Palo.ORO, 3),
                        new Carta(Palo.ORO, 4), new Carta(Palo.ORO, 5), new Carta(Palo.ORO, 6),
                        new Carta(Palo.ORO, 7))),
                new ListaCerrableTest("2 escalas (4 y 3 cartas)", List.of(
                        new Carta(Palo.ORO, 1), new Carta(Palo.ORO, 2), new Carta(Palo.ORO, 3), new Carta(Palo.ORO, 4),
                        new Carta(Palo.ESPADA, 5), new Carta(Palo.ESPADA, 6), new Carta(Palo.ESPADA, 7))),
                new ListaCerrableTest("Escala de 4 + trio", List.of(
                        new Carta(Palo.ORO, 3), new Carta(Palo.ORO, 4), new Carta(Palo.ORO, 5), new Carta(Palo.ORO, 6),
                        new Carta(Palo.ESPADA, 9), new Carta(Palo.COPA, 9), new Carta(Palo.BASTO, 9))),
                new ListaCerrableTest("Trío + cuarteto", List.of(
                        new Carta(Palo.ORO, 7), new Carta(Palo.ESPADA, 7), new Carta(Palo.COPA, 7),
                        new Carta(Palo.BASTO, 8), new Carta(Palo.ORO, 8), new Carta(Palo.COPA, 8),
                        new Carta(Palo.ESPADA, 8))),
                new ListaCerrableTest("Escala de 4 + trío", List.of(
                        new Carta(Palo.ESPADA, 5), new Carta(Palo.ESPADA, 6), new Carta(Palo.ESPADA, 7),
                        new Carta(Palo.ESPADA, 8),
                        new Carta(Palo.ORO, 10), new Carta(Palo.COPA, 10), new Carta(Palo.BASTO, 10))),
                new ListaCerrableTest("Trío y escala que comparten cartas, se puede", List.of(
                        new Carta(Palo.ORO, 5), new Carta(Palo.ORO, 6), new Carta(Palo.ORO, 7), new Carta(Palo.ORO, 8),
                        new Carta(Palo.COPA, 5), new Carta(Palo.ESPADA, 5), new Carta(Palo.BASTO, 9))),
                new ListaCerrableTest("Trío y 2 escalas que comparten cartas, se puede, 1 sobra", List.of(
                        new Carta(Palo.ORO, 5), new Carta(Palo.ORO, 6), new Carta(Palo.ORO, 7),
                        new Carta(Palo.BASTO, 4), new Carta(Palo.BASTO, 5), new Carta(Palo.BASTO, 6),
                        new Carta(Palo.COPA, 5))));

        List<ListaCerrableTest> listasNoCerrables = List.of(
                new ListaCerrableTest("Trío y escala que comparten carta, no se puede", List.of(
                        new Carta(Palo.ORO, 5), new Carta(Palo.ORO, 6), new Carta(Palo.ORO, 7),
                        new Carta(Palo.COPA, 5), new Carta(Palo.BASTO, 9), new Carta(Palo.ESPADA, 2),
                        new Carta(Palo.ESPADA, 5))),
                new ListaCerrableTest("Escala incompleta", List.of(
                        new Carta(Palo.ORO, 3), new Carta(Palo.ORO, 5), new Carta(Palo.ORO, 6), new Carta(Palo.ORO, 7),
                        new Carta(Palo.COPA, 2), new Carta(Palo.ESPADA, 4), new Carta(Palo.BASTO, 9))),
                new ListaCerrableTest("Dos pares y carta suelta", List.of(
                        new Carta(Palo.ORO, 2), new Carta(Palo.COPA, 2), new Carta(Palo.ORO, 9),
                        new Carta(Palo.COPA, 9), new Carta(Palo.ESPADA, 7), new Carta(Palo.BASTO, 11),
                        new Carta(Palo.BASTO, 3))));

        System.out.println("=== Manos Cerrables ===");
        for (ListaCerrableTest test : listasCerrables) {
            boolean resultado = ComprobadorMano.esListaCerrable(test.cartas);
            System.out.println(test.nombre + ": " + (resultado ? "✅ PASA" : "❌ FALLA"));
        }
        System.out.println("");
        System.out.println("=== Manos No Cerrables ===");
        for (ListaCerrableTest test : listasNoCerrables) {
            boolean resultado = ComprobadorMano.esListaCerrable(test.cartas);
            System.out.println(test.nombre + ": " + (!resultado ? "✅ PASA" : "❌ FALLA"));

        }
    }

    public static void testCartasNoUsadas() {
        class CasoTest {
            String nombre;
            List<Carta> mano;
            Set<Carta> esperadasNoUsadas;

            CasoTest(String nombre, List<Carta> mano, Set<Carta> esperadasNoUsadas) {
                this.nombre = nombre;
                this.mano = mano;
                this.esperadasNoUsadas = esperadasNoUsadas;
            }
        }

        List<CasoTest> casos = List.of(
                new CasoTest("Escalera de 2 cartas (no válida), resto sueltas", List.of(
                        new Carta(Palo.ORO, 3), new Carta(Palo.ORO, 4),
                        new Carta(Palo.BASTO, 1), new Carta(Palo.COPA, 5),
                        new Carta(Palo.ESPADA, 7), new Carta(Palo.ESPADA, 11),
                        new Carta(Palo.COPA, 12)),
                        Set.of(
                                new Carta(Palo.ORO, 3), new Carta(Palo.ORO, 4),
                                new Carta(Palo.BASTO, 1), new Carta(Palo.COPA, 5),
                                new Carta(Palo.ESPADA, 7), new Carta(Palo.ESPADA, 11),
                                new Carta(Palo.COPA, 12))),

                new CasoTest("Escalera de 3 cartas válida", List.of(
                        new Carta(Palo.ORO, 3), new Carta(Palo.ORO, 4), new Carta(Palo.ORO, 5),
                        new Carta(Palo.BASTO, 9), new Carta(Palo.COPA, 11),
                        new Carta(Palo.ESPADA, 7), new Carta(Palo.COPA, 2)),
                        Set.of(
                                new Carta(Palo.BASTO, 9), new Carta(Palo.COPA, 11),
                                new Carta(Palo.ESPADA, 7), new Carta(Palo.COPA, 2))),

                new CasoTest("Escalera de 5 cartas válida", List.of(
                        new Carta(Palo.BASTO, 2), new Carta(Palo.BASTO, 3), new Carta(Palo.BASTO, 4),
                        new Carta(Palo.BASTO, 5), new Carta(Palo.BASTO, 6),
                        new Carta(Palo.COPA, 1), new Carta(Palo.ESPADA, 7)),
                        Set.of(
                                new Carta(Palo.COPA, 1), new Carta(Palo.ESPADA, 7))),

                new CasoTest("Dos tríos", List.of(
                        new Carta(Palo.ORO, 7), new Carta(Palo.ESPADA, 7), new Carta(Palo.COPA, 7),
                        new Carta(Palo.ORO, 9), new Carta(Palo.COPA, 9), new Carta(Palo.BASTO, 9),
                        new Carta(Palo.ESPADA, 5)),
                        Set.of(
                                new Carta(Palo.ESPADA, 5))),

                new CasoTest("Un trío y un cuarteto", List.of(
                        new Carta(Palo.ORO, 6), new Carta(Palo.ESPADA, 6), new Carta(Palo.COPA, 6),
                        new Carta(Palo.BASTO, 8), new Carta(Palo.COPA, 8), new Carta(Palo.ORO, 8),
                        new Carta(Palo.ESPADA, 8)),
                        Set.of()),

                new CasoTest("Trío y escalera", List.of(
                        new Carta(Palo.COPA, 3), new Carta(Palo.COPA, 4), new Carta(Palo.COPA, 5),
                        new Carta(Palo.ESPADA, 11), new Carta(Palo.COPA, 11), new Carta(Palo.BASTO, 11),
                        new Carta(Palo.ORO, 1)),
                        Set.of(
                                new Carta(Palo.ORO, 1))));

        System.out.println("=== Testing cartasNoUsadas ===");
        for (CasoTest caso : casos) {
            List<Carta> resultado = ComprobadorMano.cartasNoUsadas(caso.mano);
            Set<Carta> resultadoSet = new HashSet<>(resultado);
            boolean pasa = resultadoSet.equals(caso.esperadasNoUsadas);
            System.out.println(caso.nombre + ": " + (pasa ? "✅ PASA" : "❌ FALLA"));
            if (!pasa) {
                System.out.println("  Esperado: " + caso.esperadasNoUsadas);
                System.out.println("  Obtenido: " + resultadoSet);
            }

        }
    }

}
