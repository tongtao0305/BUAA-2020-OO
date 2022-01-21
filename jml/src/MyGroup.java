import com.oocourse.spec3.main.Group;
import com.oocourse.spec3.main.Person;

import java.math.BigInteger;
import java.util.ArrayList;

public class MyGroup implements Group {

    private final Integer id;
    private final ArrayList<Person> people;

    private int relationSum;
    private int valueSum;

    public MyGroup(int id) {
        this.id = id;
        this.people = new ArrayList<>();
        relationSum = 0;
        valueSum = 0;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Group) {
            return (((Group) obj).getId() == id);
        } else {
            return false;
        }
    }

    @Override
    public void addPerson(Person person) {
        // 先更新 relationSum 和 valueSum，再添加
        for (Person p : people) {
            if (person.isLinked(p)) {
                relationSum += 2;
                valueSum += 2 * person.queryValue(p);
            }
        }
        relationSum++;

        people.add(person);
    }

    @Override
    public void delPerson(Person person) {
        people.remove(person);

        // 先删去，再更新 relationSum 和 valueSum
        for (Person p : people) {
            if (person.isLinked(p)) {
                relationSum -= 2;
                valueSum -= 2 * person.queryValue(p);
            }
        }
        relationSum--;
    }

    @Override
    public boolean hasPerson(Person person) {
        return people.contains(person);
    }

    @Override
    public int getRelationSum() {
        return relationSum;
    }

    @Override
    public int getValueSum() {
        return valueSum;
    }

    public void updateSum(Person p1, Person p2, int value) {
        relationSum += 2;
        valueSum += 2 * value;
    }

    @Override
    public BigInteger getConflictSum() {
        if (people.size() > 0) {
            BigInteger result = people.get(0).getCharacter();
            for (int i = 1; i < people.size(); i++) {
                result = result.xor(people.get(i).getCharacter());
            }
            return result;
        } else {
            return BigInteger.ZERO;
        }
    }

    @Override
    public int getAgeMean() {
        int result = 0;

        if (people.size() == 0) {
            return 0;
        }

        for (Person person : people) {
            result += person.getAge();
        }
        result = result / people.size();
        return result;
    }

    @Override
    public int getAgeVar() {
        int result = 0;
        int ageMean = getAgeMean();

        if (people.size() == 0) {
            return 0;
        }

        for (Person person : people) {
            result += (person.getAge() - ageMean) * (person.getAge() - ageMean);
        }
        result = result / people.size();
        return result;
    }

    public int getPeopleSum() {
        return people.size();
    }

    public ArrayList<Person> getPeople() {
        return people;
    }
}
