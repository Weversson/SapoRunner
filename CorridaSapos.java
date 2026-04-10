import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class CorridaSapos {

    private static final int DISTANCIA = 50;
    private static final int PISTA = 35;
    private static final int W = 70;

    // Codigos ANSI
    private static final String R   = "\033[0m";
    private static final String N   = "\033[1m";
    private static final String DIM = "\033[2m";
    private static final String G   = "\033[90m";  // cinza
    private static final String WH  = "\033[97m";  // branco
    private static final String BG  = "\033[48;5;236m";
    private static final String DOURADO = "\033[38;5;220m";
    private static final String PRATA   = "\033[38;5;250m";
    private static final String BRONZE  = "\033[38;5;173m";

    private static final String[] C = {
        "\033[38;5;47m",   // verde
        "\033[38;5;220m",  // amarelo
        "\033[38;5;39m",   // azul
        "\033[38;5;206m",  // rosa
        "\033[38;5;87m",   // ciano
        "\033[38;5;203m"   // vermelho
    };

    private static final String[] CB = {
        "\033[48;5;22m", "\033[48;5;58m", "\033[48;5;24m",
        "\033[48;5;53m", "\033[48;5;23m", "\033[48;5;52m"
    };

    private static final String[] NOMES = {
        "Frederico", "Sapilda", "Perereca", "Cururu", "Jiraya", "Gamabunta"
    };

    private final List<Sapo> sapos = new ArrayList<>();
    private int ordemChegada = 0;
    private boolean corridaEncerrada = false;
    private long t0;

    public synchronized int registrarChegada(Sapo sapo) {
        ordemChegada++;
        if (ordemChegada == sapos.size()) corridaEncerrada = true;
        return ordemChegada;
    }

    public synchronized boolean isCorridaEncerrada() { return corridaEncerrada; }

    // ==================== UTILIDADES ====================

    private static String pad(char c, int n) {
        if (n <= 0) return "";
        char[] a = new char[n]; java.util.Arrays.fill(a, c); return new String(a);
    }

    private static String rep(String s, int n) {
        if (n <= 0) return "";
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < n; i++) b.append(s);
        return b.toString();
    }

    private void cls() { System.out.print("\033[2J\033[H"); System.out.flush(); }

    // Monta uma linha com borda, conteudo centralizado
    private String ctr(String txt, int visLen) {
        int sp = W - visLen;
        int l = sp / 2, r = sp - l;
        return G + " |" + R + pad(' ', l) + txt + pad(' ', r) + G + "| " + R;
    }

    // Linha com borda, conteudo a esquerda com indent
    private String esq(String txt, int visLen) {
        int r = W - visLen - 2;
        if (r < 0) r = 0;
        return G + " |" + R + "  " + txt + pad(' ', r) + G + "| " + R;
    }

    private String topo()  { return G + " +" + pad('=', W) + "+" + R; }
    private String base()  { return topo(); }
    private String sep()   { return G + " |" + pad('-', W) + "| " + R; }
    private String vazia() { return G + " |" + R + pad(' ', W) + G + "| " + R; }

    // ==================== TELA INICIAL ====================

    private void telaInicial() {
        cls();
        p("");
        p(topo());
        p(vazia());
        p(ctr(C[0] + N +  "     .---.     " + R, 15));
        p(ctr(C[0] + N +  "    / o o \\    " + R, 15));
        p(ctr(C[0] + N +  "   (  >~<  )   " + R, 15));
        p(ctr(C[0] + N +  "    \\'---'/    " + R, 15));
        p(ctr(C[0] + N +  "   /`-...-'\\   " + R, 15));
        p(ctr(C[0] + N +  "  (_/     \\_)  " + R, 15));
        p(vazia());
        p(ctr(N + WH + "~ C O R R I D A   D O S   S A P O S ~" + R, 39));
        p(vazia());
        p(ctr(DIM + "Cada sapo e uma thread independente correndo pela vitoria!" + R, 58));
        p(ctr(DIM + "Pulos aleatorios de 1-5m | Descanso de 100-500ms" + R, 48));
        p(vazia());
        p(sep());
        p(vazia());
    }

    // ==================== CONTAGEM REGRESSIVA ====================

    private void contagem() throws InterruptedException {
        String[][] n3 = {
            {"  ######  "},
            {"       ## "},
            {"  ######  "},
            {"       ## "},
            {"  ######  "},
        };
        String[][] n2 = {
            {"  ######  "},
            {"       ## "},
            {"  ######  "},
            {"  ##      "},
            {"  ######  "},
        };
        String[][] n1 = {
            {"      ##  "},
            {"      ##  "},
            {"      ##  "},
            {"      ##  "},
            {"      ##  "},
        };

        String[][][] nums = {n3, n2, n1};
        String[] cores = {C[5], C[3], C[0]};

        for (int i = 0; i < 3; i++) {
            cls();
            p("");
            p(topo());
            p(vazia());
            p(ctr(DIM + "Preparar..." + R, 11));
            p(vazia());
            for (int l = 0; l < 5; l++) {
                p(ctr(cores[i] + N + nums[i][l][0] + R, 10));
            }
            p(vazia());
            p(base());
            Thread.sleep(850);
        }

        // VAI
        cls();
        p("");
        p(topo());
        p(vazia());
        p(vazia());
        String[] vai = {
            "#   #   ###   ###   # # #",
            "#   #  #   #   #    # # #",
            "#   #  #####   #    # # #",
            " # #   #   #   #         ",
            "  #    #   #  ###   # # #",
        };
        for (String l : vai) {
            p(ctr(C[0] + N + l + R, l.length()));
        }
        p(vazia());
        p(vazia());
        p(base());
        Thread.sleep(650);
        cls();
    }

    // ==================== TELA DA CORRIDA ====================

    public void atualizarTela() {
        synchronized (this) {
            long s = (System.currentTimeMillis() - t0) / 1000;
            StringBuilder sb = new StringBuilder();
            sb.append("\033[H\n");

            sb.append(topo()).append('\n');
            sb.append(vazia()).append('\n');

            // Header
            String tit = N + WH + "CORRIDA DOS SAPOS" + R;
            String tmp = G + "Tempo " + WH + N + String.format("%02d:%02d", s/60, s%60) + R;
            int gap = W - 17 - 11 - 4;
            sb.append(G + " |" + R + "  " + tit + pad(' ', gap) + tmp + "  " + G + "| " + R + '\n');

            sb.append(sep()).append('\n');

            // Achar lider
            int maxPos = 0;
            for (Sapo sp : sapos) if (sp.getPosicao() > maxPos) maxPos = sp.getPosicao();

            for (int i = 0; i < sapos.size(); i++) {
                Sapo sapo = sapos.get(i);
                int pos = sapo.getPosicao();
                int blocos = (int)((pos / (double) DISTANCIA) * PISTA);
                blocos = Math.min(blocos, PISTA);
                int vaz = PISTA - blocos;
                int pct = Math.min((int)((pos / (double) DISTANCIA) * 100), 100);
                String cor = sapo.getCor();
                String corBg = CB[i % CB.length];

                sb.append(vazia()).append('\n');

                // Linha nome + info
                String nome = String.format("%-10s", sapo.getNomeSapo());
                String lider = (pos == maxPos && pos > 0 && !sapo.isChegou()) ? " <<" : "   ";
                String pulo = sapo.getUltimoPulo() > 0 ? "+" + sapo.getUltimoPulo() + "m" : "   ";

                StringBuilder ln = new StringBuilder();
                ln.append("  ");
                // Indicador de posicao (ranking visual)
                if (sapo.getColocacao() > 0) {
                    switch (sapo.getColocacao()) {
                        case 1: ln.append(DOURADO + N + " [1o] " + R); break;
                        case 2: ln.append(PRATA   + " [2o] " + R); break;
                        case 3: ln.append(BRONZE  + " [3o] " + R); break;
                        default: ln.append(DIM + " [" + sapo.getColocacao() + "o] " + R); break;
                    }
                } else {
                    ln.append("      ");
                }
                ln.append(cor + N + nome + R);

                // Barra de progresso com gradiente
                ln.append(G + " [" + R);
                if (blocos > 0) {
                    ln.append(cor + corBg + rep("=", blocos) + R);
                }
                if (vaz > 0) {
                    ln.append(BG + pad(' ', vaz) + R);
                }
                ln.append(G + "]" + R);

                // Info
                ln.append(cor + N + String.format(" %3d%%", pct) + R);
                ln.append(DIM + " " + pulo + R);

                // Lider
                if (pos == maxPos && pos > 0 && !sapo.isChegou()) {
                    ln.append(cor + N + " <<" + R);
                }

                // Calcular visLen: 2 + 6 + 10 + 1 + 1 + PISTA + 1 + 5 + 4 + (lider?3:0)
                int visLen = 2 + 6 + 10 + 1 + 1 + PISTA + 1 + 5 + 4;
                if (pos == maxPos && pos > 0 && !sapo.isChegou()) visLen += 3;
                int padding = W - visLen;
                if (padding > 0) ln.append(pad(' ', padding));

                sb.append(G + " |" + R).append(ln).append(G + "| " + R + '\n');
            }

            sb.append(vazia()).append('\n');

            // Regua
            StringBuilder rg = new StringBuilder();
            rg.append("                 ");
            rg.append("0");
            int meio = PISTA / 2 - 2;
            rg.append(pad(' ', meio));
            rg.append(DISTANCIA/2 + "m");
            rg.append(pad(' ', meio - 1));
            rg.append(DISTANCIA + "m");
            String rgStr = rg.toString();
            int rgPad = W - rgStr.length();
            if (rgPad < 0) rgPad = 0;
            sb.append(G + " |" + R + DIM + rgStr + R + pad(' ', rgPad) + G + "| " + R + '\n');

            sb.append(base()).append('\n');

            System.out.print(sb);
            System.out.flush();
        }
    }

    // ==================== PLACAR FINAL ====================

    private void placar() {
        List<Sapo> rk = new ArrayList<>(sapos);
        rk.sort(Comparator.comparingInt(Sapo::getColocacao));
        long tt = (System.currentTimeMillis() - t0) / 1000;

        Sapo v = rk.get(0);

        p("");
        p(topo());
        p(vazia());
        p(ctr(N + WH + "RESULTADO FINAL" + R, 15));
        p(vazia());
        p(sep());
        p(vazia());

        // Trofeu
        p(ctr(DOURADO + N + "        ___________        " + R, 27));
        p(ctr(DOURADO + N + "       '._==_==_=_.'       " + R, 27));
        p(ctr(DOURADO + N + "       .-\\:      /-.       " + R, 27));
        p(ctr(DOURADO + N + "      | (|:.     |) |      " + R, 27));
        p(ctr(DOURADO + N + "       '-|:.     |-'       " + R, 27));
        p(ctr(DOURADO + N + "         \\::.    /         " + R, 27));
        p(ctr(DOURADO + N + "          '::. .'          " + R, 27));
        p(ctr(DOURADO + N + "            ) (            " + R, 27));
        p(ctr(DOURADO + N + "          _.' '._          " + R, 27));
        p(ctr(DOURADO + N + "         '-------'         " + R, 27));
        p(vazia());

        String vMsg = v.getNomeSapo().toUpperCase() + " VENCEU!";
        p(ctr(v.getCor() + N + ">> " + vMsg + " <<" + R, vMsg.length() + 6));

        p(vazia());
        p(sep());
        p(vazia());

        // Podio
        if (rk.size() >= 3) {
            Sapo s1 = rk.get(0), s2 = rk.get(1), s3 = rk.get(2);
            String n1 = centerPad(s1.getNomeSapo(), 10);
            String n2 = centerPad(s2.getNomeSapo(), 10);
            String n3 = centerPad(s3.getNomeSapo(), 10);

            //                2o       1o       3o
            p(ctr("                " + DOURADO + N + n1 + R + "                          ", W));
            p(ctr("      " + PRATA + n2 + R + "  " + DOURADO + N + "+----------+" + R + "  " + BRONZE + n3 + R + "          ", W));
            p(ctr("  " + PRATA + "+----------+" + R + DOURADO + N + "|          |" + R + BRONZE + "+----------+" + R + "      ", W));
            p(ctr("  " + PRATA + "|    2o    |" + R + DOURADO + N + "|    1o    |" + R + BRONZE + "|    3o    |" + R + "      ", W));
            p(ctr("  " + PRATA + "+----------+" + R + DOURADO + N + "|          |" + R + BRONZE + "+----------+" + R + "      ", W));
            p(ctr("              " + DOURADO + N + "+----------+" + R + "                        ", W));
        }

        p(vazia());
        p(sep());
        p(vazia());

        // Tabela de resultados
        p(esq(N + WH + "  POS    NOME            DISTANCIA" + R, 34));
        p(esq(G + pad('-', 50) + R, 52));

        for (Sapo sapo : rk) {
            int pos = sapo.getColocacao();
            String cor = sapo.getCor();
            String corPos;
            switch (pos) {
                case 1: corPos = DOURADO + N; break;
                case 2: corPos = PRATA; break;
                case 3: corPos = BRONZE; break;
                default: corPos = DIM; break;
            }

            String posS = String.format("  %so", pos);
            String nomeS = String.format("%-14s", sapo.getNomeSapo());
            String distS = sapo.getPosicao() + "/" + DISTANCIA + "m";

            String linha = corPos + posS + R + "     " + cor + N + nomeS + R + "  " + DIM + distS + R;
            int visLen = posS.length() + 5 + 14 + 2 + distS.length();

            int padding = W - visLen - 2;
            if (padding < 0) padding = 0;

            p(G + " |" + R + "  " + linha + pad(' ', padding) + G + "| " + R);
        }

        p(vazia());
        p(sep());
        p(vazia());

        String tempo = "Tempo total: " + String.format("%02d:%02d", tt/60, tt%60);
        p(ctr(DIM + tempo + R, tempo.length()));

        p(vazia());
        p(base());
        p("");
    }

    private String centerPad(String s, int w) {
        if (s.length() >= w) return s.substring(0, w);
        int l = (w - s.length()) / 2;
        return pad(' ', l) + s + pad(' ', w - s.length() - l);
    }

    private void p(String s) { System.out.println(s); }

    // ==================== FLUXO PRINCIPAL ====================

    public void iniciar() {
        Scanner sc = new Scanner(System.in);
        boolean jogar = true;

        while (jogar) {
            sapos.clear();
            ordemChegada = 0;
            corridaEncerrada = false;

            telaInicial();

            int n = 0;
            while (n < 2 || n > 6) {
                System.out.print(G + " |" + R + "  Quantos sapos vao competir? " + DIM + "(2-6)" + R + ": ");
                try {
                    n = Integer.parseInt(sc.nextLine().trim());
                    if (n < 2 || n > 6) p(G + " |" + R + C[5] + "  Escolha entre 2 e 6!" + R);
                } catch (NumberFormatException e) {
                    p(G + " |" + R + C[5] + "  Numero invalido!" + R);
                }
            }

            p(vazia());
            System.out.print(G + " |" + R + "  Quer dar nomes aos sapos? " + DIM + "(s/n)" + R + ": ");
            String resp = sc.nextLine().trim().toLowerCase();
            boolean custom = resp.equals("s") || resp.equals("sim");

            if (custom) p(vazia());

            for (int i = 0; i < n; i++) {
                String nome;
                if (custom) {
                    System.out.print(G + " |" + R + "  " + C[i] + N + "Sapo " + (i+1) + R + ": ");
                    nome = sc.nextLine().trim();
                    if (nome.isEmpty()) nome = NOMES[i];
                    if (nome.length() > 10) nome = nome.substring(0, 10);
                } else {
                    nome = NOMES[i];
                }
                sapos.add(new Sapo(nome, C[i], DISTANCIA, this));
            }

            p(vazia());
            p(base());
            p("");
            p(DIM + "  Pressione ENTER para comecar a corrida..." + R);
            sc.nextLine();

            try { contagem(); } catch (InterruptedException e) { return; }

            t0 = System.currentTimeMillis();

            for (Sapo sapo : sapos) sapo.start();

            while (!isCorridaEncerrada()) {
                try { Thread.sleep(50); } catch (InterruptedException e) { break; }
            }
            for (Sapo sapo : sapos) {
                try { sapo.join(); } catch (InterruptedException e) { break; }
            }

            atualizarTela();
            placar();

            System.out.print("  Jogar novamente? " + DIM + "(s/n)" + R + ": ");
            String r = sc.nextLine().trim().toLowerCase();
            jogar = r.equals("s") || r.equals("sim");
        }

        cls();
        p("");
        p(topo());
        p(vazia());
        p(ctr(N + WH + "Valeu por jogar! Ate a proxima corrida!" + R, 38));
        p(vazia());
        p(base());
        p("");
        sc.close();
    }

    public static void main(String[] args) {
        new CorridaSapos().iniciar();
    }
}
