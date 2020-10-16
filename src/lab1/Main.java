package lab1;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int threadsNumber = 10;
        ArrayList<Thread> threads = new ArrayList<>();
        for(int i = 0; i<threadsNumber; i++){
            threads.add(new Thread(new HelloRunnable(i)));
            threads.get(i).start();
        }

        for(int i = 0; i<threadsNumber; i++){
            try {
                threads.get(i).join();
            } catch(InterruptedException e){
                System.out.println("err");
            }

        }
    }
}

// oceniany jest model rozwiązania problemu w zadaniu z Erlanga
// Semafory (różnica pomiędzy teoretyczną a praktyczną definincją semafora, 2 istotne róznice)
// Szczegóły na temat funkcji w Javie
// Wyścig - implementacja współbieznego dostepu do
// danych wielu wątków, bez ochrony danych: błędne działanie
// programów współbieżnych
// Spróbowac pętlę na wątku (10 wątków ale dużo operacji)
