import com.oocourse.spec3.exceptions.EqualGroupIdException;
import com.oocourse.spec3.exceptions.EqualPersonIdException;
import com.oocourse.spec3.exceptions.EqualRelationException;
import com.oocourse.spec3.exceptions.GroupIdNotFoundException;
import com.oocourse.spec3.exceptions.PersonIdNotFoundException;
import com.oocourse.spec3.exceptions.RelationNotFoundException;
import com.oocourse.spec3.main.Group;
import com.oocourse.spec3.main.Network;
import com.oocourse.spec3.main.Person;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Stack;

public class MyNetwork implements Network {

    private final ArrayList<Person> persons;
    private final LinkedHashMap<Integer, Person> people;
    private final LinkedHashMap<Integer, Group> groups;
    private final LinkedHashMap<Integer, Integer> money;

    public MyNetwork() {
        this.persons = new ArrayList<>();
        this.people = new LinkedHashMap<>();
        this.groups = new LinkedHashMap<>();
        this.money = new LinkedHashMap<>();
    }

    @Override
    public boolean contains(int id) {
        return people.containsKey(id);
    }

    @Override
    public Person getPerson(int id) {
        return people.getOrDefault(id, null);
    }

    @Override
    public void addPerson(Person person) throws EqualPersonIdException {
        if (!people.containsKey(person.getId())) {
            persons.add(person);
            people.put(person.getId(), person);
            money.put(person.getId(), 0);
        } else {
            throw new EqualPersonIdException();
        }
    }

    @Override
    public void addRelation(int id1, int id2, int value)
            throws PersonIdNotFoundException, EqualRelationException {
        if (contains(id1) && contains(id2) && id1 != id2 &&
                !getPerson(id1).isLinked(getPerson(id2))) {
            Person person1 = getPerson(id1);
            Person person2 = getPerson(id2);
            ((MyPerson) person1).addAcquaintance(person2, value);
            ((MyPerson) person2).addAcquaintance(person1, value);
            // 更新含有这两个Person的Sum缓存
            for (Group group : groups.values()) {
                if (group.hasPerson(person1) && group.hasPerson(person2)) {
                    ((MyGroup) group).updateSum(person1, person2, value);
                }
            }
        } else if (!contains(id1) || !contains(id2) ||
                (getPerson(id1).isLinked(getPerson(id2)) && id1 != id2)) {
            if (!contains(id1) || !contains(id2)) {
                throw new PersonIdNotFoundException();
            } else if (contains(id1) && contains(id2) && getPerson(id1).isLinked(getPerson(id2))) {
                throw new EqualRelationException();
            }
        }
    }

    @Override
    public int queryValue(int id1, int id2)
            throws PersonIdNotFoundException, RelationNotFoundException {
        if (contains(id1) && contains(id2) && getPerson(id1).isLinked(getPerson(id2))) {
            return getPerson(id1).queryValue(getPerson(id2));
        } else if (!contains(id1) || !contains(id2)) {
            throw new PersonIdNotFoundException();
        } else if (contains(id1) && contains(id2) && !getPerson(id1).isLinked(getPerson(id2))) {
            throw new RelationNotFoundException();
        }
        return 0;
    }

    @Override
    public BigInteger queryConflict(int id1, int id2) throws PersonIdNotFoundException {
        if (contains(id1) && contains(id2)) {
            return getPerson(id1).getCharacter().xor(getPerson(id2).getCharacter());
        } else {
            throw new PersonIdNotFoundException();
        }
    }

    @Override
    public int queryAcquaintanceSum(int id) throws PersonIdNotFoundException {
        if (contains(id)) {
            return getPerson(id).getAcquaintanceSum();
        } else {
            throw new PersonIdNotFoundException();
        }
    }

    @Override
    public int compareAge(int id1, int id2) throws PersonIdNotFoundException {
        if (contains(id1) && contains(id2)) {
            return getPerson(id1).getAge() - getPerson(id2).getAge();
        } else {
            throw new PersonIdNotFoundException();
        }
    }

    @Override
    public int compareName(int id1, int id2) throws PersonIdNotFoundException {
        if (contains(id1) && contains(id2)) {
            return getPerson(id1).getName().compareTo(getPerson(id2).getName());
        } else {
            throw new PersonIdNotFoundException();
        }
    }

    @Override
    public int queryPeopleSum() {
        return people.size();
    }

    @Override
    public int queryNameRank(int id) throws PersonIdNotFoundException {
        int count = 1;
        if (contains(id)) {
            for (Person person : people.values()) {
                if (compareName(id, person.getId()) > 0) {
                    count++;
                }
            }
            return count;
        } else {
            throw new PersonIdNotFoundException();
        }
    }

    @Override
    public boolean isCircle(int id1, int id2) throws PersonIdNotFoundException {
        if (contains(id1) && contains(id2)) {
            HashSet<Integer> friends = ((MyPerson) getPerson(id1)).getFriends();
            return friends.contains(id2);
        } else {
            throw new PersonIdNotFoundException();
        }
    }

    @Override
    public void addGroup(Group group) throws EqualGroupIdException {
        if (!groups.containsKey(group.getId())) {
            groups.put(group.getId(), group);
        } else {
            throw new EqualGroupIdException();
        }
    }

    @Override
    public Group getGroup(int id) {
        return groups.getOrDefault(id, null);
    }

    @Override
    public void addtoGroup(int id1, int id2) throws GroupIdNotFoundException,
            PersonIdNotFoundException, EqualPersonIdException {
        if (people.containsKey(id1) && groups.containsKey(id2) &&
                !getGroup(id2).hasPerson(getPerson(id1)) &&
                ((MyGroup) getGroup(id2)).getPeopleSum() < 1111) {
            getGroup(id2).addPerson(getPerson(id1));
        } else if (!groups.containsKey(id2)) {
            throw new GroupIdNotFoundException();
        } else if (groups.containsKey(id2) && !people.containsKey(id1)) {
            throw new PersonIdNotFoundException();
        } else if (groups.containsKey(id2) && people.containsKey(id1) &&
                getGroup(id2).hasPerson(getPerson(id1))) {
            throw new EqualPersonIdException();
        }
    }

    @Override
    public int queryGroupSum() {
        return groups.size();
    }

    @Override
    public int queryGroupPeopleSum(int id) throws GroupIdNotFoundException {
        if (groups.containsKey(id)) {
            return ((MyGroup) getGroup(id)).getPeopleSum();
        } else {
            throw new GroupIdNotFoundException();
        }
    }

    @Override
    public int queryGroupRelationSum(int id) throws GroupIdNotFoundException {
        if (groups.containsKey(id)) {
            return getGroup(id).getRelationSum();
        } else {
            throw new GroupIdNotFoundException();
        }
    }

    @Override
    public int queryGroupValueSum(int id) throws GroupIdNotFoundException {
        if (groups.containsKey(id)) {
            return getGroup(id).getValueSum();
        } else {
            throw new GroupIdNotFoundException();
        }
    }

    @Override
    public BigInteger queryGroupConflictSum(int id) throws GroupIdNotFoundException {
        if (groups.containsKey(id)) {
            return getGroup(id).getConflictSum();
        } else {
            throw new GroupIdNotFoundException();
        }
    }

    @Override
    public int queryGroupAgeMean(int id) throws GroupIdNotFoundException {
        if (groups.containsKey(id)) {
            return getGroup(id).getAgeMean();
        } else {
            throw new GroupIdNotFoundException();
        }
    }

    @Override
    public int queryGroupAgeVar(int id) throws GroupIdNotFoundException {
        if (groups.containsKey(id)) {
            return getGroup(id).getAgeVar();
        } else {
            throw new GroupIdNotFoundException();
        }
    }

    @Override
    public int queryAgeSum(int l, int r) {
        int count = 0;
        for (Person person : people.values()) {
            if (l <= person.getAge() && person.getAge() <= r) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void delFromGroup(int id1, int id2) throws GroupIdNotFoundException,
            PersonIdNotFoundException, EqualPersonIdException {
        if (people.containsKey(id1) && groups.containsKey(id2) &&
                getGroup(id2).hasPerson(getPerson(id1))) {
            getGroup(id2).delPerson(getPerson(id1));
        } else if (!groups.containsKey(id2)) {
            throw new GroupIdNotFoundException();
        } else if (groups.containsKey(id2) && !people.containsKey(id1)) {
            throw new PersonIdNotFoundException();
        } else if (people.containsKey(id1) && groups.containsKey(id2) &&
                !getGroup(id2).hasPerson(getPerson(id1))) {
            throw new EqualPersonIdException();
        }
    }

    @Override
    public int queryMinPath(int id1, int id2) throws PersonIdNotFoundException {
        if (contains(id1) && contains(id2) && id1 == id2) {
            return 0;
        } else if (contains(id1) && contains(id2) && id1 != id2 && !isCircle(id1, id2)) {
            return -1;
        } else if (contains(id1) && contains(id2) && id1 != id2 && isCircle(id1, id2)) {
            return ((MyPerson) getPerson(id1)).queryMinPath(getPerson(id2));
        } else {
            throw new PersonIdNotFoundException();
        }
    }

    @Override
    public boolean queryStrongLinked(int id1, int id2) throws PersonIdNotFoundException {
        if (contains(id1) && contains(id2) && id1 == id2) {
            return true;
        } else if (contains(id1) && contains(id2) && id1 != id2) {
            Tarjan(id1);
            for (HashSet<Integer> hashSet : components) {
                if (hashSet.contains(id1) && hashSet.contains(id2)) {
                    return true;
                }
            }
            return false;
        } else if (!contains(id1) || !contains(id2)) {
            throw new PersonIdNotFoundException();
        }
        return false;
    }

    private int time;
    private Stack<Integer> stack;
    private HashMap<Integer, Integer> dfn;
    private HashMap<Integer, Integer> low;
    private ArrayList<HashSet<Integer>> components;

    public void Tarjan(int id1) {
        time = 1;
        stack = new Stack<>();
        dfn = new HashMap<>();
        low = new HashMap<>();
        components = new ArrayList<>();
        for (Person person : people.values()) {
            dfn.put(person.getId(), 0);
            low.put(person.getId(), 0);
        }

        dfsTarjan(id1, id1);
    }

    public void dfsTarjan(int curPerson, int father) {
        stack.push(curPerson);
        dfn.put(curPerson, time);
        low.put(curPerson, time);
        time++;

        Collection<Person> acquaintance = ((MyPerson) getPerson(curPerson)).getAcquaintance();
        for (Person person : acquaintance) {
            if (person.getId() == father || person.getId() == curPerson) {
                continue;
            }
            int tempID = person.getId();
            if (dfn.get(tempID) == 0) {
                dfsTarjan(tempID, curPerson);
                low.put(curPerson, Integer.min(low.get(curPerson), low.get(tempID)));
                if (dfn.get(curPerson) <= low.get(tempID)) {
                    HashSet<Integer> connectedComponent = new HashSet<>();
                    Integer temp;
                    do {
                        temp = stack.pop();
                        connectedComponent.add(temp);
                    } while (temp != tempID);
                    connectedComponent.add(stack.peek());
                    if (connectedComponent.size() > 2) {
                        components.add(connectedComponent);
                    }
                }
            } else if (stack.contains(person.getId())) {
                low.put(curPerson, Integer.min(low.get(curPerson), low.get(tempID)));
            }
        }
    }

    @Override
    public int queryBlockSum() {
        int count = 0;
        ArrayList<Person> linked = new ArrayList<>(people.values());

        while (!linked.isEmpty()) {
            count++;
            Person person = linked.get(0);
            linked.remove(person);
            dfs(linked, person);
        }

        return count;
    }

    private void dfs(ArrayList<Person> linked, Person curPerson) {
        for (Person person : ((MyPerson) curPerson).getAcquaintance()) {
            if (linked.contains(person)) {
                linked.remove(person);
                dfs(linked, person);
            }
        }
    }

    @Override
    public void borrowFrom(int id1, int id2, int value) throws PersonIdNotFoundException,
            EqualPersonIdException {
        if (contains(id1) && contains(id2) && id1 != id2) {
            money.put(id1, money.get(id1) - value);
            money.put(id2, money.get(id2) + value);
        } else if (!contains(id1) || !contains(id2)) {
            throw new PersonIdNotFoundException();
        } else if (contains(id1) && id1 == id2) {
            throw new EqualPersonIdException();
        }
    }

    @Override
    public int queryMoney(int id) throws PersonIdNotFoundException {
        if (contains(id)) {
            return money.get(id);
        } else {
            throw new PersonIdNotFoundException();
        }
    }
}
