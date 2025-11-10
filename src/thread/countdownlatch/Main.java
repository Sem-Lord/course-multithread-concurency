package thread.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // 1. Initialisation : Le compteur est à 3 (on attend 3 tâches)
        final CountDownLatch latch = new CountDownLatch(3);

        System.out.println("Thread Main: Je lance 3 tâches de travail...");

        // Lancement des 3 threads de travail
        new Thread(new Worker(latch, "Tâche #1")).start();
        new Thread(new Worker(latch, "Tâche #2")).start();
        new Thread(new Worker(latch, "Tâche #3")).start();

        // 2. Attente : Le thread principal est bloqué ici
        // jusqu'à ce que le compteur du Latch atteigne zéro.
        latch.await();

        // 4. Libération : Cette ligne s'exécute seulement après
        // que les 3 Workers aient appelé countDown().
        System.out.println("\nThread Main: Toutes les tâches sont terminées. Je peux continuer.");
    }
}


class Worker implements Runnable {
    private final CountDownLatch latch;
    private final String taskName;

    public Worker(CountDownLatch latch, String taskName) {
        this.latch = latch;
        this.taskName = taskName;
    }

    @Override
    public void run() {
        try {
            System.out.println("-> " + taskName + " a commencé.");
            // Simulation d'un travail
            Thread.sleep((long) (Math.random() * 2000));

            System.out.println("<- " + taskName + " est terminée.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // 3. Décrémentation : Indique au Latch que cette tâche est finie.
            latch.countDown();
        }
    }
}