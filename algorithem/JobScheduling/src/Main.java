import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 작업 스케줄링(JobScheduling)
 * 일자 : 2023.04.08
 * 입력 : t1,t2, ... , t3 (n개의 작업)
 * 교과서대로 tn = [s, f] (s : start, f : finish)
 * t1 = [7, 8], t2 = [3, 7],
 * t3 = [1, 5], t4 = [5, 9],
 * t5 = [0, 2], t6 = [6, 8],
 * t7 = [1, 6]
 * 출략 : 각 기계에 배졍된 작업 순서
 */

public class Main {

    public static void main(String[] args) {
        ArrayList<TaskTime> task = new ArrayList<>();
        task.add(createTask(1, 7, 8));
        task.add(createTask(2, 3, 7));
        task.add(createTask(3, 1, 5));
        task.add(createTask(4, 5, 9));
        task.add(createTask(5, 0, 2));
        task.add(createTask(6, 6, 8));
        task.add(createTask(7, 1, 6));

        Comparator<TaskTime> comp = new Comparator<TaskTime>() {
            @Override
            public int compare(TaskTime o1, TaskTime o2) {
                return o1.s - o2.s;
            }
        };

        Collections.sort(task, comp);

        ArrayList<MachineTime> machine = new ArrayList<MachineTime>();
        machinNowNum = 0;

        scheduling(task, machine);
        printingSchedul(task, machine);
    }

    static int machinNowNum;

    private static void scheduling(ArrayList<TaskTime> task, ArrayList<MachineTime> machine) {

        int pos = 0;
        MachineTime tmpM;
        while (pos <task.size()) {
            if (isMachine(machine, task.get(pos))) {
                machine.add(createMacine());
            }
            tmpM = runingMachine(machine, task.get(pos));
            tmpM.taskList.add(pos);     // 수래
            tmpM.firstRuning = task.get(pos).f;
            pos++;
        }
    }

    private static boolean isMachine(ArrayList<MachineTime> machine, TaskTime task) {
        for (int i = 0; i < machine.size(); i++) {
            if (machine.get(i).taskList.contains(task)) {
                return false;
            }
        }
        return true;
    }

    private static MachineTime runingMachine(ArrayList<MachineTime> machine, TaskTime task) {
        for (int i = 0; i < machine.size(); i++) {
            if (machine.get(i).firstRuning <= task.s) {
                return machine.get(i);
            }
        }
        return null;
    }

    private static TaskTime createTask(int taskNum, int s, int f) {
        TaskTime newTask = new TaskTime(taskNum, s, f);
        return newTask;
    }

    private static MachineTime createMacine() {
        machinNowNum++;
        MachineTime newMachine = new MachineTime(machinNowNum, 0);
        return newMachine;
    }

    private static void printingSchedul(ArrayList<TaskTime> task, ArrayList<MachineTime> mechine) {
        int pos = 0, paus = 0;
        MachineTime nowM;
        TaskTime nowT;

        nowM = mechine.get(pos);
        while (nowM.taskList.size()>0) {
            System.out.printf("machine %d : ", nowM.machineNum);

            for(int i = 0; i<nowM.taskList.size(); i++){
                nowT = task.get(nowM.taskList.get(i));
                System.out.printf("%d[%d %d] -> ",nowT.taskNum, nowT.s, nowT.f);
            }
            System.out.println("\b\b\b   ");

            System.out.print("|");
            for(int i = 0; i<nowM.taskList.size(); i++){
                nowT = task.get(nowM.taskList.get(i));

                for(int j = 0; j<nowT.s-paus;j++)
                    System.out.printf("_");
                for (int j = 0; j<nowT.f-nowT.s;j++)
                    System.out.printf("ㅁ");

                paus = nowT.f;
            }
            for(int i = 0; i<24-paus; i++){
                System.out.printf("_");
            }
            System.out.println("");

            paus = 0;
            pos++;
            nowM = mechine.get(pos);
        }
    }
}

class TaskTime {
    int s, f, taskNum;

    public TaskTime(int taskNum, int s, int f) {
        this.taskNum = taskNum;
        this.s = s;
        this.f = f;
    }
}

class MachineTime {
    int machineNum;
    int firstRuning;
    ArrayList<Integer> taskList = new ArrayList<>();

    public MachineTime(int machineNowNum, int firstRuning) {
        this.machineNum = machineNowNum;
        this.firstRuning = firstRuning;
    }
}