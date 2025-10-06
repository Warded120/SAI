package com.ivan.sai.lab3;

public class InferenceEngine {
        private final KnowledgeBase kb;

        public InferenceEngine(KnowledgeBase kb) {
            this.kb = kb;
        }

        /**
         * Простий цикл прямого виведення: поки є застосовні правила, застосовуємо їх.
         * Конфлікт вирішується просто: правила виконуються в порядку додавання (first-match).
         */
        public void run() {
            boolean fired;
            int iteration = 0;
            do {
                fired = false;
                iteration++;
                for (Rule r : kb.getRules()) {
                    if (r.condition.test(kb.getFacts())) {
                        int before = kb.getFacts().size();
                        r.action.accept(kb);
                        int after = kb.getFacts().size();

                        if (after > before) {
                            fired = true;
                            System.out.println("[Iteration " + iteration + "] Fired: " + r);
                        }
                    }
                }
            } while (fired);
        }
    }