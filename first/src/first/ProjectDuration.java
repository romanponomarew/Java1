package first;
import java.io.*;
import java.util.*;

public class ProjectDuration {


	private static final String FILE_NAME = "X14_6.RCP"; 
	
	private static void showResult(int task, int count_res, int days_project, int counter)
	{
		System.out.println();
        System.out.println("����� ����� � �������: " + task + "\n����� �������� ��������: " + count_res);
        System.out.println("�������� ������������ ������� �����: " + days_project + " ����/���/����.");
        System.out.println("���������� ������������ ����� �� ��� ����� ����������� �������: " + counter);
	}
	
	public static void main(String[] args) throws IOException {
        int task, count_res, task_time;
        int counter = 0; // ������� ��������� �����, ����� ������� �������� �� ������������� ����������
        int days_project = 0; // �������� ������������ �������
        ArrayList<Integer> time = new ArrayList<Integer>(); //���� � ��������� ��������
        ArrayList<Integer> res = new ArrayList<Integer>(); //���� � ������������ ���������
        ArrayList<ArrayList<Integer>> testArr1 = new ArrayList<ArrayList<Integer>>();
        Queue<Integer> queue = new LinkedList<>(); // ������� �����(������������������)
        Queue<Integer> tasks = new LinkedList<>(); // ���������� ������������������ �����
        BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
        String test_string = br.readLine(); // ����������� ��������� �� ����� ������ � "�������" ���
        Scanner in = new Scanner(test_string); // ������ ������ � ������� �������
        task = in.nextInt(); // ���������� ������� ����� = ���������� �����
        count_res = in.nextInt(); // ���������� ���������� ������� ��������
        test_string = br.readLine(); // ������ ��������� ������
        in = new Scanner(test_string); // ������ ������ � ������� �������
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
        in.close(); // ���������, ��� ��� ������ �� ����������
        br.close(); // ��������� ����
        int followers = testArr1.get(0).get(5); //���������� �������������� ������
        for (int i = 0; i < followers; i++) {
            queue.add(testArr1.get(0).get(6 + i)); // ��������� ������� ��� ���������� ����������� ������������������ �����: 7+0, ����� 7+1, ����� 7+2, �� ����� ����� �����������
            tasks.add(testArr1.get(0).get(6 + i)); // ����� ��������� ������ �����, ��� 7+0, ����� 7+1, ����� 7+2 � ��, ��� ����� �����������
        }
        while (!queue.isEmpty()) { // ���� ������� �� ����� ������
            // ������������ ������� ������������������
			int removed = (queue.remove()) - 1; // ������� ������ �� �������, ������������ ������ ��
			followers = testArr1.get(removed).get(5); // ���������� ������, ��������������� �����
			for (int i = 0; i < followers; i++) { // ��������� ����������� ������� ����� � ������ �����
				if (!tasks.contains(testArr1.get(removed).get(6 + i))) { // ��� ����, ����� �� �������� ��� ������������
					queue.add(testArr1.get(removed).get(6 + i));         // ��������� � �������
					tasks.add(testArr1.get(removed).get(6 + i));         // ��������� � ������� ������������������
				}
			}
        }
        Map<Integer, ArrayList<Integer>> map = new HashMap<>(); // ������� �����(�������): ������ - ���������� �� ���
        for (int i = 1; i < testArr1.size() + 1; i++) map.put(i, testArr1.get(i - 1)); // ��������� ����� ������� ����, �������� - ��� ���� ��� ����� ������, � �������� - ��� ����� ������������ �������
       
        ArrayList<Integer> arr = map.get(tasks.remove()); // �������� �� ������� ������� ������
        task_time = arr.get(0); // ������������ ���������� ������
        for(int y = 0; y < count_res; y++) time.set(y, res.get(y) - arr.get(y + 1));
        days_project += task_time; // ��������� � ����� ������������ ������� ������������ ������
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
                System.out.println("�� ������� �������� -  ��������� ������� ������");
                counter++;
                days_project += task_time;
                for(int y = 0; y < count_res; y++) time.set(y, res.get(y) - arr.get(y + 1));
                prev_time = task_time;
            }
        }
        showResult(task, count_res, days_project, counter);

    }
}
