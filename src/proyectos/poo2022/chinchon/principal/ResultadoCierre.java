package proyectos.poo2022.chinchon.principal;

public class ResultadoCierre {
    public final boolean esCerrable;
    public final Carta cartaCorte;
    public final Mano mano;

    public ResultadoCierre(boolean esCerrable, Carta cartaCorte, Mano mano) {
        this.esCerrable = esCerrable;
        this.cartaCorte = cartaCorte;
        this.mano = mano;
    }

}
