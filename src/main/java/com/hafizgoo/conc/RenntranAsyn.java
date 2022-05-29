package com.hafizgoo.conc;


import java.util.concurrent.locks.ReentrantLock;

public class RenntranAsyn {

        private static final ReentrantLock lock = new ReentrantLock(true);

        private static int sum() {
            return fibo(36);
        }

        private static int fibo(int a) {
            if (a < 2) {
                return 1;
            }
            return fibo(a - 1) + fibo(a - 2);
        }

        public static void main(String[] args) throws Exception {
            long start = System.currentTimeMillis();
            SumThread sumThread = new SumThread();
            sumThread.start();
            sumThread.sleep(1);
            lock.lock();
            try {
                System.out.println("***");
            } finally {
                lock.unlock();
            }

            int result = sumThread.getResult();
            System.out.println("异步计算结果：" + result);
            System.out.println("计算耗时：" + (System.currentTimeMillis() - start) + "  ms");
        }

        static class SumThread extends Thread {


            private Integer result;

            public Integer getResult() {
                return result;
            }

            @Override
            public void run() {
                lock.lock();
                try {
                    result = sum();
                } finally {
                    lock.unlock();
                }
            }
        }

    }
