package ru.matveykenya;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    final static int TIMEOUT_ATC = 1000; // интервал поступления звонков
    final static int TIMEOUT_SPEC = 4000; // время обработки звонка специалистом
    final static int COUNT_CALLS = 21; // всего звонков должно поступить
    final static int CAPACITY_CALL_CENTER = 10;  // максимум ожидающих звонков на линии
    static boolean isCalls = true;

    /**
     * Для данного примера нужна однонаправленная простая очередь
     * Возможно использование очереди ConcurrentLinkedQueue или ArrayBlockingQueue
     * примерно все работает одинаково на данном примере, но
     * ArrayBlockingQueue имеет лимит по числу ожидающих звонков, которое нужно знать заранее
     * а ConcurrentLinkedQueue к тому же из обеспечивает быструю неблокирующую работу (при большей нагрузке)
     * поэтому остановился на ConcurrentLinkedQueue
     * Для реальной нагруженной задачи можно было бы провести тест и выявить лидера.
     */
    static final Queue<Integer> list = new ConcurrentLinkedQueue<>();
    //static final Queue<Integer> list = new ArrayBlockingQueue<>(CAPACITY_CALL_CENTER);
    //static final Queue<Integer> list = new ArrayBlockingQueue<>(CAPACITY_CALL_CENTER, true);


    public static void main(String[] args) {

        Thread atc = new Thread(Main::atc, "АТС");
        Thread specialist_1 = new Thread(Main::specialist, "Специалист_1");
        Thread specialist_2 = new Thread(Main::specialist, "Специалист_2");
        Thread specialist_3 = new Thread(Main::specialist, "Специалист_3");

        atc.start();
        specialist_1.start();
        specialist_2.start();
        specialist_3.start();
    }

    private static void atc(){
        for (int i = 1; i <= COUNT_CALLS; i++) {

            if (list.offer(i)) {
                System.out.println("Поступил новый звонок №" + i);
                System.out.println("Всего звонков в очереди --- " + list);
            } else {
                System.out.println("Линия перегружена. Отклонен Звонок №" + i);
            }

            try {
                Thread.sleep(TIMEOUT_ATC);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("*** Больше нет звонков ***");
        isCalls = false;
    }

    private static void specialist(){
        int countCalls = 0;
        Integer call;
        while ((call = list.poll()) != null || isCalls) {
            if (call != null) {
                System.out.println(Thread.currentThread().getName() + " - беру обработку звонка №" + call);
                countCalls++;
                try {
                    Thread.sleep(TIMEOUT_SPEC);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(Thread.currentThread().getName() + " обработал " + countCalls + " звонка и *** Пошел домой ***");
    }
}
