package first;
import java.io.*;
import java.util.*;

public class ProjectDuration {


	private static final String FILE_NAME = "X14_6.RCP"; 
	
	private static void showResult(int task, int count_res, int days_project, int counter)
	{
		System.out.println();
        System.out.println("Всего задач в проекте: " + task + "\nВсего доступно ресурсов: " + count_res);
        System.out.println("Итоговая длительность проекта равна: " + days_project + " день/дня/дней.");
        System.out.println("Количество перенесенных задач за все время составления проекта: " + counter);
	}
	
	public static void main(String[] args) throws IOException {
        int task, count_res, task_time;
        int counter = 0; // счётчик переносов задач, чтобы хватало ресурсов на одновременное выполнение
        int days_project = 0; // Итоговая длительность проекта
        ArrayList<Integer> time = new ArrayList<Integer>(); //лист с остатками ресурсов
        ArrayList<Integer> res = new ArrayList<Integer>(); //лист с изначальными ресурсами
        ArrayList<ArrayList<Integer>> testArr1 = new ArrayList<ArrayList<Integer>>();
        Queue<Integer> queue = new LinkedList<>(); // очередь задач(последовательность)
        Queue<Integer> tasks = new LinkedList<>(); // получаемая последовательность задач
        BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
        String test_string = br.readLine(); // конвертация считанной из файла строки в "рабочий" вид
        Scanner in = new Scanner(test_string); // чтение строки с помощью сканера
        task = in.nextInt(); // считывание первого числа = количество задач
        count_res = in.nextInt(); // считывание количества рабочих ресурсов
        test_string = br.readLine(); // чтение следующей строки
        in = new Scanner(test_string); // чтение строки с помощью сканера
        for(int y = 0; y < count_res; y++){ 
        	res.add(in.nextInt());
        	time.add(0);
        } 
        for (int i = 0; i < task; i++) {
            test_string = br.readLine();
            in = new Scanner(test_string);
            ArrayList<Integer> arr1 = new ArrayList<Integer>();
            while (in.hasNextInt()) arr1.add(in.nextInt());
            testArr1.add(arr1);  
        }
        in.close(); // закрываем, так как больше не используем
        br.close(); // закрываем файл
        int followers = testArr1.get(0).get(5); //Количество последователей задачи
        for (int i = 0; i < followers; i++) {
            queue.add(testArr1.get(0).get(6 + i)); // заполняем очередь для дальнешего составления последовательности задач: 7+0, затем 7+1, потом 7+2, из этого будет извлекаться
            tasks.add(testArr1.get(0).get(6 + i)); // также заполняем список задач, мол 7+0, затем 7+1, потом 7+2 и всё, эта будет заполняться
        }
        while (!queue.isEmpty()) { // пока очередь не будет пустой
            // формирование рабочей последовательности
			int removed = (queue.remove()) - 1; // выносим первую из очереди, одновременно удаляя ее
			followers = testArr1.get(removed).get(5); // составляем строку, соответствующую числу
			for (int i = 0; i < followers; i++) { // повторяем составление очереди задач и список задач
				if (!tasks.contains(testArr1.get(removed).get(6 + i))) { // для того, чтобы не добавить уже существующие
					queue.add(testArr1.get(removed).get(6 + i));         // добавляем в очередь
					tasks.add(testArr1.get(removed).get(6 + i));         // добавляем в рабочую последовательность
				}
			}
        }
        Map<Integer, ArrayList<Integer>> map = new HashMap<>(); // создаем карту(словарь): задача - информация по ней
        for (int i = 1; i < testArr1.size() + 1; i++) map.put(i, testArr1.get(i - 1)); // заполняем карту методом Ключ, значение - где ключ это номер задачи, а значения - это ранее составленные массивы
       
        ArrayList<Integer> arr = map.get(tasks.remove()); // получаем из цепочки рабочий массив
        task_time = arr.get(0); // длительность конкретной задачи
        for(int y = 0; y < count_res; y++) time.set(y, res.get(y) - arr.get(y + 1));
        days_project += task_time; // добавляем в общую длительность проекта длительность задачи
        int prev_time = task_time;
        while (!tasks.isEmpty()){
            arr = map.get(tasks.remove());
            task_time = arr.get(0);
            for(int y = 0; y < count_res; y++) time.set(y, time.get(y) - arr.get(y + 1));        
            if (task_time - prev_time > 0) {
                days_project += (task_time - prev_time);
                prev_time = task_time;
            }
            if (time.get(0) <= 0 || time.get(1) <= 0 || time.get(2) <= 0 || time.get(3) <= 0) {
                System.out.println("Не хватает ресурсов -  переносим текущую задачу");
                counter++;
                days_project += task_time;
                for(int y = 0; y < count_res; y++) time.set(y, res.get(y) - arr.get(y + 1));
                prev_time = task_time;
            }
        }
        showResult(task, count_res, days_project, counter);

    }
}
