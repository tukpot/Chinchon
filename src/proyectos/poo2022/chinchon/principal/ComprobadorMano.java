package proyectos.poo2022.chinchon.principal;

import java.util.*;

import proyectos.poo2022.chinchon.enumerados.Palo;

public class ComprobadorMano {

    public static boolean esListaCerrable(List<Carta> mano) {
        if (mano.size() != 7)
            return false;

        List<List<Carta>> combinacionesPosibles = new ArrayList<>();
        combinacionesPosibles.addAll(encontrarTrio(mano));
        combinacionesPosibles.addAll(encontrarEscaleras(mano));

        if (buscarCombinacion(combinacionesPosibles, 7, new ArrayList<>(), 0) != null) {
            return true;
        }

        List<Carta> usadasSeis = buscarCombinacion(combinacionesPosibles, 6, new ArrayList<>(), 0);
        if (usadasSeis != null) {
            Carta sobrante = encontrarCartaSobrante(mano, usadasSeis);
            // System.out.println("Carta sobrante: " + sobrante.getNumero() + " de " +
            // sobrante.getPalo());
            return true;
        }

        return false;
    }

    public static ResultadoCierre esManoCerrable(List<Carta> mano) {
        ResultadoCierre resultadoCierreRecord = null; // por default retorna false, sin mano y sin carta de corte

        for (int i = 0; i < mano.size(); i++) {
            // hacemo copia de la mano sin la carta actual
            List<Carta> copia = new ArrayList<>(mano);
            Carta cartaCierre = mano.get(i);
            copia.remove(i);

            if (esListaCerrable(copia)) {
                // con que una copia cumpla la condición, la mano es cerrable
                Mano manoCerrableActual = Mano.arrayToMano(copia);
                if (resultadoCierreRecord == null) {
                    resultadoCierreRecord = new ResultadoCierre(true, cartaCierre, manoCerrableActual);
                    continue;
                }

                int puntajeRecord = resultadoCierreRecord.mano.calcularPuntajeRestante();

                if (manoCerrableActual.calcularPuntajeRestante() < puntajeRecord) {
                    // si no hay ninguna mano cerrable o si la mano cerrable actual da puntos más
                    // bajos que la guardada, actualizo el resultado
                    resultadoCierreRecord = new ResultadoCierre(true, cartaCierre, manoCerrableActual);
                }
            }

        }
        if (resultadoCierreRecord == null)
            return new ResultadoCierre(false, null, null);

        return resultadoCierreRecord;
    }

    private static Carta encontrarCartaSobrante(List<Carta> mano, List<Carta> usadas) {
        for (Carta carta : mano) {
            if (!usadas.contains(carta)) {
                return carta;
            }
        }
        return null;
    }

    private static List<List<Carta>> encontrarTrio(List<Carta> cartas) {
        Map<Integer, List<Carta>> porNumero = new HashMap<>();
        for (Carta carta : cartas) {
            porNumero.computeIfAbsent(carta.getNumero(), k -> new ArrayList<>()).add(carta);
        }

        List<List<Carta>> grupos = new ArrayList<>();
        for (Map.Entry<Integer, List<Carta>> entrada : porNumero.entrySet()) {
            List<Carta> grupo = entrada.getValue();
            if (grupo.size() >= 3) {
                grupos.addAll(combinar(grupo, 3));
                if (grupo.size() == 4) {
                    grupos.add(new ArrayList<>(grupo));
                }
            }
        }
        return grupos;
    }

    private static List<List<Carta>> encontrarEscaleras(List<Carta> cartas) {
        Map<Palo, List<Carta>> porPalo = new HashMap<>();
        for (Carta carta : cartas) {
            porPalo.computeIfAbsent(carta.getPalo(), k -> new ArrayList<>()).add(carta);
        }

        List<List<Carta>> escalas = new ArrayList<>();
        for (List<Carta> grupo : porPalo.values()) {
            Set<Integer> numerosUnicos = new TreeSet<>();
            for (Carta c : grupo)
                numerosUnicos.add(c.getNumero());
            List<Integer> listaNumeros = new ArrayList<>(numerosUnicos);

            for (int i = 0; i < listaNumeros.size(); i++) {
                List<Carta> secuencia = new ArrayList<>();
                for (int j = i; j < listaNumeros.size(); j++) {
                    if (j > i && listaNumeros.get(j) != listaNumeros.get(j - 1) + 1)
                        break;
                    int num = listaNumeros.get(j);
                    Optional<Carta> opcion = grupo.stream()
                            .filter(c -> c.getNumero() == num && !secuencia.contains(c))
                            .findFirst();
                    if (!opcion.isPresent())
                        break;
                    secuencia.add(opcion.get());
                    if (secuencia.size() >= 3) {
                        escalas.add(new ArrayList<>(secuencia));
                    }
                }
            }
        }
        return escalas;
    }

    private static List<List<Carta>> combinar(List<Carta> elementos, int n) {
        if (n > elementos.size())
            return Collections.emptyList();
        if (n == 1) {
            List<List<Carta>> individuales = new ArrayList<>();
            for (Carta c : elementos) {
                individuales.add(Arrays.asList(c));
            }
            return individuales;
        }

        List<List<Carta>> combinaciones = new ArrayList<>();
        for (int i = 0; i <= elementos.size() - n; i++) {
            Carta cabeza = elementos.get(i);
            List<Carta> cola = elementos.subList(i + 1, elementos.size());
            for (List<Carta> sub : combinar(cola, n - 1)) {
                List<Carta> nueva = new ArrayList<>();
                nueva.add(cabeza);
                nueva.addAll(sub);
                combinaciones.add(nueva);
            }
        }
        return combinaciones;
    }

    private static List<Carta> buscarCombinacion(List<List<Carta>> grupos, int objetivo, List<Carta> usadas,
            int indiceInicial) {
        // Ordenar por tamaño descendente para priorizar escaleras/cuartetos más grandes
        if (indiceInicial == 0) {
            grupos.sort((a, b) -> b.size() - a.size());
        }

        if (usadas.size() == objetivo)
            return usadas;
        if (indiceInicial >= grupos.size() || usadas.size() > objetivo)
            return null;

        for (int i = indiceInicial; i < grupos.size(); i++) {
            List<Carta> grupo = grupos.get(i);

            // Si cualquier carta del grupo ya está en 'usadas', descartamos este grupo
            boolean haySolapamiento = grupo.stream().anyMatch(usadas::contains);
            if (haySolapamiento)
                continue;

            if (usadas.size() + grupo.size() > objetivo)
                continue;

            List<Carta> combinadas = new ArrayList<>(usadas);
            combinadas.addAll(grupo);
            List<Carta> resultado = buscarCombinacion(grupos, objetivo, combinadas, i + 1);
            if (resultado != null)
                return resultado;
        }
        return null;
    }

    public static List<Carta> cartasNoUsadas(List<Carta> mano) {
        List<List<Carta>> combinacionesPosibles = new ArrayList<>();
        combinacionesPosibles.addAll(encontrarTrio(mano));
        combinacionesPosibles.addAll(encontrarEscaleras(mano));

        List<Carta> cartasUsadas = null;
        // Buscamos la combinación más larga >= 3 cartas
        for (int objetivo = mano.size(); objetivo >= 3; objetivo--) {
            cartasUsadas = buscarCombinacion(combinacionesPosibles, objetivo, new ArrayList<>(), 0);
            if (cartasUsadas != null) {
                break;
            }
        }

        if (cartasUsadas != null) {
            List<Carta> cartasNoUsadas = new ArrayList<>(mano);
            cartasNoUsadas.removeAll(cartasUsadas);
            return cartasNoUsadas;
        }

        // Si no encontramos ningún trio ni escalera de ≥3 cartas
        return new ArrayList<>(mano);
    }

    public static int sumarCartasNoUsadas(List<Carta> mano) {
        List<Carta> noUsadas = cartasNoUsadas(mano);
        return noUsadas.stream().mapToInt(Carta::getNumero).sum();
    }
}
