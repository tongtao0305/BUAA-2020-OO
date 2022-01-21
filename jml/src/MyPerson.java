import com.oocourse.spec3.main.Person;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class MyPerson implements Person {

    private final int id;
    private final String name;
    private final BigInteger character;
    private final int age;

    //private final ArrayList<Person> acquaintance;
    //private final ArrayList<Integer> value;

    private final LinkedHashMap<Integer, Person> acquaintance;
    private final LinkedHashMap<Integer, Integer> value;

    public MyPerson(int id, String name, BigInteger character, int age) {
        this.id = id;
        this.name = name;
        this.character = character;
        this.age = age;

        acquaintance = new LinkedHashMap<>();
        value = new LinkedHashMap<>();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public BigInteger getCharacter() {
        return this.character;
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person) {
            return ((Person) obj).getId() == this.id;
        } else {
            return false;
        }
    }

    @Override
    public boolean isLinked(Person person) {
        return (acquaintance.containsKey(person.getId()) || person.getId() == this.id);
    }

    @Override
    public int queryValue(Person person) {
        if (acquaintance.containsKey(person.getId())) {
            return value.get(person.getId());
        } else {
            return 0;
        }
    }

    @Override
    public int getAcquaintanceSum() {
        return acquaintance.size();
    }

    @Override
    public int compareTo(Person p2) {
        return this.name.compareTo(p2.getName());
    }

    public void addAcquaintance(Person person, int value) {
        this.acquaintance.put(person.getId(), person);
        this.value.put(person.getId(), value);
    }

    public Collection<Person> getAcquaintance() {
        return this.acquaintance.values();
    }

    public HashSet<Integer> getFriends() {

        HashSet<Integer> friends = new HashSet<>();
        Queue<Person> queue = new LinkedList<>();

        friends.add(this.id);
        queue.offer(this);

        while (!queue.isEmpty()) {
            MyPerson person = (MyPerson) queue.poll();
            for (Person p : person.getAcquaintance()) {
                if (!friends.contains(p.getId())) {
                    friends.add(p.getId());
                    queue.offer(p);
                }
            }
        }

        return friends;
    }

    static class Edge implements Comparable<Edge> {
        private Person person;
        private int value;

        Edge(Person person, int value) {
            this.person = person;
            this.value = value;
        }

        @Override
        public int compareTo(Edge o) {
            return this.value - o.value;
        }
    }

    public int queryMinPath(Person destination) {
        HashSet<Person> vis = new HashSet<>();
        HashMap<Person, Integer> dis = new HashMap<>();
        PriorityQueue<Edge> queue = new PriorityQueue<>();

        // 添加起点并首次更新dis
        dis.put(this, 0);
        queue.add(new Edge(this, 0));

        while (!vis.contains(destination)) {
            // 找出未访问过的节点中距离最近的一个
            Edge edge = queue.poll();
            if (edge == null) {
                break;
            }
            Person temp = edge.person;
            if (vis.contains(temp) || dis.get(temp) < edge.value) {
                continue;
            }

            // 将此人加入访问过的名单，并且更新dis
            vis.add(temp);
            for (Person person : ((MyPerson) temp).getAcquaintance()) {
                if (!vis.contains(person) && (!dis.containsKey(person) ||
                        edge.value + temp.queryValue(person) < dis.get(person))) {
                    dis.put(person, edge.value + temp.queryValue(person));
                    queue.add(new Edge(person, edge.value + temp.queryValue(person)));
                }
            }
        }

        return dis.get(destination);
    }

    private HashSet<Person> linked;
    private Boolean strongLinked;

    public boolean queryStrongLinked(Person destination) {
        // 初始化全局变量
        linked = new HashSet<>();
        //linked.add(this);
        strongLinked = false;
        dfs(this, destination);
        return strongLinked;
    }

    private void dfs(Person curPerson, Person destination) {
        // 判断返回条件是否成立：存在一条包含起点和终点的环路
        if (linked.size() != 0 && curPerson == this) {
            // 若起点和终点为直连，则直接返回
            if (linked.contains(destination) && linked.size() > 2) {
                strongLinked = true;
            }
            return;
        }
        for (Person person : ((MyPerson) curPerson).getAcquaintance()) {
            if (!linked.contains(person) || person == this) {
                linked.add(person);
                dfs(person, destination);
                linked.remove(person);
            }
            if (strongLinked) {
                break;
            }
        }
    }
}
