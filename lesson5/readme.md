Ответить на вопрос о том какой сборщик мусора лучше однозначно нельзя. 
SerialGC подходит для систем с ограниченными ресурсами.
ParallelGC работает быстрее SerialGC. Оба вышеуказаннх сборщика при запуске 
приводят к ситуации Stop The World. ParallelGC кроме того работает так, что Old Generation фрагментируется, 
так как каждый поток пишет в отдельную область памяти Old Generation, но данный момент не критичен.
Conccurrent GC, а именно, CMS и G1 делают паузы Stop The World более погнозируемыми, возможно настройка по требуемым параметрам.
Но пропускная способность снижается, плюс может потребоваться больше ресурсов памяти.
С учетом того, что CMS помечен как "deprecated" с версии 9.0, реальный выбор чаще всего будет 
делаться между ParallelGC и G1 и определяющим фактором скорее всего будет требование к максимальной
задежке времени отклика приложения.

Результаты сбора статистики
Количество отработанных циклов одинаково для все видов GC. Быстрее всего программа отработала с G1.
Сборка мусора c G1 заняла меньше всего времени. Разница в работе SerialGC и ParallelGC на
тестовой программе не ощутима. Хуже всего себя показал в CMS, плюс виртуальная машина выдает 
сообщение при запуске, что CMS помечен как "deprecated"

Но надо понимать, что на полученные резельтаты повлиял сам факт мониторинга работы GC.