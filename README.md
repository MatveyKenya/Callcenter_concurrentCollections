Задача 1. Колл-центр
---

###### _Описание_

---
Мы начали заниматься организацией техподдержки у одного из провайдеров города. К нам поступают звонки от разных абонентов, а мы, силами нескольких специалистов, эти звонки "разбираем"

---

###### _Работа программы_

Создание и запуск потоков-специалистов и один поток-АТС (генерирует звонки)
Поток-АТС после запуска начинает генерировать несколько (например, 60) "звонков" раз в 1 секунду
Потоки-специалисты берут доступные звонки в работу
Методом Thread.sleep() нужно реализовать эмуляцию работы специалиста над вопросом (3-4 секунды, например)
Главный поток (main) ждет конца выполнения всех потоков

---

###### _Требования к программе_

Никаких блокировок. Вся работа должна основываться на concurrent-коллекциях

Все константы должны быть оформлены как константы (никаких "Магических чисел")