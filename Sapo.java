import java.util.Random;

public class Sapo extends Thread {

    private final String nome;
    private final String cor;
    private final int distanciaTotal;
    private final CorridaSapos corrida;
    private final Random random = new Random();
    private volatile int posicao = 0;
    private volatile int ultimoPulo = 0;
    private volatile int colocacao = 0;
    private volatile boolean chegou = false;

    public Sapo(String nome, String cor, int distanciaTotal, CorridaSapos corrida) {
        this.nome = nome;
        this.cor = cor;
        this.distanciaTotal = distanciaTotal;
        this.corrida = corrida;
    }

    @Override
    public void run() {
        while (posicao < distanciaTotal) {
            int pulo = random.nextInt(5) + 1;
            ultimoPulo = pulo;
            posicao = Math.min(posicao + pulo, distanciaTotal);

            corrida.atualizarTela();

            if (posicao >= distanciaTotal) {
                chegou = true;
                colocacao = corrida.registrarChegada(this);
                break;
            }

            try {
                int descanso = random.nextInt(401) + 100;
                Thread.sleep(descanso);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public String getNomeSapo() { return nome; }
    public String getCor() { return cor; }
    public int getPosicao() { return posicao; }
    public int getDistanciaTotal() { return distanciaTotal; }
    public int getColocacao() { return colocacao; }
    public int getUltimoPulo() { return ultimoPulo; }
    public boolean isChegou() { return chegou; }
}
